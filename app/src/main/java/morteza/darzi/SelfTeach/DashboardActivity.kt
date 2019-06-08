package morteza.darzi.SelfTeach

import BL.BookRepository
import BL.TermRepository
import DAL.AppDatabase
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.coroutines.launch


class DashboardActivity : ScopedAppActivity(), TermEditFragment.OnFragmentInteractionListener
        ,BooksListFragment.OnFragmentInteractionListener,ReadsFragment.OnFragmentInteractionListener,
        PerformanceFragment.OnFragmentInteractionListener,TermFirstFragment.OnFragmentInteractionListener ,
BooksFirstFragment.OnFragmentInteractionListener{
    override fun completeBooksFirst() {
        intializeSuspend()
    }

    lateinit var termRep : TermRepository
    lateinit var bookRepo : BookRepository
    lateinit var bottomNavigation: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        MyExceptionHandler(this)//comment this line if use debugger

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        val database = AppDatabase.getInstance(applicationContext)
        termRep = TermRepository(database.termDao())
        bookRepo = BookRepository(database.bookDao())

        intializeNotRelatedToSuspend()

        intializeBeforeSuspend()
        intializeSuspend()

    }

    private fun intializeNotRelatedToSuspend() {
        bottomNavigation = findViewById(R.id.navigationView)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)    }

    private fun intializeBeforeSuspend() {
        changeToolbarAndNavigation(true)
        indic_dashboard.visibility = View.VISIBLE
    }

    private fun intializeSuspend() {
        launch {
            val frag: Fragment

            val termExist = termRep.isTermexist()
            val bookExist = bookRepo.isBooksExist()

            indic_dashboard.visibility = View.GONE

            frag = if (termExist)
                if (bookExist) {
                    changeToolbarAndNavigation(false)
                    PerformanceFragment()
                }
                else {
                    changeToolbarAndNavigation(true)
                    BooksFirstFragment()
                }
            else {
                changeToolbarAndNavigation(true)
                TermFirstFragment()
            }
            Transaction(frag)
        }
    }

    fun changeToolbarAndNavigation(hide:Boolean){
        if (hide) {
            supportActionBar!!.hide()
            bottomNavigation.visibility = View.GONE
        }else{
            supportActionBar!!.show()
            bottomNavigation.visibility = View.VISIBLE
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.performancing -> {
                Transaction(PerformanceFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.AddRead -> {
                Transaction(ReadsFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.Booking -> {
                Transaction(BooksListFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    protected fun Transaction(fragment: Fragment) {
        val fragmentManager = supportFragmentManager.beginTransaction()
        fragmentManager.setCustomAnimations(R.animator.fragment_slide_left_enter,
                R.animator.fragment_slide_left_exit,
                R.animator.fragment_slide_right_enter,
                R.animator.fragment_slide_right_exit)
        fragmentManager.replace(R.id.container, fragment).commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.mainmenu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.Reseting -> {
                launch {
                    val term = termRep.getTerm()
                    if (term!=null)
                        termRep.delete(term)

                    val books = bookRepo.getAllBook()
                    if (!books.isNullOrEmpty()) {
                        bookRepo.deleteAll()
                    }

                    intializeSuspend()
                }
            }
            R.id.TermManaging -> {
                changeToolbarAndNavigation(true)
                Transaction(TermEditFragment())
            }
            R.id.book_first -> {
                changeToolbarAndNavigation(true)
                Transaction(BooksFirstFragment())
            }
        }

        return super.onOptionsItemSelected(item)

    }
    override fun failOpenBooks() {
        intializeSuspend()
        Toast.makeText(applicationContext,"fail book",Toast.LENGTH_LONG).show()
    }
    override fun failPerformance() {
        intializeSuspend()
        Toast.makeText(applicationContext,"fail performance",Toast.LENGTH_LONG).show()
    }
    override fun onSaveTermComplete() {
        intializeSuspend()
    }
    override fun failRead() {
        intializeSuspend()
        Toast.makeText(applicationContext,"fail read",Toast.LENGTH_LONG).show()
    }
}

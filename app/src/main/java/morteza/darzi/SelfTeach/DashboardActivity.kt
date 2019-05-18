package morteza.darzi.SelfTeach

import BL.*
import DAL.AppDatabase
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.dashboard_content.*
import kotlinx.coroutines.launch


class DashboardActivity : ScopedAppActivity(), TermFragment.OnFragmentInteractionListener
        ,BooksFragment.OnFragmentInteractionListener,ReadsFragment.OnFragmentInteractionListener,
        PerformanceFragment.OnFragmentInteractionListener {

    lateinit var termRep : TermRepository
    lateinit var bookRepo : BookRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        MyExceptionHandler(this)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        val database = AppDatabase.getInstance(applicationContext)
        termRep = TermRepository(database.termDao())
        bookRepo = BookRepository(database.bookDao())

        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        initializeFirst()
    }

    private fun initializeFirst() {
        launch {
            var frag = Fragment()

            val termExist = termRep.isTermexist()
            val bookExist = bookRepo.isBooksExist()

            frag = if (termExist)
                if (bookExist)
                    PerformanceFragment()
                else
                    BooksFragment()
            else
                TermFragment()

            Transaction(frag)
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.performancing -> {
                Transaction(TermFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.AddRead -> {
                Transaction(ReadsFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.Booking -> {
                Transaction(BooksFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    protected fun Transaction(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit()
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
                    termRep.delete(term!!)

                    val books = bookRepo.getAllBook()
                    for (b in books!!) {
                        bookRepo.delete(b)
                    }

                    initializeFirst()
                }
            }
            R.id.TermManaging -> Transaction(TermFragment())
        }

        return super.onOptionsItemSelected(item)

    }
    override fun failOpenBooks() {
        initializeFirst()
        Toast.makeText(applicationContext,"fail book",Toast.LENGTH_LONG).show()
    }
    override fun failPerformance() {
        initializeFirst()
        Toast.makeText(applicationContext,"fail performance",Toast.LENGTH_LONG).show()
    }
    override fun onSaveTermComplete() {
        initializeFirst()
    }
    override fun failRead() {
        initializeFirst()
        Toast.makeText(applicationContext,"fail read",Toast.LENGTH_LONG).show()
    }
}

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
import com.activeandroid.query.Select
import kotlinx.coroutines.launch


class DashboardActivity : ScopedAppActivity(), TermFragment.OnFragmentInteractionListener
        ,BooksFragment.OnFragmentInteractionListener,ReadsFragment.OnFragmentInteractionListener,
        PerformanceFragment.OnFragmentInteractionListener {

    lateinit var termRep : TermRepository
    lateinit var bookRepo : BookRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
//        MyExceptionHandler(this)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        val database = AppDatabase.getInstance(applicationContext)
        termRep = TermRepository(database.termDao())
        bookRepo = BookRepository(database.bookDao())

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
//        when {
//            FirstChecker.checkLevel()==TermLevel.Term -> {
//                menu.getItem(0).isVisible = false//add read
//                menu.getItem(2).isVisible = false//add bookOld
//            }
//            FirstChecker.checkLevel()==TermLevel.Book -> menu.getItem(0).isVisible = false//add read
//        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.Reseting -> {
                val t = Select().from(Term_old::class.java).executeSingle<Term_old>()
                t?.delete()
                val bs = Select().from(Book_Old::class.java).execute<Book_Old>()
                if (bs.isNotEmpty()) {
                    for (b in bs) {
                        b.delete()
                    }
                }
                val rs = Select().from(Read_Old::class.java).execute<Read_Old>()
                if (rs.isNotEmpty()) {
                    for (r in rs) {
                        r.delete()
                    }
                }

                initializeFirst()
            }
            R.id.TermManaging -> Transaction(TermFragment())
            R.id.AddRead -> Transaction(ReadsFragment())
            R.id.Booking -> Transaction(BooksFragment())
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

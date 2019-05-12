package morteza.darzi.SelfTeach

import BL.*
import DAL.TermRepository
import DAL.Termdb
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.activeandroid.query.Select
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DashboardActivity : ScopedAppActivity(), TermFragment.OnFragmentInteractionListener
        ,BooksFragment.OnFragmentInteractionListener,ReadsFragment.OnFragmentInteractionListener,
        PerformanceFragment.OnFragmentInteractionListener {

    lateinit var repository : TermRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        MyExceptionHandler(this)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        initializeFirst()

        repository = TermRepository(MyApplication.database.termDao())
    }

    private fun initializeFirst() {
        var frag = Fragment()

        val checker = launch (Dispatchers.IO){
             repository.term
        }
        when(checker){
            TermLevel.Term -> frag = TermFragment()
            TermLevel.Book -> frag = BooksFragment()
            TermLevel.Perfermance -> frag = PerformanceFragment()
        }
        Transaction(frag)
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
        when {
            FirstChecker.checkLevel()==TermLevel.Term -> {
                menu.getItem(0).isVisible = false//add read
                menu.getItem(2).isVisible = false//add bookOld
            }
            FirstChecker.checkLevel()==TermLevel.Book -> menu.getItem(0).isVisible = false//add read
        }
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
                val rs = Select().from(Read::class.java).execute<Read>()
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
    }
    override fun failPerformance() {
        initializeFirst()
    }
    override fun onSaveTermComplete() {
        initializeFirst()
    }
    override fun failRead() {
        initializeFirst()
    }
}

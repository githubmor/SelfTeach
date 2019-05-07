package morteza.darzi.SelfTeach

import BL.*
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.activeandroid.query.Select
import kotlinx.android.synthetic.main.activity_dashboard.*


class DashboardActivity : AppCompatActivity(),TermFragment.OnFragmentInteractionListener
        ,BooksFragment.OnFragmentInteractionListener,ReadsFragment.OnFragmentInteractionListener,
        PerformanceFragment.OnFragmentInteractionListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setSupportActionBar(toolbar)

        initializeFirst()


    }

    private fun initializeFirst() {
        val frag : Fragment
        when(FirstChecker.checkLevel()){
            TermLevel.Term -> frag = TermFragment()
            TermLevel.Book -> frag = BooksFragment()
            TermLevel.Perfermance -> frag = PerformanceFragment()
        }
        Transaction(frag)
    }

    private fun Transaction(frag: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, frag).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.mainmenu, menu)
        when {
            FirstChecker.checkLevel()==TermLevel.Term -> {
                menu.getItem(0).isVisible = false//add read
                menu.getItem(2).isVisible = false//add book
            }
            FirstChecker.checkLevel()==TermLevel.Book -> menu.getItem(0).isVisible = false//add read
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.Reseting -> {
                val t = Select().from(Term::class.java).executeSingle<Term>()
                val bs = Select().from(Book::class.java).execute<Book>()
                val rs = Select().from(Read::class.java).execute<Read>()
                t?.delete()
                if (!bs.isEmpty()) {
                    for (b in bs) {
                        b.delete()
                    }
                }
                if (!rs.isEmpty()) {
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
    override fun FailOpenBooks() {
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

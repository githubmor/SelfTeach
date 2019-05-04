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
        ,BooksFragment.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {

    }


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
            TermLevel.Perfermance -> TODO()
        }
        supportFragmentManager.beginTransaction().replace(R.id.container,frag).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.mainmenu, menu)
//        if (teacher.IsBooksSet()!!.not()) {
//            if (teacher.IsTermSet()!!.not())
//                menu.getItem(3).isVisible = false//add books
//            if (freeBooks.size <= 0)
//                menu.getItem(0).isVisible = false//add read
//        }
//        menu.getItem(1).isVisible = false//reporting
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

                startActivity(Intent(this, MainActivity::class.java))
            }
            R.id.TermManaging -> startActivity(Intent(this, TermFragment::class.java))

            R.id.AddRead -> {
                val i = Intent(this, Reading::class.java)
                startActivity(i)
            }
            R.id.Booking -> {
                val b = Intent(this, Booking::class.java)
                b.putExtra("free", false)
                startActivity(b)
            }
            R.id.FreeBooking -> {
                val f = Intent(this, Booking::class.java)
                f.putExtra("free", true)
                startActivity(f)
            }
            R.id.Report -> Toast.makeText(applicationContext, "در حال توسعه", Toast.LENGTH_SHORT).show()
            else -> {
            }
        }

        return super.onOptionsItemSelected(item)

    }

    override fun onSaveTermComplete() {
        initializeFirst()
    }
}

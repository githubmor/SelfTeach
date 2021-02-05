package morteza.darzi.SelfTeach2

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import core.services.BookService
import core.services.TermService
import kotlinx.coroutines.launch
import morteza.darzi.SelfTeach2.databinding.ActivityDashboardBinding


class DashboardActivity : ScopedAppActivity()
        , BooksPerformanceFragment.OnFragmentInteractionListener, ReadsFragment.OnFragmentInteractionListener,
        TermPerformanceFragment.OnFragmentInteractionListener, TermFragment.OnFragmentInteractionListener,
        BooksFragment.OnFragmentInteractionListener {

    private lateinit var termService: TermService
    private lateinit var bookService: BookService
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var activityDashboardBinding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        activityDashboardBinding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(activityDashboardBinding.root)
        intializeNotRelatedToSuspend()

        intializeBeforeSuspend()

        intializeSuspend()

    }

    private fun intializeNotRelatedToSuspend() {

        //MyExceptionHandler(this)//comment this line if Use Debuger

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        termService = TermService(applicationContext)
        bookService = BookService(applicationContext)

        bottomNavigation = findViewById(R.id.navigationView)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun intializeBeforeSuspend() {
        hideToolbarAndNavigation(true)
        showLoader(true)
    }

    private fun intializeSuspend() {
        launch {
            val frag: Fragment

            val termExist = termService.isTermExist()
            val bookExist = bookService.anyBooksExist()

            showLoader(false)

            frag = if (termExist)
                if (bookExist) {
                    hideToolbarAndNavigation(false)
                    TermPerformanceFragment()
                } else {
                    hideToolbarAndNavigation(true)
                    BooksFragment()
                }
            else {
                hideToolbarAndNavigation(true)
                TermFragment()
            }

            showFragment(frag)
        }
    }

    private fun showLoader(isShowing: Boolean) = if (isShowing) {
        activityDashboardBinding.waintingLoaderDashboard.visibility = View.VISIBLE
    } else {
        activityDashboardBinding.waintingLoaderDashboard.visibility = View.GONE
    }

    private fun hideToolbarAndNavigation(hide: Boolean) = if (hide) {
        supportActionBar!!.hide()
        bottomNavigation.visibility = View.GONE
    } else {
        supportActionBar!!.show()
        bottomNavigation.visibility = View.VISIBLE
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.performancing -> {
                showFragment(TermPerformanceFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.AddRead -> {
                showFragment(ReadsFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.Booking -> {
                showFragment(BooksPerformanceFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun showFragment(fragment: Fragment) {
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
                    val term = termService.getTerm()
                    if (term.isSaved()) {
                        val saved = termService.delete(term)
                        if (!saved)
                            throw IllegalArgumentException("حذف ترم دچار مشکل شده. لطفا به سازنده برنامه اطلاع دهید")
                    }

                    val books = bookService.getAllBookWithSumRead()
                    if (!books.isNullOrEmpty()) {
                        val saved =bookService.deleteAll()
                        if (!saved)
                            throw IllegalArgumentException("حذف کتاب ها دچار مشکل شده. لطفا به سازنده برنامه اطلاع دهید")
                    }

                    intializeSuspend()
                }
            }
            R.id.TermManaging -> {
                hideToolbarAndNavigation(true)
                showFragment(TermFragment())
            }
            R.id.book_first -> {
                hideToolbarAndNavigation(true)
                showFragment(BooksFragment())
            }
        }

        return super.onOptionsItemSelected(item)

    }

    override fun failOpenBooks() {
        intializeSuspend()
        Toast.makeText(applicationContext, "در نمايش كتاب هاي ايرادي به وجود آمده", Toast.LENGTH_LONG).show()
    }

    override fun failPerformance() {
        intializeSuspend()
        Toast.makeText(applicationContext, "در نمايش پيشرفت درسي ايرادي به وجود آمده", Toast.LENGTH_LONG).show()
    }

    override fun onSaveTermComplete() {
        intializeSuspend()
    }

    override fun completeBooksFirst() {
        hideKeyboard()
        intializeSuspend()

    }

    override fun failRead() {
        intializeSuspend()
        Toast.makeText(applicationContext, "در نمايش ليست خوانده شده ها ايرادي به وجود آمده", Toast.LENGTH_LONG).show()
    }


}

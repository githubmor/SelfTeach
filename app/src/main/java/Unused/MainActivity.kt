package Unused

//package morteza.darzi.SelfTeach
//
//import BL.Book_Old
//import BL.Read_Old
//import BL.Teacher
//import BL.Term_old
//import android.content.Intent
//import android.os.Bundle
//import android.view.Menu
//import android.view.MenuItem
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.viewpager.widget.ViewPager
//import com.activeandroid.query.Select
//
//class MainActivity : AppCompatActivity() {
//
//
//    private var teacher: Teacher? = null
//    private var freeBooks: List<Book_Old>? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.main)
//
//
//        val mViewPager = findViewById<ViewPager>(R.id.pager)
//
//        teacher = Teacher()
//        freeBooks = Select().from(Book_Old::class.java).where("free = " + 1).execute()
//        if (freeBooks!!.isNotEmpty())
//            SetReadsToFreeBooks()
//
//
//        if (mViewPager != null) {//this is in phone
//
//            val mDemoCollectionPagerAdapter = DemoCollectionPagerAdapter(
//                    supportFragmentManager, teacher!!, freeBooks as MutableList<Book_Old>)
//
//            mViewPager.adapter = mDemoCollectionPagerAdapter
//        } else {//this is in land tablet
//
//            //because want to show just two fragment , not show free bookOlds in land tablet
//            if (savedInstanceState != null)
//                return
//            val termMain = PerformanceFragment()
//            termMain.GiveTeacher(teacher!!)
//            val bookMain = BookMain()
//            bookMain.GiveTeacher(teacher!!)
//            val mFragmentManager = supportFragmentManager
//            val mFragmentTrasaction = mFragmentManager.beginTransaction()
//            mFragmentTrasaction
//                    .add(R.id.books_fragment_container, bookMain)
//                    .add(R.id.term_fragment_container, termMain)
//                    .commit()
//
//        }
//
//    }
//
//    private fun SetReadsToFreeBooks() {
//        for (b in freeBooks!!) {
//            b.LoadReads()
//        }
//    }
//
//
//    //    private void showMessageBox(String message){
//    //        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//    //    }
//
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.mainmenu, menu)
//        if (teacher!!.IsBooksSet_old()!!.not()) {
//            if (teacher!!.IsTermSet()!!.not())
//                menu.getItem(3).isVisible = false//add bookOlds
//            if (freeBooks!!.size <= 0)
//                menu.getItem(0).isVisible = false//add read
//        }
//        menu.getItem(1).isVisible = false//reporting
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//
//        when (item.itemId) {
//            R.id.Reseting -> {
//                //                if (teacher.IsTermSet() & teacher.IsBooksSet_old()) {
//                //                    teacher.getTerm_old().delete();
//                //                    for (Book_Old b : teacher.getBookOlds()) {
//                //                        b.DeleteReads();
//                //                        b.delete();
//                //                    }
//                //                }else if (teacher.IsTermSet()){
//                //                    teacher.getTerm_old().delete();
//                //                }
//                //                if (freeBooks.size()>0){
//                //                    for (Book_Old n: freeBooks){
//                //                        n.delete();
//                //                    }
//                //                }
//                val t = Select().from(Term_old::class.java).executeSingle<Term_old>()
//                val bs = Select().from(Book_Old::class.java).execute<Book_Old>()
//                val rs = Select().from(Read_Old::class.java).execute<Read_Old>()
//                t?.delete()
//                if (!bs.isEmpty()) {
//                    for (b in bs) {
//                        b.delete()
//                    }
//                }
//                if (!rs.isEmpty()) {
//                    for (r in rs) {
//                        r.delete()
//                    }
//                }
//
//                startActivity(Intent(this, MainActivity::class.java))
//            }
//            R.id.TermManaging -> startActivity(Intent(this, TermFragment::class.java))
//
//            R.id.AddRead -> {
//                val i = Intent(this, Reading::class.java)
//                startActivity(i)
//            }
//            R.id.Booking -> {
//                val b = Intent(this, Booking::class.java)
//                b.putExtra("free", false)
//                startActivity(b)
//            }
//            R.id.FreeBooking -> {
//                val f = Intent(this, Booking::class.java)
//                f.putExtra("free", true)
//                startActivity(f)
//            }
//            R.id.Report -> Toast.makeText(applicationContext, "در حال توسعه", Toast.LENGTH_SHORT).show()
//            else -> {
//            }
//        }
//
//        return super.onOptionsItemSelected(item)
//
//    }
//
//
//}
//

package Unused

//package morteza.darzi.SelfTeach
//
//import BL.Book_Old
//import BL.Read_Old
//import BL.Teacher
//import DBAdapter.Read_Adapter
//import android.content.Intent
//import android.os.Bundle
//import android.view.View
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.activeandroid.query.Select
//import com.gc.materialdesign.views.ButtonFloat
//import java.util.*
//
///**
// * Created by M on 14/11/29.
// */
//class Reading : AppCompatActivity() {
//
//
//    private var reads: MutableList<Read_Old>? = null
//
//    private var buttonFloat: ButtonFloat? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.reading)
//
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//
//        buttonFloat = findViewById<View>(R.id.addReed_Float) as ButtonFloat
//        val res = resources
//        buttonFloat!!.drawableIcon = res.getDrawable(R.drawable.ic_add_white_48dp)
//        buttonFloat!!.setOnClickListener {
//            val intent = Intent(applicationContext, Add_Read::class.java)
//            startActivity(intent)
//        }
//
//        val bookOlds = Select().from(Book_Old::class.java).execute<Book_Old>()
//
//        reads = ArrayList()
//
//        for (b in bookOlds) {
//            val rs = Select()
//                    .from(Read_Old::class.java)
//                    .where("Book_Old = ?", b.id!!)
//                    .execute<Read_Old>()
//            if (!rs.isEmpty()) {
//                for (r in rs) {
//                    r.bookOld = b
//                }
//            }
//            reads!!.addAll(rs)
//        }
//
//        val teacher = Teacher()
//        //        reads = teacher.getReads();
//
//        val mRecyclerView = findViewById<View>(R.id.ReedList) as RecyclerView
//
//        mRecyclerView.setHasFixedSize(true)
//
//        // use a linear layout manager
//        val mLayoutManager = LinearLayoutManager(applicationContext)
//        mRecyclerView.layoutManager = mLayoutManager
//
//        // specify an adapter (see also next example)
//
//        val mAdapter = Read_Adapter(applicationContext, reads as ArrayList<Read_Old>)
//        mRecyclerView.adapter = mAdapter
//
//    }
//}
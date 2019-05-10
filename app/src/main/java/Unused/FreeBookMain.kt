package Unused

//package morteza.darzi.SelfTeach
//
//
//import BL.Book_Old
//import DBAdapter.FreeBook_list_Performance
//import android.content.Intent
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.TextView
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//
///**
// * Created by M on 14/12/25.
// */
//class FreeBookMain : Fragment() {
//
//    private var bookOlds: List<Book_Old>? = null
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//
//        if (bookOlds!!.size > 0) {
//            val view = inflater.inflate(R.layout.bookmain, container, false)
//
//            val mRecyclerView = view.findViewById<View>(R.id.my_recycler_view) as RecyclerView
//
//
//            // use this setting to improve performance if you know that changes
//            // in content do not change the layout size of the RecyclerView
//            mRecyclerView.setHasFixedSize(true)
//
//            // use a linear layout manager
//            val mLayoutManager = LinearLayoutManager(this.activity!!.applicationContext)
//            mRecyclerView.layoutManager = mLayoutManager
//
//            // specify an adapter (see also next example)
//
//            val mAdapter = FreeBook_list_Performance(bookOlds!!)
//            mRecyclerView.adapter = mAdapter
//            return view
//        } else {
//
//            val view = inflater.inflate(R.layout.nofreebook, container, false)
//
//            val sd = view.findViewById<View>(R.id.Desc) as TextView
//            val td = view.findViewById<View>(R.id.ToADDFreeBooks) as Button
//            //Button fd = (Button) view.findViewById(R.id.ToADDFreeBook);
//
//            sd.text = "کتاب های مطالعه آزاد خود را معرفی کنید تا خودخوان شما را در خواندن آنها همراهی کند"
//
//            td.setOnClickListener {
//                val f = Intent(activity, Booking::class.java)
//                f.putExtra("free", true)
//                startActivity(f)
//            }
//
//
//            return view
//        }
//    }
//
//    fun GiveBooks(bookOlds: List<Book_Old>) {
//        this.bookOlds = bookOlds
//    }
//
//
//}
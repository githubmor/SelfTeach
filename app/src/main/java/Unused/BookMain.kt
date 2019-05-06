package Unused

//package morteza.darzi.SelfTeach
//
//
//import BL.Teacher
//import DBAdapter.Book_list_Performance
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//
///**
// * Created by M on 14/12/25.
// */
//class BookMain : Fragment() {
//
//    private var teacher: Teacher? = null
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//
//        val view = inflater.inflate(R.layout.bookmain, container, false)
//
//        val mRecyclerView = view.findViewById<View>(R.id.my_recycler_view) as RecyclerView
//
//
//        // use this setting to improve performance if you know that changes
//        // in content do not change the layout size of the RecyclerView
//        mRecyclerView.setHasFixedSize(true)
//
//        // use a linear layout manager
//        val mLayoutManager = LinearLayoutManager(this.activity!!.applicationContext)
//        mRecyclerView.layoutManager = mLayoutManager
//
//        // specify an adapter (see also next example)
//
//        val mAdapter = Book_list_Performance(teacher!!)
//        mRecyclerView.adapter = mAdapter
//        return view
//    }
//
//    fun GiveTeacher(teacher: Teacher) {
//        this.teacher = teacher
//    }
//
//
//}
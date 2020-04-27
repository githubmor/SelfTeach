package morteza.darzi.SelfTeach2

import BL.Book
import BL.BookRepository
import BL.TermRepository
import DAL.AppDatabase
import DBAdapter.Book_Adapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_book_list.view.*
import kotlinx.android.synthetic.main.include_book_list.view.list
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class BooksPerformanceFragment : BaseFragment() {

    override val title: String
        get() = "كتاب ها"

    var books : MutableList<Book> = mutableListOf()
    private var listener: OnFragmentInteractionListener? = null
    lateinit var repository : BookRepository
    lateinit var adapter: Book_Adapter

    lateinit var v: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_book_list, container, false)

        repository = BookRepository(AppDatabase.getInstance(context!!).bookDao())

        intializeBeforeSuspend()
        intializeSuspend()

        return v
    }

    private fun intializeBeforeSuspend() {
        ShowLoader(true)
    }

    private fun intializeSuspend() {
        launch {
            delay(500)
            val termRepo = TermRepository(AppDatabase.getInstance(context!!).termDao())

            if (!termRepo.isTermexist()) {
                listener!!.failOpenBooks()
            }

            val list = repository.getAllBookWithRead()

            if (list != null) {
                for (bill in list) {
                    books.add(Book(bill))
                }
            }
            intializeAfterSuspend()

        }
    }

    private fun intializeAfterSuspend() {
        adapter = Book_Adapter(context!!,books,repository)
        v.list.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        v.list.adapter = adapter

        ShowLoader(false)
    }


    private fun ShowLoader(isShowder:Boolean){
        if (isShowder){
            v.indic_book_list.visibility = VISIBLE
            v.list.visibility = GONE
        }else{
            v.indic_book_list.visibility = GONE
            v.list.visibility= VISIBLE
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {

        fun failOpenBooks()
    }

}

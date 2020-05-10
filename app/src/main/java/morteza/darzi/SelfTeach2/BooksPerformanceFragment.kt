package morteza.darzi.SelfTeach2

//import DAL.AppDatabase
//import DAL.BookRepository
//import DAL.TermRepository
import core.BookPerformance
import core.services.BookService
import core.services.TermService
import DBAdapter.Book_Performance_Adapter
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

    private var bookPerformances: MutableList<BookPerformance> = mutableListOf()
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var bookService: BookService
    private lateinit var performanceAdapter: Book_Performance_Adapter

    private lateinit var v: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_book_list, container, false)

        bookService = BookService(context!!)

        intializeBeforeSuspend()
        intializeSuspend()

        return v
    }

    private fun intializeBeforeSuspend() {
        showLoader(true)
    }

    private fun intializeSuspend() {
        launch {
            delay(500)
            val termService = TermService(context!!)

            if (!termService.isTermExist()) {
                listener!!.failOpenBooks()
            }
            val term = termService.getTerm()

            for (book in bookService.getAllBookWithSumRead()) {
                bookPerformances.add(BookPerformance(term, book))
            }
            intializeAfterSuspend()

        }
    }

    private fun intializeAfterSuspend() {
        performanceAdapter = Book_Performance_Adapter(context!!, bookPerformances)
        v.list.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        v.list.adapter = performanceAdapter

        showLoader(false)
    }


    private fun showLoader(isShowder: Boolean) {
        if (isShowder) {
            v.indic_book_list.visibility = VISIBLE
            v.list.visibility = GONE
        } else {
            v.indic_book_list.visibility = GONE
            v.list.visibility = VISIBLE
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
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

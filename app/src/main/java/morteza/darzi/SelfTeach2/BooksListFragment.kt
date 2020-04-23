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


class BooksListFragment : BaseFragment() {

    private val bookNameErrorMessage = "لطفا نام كتاب را وارد كنيد"
    private val bookPageCountErrorMessage ="لطفا تعداد صفحات كتاب را وارد كنيد"
    override val title: String
        get() = "كتاب ها"

    var books : MutableList<Book> = mutableListOf()
    private var listener: OnFragmentInteractionListener? = null
    lateinit var repository : BookRepository
    lateinit var adapter: Book_Adapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_book_list, container, false)

        repository = BookRepository(AppDatabase.getInstance(context!!).bookDao())

        intializeBeforeSuspend(v)
        intializeSuspend(v)

        return v
    }

    private fun intializeBeforeSuspend(v: View) {
        v.indic_book_list.visibility = View.VISIBLE
        v.list.visibility = View.GONE
    }

    private fun intializeSuspend(v: View) {
        launch {
            delay(500)
            val termRepo = TermRepository(AppDatabase.getInstance(context!!).termDao())

            if (!termRepo.isTermexist()) {
                listener!!.failOpenBooks()
            }

            val bills = repository.getAllBookWithRead()

            if (bills != null) {
                for (bill in bills) {
                    books.add(Book(bill))
                }
            }
            intializeAfterSuspend(v)

        }
    }

    private fun intializeAfterSuspend(v: View) {
        adapter = Book_Adapter(context!!,books,repository)
        v.list.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        v.list.adapter = adapter

        v.indic_book_list.visibility = GONE
        v.list.visibility= VISIBLE
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

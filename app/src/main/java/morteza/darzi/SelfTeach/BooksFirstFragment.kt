package morteza.darzi.SelfTeach

import BL.Book
import BL.BookRepository
import BL.TermRepository
import DAL.AppDatabase
import DAL.BookReadsdb
import DAL.Bookdb
import DBAdapter.Book_first_Adapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_books_first.view.*
import kotlinx.android.synthetic.main.include_book_add.view.*
import kotlinx.android.synthetic.main.include_book_list.view.*
import kotlinx.coroutines.launch


class BooksFirstFragment : BaseFragment() {

    private val bookNameErrorMessage = "لطفا نام كتاب را وارد كنيد"
    private val bookPageCountErrorMessage ="لطفا تعداد صفحات كتاب را وارد كنيد"
    override val title: String
        get() = "كتاب ها"

    var books : MutableList<Book> = mutableListOf()
    private var listener: OnFragmentInteractionListener? = null
    lateinit var repository : BookRepository
    lateinit var adapter: Book_first_Adapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_books_first, container, false)


        intializeBeforeSuspend(v)
        intializeSuspend(v)
        intializeNotRelatedToSuspend(v)

        return v
    }

    private fun intializeBeforeSuspend(v: View) {
        v.indic_book_first.visibility = View.VISIBLE
        v.switcher.visibility = View.GONE
        v.emptyText.visibility = GONE
        v.fab.hide()
    }

    private fun intializeSuspend(v: View) {
        launch {
            val termRepo = TermRepository(AppDatabase.getInstance(context!!).termDao())
            repository = BookRepository(AppDatabase.getInstance(context!!).bookDao())

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
        adapter = Book_first_Adapter(context!!,books,repository)
        v.list.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        v.list.adapter = adapter

        v.switcher.visibility = View.VISIBLE
        v.indic_book_first.visibility = GONE

        if (books.size <= 0) {
            arrangeForFirstViewSwitcher(v, false)
        } else {
            arrangeForFirstViewSwitcher(v, true)
        }
    }



    private fun intializeNotRelatedToSuspend(v: View) {
        v.fab.setOnClickListener {
            arrangeForSecondViewSwitcher(v)
        }

        v.book_save.setOnClickListener {
            if (validateToSave(v)) {
                launch {
                    val b = Bookdb(0,v.book_name.text.toString(),v.book_page_count.text.toString().toInt(),v.priority.rating.toInt())
                    repository.insert(b)
                    adapter.addNewBook(Book(BookReadsdb(b)))
                    arrangeForFirstViewSwitcher(v,true)
                }
            }
        }
        errorTextChangeListner(v.book_name_lay, bookNameErrorMessage)
        errorTextChangeListner(v.book_page_count_lay,bookPageCountErrorMessage)

    }

    private fun validateToSave(v:View):Boolean {
        return when {
            v.book_name.text.isNullOrEmpty() -> {
                v.book_name_lay.error = bookNameErrorMessage
                false
            }
            v.book_page_count.text.isNullOrEmpty() -> {
                v.book_page_count_lay.error = bookPageCountErrorMessage
                false
            }
            else -> true
        }
    }

    private fun arrangeForSecondViewSwitcher(v: View) {
        v.book_name.setText("")
        v.book_name_lay.isErrorEnabled = false
        v.book_page_count.setText("")
        v.book_page_count_lay.isErrorEnabled = false
        if (v.switcher.displayedChild==0)
            v.switcher.showNext()
        v.fab.hide()
    }



    private fun arrangeForFirstViewSwitcher(v: View, isListShow:Boolean) {
        if (isListShow) {
            v.list.visibility = VISIBLE
            v.emptyText.visibility = GONE
        }else{
            v.list.visibility = GONE
            v.emptyText.visibility = VISIBLE
        }
        v.fab.show()
        if (v.switcher.displayedChild==1)
            v.switcher.showPrevious()


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

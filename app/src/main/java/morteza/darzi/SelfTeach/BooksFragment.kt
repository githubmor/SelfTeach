package morteza.darzi.SelfTeach

import BL.Book
import BL.BookRepository
import BL.FirstChecker
import BL.TermLevel
import DAL.AppDatabase
import DAL.BookReads
import DAL.Bookdb
import DBAdapter.Book_Adapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_books.view.*
import kotlinx.android.synthetic.main.include_book_add.view.*
import kotlinx.android.synthetic.main.include_book_list.view.*
import kotlinx.coroutines.launch


class BooksFragment : BaseFragment() {

    private val bookNameErrorMessage = "لطفا نام كتاب را وارد كنيد"
    private val bookPageCountErrorMessage ="لطفا تعداد صفحات كتاب را وارد كنيد"
    override val title: String
        get() = "كتاب ها"

    var books : MutableList<Book>? = null
    private var listener: OnFragmentInteractionListener? = null
    val repository = BookRepository(AppDatabase.getInstance(context!!).bookDao())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (FirstChecker.checkLevel()==TermLevel.Term) {
            listener!!.failOpenBooks()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_books, container, false)

        if (books.isNullOrEmpty()){
            arrangeForFirstViewSwitcher(v,false)
        }else{
            arrangeForFirstViewSwitcher(v,true)
        }

        v.fab.setOnClickListener {
            arrangeForSecondViewSwitcher(v)
        }



        v.list.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)

        val adapter = Book_Adapter(context!!,books,repository)
        v.list.adapter = adapter

        launch {
            val bills = repository.getAllBookWithRead()
            for (bill in bills) {
                books!!.add(Book(bill))
            }
            adapter.updateBooks(bills.map { Book(it) })
        }
        errorTextChangeListner(v.book_name_lay, bookNameErrorMessage)
        errorTextChangeListner(v.book_page_count_lay,bookPageCountErrorMessage)

        v.book_save.setOnClickListener {
            if (validateToSave(v)) {
                launch {
                    val b = Bookdb(0,v.book_name.text.toString(),v.book_page_count.text.toString().toInt())
                    repository.insert(b)
                    adapter.addNewBook(Book(BookReads(b, listOf())))
                    arrangeForFirstViewSwitcher(v,true)
                }
            }
        }

        return v
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

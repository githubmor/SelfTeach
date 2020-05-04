package morteza.darzi.SelfTeach2

import BL.Book
import BL.BookService
import BL.TermService
//import DAL.TermRepository
//import DAL.AppDatabase
import DBAdapter.Books_Adapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_books_first.view.*
import kotlinx.android.synthetic.main.include_book_add.view.*
import kotlinx.android.synthetic.main.include_book_list.view.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class BooksFragment : BaseFragment() {

    private val bookNameErrorMessage = "لطفا نام كتاب را وارد كنيد"
    private val bookPageCountErrorMessage ="لطفا تعداد صفحات كتاب را وارد كنيد"
    override val title: String
        get() = "كتاب ها"

    var books : MutableList<Book> = mutableListOf()
    private var listener: OnFragmentInteractionListener? = null
    lateinit var service : BookService
    lateinit var adapter: Books_Adapter

    lateinit var v: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_books_first, container, false)

        intializeBeforeSuspend()
        intializeSuspend()

        return v
    }

    private fun intializeBeforeSuspend() {

        ShowLoader(true)

        v.fab.setOnClickListener {
            showAddNewBookSwitcher()
        }
        v.complete.setOnClickListener {
            listener!!.completeBooksFirst()
        }

        v.book_save.setOnClickListener {
            if (validateToSave()) {
                launch {

                    val b = Book()

                    b.name = v.book_name.text.toString()
                    b.pageCount = v.book_page_count.text.toString().toInt()
                    b.priority = v.priority.rating.toInt()

                    service.insert(b)

                    adapter.addNewBook(b)

                    ShowBookListSwitcher()
                }
            }
        }
        errorTextChangeListner(v.book_name_lay, bookNameErrorMessage)
        errorTextChangeListner(v.book_page_count_lay,bookPageCountErrorMessage)
    }

    private fun intializeSuspend() {
        launch {
            delay(500)

            checkHasTerm()

            GetBookList()

            intializeAfterSuspend()

        }
    }

    private suspend fun GetBookList() {

        service = BookService(context!!)

        books = service.getAllBookWithSumRead()!!.toMutableList()

        adapterIntialize()
    }

    private suspend fun checkHasTerm() {

        if (!TermService(context!!).isTermexist()) {
            listener!!.failOpenBooks()
        }
    }

    private fun intializeAfterSuspend() {

        ShowLoader(false)
        ShowBookListSwitcher()
    }

    private fun adapterIntialize() {
        adapter = Books_Adapter(context!!, books)
        v.list.layoutManager = LinearLayoutManager(context)
        v.list.adapter = adapter
    }

    private fun ShowLoader(isShow:Boolean) {
        if (isShow) {
            v.switcher.visibility = GONE
            v.indic_book_first.visibility = VISIBLE
            v.PLZDefineBooks.visibility = GONE
            v.fab.hide()
        }else{
            v.switcher.visibility = VISIBLE
            v.indic_book_first.visibility = GONE
            v.PLZDefineBooks.visibility = VISIBLE
            v.fab.show()
        }
    }

    private fun ShowBookListSwitcher() {

        isShowListOfBooks(books.size > 0)

        v.fab.show()

        if (v.switcher.displayedChild==1)
            v.switcher.showPrevious()
    }

    private fun isShowListOfBooks(isShow:Boolean) {
        if (isShow){
            v.PLZDefineBooks.visibility = GONE
            v.list.visibility = VISIBLE
            v.complete.visibility = VISIBLE
        }else{
            v.PLZDefineBooks.visibility = VISIBLE
            v.list.visibility = GONE
            v.complete.visibility = GONE
        }

    }

    private fun showAddNewBookSwitcher() {
        v.book_name.setText("")
        v.book_name_lay.isErrorEnabled = false
        v.book_page_count.setText("")
        v.book_page_count_lay.isErrorEnabled = false

        if (v.switcher.displayedChild==0)
            v.switcher.showNext()

        v.fab.hide()

        v.complete.visibility = GONE
        v.PLZDefineBooks.visibility = GONE
    }

    private fun validateToSave():Boolean {
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
        fun completeBooksFirst()
        fun failOpenBooks()
    }

}

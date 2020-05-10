package morteza.darzi.SelfTeach2

import core.Book
import core.services.BookService
import core.services.TermService
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
    private val bookPageCountErrorMessage = "لطفا تعداد صفحات كتاب را وارد كنيد"
    override val title: String
        get() = "كتاب ها"

    private var books: MutableList<Book> = mutableListOf()
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var service: BookService
    private lateinit var adapter: Books_Adapter

    private lateinit var fragmentView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.fragment_books_first, container, false)

        intializeBeforeSuspend()
        intializeSuspend()

        return fragmentView
    }

    private fun intializeBeforeSuspend() {

        showLoader(true)

        fragmentView.fab.setOnClickListener {
            showAddNewBookSwitcher()
        }
        fragmentView.complete.setOnClickListener {
            listener!!.completeBooksFirst()
        }

        fragmentView.book_save.setOnClickListener {
            if (validateToSave()) {
                launch {

                    val book = Book()

                    book.name = fragmentView.book_name.text.toString()
                    book.pageCount = fragmentView.book_page_count.text.toString().toInt()
                    book.priority = fragmentView.priority.rating.toInt()

                    val saved = service.insert(book)
                    if (!saved)
                        throw IllegalArgumentException("ایجاد کتاب دچار مشکل شده. لطفا به سازنده برنامه اطلاع دهید")
                    adapter.addNewBook(book)

                    showBookListSwitcher()
                }
            }
        }
        errorTextChangeListner(fragmentView.book_name_lay, bookNameErrorMessage)
        errorTextChangeListner(fragmentView.book_page_count_lay, bookPageCountErrorMessage)
    }

    private fun intializeSuspend() {
        launch {
            delay(500)

            checkHasTerm()

            getBookList()

            intializeAfterSuspend()

        }
    }

    private suspend fun getBookList() {

        service = BookService(context!!)

        if (service.anyBooksExist())
            books = service.getAllBookWithSumRead().toMutableList()

        adapterIntialize()
    }

    private suspend fun checkHasTerm() {

        if (!TermService(context!!).isTermExist()) {
            listener!!.failOpenBooks()
        }
    }

    private fun intializeAfterSuspend() {

        showLoader(false)
        showBookListSwitcher()
    }

    private fun adapterIntialize() {
        adapter = Books_Adapter(context!!, books)
        fragmentView.list.layoutManager = LinearLayoutManager(context)
        fragmentView.list.adapter = adapter
    }

    private fun showLoader(isShow: Boolean) {
        if (isShow) {
            fragmentView.switcher.visibility = GONE
            fragmentView.indic_book_first.visibility = VISIBLE
            fragmentView.PLZDefineBooks.visibility = GONE
            fragmentView.fab.hide()
        } else {
            fragmentView.switcher.visibility = VISIBLE
            fragmentView.indic_book_first.visibility = GONE
            fragmentView.PLZDefineBooks.visibility = VISIBLE
            fragmentView.fab.show()
        }
    }

    private fun showBookListSwitcher() {

        isShowListOfBooks(books.size > 0)

        fragmentView.fab.show()

        if (fragmentView.switcher.displayedChild == 1)
            fragmentView.switcher.showPrevious()
    }

    private fun isShowListOfBooks(isShow: Boolean) {
        if (isShow) {
            fragmentView.PLZDefineBooks.visibility = GONE
            fragmentView.list.visibility = VISIBLE
            fragmentView.complete.visibility = VISIBLE
        } else {
            fragmentView.PLZDefineBooks.visibility = VISIBLE
            fragmentView.list.visibility = GONE
            fragmentView.complete.visibility = GONE
        }

    }

    private fun showAddNewBookSwitcher() {
        fragmentView.book_name.setText("")
        fragmentView.book_name_lay.isErrorEnabled = false
        fragmentView.book_page_count.setText("")
        fragmentView.book_page_count_lay.isErrorEnabled = false

        if (fragmentView.switcher.displayedChild == 0)
            fragmentView.switcher.showNext()

        fragmentView.fab.hide()

        fragmentView.complete.visibility = GONE
        fragmentView.PLZDefineBooks.visibility = GONE
    }

    private fun validateToSave(): Boolean {
        return when {
            fragmentView.book_name.text.isNullOrEmpty() -> {
                fragmentView.book_name_lay.error = bookNameErrorMessage
                false
            }
            fragmentView.book_page_count.text.isNullOrEmpty() -> {
                fragmentView.book_page_count_lay.error = bookPageCountErrorMessage
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
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
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

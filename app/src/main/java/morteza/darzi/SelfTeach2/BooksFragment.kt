package morteza.darzi.SelfTeach2

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
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import core.Book
import core.services.BookService
import core.services.TermService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import morteza.darzi.SelfTeach2.databinding.FragmentBooksFirstBinding


class BooksFragment : BaseFragment() {

    private val bookNameErrorMessage = "لطفا نام كتاب را وارد كنيد"
    private val bookPageCountErrorMessage = "لطفا تعداد صفحات كتاب را وارد كنيد"
    override val title: String
        get() = "كتاب ها"

    private var books: MutableList<Book> = mutableListOf()
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var service: BookService
    private lateinit var adapter: Books_Adapter

    private var _binding: FragmentBooksFirstBinding? = null
    private val fragmentBooksFirstBinding get() = _binding!!
    private val includeBookAddBinding get() = fragmentBooksFirstBinding.includeBookAddInc
    private val includeBookListBinding get() = fragmentBooksFirstBinding.includeBookListInc

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentBooksFirstBinding.inflate(inflater, container, false)
        intializeBeforeSuspend()
        intializeSuspend()

        return fragmentBooksFirstBinding.root
    }

    private fun intializeBeforeSuspend() {

        showLoader(true)

        fragmentBooksFirstBinding.fab.setOnClickListener {
            showAddNewBookSwitcher()
        }
        includeBookListBinding.complete.setOnClickListener {
            listener!!.completeBooksFirst()
        }

        includeBookAddBinding.bookSave.setOnClickListener {
            if (validateToSave()) {
                launch {

                    val book = Book()

                    book.name = includeBookAddBinding.bookName.text.toString()
                    book.pageCount = includeBookAddBinding.bookPageCount.text.toString().toInt()
                    book.priority = includeBookAddBinding.priority.rating.toInt()

                    val saved = service.insert(book)
                    if (!saved)
                        Toast.makeText(requireContext(), "ایجاد کتاب دچار مشکل شده. لطفا به سازنده برنامه اطلاع دهید", Toast.LENGTH_LONG).show()
                    adapter.addNewBook(book)

                    showBookListSwitcher()
                }
            }
        }
        errorTextChangeListner(includeBookAddBinding.bookNameLay, bookNameErrorMessage)
        errorTextChangeListner(includeBookAddBinding.bookPageCountLay, bookPageCountErrorMessage)
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

        service = BookService(requireContext())

        if (service.anyBooksExist())
            books = service.getAllBookWithSumRead().toMutableList()

        adapterIntialize()
    }

    private suspend fun checkHasTerm() {

        if (!TermService(requireContext()).isTermExist()) {
            listener!!.failOpenBooks()
        }
    }

    private fun intializeAfterSuspend() {

        showLoader(false)
        showBookListSwitcher()
    }

    private fun adapterIntialize() {
        adapter = Books_Adapter(requireContext(), books)
        includeBookListBinding.list.layoutManager = LinearLayoutManager(context)
        includeBookListBinding.list.adapter = adapter
    }

    private fun showLoader(isShowLoader: Boolean) {
        fragmentBooksFirstBinding.switcher.visibility = if (isShowLoader) GONE else VISIBLE
        fragmentBooksFirstBinding.indicBookFirst.visibility = if (isShowLoader) VISIBLE else GONE
        fragmentBooksFirstBinding.PLZDefineBooks.visibility = if (isShowLoader) GONE else VISIBLE
        if (isShowLoader) fragmentBooksFirstBinding.fab.hide() else fragmentBooksFirstBinding.fab.show()
    }

    private fun showBookListSwitcher() {

        isShowListOfBooks(books.size > 0)

        fragmentBooksFirstBinding.fab.show()

        if (fragmentBooksFirstBinding.switcher.displayedChild == 1)
            fragmentBooksFirstBinding.switcher.showPrevious()
    }

    private fun isShowListOfBooks(isShow: Boolean) {

        fragmentBooksFirstBinding.PLZDefineBooks.visibility = if (isShow) GONE else VISIBLE
        includeBookListBinding.list.visibility = if (isShow) VISIBLE else GONE
        includeBookListBinding.complete.visibility = if (isShow) VISIBLE else GONE
    }

    private fun showAddNewBookSwitcher() {
        includeBookAddBinding.bookName.setText("")
        includeBookAddBinding.bookNameLay.isErrorEnabled = false
        includeBookAddBinding.bookPageCount.setText("")
        includeBookAddBinding.bookPageCountLay.isErrorEnabled = false

        if (fragmentBooksFirstBinding.switcher.displayedChild == 0)
            fragmentBooksFirstBinding.switcher.showNext()

        fragmentBooksFirstBinding.fab.hide()

        includeBookListBinding.complete.visibility = GONE
        fragmentBooksFirstBinding.PLZDefineBooks.visibility = GONE
    }

    private fun validateToSave(): Boolean {
        return when {
            includeBookAddBinding.bookName.text.isNullOrEmpty() -> {
                includeBookAddBinding.bookNameLay.error = bookNameErrorMessage
                false
            }
            includeBookAddBinding.bookPageCount.text.isNullOrEmpty() -> {
                includeBookAddBinding.bookPageCountLay.error = bookPageCountErrorMessage
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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
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

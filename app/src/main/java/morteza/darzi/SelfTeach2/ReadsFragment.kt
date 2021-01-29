package morteza.darzi.SelfTeach2


//import DAL.*
import DBAdapter.Read_Adapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import core.Book
import core.Read
import core.ReadBook
import core.Term
import core.services.BookService
import core.services.ReadService
import core.services.TermService
import kotlinx.coroutines.launch
import morteza.darzi.SelfTeach2.databinding.FragmentReadsBinding


class ReadsFragment : BaseDatePickerFragment() {
    override val selectableDateList: Array<PersianCalendar>
        get() = term.getCalenderActiveDaysList()
    private lateinit var readDate: TextInputEditText
    override val title: String
        get() = "خوانده ها"

    private val readDateErrorMessage = "لطفا زمان خواندن را مشخص كنيد"
    private val readPageCountErrorMessage = "لطفا تعداد صفحات خوانده شده را وارد كنيد"
    private val readSelectBookErrorMessage = "لطفا نام كتاب را وارد كنيد"
    private val readPageBiggerThanPageErrorMessage = "تعداد صفحات خوانده شده نباید بیشتر از تعداد صفحات کتاب باشد"

    var books: List<Book> = listOf()
    private var reads: MutableList<ReadBook> = mutableListOf()
    lateinit var term: Term
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var readService: ReadService
    private lateinit var bookService: BookService
    private lateinit var termRepository: TermService

    private var _binding: FragmentReadsBinding? = null
    private val fragmentReadsBinding get() = _binding!!
    private val includeReadAddBinding get() = fragmentReadsBinding.includeReadAddInc
    private val includeReadListBinding get() = fragmentReadsBinding.includeReadListInc

    private var selectedBook: Book? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentReadsBinding.inflate(inflater, container, false)

        intializeNotRelatedSuspend()

        intializeSuspend()

        return fragmentReadsBinding.root
    }

    private fun intializeNotRelatedSuspend() {

        ShowLoader(true)
        readService = ReadService(requireContext())
        bookService = BookService(requireContext())
        termRepository = TermService(requireContext())

        fragmentReadsBinding.fab.setOnClickListener {
            arrangeToShowSecondViewSwitcher()
        }

        includeReadAddBinding.readDateLay.setOnClickListener {
            showDatapicker("")
        }
        includeReadAddBinding.readDate.setOnClickListener {
            showDatapicker("")
        }

        errorTextChangeListner(includeReadAddBinding.readPageCountLay, readPageCountErrorMessage)
        errorTextChangeListner(includeReadAddBinding.readDateLay, readDateErrorMessage)

        readDate = includeReadAddBinding.readDate
    }

    private fun intializeSuspend() {
        launch {


            intializeSpinnerBookList()

            term = termRepository.getTerm()

            reads = readService.getAllReadsWithBookName().toMutableList()



            if (reads.size <= 0) {
                arrangeForShowFirstViewSwitcher(false)
            } else {
                arrangeForShowFirstViewSwitcher(true)
            }
        }
    }



    private fun intializeReadList(): Read_Adapter {
        includeReadListBinding.list.layoutManager = LinearLayoutManager(context)
        val adapter = Read_Adapter(requireContext(), reads)
        includeReadListBinding.list.adapter = adapter
        return adapter
    }

    private fun intializeSpinnerBookList() {
        launch {
            if (!bookService.anyBooksExist())
                listener!!.failRead()
            books = bookService.getAllBookWithSumRead()

            val dataAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, books.map {
                it.name + " - " + it.pageCount + " صفحه"
            })
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            includeReadAddBinding.spinner.adapter = dataAdapter
        }

        includeReadAddBinding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                selectedBook = books.single { it.name == adapterView.getItemAtPosition(i).toString().substringBefore(" - ") }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }
    }

    private fun validateToSave(): Boolean {
        return when {
            selectedBook == null -> {
                Toast.makeText(requireContext(), readSelectBookErrorMessage, Toast.LENGTH_SHORT).show()
                return false
            }
            includeReadAddBinding.readPageCount.text.isNullOrEmpty() -> {
                includeReadAddBinding.readPageCountLay.error = readPageCountErrorMessage
                return false
            }
            selectedBook != null -> {
                if (includeReadAddBinding.readPageCount.text.toString().toInt() > selectedBook!!.pageCount) {
                    includeReadAddBinding.readPageCountLay.error = readPageBiggerThanPageErrorMessage
                    return false
                } else
                    true
            }
            includeReadAddBinding.readDate.text.isNullOrEmpty() -> {
                includeReadAddBinding.readDateLay.error = readDateErrorMessage
                return false
            }
            else -> true
        }
    }

    private fun arrangeToShowSecondViewSwitcher() {
        includeReadAddBinding.readPageCount.setText("")
        includeReadAddBinding.readPageCountLay.isErrorEnabled = false
        includeReadAddBinding.readDate.setText("")
        includeReadAddBinding.readDateLay.isErrorEnabled = false
        if (fragmentReadsBinding.switcher.displayedChild == 0)
            fragmentReadsBinding.switcher.showNext()
        fragmentReadsBinding.fab.hide()
    }

    private fun ShowLoader(isShow: Boolean) {
        if (isShow) fragmentReadsBinding.indicReadFirst.show() else fragmentReadsBinding.indicReadFirst.hide()
        fragmentReadsBinding.switcher.visibility = if (isShow) GONE else VISIBLE
        fragmentReadsBinding.fab.visibility = if (isShow) GONE else VISIBLE

    }



    private fun arrangeForShowFirstViewSwitcher(isListShow: Boolean) {
        ShowLoader(false)
        includeReadListBinding.list.visibility = if (isListShow) VISIBLE else GONE
        includeReadListBinding.PLZDefineReads.visibility = if (isListShow) GONE else VISIBLE

        fragmentReadsBinding.fab.show()
        if (fragmentReadsBinding.switcher.displayedChild == 1)
            fragmentReadsBinding.switcher.showPrevious()
        val adapter = intializeReadList()

        includeReadAddBinding.readSave.setOnClickListener {
            if (validateToSave()) {
                launch {

                    val read = Read(selectedBook!!.getBookDataTable().id)

                    read.pageReadCount = includeReadAddBinding.readPageCount.text.toString().toInt()
                    read.readDate = includeReadAddBinding.readDate.text.toString()

                    val saved = readService.insert(read)
                    if (!saved)
                        throw IllegalArgumentException("ایجاد خواندن دچار مشکل شده. لطفا به سازنده برنامه اطلاع دهید")

                    adapter.addNewRead(ReadBook(read.getReadDataTable(), selectedBook!!.name))
                    arrangeForShowFirstViewSwitcher(true)
                    hideKeyboard()
                }
            }
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


    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {

        val selectedDate = PersianCalendar().apply {
            setPersianDate(year, monthOfYear, dayOfMonth)
        }
        readDate.setText(selectedDate.persianShortDate)
    }

    interface OnFragmentInteractionListener {
        fun failRead()
    }

}

package morteza.darzi.SelfTeach2


import BL.*
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
import kotlinx.android.synthetic.main.fragment_reads.view.*
import kotlinx.android.synthetic.main.include_read_add.view.*
import kotlinx.android.synthetic.main.include_read_list.view.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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

    private lateinit var v: View

    private var selectedBook: Book? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_reads, container, false)

        intializeNotRelatedSuspend()

        intializeSuspend()

        return v
    }

    private fun intializeSuspend() {
        launch {

            delay(500)

            intializeSpinnerBookList()

            term = termRepository.getTerm()!!

            reads = readService.getAllReadsWithBookName()!!.toMutableList()

            val adapter = intializeReadList()

            v.read_save.setOnClickListener {
                if (validateToSave()) {
                    launch {

                        val read = Read(selectedBook!!.getBookDataTable().id)

                        read.pageReadCount = v.read_page_count.text.toString().toInt()
                        read.readDate = v.read_date.text.toString()

                        readService.insert(read)

                        adapter.addNewRead(ReadBook(read.getReadDataTable(), selectedBook!!.name))
                        arrangeForShowFirstViewSwitcher(true)
                    }
                }
            }

            if (reads.size <= 0) {
                arrangeForShowFirstViewSwitcher(false)
            } else {
                arrangeForShowFirstViewSwitcher(true)
            }
        }
    }

    private fun intializeNotRelatedSuspend() {
        readService = ReadService(context!!)
        bookService = BookService(context!!)
        termRepository = TermService(context!!)

        v.fab.setOnClickListener {
            arrangeToShowSecondViewSwitcher()
        }

        v.read_date_lay.setOnClickListener {
            showDatapicker("")
        }
        v.read_date.setOnClickListener {
            showDatapicker("")
        }

        errorTextChangeListner(v.read_page_count_lay, readPageCountErrorMessage)
        errorTextChangeListner(v.read_date_lay, readDateErrorMessage)




        readDate = v.read_date
    }

    private fun intializeReadList(): Read_Adapter {
        v.list.layoutManager = LinearLayoutManager(context)
        val adapter = Read_Adapter(context!!, reads)
        v.list.adapter = adapter
        return adapter
    }

    private fun intializeSpinnerBookList() {
        launch {
            if (!bookService.anyBooksExist())
                listener!!.failRead()
            books = bookService.getAllBookWithSumRead()

            val dataAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, books.map {
                it.name + " - " + it.pageCount + " صفحه"
            })
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            v.spinner.adapter = dataAdapter
        }

        v.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
                Toast.makeText(context!!, readSelectBookErrorMessage, Toast.LENGTH_SHORT).show()
                return false
            }
            v.read_page_count.text.isNullOrEmpty() -> {
                v.read_page_count_lay.error = readPageCountErrorMessage
                return false
            }
            selectedBook != null -> {
                if (v.read_page_count.text.toString().toInt() > selectedBook!!.pageCount) {
                    v.read_page_count_lay.error = readPageBiggerThanPageErrorMessage
                    return false
                } else
                    true
            }
            v.read_date.text.isNullOrEmpty() -> {
                v.read_date_lay.error = readDateErrorMessage
                return false
            }
            else -> true
        }
    }

    private fun arrangeToShowSecondViewSwitcher() {
        v.read_page_count.setText("")
        v.read_page_count_lay.isErrorEnabled = false
        v.read_date.setText("")
        v.read_date_lay.isErrorEnabled = false
        if (v.switcher.displayedChild == 0)
            v.switcher.showNext()
        v.fab.hide()
    }


    private fun arrangeForShowFirstViewSwitcher(isListShow: Boolean) {
        if (isListShow) {
            v.list.visibility = VISIBLE
            v.PLZDefineReads.visibility = GONE
        } else {
            v.list.visibility = GONE
            v.PLZDefineReads.visibility = VISIBLE
        }
        v.fab.show()
        if (v.switcher.displayedChild == 1)
            v.switcher.showPrevious()

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

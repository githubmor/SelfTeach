package morteza.darzi.SelfTeach


import BL.*
import DAL.AppDatabase
import DAL.Bookdb
import DAL.ReadBookdb
import DAL.Readdb
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
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianDateParser
import kotlinx.android.synthetic.main.fragment_reads.view.*
import kotlinx.android.synthetic.main.include_read_add.view.*
import kotlinx.android.synthetic.main.include_read_list.view.*
import kotlinx.coroutines.launch


class ReadsFragment : BaseDatePickerFragment() {
    override val selectableDateList: Array<PersianCalendar>
        get() = term.getTermDaysList()
    private lateinit var readDate: TextInputEditText
    override val title: String
        get() = "خوانده ها"

    private val readDateErrorMessage = "لطفا زمان خواندن را مشخص كنيد"
    private val readPageCountErrorMessage = "لطفا تعداد صفحات خوانده شده را وارد كنيد"
    private val readSelectBookErrorMessage = "لطفا نام كتاب را وارد كنيد"

    var books : List<Bookdb> = listOf()
    var reads : MutableList<Read> = mutableListOf()
    lateinit var term :Term
    private var listener: OnFragmentInteractionListener? = null
    lateinit var readRepo : ReadRepository
    lateinit var bookRepo : BookRepository
    lateinit var termRepo : TermRepository

    private var selectedBook: Bookdb? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_reads, container, false)

        readRepo = ReadRepository(AppDatabase.getInstance(context!!).readDao())
        bookRepo = BookRepository(AppDatabase.getInstance(context!!).bookDao())
        termRepo = TermRepository(AppDatabase.getInstance(context!!).termDao())

        val adapter = intializeReadList(v)

        launch {
            if (!bookRepo.isBooksExist()) {
                listener!!.failRead()
            }else
                intializeBookList(v)

            term = termRepo.getTerm()!!

            val rs = readRepo.getAllReadsWithBookName()

            if (rs!=null) {
                for (r in rs) {
                    reads.add(Read(r))
                }
            }

            if (reads.size<=0){
                arrangeForShowFirstViewSwitcher(v,false)
            }else{
                arrangeForShowFirstViewSwitcher(v,true)
            }
        }

        readDate = v.read_date

        v.fab.setOnClickListener {
            arrangeToShowSecondViewSwitcher(v)
        }



        v.read_date_lay.setOnClickListener {
            showDatapicker("")
        }
        v.read_date.setOnClickListener {
            showDatapicker("")
        }

        errorTextChangeListner(v.read_page_count_lay, readPageCountErrorMessage)
        errorTextChangeListner(v.read_date_lay, readDateErrorMessage)

        v.read_save.setOnClickListener {
            if (validateToSave(v)) {
                launch {
                    val read = Readdb(0,selectedBook!!.id,v.read_page_count.text.toString().toInt(),v.read_date.text.toString())
                    readRepo.insert(read)
                    adapter.addNewRead(Read(ReadBookdb(read,selectedBook!!.name)))
                    arrangeForShowFirstViewSwitcher(v,true)
                }
            }
        }


        return v
    }

    private fun intializeReadList(v: View): Read_Adapter {
        v.list.layoutManager = LinearLayoutManager(context)
        val adapter = Read_Adapter(context!!, reads,readRepo)
        v.list.adapter = adapter
        return adapter
    }

    private fun intializeBookList(v: View) {
        launch {
            books = bookRepo.getAllBook()!!

            val dataAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, books.map { it.name })
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            v.spinner.adapter = dataAdapter
        }

        v.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                selectedBook = books.single { it.name == adapterView.getItemAtPosition(i) as String }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }
    }

    private fun validateToSave(v:View):Boolean{
        return when {
            selectedBook==null ->{
                Toast.makeText(context!!, readSelectBookErrorMessage,Toast.LENGTH_SHORT).show()
                return false
            }
            v.read_page_count.text.isNullOrEmpty() -> {
                v.read_page_count_lay.error = readPageCountErrorMessage
                return false
            }
            v.read_date.text.isNullOrEmpty() -> {
                v.read_date_lay.error =readDateErrorMessage
                return false
            }
            else -> true
        }
    }

    private fun arrangeToShowSecondViewSwitcher(v: View) {
        v.read_page_count.setText("")
        v.read_page_count_lay.isErrorEnabled = false
        v.read_date.setText("")
        v.read_date_lay.isErrorEnabled = false
        if (v.switcher.displayedChild==0)
            v.switcher.showNext()
        v.fab.hide()
    }


    private fun arrangeForShowFirstViewSwitcher(v: View, isListShow :Boolean) {
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


    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {

        val selectedDate = PersianCalendar().apply {
            setPersianDate(year, monthOfYear, dayOfMonth)
        }
//        val start = PersianDateParser(term.startDate).persianDate.timeInMillis
//        val end = PersianDateParser(term.endDate).persianDate.timeInMillis
//
//        if (selectedDate.timeInMillis in (start + 1) until end)
            readDate.setText(selectedDate.persianShortDate)
//        else {
//            readDate.setText("")
//
//        }

    }

    interface OnFragmentInteractionListener {
        fun failRead()
    }

}

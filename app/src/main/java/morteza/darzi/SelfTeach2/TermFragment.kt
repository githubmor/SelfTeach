package morteza.darzi.SelfTeach2



import BL.Term
import BL.TermRepository
import BL.Ultility
import DAL.AppDatabase
import DAL.Termdb
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_term_first.view.*
import kotlinx.android.synthetic.main.include_term_add.*
import kotlinx.android.synthetic.main.include_term_add.view.*
import kotlinx.android.synthetic.main.include_term_empty.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class TermFragment : BaseDatePickerFragment() {

    private lateinit var selectedTermType: termType
    private val startDateError = "لطفا تاریخ شروع ترم را مشخص کنید"
    private val endDateError = "لطفا تاریخ پایان ترم را مشخص کنید"

    override val title: String
        get() = "ترم"


    private var listener: OnFragmentInteractionListener? = null
    private var term :Term ? = null
    private val startTag = "start"
    private val endTag = "end"
    lateinit var termRepository : TermRepository
    var isTermEdit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getInstance(context!!)
        termRepository = TermRepository(database.termDao())
    }

    private lateinit var fragmentView:View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentView = inflater.inflate(R.layout.fragment_term_first, container, false)

        intializeBeforeSuspend()
        intializeSuspend()
        intializeNotRelatedToSuspend()

        return fragmentView
    }

    private fun intializeBeforeSuspend() {
        showViewSwitcher(false)
    }

    private fun showViewSwitcher(isShow:Boolean) = if (isShow){
        fragmentView.switcher.visibility = View.VISIBLE
        fragmentView.indicator_term_first.visibility = View.GONE
    }else{
        fragmentView.switcher.visibility = View.GONE
        fragmentView.indicator_term_first.visibility = View.VISIBLE
    }

    private fun intializeSuspend() {
        launch {
            delay(500)

            term = termRepository.getTerm()

            intializeAfterSuspend()
        }
    }
    private fun intializeAfterSuspend() {
        if (term!=null) {
            show_EditTermView()
        }
        showViewSwitcher(true)
    }

    private fun show_EditTermView() {
        isTermEdit = true
        showTermView()
        loadTermInView()
    }

    private fun showTermView() {
        if (fragmentView.switcher.displayedChild==0)
            fragmentView.switcher.showNext()
    }

    private fun loadTermInView() {
        fragmentView.term_type.setSelection(termType.values().single{it.typeName==term!!.type}.ordinal)
        fragmentView.term_start_date.setText(term?.startDate)
        fragmentView.term_end_date.setText(term?.endDate)
    }

    private fun intializeNotRelatedToSuspend() {
        fragmentView.add_new_term.setOnClickListener {
            showTermView()
        }

        fragmentView.term_start_date.setOnClickListener {
            showDatapicker(startTag)
        }
        fragmentView.term_start_date_lay.setOnClickListener {
            showDatapicker(startTag)
        }

        fragmentView.term_end_date.setOnClickListener {
            showDatapicker(endTag)
        }
        fragmentView.term_end_date_lay.setOnClickListener {
            showDatapicker(endTag)
        }

        intializeSpinner()

        errorTextChangeListner(fragmentView.term_start_date_lay, startDateError)
        errorTextChangeListner(fragmentView.term_end_date_lay,endDateError)

        fragmentView.term_save.setOnClickListener {
            if(validateToSave()) {
                getTermAndSave()
            }
        }
    }

    private fun intializeSpinner() {
        val dataAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, termType.values().map { it.typeName })
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fragmentView.term_type.adapter = dataAdapter

        fragmentView.term_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                selectedTermType = termType.values().single { it.typeName == adapterView.getItemAtPosition(i) as String }

                    fragmentView.term_start_date.setText(Ultility.getStartDate(selectedTermType))
                    fragmentView.term_end_date.setText(Ultility.getEndDate(selectedTermType))
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }
    }

    private fun validateToSave():Boolean{
        return when {
            fragmentView.term_start_date.text.isNullOrEmpty() -> {
                fragmentView.term_start_date_lay.error = startDateError
                false
            }
            fragmentView.term_end_date.text.isNullOrEmpty() -> {
                fragmentView.term_end_date_lay.error =endDateError
                false
            }
            else -> true
        }
    }

    private fun getTermAndSave() {
        launch {
            if (isTermEdit){

                GetTermFromViewData()

                termRepository.update(term!!)

                withContext(Dispatchers.Main) {
                    Toast.makeText(context,  selectedTermType.typeName + " به روز شد", Toast.LENGTH_SHORT).show()
                    listener!!.onSaveTermComplete()
                }
            }else {
                term = Term(Termdb(0,selectedTermType.name,fragmentView.term_start_date.text.toString(),fragmentView.term_end_date.text.toString()))

                termRepository.insert(term!!)
                withContext(Dispatchers.Main) {
                    Toast.makeText(context,  selectedTermType.typeName + " ذخیره شد", Toast.LENGTH_SHORT).show()
                    listener!!.onSaveTermComplete()
                }
            }
        }
    }

    private fun GetTermFromViewData() {
        term!!.type = selectedTermType.name
        term!!.startDate = fragmentView.term_start_date.text.toString()
        term!!.endDate = fragmentView.term_end_date.text.toString()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this.clearFindViewByIdCache()
    }
    override val selectableDateList: Array<PersianCalendar>?
        get() {
            val start = PersianCalendar().apply { addPersianDate(PersianCalendar.MONTH,-4)}.persianShortDate
            val end = PersianCalendar().apply { addPersianDate(PersianCalendar.MONTH,4)}.persianShortDate
            return  Ultility.getTermabledays(start,end)
        }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {

        val tag = view!!.tag
        val d = PersianCalendar()
        d.setPersianDate(year, monthOfYear, dayOfMonth)

        if (tag == startTag) {
            term_start_date.setText(d.persianShortDate)
            term_start_date_lay.isErrorEnabled = false
        } else if (tag == endTag) {
            term_end_date.setText(d.persianShortDate)
            term_end_date_lay.isErrorEnabled = false
        }
    }
    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onSaveTermComplete()
    }
}
enum class termType(val typeName:String){
    nimsalAvl("ترم تحصیلی اول"),
    nimsalDovom("ترم تحصیلی دوم"),
    termTabestan("ترم تحصیلی تابستان"),
    termManual("دوره آزاد"),
}

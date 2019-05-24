package morteza.darzi.SelfTeach



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
import kotlinx.android.synthetic.main.include_term_add.*
import kotlinx.android.synthetic.main.include_term_add.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class TermEditFragment : BaseDatePickerFragment() {
    override val selectableDateList: Array<PersianCalendar>?
        get() {
            val start = PersianCalendar().apply { addPersianDate(PersianCalendar.MONTH,-4)}.persianShortDate
            val end = PersianCalendar().apply { addPersianDate(PersianCalendar.MONTH,4)}.persianShortDate
            return  Ultility.getTermabledays(start,end)
        }

    private lateinit var selectedTermType: termType
    private val startDateError = "لطفا تاریخ شروع ترم را مشخص کنید"
    private val endDateError = "لطفا تاریخ پایان ترم را مشخص کنید"

    override val title: String
        get() = "ترم"


    private var listener: OnFragmentInteractionListener? = null
    private var term :Term ? = null
    private val startTag = "start"
    private val endTag = "end"
    lateinit var termRep : TermRepository
    var isTermEdit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = AppDatabase.getInstance(context!!)
        termRep = TermRepository(database.termDao())

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.include_term_add, container, false)

        intializeBeforeSuspend(v)
        intializeSuspend(v)

        intializeNotRelatedToSuspend(v)

        return v
    }

    private fun intializeBeforeSuspend(v:View) {
//        v.switcher.visibility = View.GONE
        v.term_type.isEnabled = false
        v.term_start_date_lay.isEnabled = false
        v.term_end_date_lay.isEnabled = false
        v.term_save.isEnabled = false
        v.indicator_add_term.visibility = View.VISIBLE
    }

    private fun intializeSuspend(v: View) {
        launch {
            val bills = termRep.getTerm()

            if (bills!=null) {
                term = bills
                isTermEdit = true
            }
            intializeAfterSuspend(v)
        }
    }
    private fun intializeAfterSuspend(v: View) {
        if (term!=null) {
            show_EditTermView(v)
        }
//        v.switcher.visibility = View.VISIBLE
        v.term_type.isEnabled = true
        v.term_save.isEnabled = true
        v.term_start_date_lay.isEnabled = true
        v.term_end_date_lay.isEnabled = true
        v.indicator_add_term.visibility = View.GONE
    }

    private fun show_EditTermView(v: View) {
//        showTermView(v)
        loadTermInView(v)
    }

//    private fun showTermView(v: View) {
//        if (v.switcher.displayedChild==0)
//            v.switcher.showNext()
//    }

    private fun loadTermInView(v: View) {
        v.term_type.setSelection(termType.values().single{it.typeName==term!!.type}.ordinal)
        v.term_start_date.setText(term?.startDate)
        v.term_end_date.setText(term?.endDate)
    }

    private fun intializeNotRelatedToSuspend(v: View) {
//        v.add_new_term.setOnClickListener {
//            showTermView(v)
//        }

        v.term_start_date.setOnClickListener {
            showDatapicker(startTag)
        }
        v.term_start_date_lay.setOnClickListener {
            showDatapicker(startTag)
        }

        v.term_end_date.setOnClickListener {
            showDatapicker(endTag)
        }
        v.term_end_date_lay.setOnClickListener {
            showDatapicker(endTag)
        }

        intializeSpinner(v)

        errorTextChangeListner(v.term_start_date_lay, startDateError)
        errorTextChangeListner(v.term_end_date_lay,endDateError)

        v.term_save.setOnClickListener {
            if(validateToSave(v)) {
                getTermAndSave(v)
            }
        }
    }

    private fun intializeSpinner(v: View) {
        val dataAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, termType.values().map { it.typeName })
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        v.term_type.adapter = dataAdapter

        v.term_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                selectedTermType = termType.values().single { it.typeName == adapterView.getItemAtPosition(i) as String }

                if (!isTermEdit) {
                    v.term_start_date.setText(Ultility.getStartDate(selectedTermType))
                    v.term_end_date.setText(Ultility.getEndDate(selectedTermType))
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }
    }

    private fun validateToSave(v:View):Boolean{
        return when {
            v.term_start_date.text.isNullOrEmpty() -> {
                v.term_start_date_lay.error = startDateError
                false
            }
            v.term_end_date.text.isNullOrEmpty() -> {
                v.term_end_date_lay.error =endDateError
                false
            }
            else -> true
        }
    }

    private fun getTermAndSave(v: View) {
        launch {
            if (isTermEdit){//TODO اینجا نباید اصلا ایجاد ترم باشد . فقط ویرایش
                term!!.type = selectedTermType.name
                term!!.startDate = v.term_start_date.text.toString()
                term!!.endDate = v.term_end_date.text.toString()

                termRep.update(term!!)
                withContext(Dispatchers.Main) {
                    Toast.makeText(context,  selectedTermType.typeName + " به روز شد", Toast.LENGTH_SHORT).show()
                    listener!!.onSaveTermComplete()
                }
            }else {
                term = Term(Termdb(0,selectedTermType.name,v.term_start_date.text.toString(),v.term_end_date.text.toString()))

                termRep.insert(term!!)
                withContext(Dispatchers.Main) {
                    Toast.makeText(context,  selectedTermType.typeName + " ذخیره شد", Toast.LENGTH_SHORT).show()
                    listener!!.onSaveTermComplete()
                }
            }
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

    override fun onDestroyView() {
        super.onDestroyView()
        this.clearFindViewByIdCache()
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

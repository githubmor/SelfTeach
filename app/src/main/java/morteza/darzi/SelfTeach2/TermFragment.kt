package morteza.darzi.SelfTeach2

//import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog
//import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sardari.daterangepicker.utils.PersianCalendar
import core.Term
import core.Ultility
import core.services.TermService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import morteza.darzi.SelfTeach2.databinding.FragmentTermFirstBinding


class TermFragment : BaseDatePickerFragment() {

//    private lateinit var selectedTermType: TermType
//    private val startDateError = "لطفا تاریخ شروع ترم را مشخص کنید"
//    private val endDateError = "لطفا تاریخ پایان ترم را مشخص کنید"

    override val title: String
        get() = "ترم"


    private var listener: OnFragmentInteractionListener? = null
    private lateinit var term: Term
    private val isRange = true

    //    private val endTag = "end"
    private lateinit var termRepository: TermService
    private var isTermEdit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        termRepository = TermService(requireContext())
    }

    private var _binding: FragmentTermFirstBinding? = null
    private val fragmentView get() = _binding!!
    private val includeTermAddBinding get() = fragmentView.includeTermAddInc
//    private val includeTermEmptyBinding get() = fragmentView.includeTermEmptyInc


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentTermFirstBinding.inflate(inflater, container, false)

        intializeBeforeSuspend()
        intializeSuspend()
        intializeNotRelatedToSuspend()

        return fragmentView.root
    }

    private fun intializeBeforeSuspend() {
        showViewSwitcher(false)

    }

    private fun showViewSwitcher(isShow: Boolean) = if (isShow) {
        fragmentView.switcher.visibility = View.VISIBLE
        fragmentView.loaderTermFirst.visibility = View.GONE
    } else {
        fragmentView.switcher.visibility = View.GONE
        fragmentView.loaderTermFirst.visibility = View.VISIBLE
    }

    private fun intializeSuspend() {
        launch {
            delay(500)

            term = termRepository.getTerm()

            intializeAfterSuspend()
        }
    }

    private fun intializeAfterSuspend() {
        loadDefaultDateInView()
        if (term.isSaved()) {
            isTermEdit = true
            showEditTermView()
        }
        showViewSwitcher(true)
    }

    private fun showEditTermView() {
        showTermView()
        loadTermInView()
    }

    private fun showTermView() {
        if (fragmentView.switcher.displayedChild == 0)
            fragmentView.switcher.showNext()
    }

    private fun loadTermInView() {
        when {
            TermType.NimsalAvl.typeName == term.name -> {
//                includeTermAddBinding.avalCard.strokeColor = Color.GREEN
                includeTermAddBinding.avalCard.strokeWidth = 5
            }
            TermType.NimsalDovom.typeName == term.name -> {
//                includeTermAddBinding.dovomCard.strokeColor = Color.GREEN
                includeTermAddBinding.dovomCard.strokeWidth = 5
            }
            TermType.TermTabestan.typeName == term.name -> {
//                includeTermAddBinding.tabeCard.strokeColor = Color.GREEN
                includeTermAddBinding.tabeCard.strokeWidth = 5
            }
            TermType.TermManual.typeName == term.name -> {
//                includeTermAddBinding.azadCard.strokeColor = Color.GREEN
                includeTermAddBinding.azadCard.strokeWidth = 5
                loadAzadDateInView()
            }
        }

//        includeTermAddBinding.termType.setSelection(TermType.values().single { it.typeName == term.name }.ordinal)
//        includeTermAddBinding.termStartDate.setText(term.startDate)
//        includeTermAddBinding.termEndDate.setText(term.endDate)
    }

    private fun loadAzadDateInView() {
        includeTermAddBinding.azadStart.text = term.startDate
        includeTermAddBinding.azadEnd.text = term.endDate
        includeTermAddBinding.azadDuration.text = Ultility.getDuration(TermType.TermManual)//TODO باید حساب کنیم
    }

    private fun loadDefaultDateInView() {
        includeTermAddBinding.avalStart.text = Ultility.getStartDate(TermType.NimsalAvl)
        includeTermAddBinding.avalEnd.text = Ultility.getEndDate(TermType.NimsalAvl)
        includeTermAddBinding.avalDuration.text = Ultility.getDuration(TermType.NimsalAvl)

        includeTermAddBinding.dovomStart.text = Ultility.getStartDate(TermType.NimsalDovom)
        includeTermAddBinding.dovomEnd.text = Ultility.getEndDate(TermType.NimsalDovom)
        includeTermAddBinding.dovomDuration.text = Ultility.getDuration(TermType.NimsalDovom)

        includeTermAddBinding.tabeStart.text = Ultility.getStartDate(TermType.TermTabestan)
        includeTermAddBinding.tabeEnd.text = Ultility.getEndDate(TermType.TermTabestan)
        includeTermAddBinding.tabeDuration.text = Ultility.getDuration(TermType.TermTabestan)

        includeTermAddBinding.azadStart.text = Ultility.getStartDate(TermType.TermManual)
        includeTermAddBinding.azadEnd.text = Ultility.getEndDate(TermType.TermManual)
        includeTermAddBinding.azadDuration.text = Ultility.getDuration(TermType.TermManual)

    }

    private fun intializeNotRelatedToSuspend() {

//        includeTermEmptyBinding.addNewTerm.setOnClickListener {
//            loadDefaultDateInView()
//            showTermView()
//        }

        includeTermAddBinding.azadCard.setOnClickListener {
            showDatapicker(isRange)
        }
        includeTermAddBinding.avalCard.setOnClickListener {
            termSave(TermType.NimsalAvl, Ultility.getStartDate(TermType.NimsalAvl), Ultility.getEndDate(TermType.NimsalAvl))
        }

        includeTermAddBinding.dovomCard.setOnClickListener {
            termSave(TermType.NimsalDovom, Ultility.getStartDate(TermType.NimsalDovom), Ultility.getEndDate(TermType.NimsalDovom))
        }
        includeTermAddBinding.tabeCard.setOnClickListener {
            termSave(TermType.TermTabestan, Ultility.getStartDate(TermType.TermTabestan), Ultility.getEndDate(TermType.TermTabestan))
        }


//        errorTextChangeListner(includeTermAddBinding.termStartDateLay, startDateError)
//        errorTextChangeListner(includeTermAddBinding.termEndDateLay, endDateError)


    }

//    private fun SaveTerm(){
////        if (validateToSave()) {
//            getTermAndSave()
////        }
//    }

//    private fun intializeSpinner() {
//        val dataAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, TermType.values().map { it.typeName })
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        includeTermAddBinding.termType.adapter = dataAdapter
//
//        includeTermAddBinding.termType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
//                selectedTermType = TermType.values().single { it.typeName == adapterView.getItemAtPosition(i) as String }
//                if (!isTermEdit) {
//                    includeTermAddBinding.termStartDate.setText(Ultility.getStartDate(selectedTermType))
//                    includeTermAddBinding.termEndDate.setText(Ultility.getEndDate(selectedTermType))
//                }
//
//            }
//
//            override fun onNothingSelected(adapterView: AdapterView<*>) {
//
//            }
//        }
//    }

//    private fun validateToSave(): Boolean {
//        return when {
//            includeTermAddBinding.termStartDate.text.isNullOrEmpty() -> {
//                includeTermAddBinding.termStartDateLay.error = startDateError
//                false
//            }
//            includeTermAddBinding.termEndDate.text.isNullOrEmpty() -> {
//                includeTermAddBinding.termEndDateLay.error = endDateError
//                false
//            }
//            isDateValidate(includeTermAddBinding.termStartDate.text.toString(), includeTermAddBinding.termEndDate.text.toString()) -> true
//            else -> true
//        }
//    }

//    private fun isDateValidate(text: String, text1: String): Boolean {
//        //TODO باید چک شود تاریخ پایان بعد از تاریخ شروع باشد
//        return text == text1
//    }

    private fun termSave(type: TermType, start: String, end: String) {
        launch {
            getTermObject(type, start, end)
            if (isTermEdit) {

                val saved = termRepository.update(term)

                if (!saved)
                    throw IllegalArgumentException("به روز رسانی ترم دچار مشکل شده. لطفا به سازنده برنامه اطلاع دهید")

                withContext(Dispatchers.Main) {
                    Toast.makeText(context, term.name + " به روز شد", Toast.LENGTH_SHORT).show()
                    listener!!.onSaveTermComplete()
                }
            } else {

                val saved = termRepository.insert(term)

                withContext(Dispatchers.Main) {
                    if (saved) {
                        Toast.makeText(context, term.name + " ذخیره شد", Toast.LENGTH_SHORT).show()
                        listener!!.onSaveTermComplete()
                    } else
                        throw IllegalArgumentException("ذخیره سازی ترم دچار مشکل شده . لطفا به سازنده با ایمیل pc2man@gmail.com اطلاع دهید")
                }
            }
        }
    }

    private fun getTermObject(type: TermType, start: String, end: String) {
        term.name = type.name
        term.startDate = start
        term.endDate = end
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

    override val selectableDateList: Array<PersianCalendar>?
        get() {
            //2 month kamtar for bug
            val start = PersianCalendar().apply { addPersianDate(PersianCalendar.MONTH, -8) }.persianShortDate
            val end = PersianCalendar().apply { addPersianDate(PersianCalendar.MONTH, 12) }.persianShortDate
            return Ultility.arrayOfPersianCalendars(start, end)
        }
    override val mindate: PersianCalendar
        get() = PersianCalendar().apply { addPersianDate(PersianCalendar.MONTH, -8) }
    override val maxdate: PersianCalendar
        get() = PersianCalendar().apply { addPersianDate(PersianCalendar.MONTH, 12) }

    override fun onRangeDateSelected(startDate: PersianCalendar, endDate: PersianCalendar) {
        //TODO اینجا باید بک وقفه ای اتفاق بیافتد تا اطلاعات ترم آزاد به کاربر نمایش داده شود بعد سیو شود
//        getTermObject(TermType.TermManual,startDate.persianShortDate,endDate.persianShortDate)
//        loadAzadDateInView()
        termSave(TermType.TermManual, startDate.persianShortDate, endDate.persianShortDate)
    }

    override fun onSingleDateSelected(date: PersianCalendar?) {
        TODO("Not yet implemented")
    }
//    override fun onDateSet(datapicker: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
//
//        val tag = datapicker!!.tag
//        val selectedDate = PersianCalendar()
//        selectedDate.setPersianDate(year, monthOfYear, dayOfMonth)
//
//        if (tag == startTag) {
//            if (!includeTermAddBinding.termEndDate.text.isNullOrEmpty()) {
//                val end = PersianCalendar()
//                end.parse(includeTermAddBinding.termEndDate.text.toString())
//                end.addPersianDate(Calendar.MONTH,-2)//this is for bug
//                if (selectedDate.timeInMillis >= end.timeInMillis) {
//                    includeTermAddBinding.termStartDate.setText("")
//                    includeTermAddBinding.termStartDateLay.error = "تاریخ شروع ترم باید قبل از تاریخ پایان باشد"
//                }else{
//                    includeTermAddBinding.termStartDate.setText(selectedDate.persianShortDate)
//                    includeTermAddBinding.termStartDateLay.isErrorEnabled = false
//                }
//            }else {
//                includeTermAddBinding.termStartDate.setText(selectedDate.persianShortDate)
//                includeTermAddBinding.termStartDateLay.isErrorEnabled = false
//            }
//
//        } else if (tag == endTag) {
//            if (!includeTermAddBinding.termStartDate.text.isNullOrEmpty()) {
//                val start = PersianCalendar()
//                start.parse(includeTermAddBinding.termStartDate.text.toString())
//                start.addPersianDate(Calendar.MONTH,-2)//this is for bug
//                if (selectedDate.timeInMillis <= start.timeInMillis) {
//                    includeTermAddBinding.termEndDate.setText("")
//                    includeTermAddBinding.termEndDateLay.error = "تاریخ پایان ترم باید بعد از تاریخ شروع باشد"
//                }else{
//                    includeTermAddBinding.termEndDate.setText(selectedDate.persianShortDate)
//                    includeTermAddBinding.termEndDateLay.isErrorEnabled = false
//                }
//            }else {
//                includeTermAddBinding.termEndDate.setText(selectedDate.persianShortDate)
//                includeTermAddBinding.termEndDateLay.isErrorEnabled = false
//            }
//        }
//    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onSaveTermComplete()
    }
}

enum class TermType(val typeName: String) {
    NimsalAvl("ترم تحصیلی اول"),
    NimsalDovom("ترم تحصیلی دوم"),
    TermTabestan("ترم تحصیلی تابستان"),
    TermManual("دوره آزاد"),
}

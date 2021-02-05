package morteza.darzi.SelfTeach2

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

    override val title: String
        get() = "ترم"


    private var listener: OnFragmentInteractionListener? = null
    private lateinit var term: Term
    private val isRange = true
    private lateinit var termRepository: TermService
    private var isTermEdit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        termRepository = TermService(requireContext())
    }

    private var _binding: FragmentTermFirstBinding? = null
    private val fragmentView get() = _binding!!
    private val includeTermAddBinding get() = fragmentView.includeTermAddInc

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
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

            try {
                term = termRepository.getTerm()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
            }

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
                includeTermAddBinding.avalCard.strokeWidth = 5
            }
            TermType.NimsalDovom.typeName == term.name -> {
                includeTermAddBinding.dovomCard.strokeWidth = 5
            }
            TermType.TermTabestan.typeName == term.name -> {
                includeTermAddBinding.tabeCard.strokeWidth = 5
            }
            TermType.TermManual.typeName == term.name -> {
                includeTermAddBinding.azadCard.strokeWidth = 5
                loadAzadDateInView()
            }
        }

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

    }

    private fun termSave(type: TermType, start: String, end: String) {
        launch {
            getTermObject(type, start, end)
            if (isTermEdit) {

                val saved = termRepository.update(term)

                if (!saved) {
                    Toast.makeText(requireContext(),
                            "به روز رسانی ترم دچار مشکل شده. لطفا به سازنده برنامه اطلاع دهید", Toast.LENGTH_LONG).show()
                }

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
                        Toast.makeText(requireContext(),
                                "ذخیره سازی ترم دچار مشکل شده ، لطفا به سازنده اطلاع دهید",
                                Toast.LENGTH_LONG).show()

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


    override val mindate: PersianCalendar
        get() = PersianCalendar().apply { addPersianDate(PersianCalendar.MONTH, -8) }
    override val maxdate: PersianCalendar
        get() = PersianCalendar().apply { addPersianDate(PersianCalendar.MONTH, 12) }

    override fun onRangeDateSelected(startDate: PersianCalendar, endDate: PersianCalendar) {
        //TODO اینجا باید بک وقفه ای اتفاق بیافتد تا اطلاعات ترم آزاد به کاربر نمایش داده شود بعد سیو شود
        termSave(TermType.TermManual, startDate.persianShortDate, endDate.persianShortDate)
    }

    override fun onSingleDateSelected(date: PersianCalendar?) {
        TODO("Not Need implementation")
    }
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

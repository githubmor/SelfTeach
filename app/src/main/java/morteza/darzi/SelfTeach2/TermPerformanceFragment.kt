package morteza.darzi.SelfTeach2


import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AlertDialog
import com.github.lzyzsd.circleprogress.DonutProgress
import com.google.android.material.textview.MaterialTextView
import core.*
import core.services.BookService
import core.services.TermService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import morteza.darzi.SelfTeach2.databinding.FragmentPerformanceBinding

class TermPerformanceFragment : BaseFragment() {
    override val title: String
        get() = "خود خوان"

    private lateinit var termPerformance: TermPerformance
    private lateinit var donutProgress: DonutProgress
    private var listener: OnFragmentInteractionListener? = null

    private var _binding: FragmentPerformanceBinding? = null
    private val fragmentView get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentPerformanceBinding.inflate(inflater, container, false)

        intializeBeforeSuspend()
        intializeSuspend()

        return fragmentView.root
    }

    private fun intializeBeforeSuspend() {
        showLoader(true)
        donutProgress = fragmentView.performanceCircle
    }

    private fun showLoader(isShow: Boolean) {
        fragmentView.indicPerformance.visibility = if (isShow) VISIBLE else GONE
        fragmentView.today.visibility = if (isShow) GONE else VISIBLE
        fragmentView.perDay.visibility = if (isShow) GONE else VISIBLE
        fragmentView.dayRemind.visibility = if (isShow) GONE else VISIBLE
        fragmentView.progressBar.visibility = if (isShow) GONE else VISIBLE
        fragmentView.readList.visibility = if (isShow) GONE else VISIBLE
        fragmentView.performanceCircle.visibility = if (isShow) GONE else VISIBLE
        fragmentView.todayLab.visibility = if (isShow) GONE else VISIBLE
        fragmentView.perDayLab.visibility = if (isShow) GONE else VISIBLE
        fragmentView.safe1.visibility = if (isShow) GONE else VISIBLE
        fragmentView.safe2.visibility = if (isShow) GONE else VISIBLE

    }

    private fun intializeSuspend() {

        launch {

            delay(500)

            createPerformance()

            intializeAfterSusped()
        }
    }

    private suspend fun createPerformance() {
        val bookService = BookService(requireContext())
        if (!bookService.anyBooksExist()) {
            listener!!.failPerformance()
        }

        val term = TermService(requireContext()).getTerm()
        val books = bookService.getAllBookWithListRead()

        termPerformance = TermPerformance(term, books)

        suggest = Suggestion(term, books).getBookSuggestList(termPerformance)

    }

    fun showSummery(summery: ISummery) {
        val builder: AlertDialog.Builder? = activity?.let {
            AlertDialog.Builder(it)
        }

        builder?.setMessage(summery.getSummery())?.setTitle(summery.title)

        val dialog: AlertDialog? = builder?.create()

        dialog?.show()
    }

    private fun intializeAfterSusped() {

        showLoader(false)

        loadPerformanceDataToUI()

//        fragmentView.per_day.setOnClickListener { showSummery(AvregPerDaySummery(termPerformance)) }
//        fragmentView.today.setOnClickListener { showSummery(TodaySummery(termPerformance)) }
    }

    private fun loadPerformanceDataToUI() {

        animatePerformanceCircle(termPerformance.performance)

        loadPerformanceData()

        loadBookSuggestionData()
    }

    private fun loadPerformanceData() {
        fragmentView.today.text = termPerformance.pageReadTo100Percent.toString()
        fragmentView.perDay.text = termPerformance.avgPagePerDayRemind.toString()

        fragmentView.dayRemind.text = "( " + termPerformance.term.dayPast + "/" + termPerformance.term.dayCount + " ) روز"

        fragmentView.progressBar.progress = termPerformance.term.dayPastPercent
    }

    private lateinit var suggest: List<ReadSuggestion>

    private fun loadBookSuggestionData() {

        suggest.forEach {
            val te = MaterialTextView(requireContext())
            when {
                it.isFoghBarname -> te.text = "فوق برنامه : " + it.name + it.readSuggest + " صفحه بخوان"
                else -> te.text = it.name + it.readSuggest + " صفحه بخوان"
            }
//            if (Build.VERSION.SDK_INT < 23) {
//                te.setTextAppearance(requireContext(), R.style.creditCardText)
//            } else {
//                te.setTextAppearance(R.style.creditCardText)
//            }
//            te.setTextColor(fragmentView.perDayLab.textColors)
            te.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

            fragmentView.readList.addView(te)
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


    private fun animatePerformanceCircle(p: Float) {
        var performance = p
        if (performance > 100) {
            performance = 100F
        }

        val red = -0x40f600
        val green = Ultility.getColorToGreen(performance * 1.0 / 100)

        val storkColorAnim = ObjectAnimator.ofObject(donutProgress,
                "FinishedStrokeColor",
                HsvEvaluator(),
                red, green)

        val vv = ValueAnimator.ofInt(0, performance.toInt()).apply {
            addUpdateListener {
                val animateValue = it.animatedValue as Int
                donutProgress.progress = animateValue.toFloat()
            }
        }


        val animatorSet = AnimatorSet()
        animatorSet.playTogether(storkColorAnim, vv)

        animatorSet.startDelay = 1000
        animatorSet.duration = timeCalculator(performance)
        animatorSet.interpolator = LinearInterpolator()
        animatorSet.start()

    }

    private fun timeCalculator(performance: Float): Long {
        return if (performance < 20) {
            500
        } else {
            (performance * 2500 / 100).toLong()
        }
    }

    interface OnFragmentInteractionListener {
        fun failPerformance()
    }

}
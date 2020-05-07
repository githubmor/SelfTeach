package morteza.darzi.SelfTeach2


import BL.*
//import DAL.AppDatabase
//import DAL.BookRepository
//import DAL.TermRepository
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.TextView
import com.github.lzyzsd.circleprogress.DonutProgress
import kotlinx.android.synthetic.main.fragment_performance.view.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TermPerformanceFragment : BaseFragment() {
    override val title: String
        get() = "خود خوان"

    private lateinit var termPerformance: TermPerformance
    private lateinit var donutProgress: DonutProgress
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var fragmentView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentView = inflater.inflate(R.layout.fragment_performance, container, false)

        intializeBeforeSuspend()
        intializeSuspend()

        return fragmentView
    }

    private fun intializeBeforeSuspend() {
        showLoader(true)
        donutProgress = fragmentView.performanceCircle
    }

    private fun showLoader(isShow: Boolean) {
        if (isShow) {
            fragmentView.indic_performance.visibility = VISIBLE
            fragmentView.today.visibility = GONE
            fragmentView.per_day.visibility = GONE
            fragmentView.day_remind.visibility = GONE
            fragmentView.progressBar.visibility = GONE
            fragmentView.read_list.visibility = GONE
            fragmentView.performanceCircle.visibility = GONE
            fragmentView.today_lab.visibility = GONE
            fragmentView.per_day_lab.visibility = GONE
            fragmentView.safe_1.visibility = GONE
            fragmentView.safe_2.visibility = GONE
        } else {
            fragmentView.indic_performance.visibility = GONE
            fragmentView.today.visibility = VISIBLE
            fragmentView.per_day.visibility = VISIBLE
            fragmentView.day_remind.visibility = VISIBLE
            fragmentView.progressBar.visibility = VISIBLE
            fragmentView.read_list.visibility = VISIBLE
            fragmentView.performanceCircle.visibility = VISIBLE
            fragmentView.today_lab.visibility = VISIBLE
            fragmentView.per_day_lab.visibility = VISIBLE
            fragmentView.safe_1.visibility = VISIBLE
            fragmentView.safe_2.visibility = VISIBLE
        }
    }

    private fun intializeSuspend() {

        launch {

            delay(500)

            createPerformance()

            intializeAfterSusped()
        }
    }

    private suspend fun createPerformance() {
        val bookService = BookService(context!!)
        if (!bookService.anyBooksExist()) {
            listener!!.failPerformance()
        }

        val term = TermService(context!!).getTerm()!!
        val books = bookService.getAllBookWithListRead()!!

        termPerformance = TermPerformance(term, books)

        suggest = Suggestion(term, books).getBookSuggestList(termPerformance.pageReadTo100Percent)

    }

    private fun intializeAfterSusped() {

        showLoader(false)

        loadPerformanceDataToUI()
    }

    private fun loadPerformanceDataToUI() {

        animatePerformanceCircle(termPerformance.performance)

        loadPerformanceData()

        loadBookSuggestionData()
    }

    private fun loadPerformanceData() {
        fragmentView.today.text = termPerformance.pageReadTo100Percent.toString()
        fragmentView.per_day.text = termPerformance.avgPagePerDayRemind.toString()

        fragmentView.day_remind.text = "( " + termPerformance.term.dayPast + "/" + termPerformance.term.dayCount + " ) روز"

        fragmentView.progressBar.progress = termPerformance.term.dayPastPercent
    }

    private lateinit var suggest: List<BookSuggestion>

    private fun loadBookSuggestionData() {

        suggest.forEach {
            val te = TextView(context)
            te.text = it.name + it.readSuggest() + " صفحه بخوان"
            if (Build.VERSION.SDK_INT < 23) {
                te.setTextAppearance(context, R.style.creditCardText)
            } else {
                te.setTextAppearance(R.style.creditCardText)
            }
            te.setTextColor(fragmentView.per_day_lab.textColors)
            te.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

            fragmentView.read_list.addView(te)
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
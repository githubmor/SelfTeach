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
//    private lateinit var term: Term
//    private lateinit var service: BookRepository
    override val title: String
        get() = "خود خوان"

    private lateinit var termPerformance: TermPerformance
    private lateinit var performanceircle: DonutProgress
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var fragmentView :View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentView = inflater.inflate(R.layout.fragment_performance, container, false)

        intializeBeforeSuspend()
        intializeSuspend()

        return fragmentView
    }

    private fun intializeBeforeSuspend() {
        ShowLoadr(true)
        performanceircle = fragmentView.performanceCircle
    }

    private fun ShowLoadr(isShow:Boolean) {
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
        }else{
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

            CreatePerformance()

            intializeAfterSusped()
        }
    }

    private suspend fun CreatePerformance() {
        val bookService = BookService(context!!)
        if (!bookService.isBooksExist()) {
            listener!!.failPerformance()
        }

        val term = TermService(context!!).getTerm()!!
        val books = bookService.getAllBookWithListRead()!!

        termPerformance = TermPerformance(term,books)

        var remindPage = termPerformance.pageReadTo100Percent

        books.forEach { book ->
            val suggestion =  Suggestion(BookPlan(book),BookPerformance(term,book).pageReadTo100Percent)
            if(suggestion.HasSuggest(remindPage)){
                suggest = suggest + suggestion
                remindPage -= suggestion.suggestRead()
            }
        }

    }

    private fun intializeAfterSusped() {

        ShowLoadr(false)

        LoadPerformanceDataToUI()
    }

    private fun LoadPerformanceDataToUI() {

        animatePerformanceCircle(termPerformance.performance)

        LoadPerformanceData()

        LoadBookSuggestionData()
    }

    private fun LoadPerformanceData() {
        fragmentView.today.text = termPerformance.pageReadTo100Percent.toString()
        fragmentView.per_day.text = termPerformance.avgPagePerDayRemind.toString()

        fragmentView.day_remind.text = termPerformance.term.termDateState

        fragmentView.progressBar.progress = termPerformance.term.dayPastPercent
    }
    private lateinit var suggest : List<Suggestion>

    private fun LoadBookSuggestionData() {

        suggest.forEach {
//            if (it.HasSuggest()) {
                val te = TextView(context)
                te.text = it.bookName + it.suggestRead() + " صفحه بخوان"
                if (Build.VERSION.SDK_INT < 23) {
                    te.setTextAppearance(context, R.style.creditCardText);
                } else {
                    te.setTextAppearance(R.style.creditCardText);
                }
                te.setTextColor(fragmentView.per_day_lab.textColors)
                te.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

                fragmentView.read_list.addView(te)
//            }
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

        val storkColorAnim = ObjectAnimator.ofObject(performanceircle,
                "FinishedStrokeColor",
                HsvEvaluator(),
                red, green)

        val vv = ValueAnimator.ofInt(0,performance.toInt()).apply {
            addUpdateListener {
                val valu = it.animatedValue as Int
                performanceircle.progress = valu.toFloat()
            }
        }


        val animatorSet = AnimatorSet()
        animatorSet.playTogether(storkColorAnim,vv)

        animatorSet.startDelay = 1000
        animatorSet.duration = timecal(performance)
        animatorSet.interpolator = LinearInterpolator()
        animatorSet.start()

    }

    private fun timecal(perf: Float): Long {
        return if (perf < 20) {
            500
        } else {
            (perf * 2500 / 100).toLong()
        }
    }

    interface OnFragmentInteractionListener {
        fun failPerformance()
    }

}
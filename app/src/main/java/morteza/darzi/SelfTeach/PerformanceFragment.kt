package morteza.darzi.SelfTeach


import BL.*
import DAL.AppDatabase
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
import kotlinx.coroutines.launch

class PerformanceFragment : BaseFragment() {
    private lateinit var term: Term
    private lateinit var repository: BookRepository
    override val title: String
        get() = "خود خوان"

    private lateinit var performance: Performance
    private lateinit var performanceircle: DonutProgress
    private var listener: OnFragmentInteractionListener? = null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v = inflater.inflate(R.layout.fragment_performance, container, false)

        intializeBeforeSuspend(v)
        intializeSuspend(v)

        return v
    }

    private fun intializeSuspend(v: View) {

        launch {
            repository = BookRepository(AppDatabase.getInstance(context!!).bookDao())
            if (!repository.isBooksExist()) {
                listener!!.failPerformance()
            }

            term = TermRepository(AppDatabase.getInstance(context!!).termDao()).getTerm()!!
            val books = BookRepository(AppDatabase.getInstance(context!!).bookDao()).getAllBookWithRead()!!.map { Book(it) }

            val pBooks = books.map { PerformanceBook(term,it) }

            performance = Performance(term, pBooks)

            intializeAfterSusped(v)
        }
    }

    private fun intializeAfterSusped(v: View) {

        v.indic_performance.visibility = GONE
        v.today.visibility = VISIBLE
        v.per_day.visibility = VISIBLE
        v.day_remind.visibility = VISIBLE
        v.progressBar.visibility = VISIBLE
        v.read_list.visibility = VISIBLE
        v.performanceCircle.visibility = VISIBLE
        v.today_lab.visibility = VISIBLE
        v.per_day_lab.visibility = VISIBLE

        performanceircle = v.performanceCircle

        animateArcPerformance(performance.performance)

        v.today.text = performance.pageTo100Percent.toInt().toString()
        v.per_day.text = performance.pagePerDayRemind.toInt().toString()

        v.day_remind.text = term.termDateState

//        v.progressBar.animateProgress(1000, 0, term.dayPastPercent);

        v.progressBar.progress = term.dayPastPercent

        for (book in performance.readList()) {
            val te = TextView(context)
            te.text = book + " صفحه بخوان"
            if (Build.VERSION.SDK_INT < 23) {
                te.setTextAppearance(context, R.style.creditCardText);
            } else {
                te.setTextAppearance(R.style.creditCardText);
            }
            te.setTextColor(v.per_day_lab.textColors)
            te.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

            v.read_list.addView(te)
        }
    }

    private fun intializeBeforeSuspend(v: View) {
        v.indic_performance.visibility = View.VISIBLE
        v.today.visibility = GONE
        v.per_day.visibility = GONE
        v.day_remind.visibility = GONE
        v.progressBar.visibility = GONE
        v.read_list.visibility = GONE
        v.performanceCircle.visibility = GONE
        v.today_lab.visibility = GONE
        v.per_day_lab.visibility = GONE
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


    private fun animateArcPerformance(p: Float) {
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
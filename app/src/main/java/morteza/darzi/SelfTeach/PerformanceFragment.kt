package morteza.darzi.SelfTeach


import BL.*
import DAL.AppDatabase
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.TextView
import com.github.lzyzsd.circleprogress.DonutProgress
import kotlinx.android.synthetic.main.fragment_performance.view.*
import kotlinx.coroutines.launch

class PerformanceFragment : BaseFragment() {
    override val title: String
        get() = "خود خوان"

    private lateinit var performance: Performance
    private lateinit var performanceircle: DonutProgress
    private var listener: OnFragmentInteractionListener? = null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v = inflater.inflate(R.layout.fragment_performance, container, false)
        val repository = BookRepository(AppDatabase.getInstance(context!!).bookDao())

        launch {
            if (!repository.isBooksExist()) {
                listener!!.failPerformance()
            }
        }

        performanceircle = v.performanceCircle

        val database = AppDatabase.getInstance(context!!)

        launch {
            val term = TermRepository(database.termDao()).getTerm()!!
            val books = BookRepository(database.bookDao()).getAllBookWithRead()!!.map { Book(it) }

            val pBooks = books.map { PerformanceBook(term,it) }

            performance = Performance(term, pBooks)



            animateArcPerformance(performance.performance())

            v.today.text = performance.pageTo100Percent().toString()
            v.per_day.text = performance.pagePerDayRemind().toString()

            v.day_remind.text = term.termDateState()

            v.progressBar.progress = term.dayPastPercent()

            for (book in performance.readList()) {
                val te = TextView(context)
                te.text = book + " صفحه بخوان"
                te.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

                v.read_list.addView(te)
            }
        }

        return v
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


    private fun animateArcPerformance(p: Int) {
        var performance = p
        if (performance > 100) {
            performance = 100
        }

        val red = -0x40f600
        val green = Ultility.getColorToGreen(performance * 1.0 / 100)

        val storkColorAnim = ObjectAnimator.ofObject(performanceircle,
                "FinishedStrokeColor",
                HsvEvaluator(),
                red, green)

        val performanceAnim = ObjectAnimator.ofFloat(performanceircle,
                "progress",
                0F,
                performance.toFloat()) // anim 2


        val animatorSet = AnimatorSet()
        animatorSet.playTogether(storkColorAnim,performanceAnim)

        animatorSet.startDelay = 1000
        animatorSet.duration = timecal(performance).toLong()
        animatorSet.interpolator = LinearInterpolator()
        animatorSet.start()

    }

    private fun timecal(perf: Int): Int {
        return if (perf < 20) {
            500
        } else {
            perf * 2500 / 100
        }
    }

    interface OnFragmentInteractionListener {
        fun failPerformance()
    }

}
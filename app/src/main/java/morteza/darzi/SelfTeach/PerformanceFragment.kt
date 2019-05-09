package morteza.darzi.SelfTeach


import BL.*
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.github.lzyzsd.circleprogress.DonutProgress

class PerformanceFragment : BaseFragment() {
    override val title: String
        get() = "خود خوان"

    private var teacher: Teacher? = null
    private var performanceircle: DonutProgress? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(FirstChecker.checkLevel()!=TermLevel.Perfermance) {
            Toast.makeText(context!!, "هنوز ترم يا كتابي ثبت نشده", Toast.LENGTH_SHORT).show()
            listener!!.failPerformance()
        }

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_performance, container, false)

        val jdf = JDF()

        teacher = Teacher()
        teacher!!.setNow(jdf.iranianDate)

        val termRemindTxt: TextView
        val book_for_read: TextView
        val page_to100_percent: TextView
        val other_book_for_read: TextView
        val page_perDay_Remind_plan: TextView
        val book_need_toPlane: TextView

        val termDayPercent: ProgressBar



        performanceircle = view.findViewById<View>(R.id.performanceCircle) as DonutProgress
        book_for_read = view.findViewById<View>(R.id.book_for_read) as TextView
        page_to100_percent = view.findViewById<View>(R.id.page_to100_percent) as TextView
        other_book_for_read = view.findViewById<View>(R.id.other_book_for_read) as TextView
        page_perDay_Remind_plan = view.findViewById<View>(R.id.page_perDay_Remind_plan) as TextView
        book_need_toPlane = view.findViewById<View>(R.id.book_need_toPlane) as TextView
        termRemindTxt = view.findViewById<View>(R.id.termRemindTxt) as TextView
        termDayPercent = view.findViewById<View>(R.id.progressBar) as ProgressBar

        SetArcProgressBarPerformance(teacher!!.Performance())

        val gn = teacher!!.BookNameForRead()
        if (gn != "") {
            val bfr = "کتاب <font color=\"#029789\">$gn</font> را در اولین فرصت بخوانید"
            book_for_read.text = Html.fromHtml(bfr)

        } else {
            book_for_read.visibility = View.GONE
        }

        val pt = teacher!!.PageTo100Percent()
        if (pt <= 0)
            page_to100_percent.visibility = View.GONE
        else if (pt > 1000) {
            val pp2 = "<font color=\"#029789\">" + (pt - 1000).toString() + "</font>" + " صفحه امروز باید خوانده شود!. نیاز به فوق برنامه می باشد"
            page_to100_percent.text = Html.fromHtml(pp2)
        } else {
            val pp = "در کل امروز باید <font color=\"#029789\">$pt</font> صفحه خوانده شود . سهم هر کتاب در لیست مشخص شده"
            page_to100_percent.text = Html.fromHtml(pp)
        }


        val br = teacher!!.OtherBookForRead()
        if ("" != br) {
            val o = "کتاب <font color=\"#029789\">$br</font> هم امروز باید خوانده شود"
            other_book_for_read.text = Html.fromHtml(o)
        } else
            other_book_for_read.visibility = View.GONE


        termDayPercent.progress = teacher!!.TermDayPercent()

        if (teacher!!.term!!.DayRemind() >= teacher!!.term!!.DayCount() || teacher!!.term!!.DayRemind() < 0)
            termRemindTxt.text = "هنوز ترم شروع نشده"
        else {
            val brt = teacher!!.term!!.DayRemind().toString()
            val ot = "<font color=\"#029789\">$brt</font> روز از ترم باقی مانده"
            termRemindTxt.text = Html.fromHtml(ot)
        }

        val pr = teacher!!.PagePerDayRemind()
        if (pr > 1000) {
            val prp = "تا پایان ترم " + "<font color=\"#029789\">" + (pr - 1000).toString() + "</font>" + " صفحه در روز باید خوانده شود! نیاز به فوق برنامه می باشد"
            page_perDay_Remind_plan.text = Html.fromHtml(prp)
        } else if (pr <= 0) {
            page_perDay_Remind_plan.visibility = View.GONE
        } else {
            val prp = " روزی <font color=\"#029789\">$pr</font> صفحه تا آخر ترم باید بخوانید"
            page_perDay_Remind_plan.text = Html.fromHtml(prp)
        }


        val bn = teacher!!.BooksNeedPlane()
        if ("" != bn) {
            val ij = "کتاب های <font color=\"#029789\">$bn</font> وضعیت خوبی ندارند! نیاز به فوق برنامه می باشد"
            book_need_toPlane.text = Html.fromHtml(ij)
        } else
            book_need_toPlane.visibility = View.GONE

        return view
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
    internal fun getColor(power: Double): Int {
        val H = power * 80
        val floats = FloatArray(3)
        floats[0] = H.toFloat()
        floats[1] = 1.toFloat()
        floats[2] = 0.7.toFloat()


        return Color.HSVToColor(floats)
    }

    private fun SetArcProgressBarPerformance(performance: Int) {
        var performance = performance
        if (performance > 100) {
            performance = 100
        }

        val red = -0x40f600
        val green = getColor(performance * 1.0 / 100)

        val anim = ObjectAnimator.ofObject(performanceircle,
                "FinishedStrokeColor",
                HsvEvaluator(),
                red, green)

        val `as` = AnimatorSet()
        `as`.playTogether(anim, // anim 1
                ObjectAnimator.ofFloat(performanceircle!!, "progress", 0F, performance.toFloat())) // anim 2
        `as`.startDelay = 2000
        `as`.duration = timecal(performance).toLong()
        `as`.interpolator = LinearInterpolator()
        `as`.start()

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
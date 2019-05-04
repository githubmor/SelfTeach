package morteza.darzi.SelfTeach


import BL.*
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
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
import com.github.lzyzsd.circleprogress.DonutProgress

/**
 * Created by M on 14/12/25.
 */
class TermMain : Fragment() {

    private var teacher: Teacher? = null
    private var performanceircle: DonutProgress? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val jdf = JDF()

        if (teacher!!.IsBooksSet()!!) {//term and books set
            teacher!!.setNow(jdf.iranianDate)

            val termRemindTxt: TextView
            val book_for_read: TextView
            val page_to100_percent: TextView
            val other_book_for_read: TextView
            val page_perDay_Remind_plan: TextView
            val book_need_toPlane: TextView

            val termDayPercent: ProgressBar

            val view = inflater.inflate(R.layout.termmain, container, false)

            performanceircle = view.findViewById<View>(R.id.performanceCircle) as DonutProgress
            book_for_read = view.findViewById<View>(R.id.book_for_read) as TextView
            page_to100_percent = view.findViewById<View>(R.id.page_to100_percent) as TextView
            other_book_for_read = view.findViewById<View>(R.id.other_book_for_read) as TextView
            page_perDay_Remind_plan = view.findViewById<View>(R.id.page_perDay_Remind_plan) as TextView
            book_need_toPlane = view.findViewById<View>(R.id.book_need_toPlane) as TextView
            termRemindTxt = view.findViewById<View>(R.id.termRemindTxt) as TextView
            termDayPercent = view.findViewById<View>(R.id.progressbar) as ProgressBar

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
        } else if (teacher!!.IsTermSet()!!) {//term set without books

            val view = inflater.inflate(R.layout.nobook, container, false)

            val st = view.findViewById<View>(R.id.showTerm) as TextView
            val sd = view.findViewById<View>(R.id.Desc) as TextView
            val ad = view.findViewById<View>(R.id.ToADDBook) as Button

            val start = "<font color=\"#029789\">" + teacher!!.term!!.startDate + "</font>"
            val end = "<font color=\"#029789\">" + teacher!!.term!!.endDate + "</font>"

            st.text = "تاریخ شروع ترم از " + Html.fromHtml(start) + " تا " + Html.fromHtml(end) + " می باشد"
            sd.text = "هیچ کتابی برای این ترم ثبت نشده"

            ad.setOnClickListener {
                val b = Intent(activity, Booking::class.java)
                b.putExtra("free", false)
                startActivity(b)
            }

            return view
        } else {//no term no books
            val view = inflater.inflate(R.layout.nodata, container, false)

            val sd = view.findViewById<View>(R.id.Desc) as TextView
            val td = view.findViewById<View>(R.id.ToADDTerm) as Button
            //Button fd = (Button) view.findViewById(R.id.ToADDFreeBook);

            sd.text = "ترم تحصیلی خود را معرفی کنید تا خودخوان شما را در طول ترم همراهی کند"

            td.setOnClickListener { startActivity(Intent(activity, TermFragment::class.java)) }

            //            fd.setOnClickListener(new View.OnClickListener() {
            //                @Override
            //                public void onClick(View view) {
            //                    Intent f = new Intent(getActivity(),Booking.class);
            //                    f.putExtra("free",true);
            //                    startActivity(f);
            //                }
            //            });

            return view
        }


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
                ObjectAnimator.ofInt(performanceircle, "Progress", 0, performance)) // anim 2
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


    fun GiveTeacher(teacher: Teacher) {
        this.teacher = teacher
    }
}
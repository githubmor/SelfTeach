//package DBAdapter
//
//import BL.Book
//import BL.HsvEvaluator
//import BL.Teacher
//import android.animation.AnimatorSet
//import android.animation.ObjectAnimator
//import android.graphics.Color
//import androidx.recyclerview.widget.RecyclerView
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.view.animation.LinearInterpolator
//import android.widget.ProgressBar
//import android.widget.TextView
//import com.github.lzyzsd.circleprogress.DonutProgress
//import morteza.darzi.SelfTeach.R
//
//class Book_list_Performance// Provide a suitable constructor (depends on the kind of dataset)
//(private val teacher: Teacher) : RecyclerView.Adapter<Book_list_Performance.BookListViewHolder>() {
//
//    private val books: List<Book>
//
//    // Provide a reference to the views for each data item
//    // Complex data items may need more than one view per item, and
//    // you provide access to all the views for a data item in a view holder
//    class BookListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
//        internal val vbookName: TextView
//        internal val vPageTo100: TextView
//        internal val pageRemain: TextView
//        internal val vperformnce: DonutProgress
//        internal val vpageRemindPercent: ProgressBar
//
//
//        init {
//            vbookName = v.findViewById<View>(R.id.booknam) as TextView
//            vPageTo100 = v.findViewById<View>(R.id.page_toRead_100percent) as TextView
//            pageRemain = v.findViewById<View>(R.id.pageRemain) as TextView
//            vperformnce = v.findViewById<View>(R.id.Book_performance) as DonutProgress
//            vpageRemindPercent = v.findViewById<View>(R.id.Book_pageRemindPercent) as ProgressBar
//        }
//    }
//
//    init {
//        this.books = teacher.books
//    }
//
//    // Create new views (invoked by the layout manager)
//    override fun onCreateViewHolder(parent: ViewGroup,
//                                    viewType: Int): Book_list_Performance.BookListViewHolder {
//        // create a new view
//        val v = LayoutInflater.from(parent.context)
//                .inflate(R.layout.bookperformancelist, parent, false)
//        // set the view's size, margins, paddings and layout parameters
//
//        return BookListViewHolder(v)
//    }
//
//    // Replace the contents of a view (invoked by the layout manager)
//    override fun onBindViewHolder(holder: BookListViewHolder, i: Int) {
//        val b = books[i]
//        holder.vbookName.text = b.name
//
//        if (b.PageRemind() >= b.pageCount || b.PageRemind() < 0)
//            holder.pageRemain.text = "هنوز شروع به خواندن نکردید"
//        else
//            holder.pageRemain.text = b.PageRemind().toString() + " صفحه باقی مانده"
//
//        val pt = teacher.BookPageTo100Percent(b)
//        if (pt <= 0)
//            holder.vPageTo100.visibility = View.GONE
//        else if (pt > 1000)
//            holder.vPageTo100.text = (pt - 1000).toString() + " صفحه باید خوانده شود !! - فوق برنامه بزار"
//        else
//            holder.vPageTo100.text = "امروز باید $pt صفحه خوانده شود"
//
//        SetArcProgressBarPerformance(holder.vperformnce, teacher.BookPerformance(b))
//
//        holder.vpageRemindPercent.progress = b.PageReadPercent()
//    }
//
//    override fun getItemCount(): Int {
//        return books.size
//    }
//
//    private fun SetArcProgressBarPerformance(performanceircle: DonutProgress, performance: Int) {
//        var performance = performance
//        if (performance > 100) {
//            performance = 100
//        }
//        //performance = 100;
//
//
//        val red = -0x40f600
//        val green = getColor(performance * 1.0 / 100)
//
//        val anim = ObjectAnimator.ofObject(performanceircle,
//                "FinishedStrokeColor",
//                HsvEvaluator(),
//                red, green)
//
//
//        val `as` = AnimatorSet()
//        `as`.playTogether(anim, // anim 1
//                ObjectAnimator.ofInt(performanceircle, "Progress", 0, performance)) // anim 2
//        `as`.startDelay = 1000
//        `as`.duration = 2000
//        `as`.interpolator = LinearInterpolator()
//        `as`.start()
//
//    }
//
//    internal fun getColor(power: Double): Int {
//        val H = power * 80
//        val floats = FloatArray(3)
//        floats[0] = H.toFloat()
//        floats[1] = 1.toFloat()
//        floats[2] = 0.7.toFloat()
//
//        return Color.HSVToColor(floats)
//    }
//}
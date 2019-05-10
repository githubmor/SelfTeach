//package DBAdapter
//
//import BL.Book_Old
//import androidx.recyclerview.widget.RecyclerView
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ProgressBar
//import android.widget.TextView
//import com.github.lzyzsd.circleprogress.DonutProgress
//import morteza.darzi.SelfTeach.R
//
//class FreeBook_list_Performance// Provide a suitable constructor (depends on the kind of dataset)
//(private val bookOlds: List<Book_Old>) : RecyclerView.Adapter<FreeBook_list_Performance.BookListViewHolder>() {
//    //private Teacher teacher;
//
//    // Provide a reference to the views for each data item
//    // Complex data items may need more than one view per item, and
//    // you provide access to all the views for a data item in a view holder
//    class BookListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
//        internal val vbookName: TextView
//        internal val tr: TextView
//        internal val vPageTo100: TextView
//        internal val pageRemain: TextView
//        internal val vperformnce: DonutProgress
//        internal val vpageRemindPercent: ProgressBar
//
//
//        init {
//            tr = v.findViewById<View>(R.id.tr) as TextView
//            vbookName = v.findViewById<View>(R.id.booknam) as TextView
//            vPageTo100 = v.findViewById<View>(R.id.page_toRead_100percent) as TextView
//            pageRemain = v.findViewById<View>(R.id.pageRemain) as TextView
//            vperformnce = v.findViewById<View>(R.id.Book_performance) as DonutProgress
//            vpageRemindPercent = v.findViewById<View>(R.id.Book_pageRemindPercent) as ProgressBar
//        }
//    }
//
//    // Create new views (invoked by the layout manager)
//    override fun onCreateViewHolder(parent: ViewGroup,
//                                    viewType: Int): FreeBook_list_Performance.BookListViewHolder {
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
//        val b = bookOlds[i]
//        holder.vbookName.text = b.name
//
//        if (b.PageRemind() >= b.pageCount || b.PageRemind() < 0)
//            holder.pageRemain.text = "هنوز شروع به خواندن نکردید"
//        else
//            holder.pageRemain.text = b.PageRemind().toString() + " صفحه باقی مانده"
//
//        holder.vPageTo100.visibility = View.GONE
//        holder.vperformnce.visibility = View.GONE
//        holder.tr.visibility = View.GONE
//        //        int pt = teacher.BookPageTo100Percent(b);
//        //        if (pt<=0)
//        //            holder.vPageTo100.setVisibility(View.GONE);
//        //        else if (pt>1000)
//        //            holder.vPageTo100.setText(String.valueOf(pt-1000)+" صفحه باید خوانده شود !! - فوق برنامه بزار");
//        //        else
//        //            holder.vPageTo100.setText("امروز باید "+String.valueOf(pt)+ " صفحه خوانده شود");
//
//        //holder.vPageTo100.setText(String.valueOf(teacher.BookPageTo100Percent(b))+" صفحه ی دیگر تا تکمیل برنامه امروز");
//        //SetArcProgressBarPerformance(holder.vperformnce,teacher.BookPerformance(b));
//        //holder.vperformnce.setProgress(teacher.BookPerformance(b));
//        holder.vpageRemindPercent.progress = b.PageReadPercent()
//    }
//
//    // Return the size of your dataset (invoked by the layout manager)
//    override fun getItemCount(): Int {
//        return bookOlds.size
//    }
//
//    //    private void SetArcProgressBarPerformance(DonutProgress performanceircle,int performance) {
//    //        if (performance > 100){
//    //            performance = 100;
//    //        }
//    //        //performance = 100;
//    //
//    //
//    //        final int red = 0xffbf0a00;
//    //        final int green = getColor((performance*1.0)/100);
//    //
//    //        ObjectAnimator anim = ObjectAnimator.ofObject(performanceircle,
//    //                "FinishedStrokeColor",
//    //                new HsvEvaluator(),
//    //                red, green);
//    //
//    //
//    //
//    //        AnimatorSet as = new AnimatorSet();
//    //        as.playTogether(anim, // anim 1
//    //                ObjectAnimator.ofInt(performanceircle,"Progress",0,performance)); // anim 2
//    //        as.setStartDelay(1000);
//    //        as.setDuration(2000);
//    //        as.setInterpolator(new LinearInterpolator());
//    //        as.start();
//    //
//    //    }
//
//    //    int getColor(double power)
//    //    {
//    //        double H = power * 80;
//    //        float[] floats = new float[3];
//    //        floats[0]=(float)H;
//    //        floats[1]=(float)1;
//    //        floats[2]=(float)0.7;
//    //
//    //        return Color.HSVToColor(floats);
//    //    }
//}
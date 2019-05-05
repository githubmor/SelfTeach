package DBAdapter

import BL.Read
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import morteza.darzi.SelfTeach.R

class Read_Adapter// Provide a suitable constructor (depends on the kind of dataset)
(//    private ReadDBhandeler readDBhandeler;
        //    BookDBhandeler bookDBhandeler;
        //    DBhelper dBhelper;
        private val context: Context, private val reads: MutableList<Read>?)//        dBhelper = new DBhelper(context);
//        readDBhandeler = new ReadDBhandeler(dBhelper);
//        bookDBhandeler = new BookDBhandeler(dBhelper);
    : RecyclerView.Adapter<Read_Adapter.ReadListViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class ReadListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var readbook: TextView
        internal var readpagecount: TextView
        internal var readdate: TextView
        internal var delRead: Button


        init {
            readbook = v.findViewById<TextView>(R.id.read_book_name)
            readpagecount = v.findViewById<View>(R.id.read_page_count) as TextView
            readdate = v.findViewById<View>(R.id.read_date) as TextView
            delRead = v.findViewById<View>(R.id.del_read) as Button
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): Read_Adapter.ReadListViewHolder {
        // create a new view
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_read, parent, false)
        // set the view's size, margins, paddings and layout parameters

        return ReadListViewHolder(v)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ReadListViewHolder, i: Int) {
        if (reads!=null) {
            val r = reads[i]
            //final Book book = bookDBhandeler.GetBook(reads.get(i).getBookId());

            holder.readbook.text = r.book!!.name.toString()
            holder.readpagecount.text = reads[i].pageReadCount.toString() + " صفحه خوانده شد"
            holder.readdate.text = "در تاریخ " + reads[i].readDate.toString()

            holder.delRead.setOnClickListener {
                Toast.makeText(context, "حذف شد", Toast.LENGTH_SHORT).show()
                reads[i].delete()
                //readDBhandeler.DelReadFromDb(reads.get(i).getId());
                reads.removeAt(i) //or some other task
                notifyItemRemoved(i)
            }
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return reads?.size ?: 0
    }

    fun addNewRead(b: Read) {
        reads?.add(b)
        notifyDataSetChanged()
    }
}
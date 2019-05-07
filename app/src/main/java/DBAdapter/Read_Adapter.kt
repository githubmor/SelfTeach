package DBAdapter

import BL.Read
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import morteza.darzi.SelfTeach.R

class Read_Adapter
(private val context: Context, private val reads: MutableList<Read>?)
    : RecyclerView.Adapter<Read_Adapter.ReadListViewHolder>() {

    class ReadListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var readbook= v.findViewById<TextView>(R.id.read_book_name)
        internal var readpagecount = v.findViewById(R.id.read_page_count) as TextView
        internal var readdate = v.findViewById<View>(R.id.read_date) as TextView
        internal var delRead = v.findViewById<View>(R.id.del_read) as Button


    }
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): Read_Adapter.ReadListViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_read, parent, false)
        return ReadListViewHolder(v)
    }

    override fun onBindViewHolder(holder: ReadListViewHolder, i: Int) {
        if (reads!=null) {
            val r = reads[i]
            holder.readbook.text = r.book!!.name.toString()
            holder.readpagecount.text = reads[i].pageReadCount.toString() + " صفحه خوانده شد"
            holder.readdate.text = "در تاریخ " + reads[i].readDate.toString()

            holder.delRead.setOnClickListener {
                Toast.makeText(context, "حذف شد", Toast.LENGTH_SHORT).show()
                reads[i].delete()
                reads.removeAt(i)
                notifyItemRemoved(i)
            }
        }

    }
    override fun getItemCount(): Int {
        return reads?.size ?: 0
    }

    fun addNewRead(b: Read) {
        reads?.add(b)
        notifyDataSetChanged()
    }
}
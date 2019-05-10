package DBAdapter

import BL.Read
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_read.view.*
import morteza.darzi.SelfTeach.R

class Read_Adapter
(private val context: Context, private val reads: MutableList<Read>?)
    : RecyclerView.Adapter<Read_Adapter.ReadListViewHolder>() {

    class ReadListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var readbook= v.read_book_name_lab
        internal var readpagecount = v.read_page_count_lab
        internal var readdate = v.read_date_lab
        internal var delRead = v.del_read


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
            holder.readbook.text = r.bookOld!!.name.toString()
            holder.readpagecount.text = r.pageReadCount.toString() + " صفحه"
            holder.readdate.text = r.readDate.toString()

            holder.delRead.setOnClickListener {
                Toast.makeText(context, "حذف شد", Toast.LENGTH_SHORT).show()
                r.delete()
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
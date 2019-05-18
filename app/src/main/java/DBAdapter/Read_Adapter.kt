package DBAdapter

import BL.Read
import BL.ReadRepository
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_read.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import morteza.darzi.SelfTeach.R

class Read_Adapter
(private val context: Context, private val reads: MutableList<Read>?,val repository: ReadRepository)
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
            holder.readbook.text = r.book
            holder.readpagecount.text = "$r.pageReadCount صفحه"
            holder.readdate.text = r.readDate.toString()

            holder.delRead.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch{
                    repository.delete(r.dbDto.read)
                    withContext(Dispatchers.Main){
                        Toast.makeText(context, "حذف شد", Toast.LENGTH_SHORT).show()
                        reads.removeAt(i)
                        notifyItemRemoved(i)
                    }

                }
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
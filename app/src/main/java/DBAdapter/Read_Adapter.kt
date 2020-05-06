package DBAdapter

import BL.ReadBook
import BL.ReadService
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
import morteza.darzi.SelfTeach2.R

class Read_Adapter
(private val context: Context, private val reads: MutableList<ReadBook>?)
    : RecyclerView.Adapter<Read_Adapter.ReadListViewHolder>() {

    private lateinit var readService: ReadService

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
        readService = ReadService(context)
        return ReadListViewHolder(v)
    }

    override fun onBindViewHolder(holder: ReadListViewHolder, i: Int) {
        if (reads!=null) {
            val r = reads[i]
            holder.readbook.text = r.bookName
            holder.readpagecount.text = r.pageReadCount.toString() + " صفحه"
            holder.readdate.text = r.readDate.toString()

            holder.delRead.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch{
                    readService.delete(r)
                    withContext(Dispatchers.Main){
                        Toast.makeText(context, "حذف شد", Toast.LENGTH_SHORT).show()
                        reads.remove(r)
                        notifyItemRemoved(i)
                    }

                }
            }
        }

    }
    override fun getItemCount(): Int {
        return reads?.size ?: 0
    }

    fun addNewRead(b: ReadBook) {
        reads?.add(b)
        notifyDataSetChanged()
    }
}
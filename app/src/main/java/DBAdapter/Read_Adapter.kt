package DBAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import core.ReadBook
import core.services.ReadService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import morteza.darzi.SelfTeach2.databinding.ItemReadBinding

class Read_Adapter
(private val context: Context, private val reads: MutableList<ReadBook>?)
    : RecyclerView.Adapter<Read_Adapter.ReadListViewHolder>() {

    private lateinit var readService: ReadService

    //    class ReadListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
//        internal var readbook= v.read_book_name_lab
//        internal var readpagecount = v.read_page_count_lab
//        internal var readdate = v.read_date_lab
//        internal var delRead = v.del_read
//    }
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ReadListViewHolder {
        val binding = ItemReadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        readService = ReadService(context)
        return ReadListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReadListViewHolder, i: Int) {
        with(holder) {
            if (reads != null) {
                val r = reads[i]
                binding.readBookNameLab.text = r.bookName
                binding.readPageCountLab.text = r.pageReadCount.toString() + " صفحه"
                binding.readDateLab.text = r.readDate.toString()

                binding.delRead.setOnClickListener {
                    CoroutineScope(Dispatchers.IO).launch {
                        val saved = readService.delete(r)
                        withContext(Dispatchers.Main) {
                            if (saved) {
                                Toast.makeText(context, "حذف شد", Toast.LENGTH_SHORT).show()
                                reads.remove(r)
                                notifyItemRemoved(i)
                            } else
                                throw IllegalArgumentException("حذف خواندن دچار مشکل شده. لطفا به سازنده برنامه اطلاع دهید")
                        }

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

    inner class ReadListViewHolder(val binding: ItemReadBinding)
        : RecyclerView.ViewHolder(binding.root)
}
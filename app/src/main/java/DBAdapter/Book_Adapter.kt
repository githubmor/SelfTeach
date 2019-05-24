package DBAdapter

import BL.Book
import BL.BookRepository
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_book.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import morteza.darzi.SelfTeach.R


class Book_Adapter(private val context: Context, private val books: MutableList<Book>,val repository: BookRepository)
    : RecyclerView.Adapter<Book_Adapter.BookListViewHolder>() {

    class BookListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal val bookName = v.book_name_lab
        internal val pageCount = v.book_page_count_lab
        internal val delBook = v.del_book
        internal val readProgress = v.read_progress
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Book_Adapter.BookListViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_book, parent, false)

        return BookListViewHolder(v)
    }

    override fun onBindViewHolder(holder: BookListViewHolder, i: Int) {
        val b = books[i]
        holder.bookName.text = b.name
        holder.pageCount.text = b.pageReadState
        holder.readProgress.progress = b.pageReadPercent.toInt()
        holder.delBook.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch{
                repository.delete(b.dbDto.book)
                withContext(Dispatchers.Main){
                    Toast.makeText(context, "کتاب " + b.name + " حذف شد", Toast.LENGTH_SHORT).show()
                    books.removeAt(i)
                    notifyItemRemoved(i)
                }

            }

        }

    }

    override fun getItemCount(): Int {
        return books.size
    }

    fun addNewBook(b: Book) {
        books.add(b)
        notifyDataSetChanged()
    }

    fun updateBooks(bs: List<Book>) {
        books.clear()
        books.addAll(bs)
        notifyDataSetChanged()
    }
}
package DBAdapter

import BL.Book
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_book.view.*
import morteza.darzi.SelfTeach.R

class Book_Adapter(private val context: Context, private val books: MutableList<Book>?)
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
        if (books!=null) {
            val b = books[i]
            holder.bookName.setText(b.name)
            holder.pageCount.setText(b.pageCount.toString())
            holder.readProgress.progress = b.PageReadPercent()
            holder.delBook.setOnClickListener {
                Toast.makeText(context, "کتاب " + b.name + " حذف شد", Toast.LENGTH_SHORT).show()
                b.DeleteReads()
                b.delete()

                books.removeAt(i)
                notifyItemRemoved(i)
            }
        }

    }

    override fun getItemCount(): Int {
        return books?.size ?: 0
    }

    fun addNewBook(b: Book) {
        books?.add(b)
        notifyDataSetChanged()
    }
}
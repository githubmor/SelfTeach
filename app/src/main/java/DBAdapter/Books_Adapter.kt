package DBAdapter

import core.Book
import core.services.BookService
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_book_first.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import morteza.darzi.SelfTeach2.R


class Books_Adapter(private val context: Context, private val books: MutableList<Book>)
    : RecyclerView.Adapter<Books_Adapter.BookListViewHolder>() {

    private lateinit var bookService: BookService

    class BookListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal val bookName = v.book_name_lab
        internal val pageCount = v.book_page_count_lab
        internal val delBook = v.del_book
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Books_Adapter.BookListViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_book_first, parent, false)
        bookService = BookService(context)
        return BookListViewHolder(v)
    }

    override fun onBindViewHolder(holder: BookListViewHolder, i: Int) {
        val b = books[i]
        holder.bookName.text = b.name
        holder.pageCount.text = b.pageCount.toString()
        holder.delBook.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch{
                val saved =bookService.delete(b)
                withContext(Dispatchers.Main){
                    if (saved) {
                        Toast.makeText(context, "کتاب " + b.name + " حذف شد", Toast.LENGTH_SHORT).show()
                        books.remove(b)
                        notifyItemRemoved(i)
                    }else
                        throw IllegalArgumentException("حذف کتاب دچار مشکل شده. لطفا به سازنده برنامه اطلاع دهید")
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

}
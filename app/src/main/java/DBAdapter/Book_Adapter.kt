package DBAdapter

import BL.Book
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import morteza.darzi.SelfTeach.R

class Book_Adapter
(private val context: Context, private val books: MutableList<Book>?)
    : RecyclerView.Adapter<Book_Adapter.BookListViewHolder>() {

    class BookListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal val bookName: TextView
        internal val pageCount: TextView
        internal val delBook: Button


        init {
            bookName = v.findViewById<View>(R.id.bookname) as TextView
            pageCount = v.findViewById<View>(R.id.bookpagecount) as TextView
            delBook = v.findViewById<View>(R.id.delbook) as Button

            v.setOnLongClickListener {

                return@setOnLongClickListener true
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Book_Adapter.BookListViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_book, parent, false)

        return BookListViewHolder(v)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: BookListViewHolder, i: Int) {
        if (books!=null) {
            val b = books[i]
            holder.bookName.text = b.name
            holder.pageCount.text = books[i].pageCount.toString() + " صفحه"
            holder.delBook.setOnClickListener {
                Toast.makeText(context, "کتاب " + books[i].name + " حذف شد", Toast.LENGTH_SHORT).show()
                books[i].DeleteReads()
                books[i].delete()

                books.removeAt(i)
                notifyItemRemoved(i)
            }
        }

    }


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return if (books==null) 0 else books.size
    }

    fun addNewBook(b: Book) {
        books?.add(b)
        notifyDataSetChanged()
    }
}
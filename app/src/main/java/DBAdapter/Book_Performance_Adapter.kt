package DBAdapter

import BL.BookPerformance
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_book.view.*
import morteza.darzi.SelfTeach2.R


class Book_Performance_Adapter(private val context: Context, private val bookPerformances: MutableList<BookPerformance>)
    : RecyclerView.Adapter<Book_Performance_Adapter.BookListViewHolder>() {

    class BookListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal val bookName = v.book_name_lab
        internal val pageCount = v.book_page_count_lab
        internal val readProgress = v.read_progress
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookListViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_book, parent, false)

        return BookListViewHolder(v)
    }

    override fun onBindViewHolder(holder: BookListViewHolder, i: Int) {
        val performanceBook = bookPerformances[i]
        holder.bookName.text = performanceBook.book.name
        holder.pageCount.text = "(" + performanceBook.book.readSum + "/" + performanceBook.book.pageCount + ")"
        holder.readProgress.progress = performanceBook.pageReadPercent.toInt()
    }

    override fun getItemCount(): Int {
        return bookPerformances.size
    }


}
package DBAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import core.BookPerformance
import morteza.darzi.SelfTeach2.R


class Book_Performance_Adapter(private val context: Context, private val bookPerformances: MutableList<BookPerformance>)
    : RecyclerView.Adapter<Book_Performance_Adapter.BookListViewHolder>() {

    class BookListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal val bookName = v.findViewById<TextView>(R.id.book_name_lab)
        internal val pageCount = v.findViewById<TextView>(R.id.book_page_count_lab)
        internal val readProgress = v.findViewById<ProgressBar>(R.id.read_progress)
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
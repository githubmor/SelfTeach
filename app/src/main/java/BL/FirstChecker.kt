package BL

import DAL.TermRepository
import DAL.Termdb
import android.app.Application
import android.content.Context
import com.activeandroid.query.Select
import kotlinx.coroutines.*
import morteza.darzi.SelfTeach.MyApplication
import morteza.darzi.SelfTeach.MyApplication.Companion.database
import kotlin.coroutines.CoroutineContext


class FirstChecker(val context:Context):CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    private val repository: TermRepository

    init {
        val wordsDao = MyApplication.database.termDao()
        repository = TermRepository(wordsDao)
    }
    fun Termexist(): Boolean {
        return repository.term!=null
    }
    fun insert(word: Termdb) = GlobalScope.launch(Dispatchers.IO) {
        repository.insert(word)
    }
    companion object{
        fun checkLevel() : TermLevel{
            return if (Termexist())
                if (BooksExist())
                    TermLevel.Perfermance
                else
                    TermLevel.Book
            else
                TermLevel.Term

        }

        fun BooksExist(): Boolean {
            return database.bookDao().getAllBook().count()>0
        }

        fun Termexist(): Boolean {
            return database.bookDao().getAllBook().count()>0
        }


        fun getBooks(): MutableList<Book_Old>? {

            val bs = Select().from(Book_Old::class.java).where("free = " + 0).execute<Book_Old>()
            if (bs!=null){
                for (b in bs) {
                    b.LoadReads()
                }
            }
            return bs
        }

        fun getReads(): MutableList<Read> {
            val books = Select().from(Book_Old::class.java).execute<Book_Old>()
            val reads: MutableList<Read> = mutableListOf()

            for (b in books) {
                val rs = Select()
                        .from(Read::class.java)
                        .where("Book_Old = ?", b.id!!)
                        .execute<Read>()
                if (!rs.isEmpty()) {
                    for (r in rs) {
                        r.bookOld = b
                    }
                }
                reads.addAll(rs)
            }

            return reads
        }
    }

}


enum class TermLevel {
    Term,Book,Perfermance
}

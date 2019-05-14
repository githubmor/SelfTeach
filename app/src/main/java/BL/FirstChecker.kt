package BL

import DAL.AppDatabase
import com.activeandroid.query.Select



class FirstChecker(val database: AppDatabase){
    private val repository: TermRepository

    init {
        val wordsDao = database.termDao()
        repository = TermRepository(wordsDao)
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
            return Select().from(Book_Old::class.java).where("free = " + 0).exists()
        }

        fun Termexist(): Boolean {
            return Select().from(Term_old::class.java).exists()
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

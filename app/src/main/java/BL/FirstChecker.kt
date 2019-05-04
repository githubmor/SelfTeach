package BL

import com.activeandroid.query.Select
import java.util.ArrayList

class FirstChecker {
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
            val booksExist = Select().from(Book::class.java).where("free = " + 0).exists()
            return booksExist
        }

        fun Termexist(): Boolean {
            return Select().from(Term::class.java).exists()
        }

        fun getTerm(): Term? {
            return Select().from(Term::class.java).executeSingle()
        }

        fun getBooks(): MutableList<Book>? {
            return Select().from(Book::class.java).where("free = " + 0).execute()
        }

        fun getReads(): MutableList<Read>? {
            val books = Select().from(Book::class.java).execute<Book>()
            val reads: MutableList<Read>? = null

            for (b in books) {
                val rs = Select()
                        .from(Read::class.java)
                        .where("Book = ?", b.id!!)
                        .execute<Read>()
                if (!rs.isEmpty()) {
                    for (r in rs) {
                        r.book = b
                    }
                }
                reads?.addAll(rs)
            }

            return reads
        }
    }
}

enum class TermLevel {
    Term,Book,Perfermance
}

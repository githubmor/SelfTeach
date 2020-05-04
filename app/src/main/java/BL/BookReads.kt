package BL

import DAL.Book_db
import DAL.Read_db

class BookReads(book_db: Book_db, val read_dbs:List<Read_db>):Book(book_db,read_dbs.sumBy { it.pageRead })
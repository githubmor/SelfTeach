package BL

import DAL.Read_db

class ReadBook(read_db:Read_db, val bookName:String):Read(read_db)
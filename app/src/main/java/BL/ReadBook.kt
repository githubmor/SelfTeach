package BL

import DAL.ReadDataTable

class ReadBook(readDataTable: ReadDataTable, val bookName: String) : Read(readDataTable)
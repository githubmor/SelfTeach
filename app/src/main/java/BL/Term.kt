package BL

import DAL.Termdb

class Term(db : Termdb) {

    var endDate = db.endDate
    var startDate = db.startDate
    var termName = db.name
}
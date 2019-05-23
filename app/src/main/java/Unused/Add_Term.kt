package Unused

//package morteza.darzi.SelfTeach
//
//import BL.MyException
//import BL.Term_old
//import android.content.Intent
//import android.os.Bundle
//import android.view.Menu
//import android.view.MenuItem
//import android.view.View
//import android.widget.EditText
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.activeandroid.query.Select
//import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog
//import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
//
///**
// * Created by M on 14/11/23.
// */
//class Add_Term : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
//
//
//    private var type: EditText? = null
//    private var start: EditText? = null
//    private var end: EditText? = null
//    private var TermName: String? = null
//    private var StartDate: String? = null
//    private var EndDate: String? = null
//    private var state: String? = null
//
//    private var term_old: Term_old? = null
//    private val START = "start"
//    private val END = "end"
//
//    public override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_term)
//
//
//
//        type = findViewById<View>(R.id.TermName) as EditText
//        start = findViewById<View>(R.id.StartDate) as EditText
//        end = findViewById<View>(R.id.EndDate) as EditText
//
//        start!!.setOnClickListener {
//            val persianCalendar = PersianCalendar()
//            val datePickerDialog = DatePickerDialog.newInstance(
//                    this@Add_Term,
//                    persianCalendar.persianYear,
//                    persianCalendar.persianMonth,
//                    persianCalendar.persianDay
//            )
//
//            datePickerDialog.show(fragmentManager, START)
//        }
//
//        end!!.setOnClickListener {
//            val persianCalendar = PersianCalendar()
//            val datePickerDialog = DatePickerDialog.newInstance(
//                    this@Add_Term,
//                    persianCalendar.persianYear,
//                    persianCalendar.persianMonth,
//                    persianCalendar.persianDay
//            )
//            datePickerDialog.show(fragmentManager, END)
//        }
//
//
//        term_old = Select().from(Term_old::class.java).executeSingle()
//
//
//
//        if (term_old == null) {
//            state = "AddNewTerm"
//            ChangeState(state!!)
//
//        } else {
//            state = "ShowTerm"
//
//            TermName = term_old!!.type
//            StartDate = term_old!!.startDate
//            EndDate = term_old!!.endDate
//
//            ChangeState(state!!)
//
//
//        }
//    }
//
//
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.termingmenu, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//
//        when (item.itemId) {
//
//            R.id.saveTerm -> if (state === "ShowTerm") {//Maybe Edit Term_old
//
//                try {
//                    term_old!!.type = type!!.text.toString()
//                    term_old!!.setStartAndEndDate(start!!.text.toString(), end!!.text.toString())
//                    //term_old.setEndDate(end.getText().toString());
//                    //term_old = new Term_old(type.getText().toString(), start.getText().toString(), end.getText().toString());
//                    term_old!!.save()
//
//                    Toast.makeText(applicationContext, "اطلاعات ترم ویرایش شد", Toast.LENGTH_SHORT).show()
//                    startActivity(Intent(applicationContext, MainActivity::class.java))
//                } catch (e: MyException) {
//                    Toast.makeText(applicationContext, e.message.toString(), Toast.LENGTH_SHORT).show()
//                }
//
//                //                        term_old.setType(type.getText().toString());
//                //                        term_old.setStartDate(start.getText().toString());
//                //                        term_old.setEndDate(end.getText().toString());
//
//            } else {//add new Term_old
//
//                TermName = type!!.text.toString()
//                StartDate = start!!.text.toString()
//                EndDate = end!!.text.toString()
//                try {
//                    term_old = Term_old(TermName!!, StartDate!!, EndDate!!)
//                    term_old!!.save()
//                    Toast.makeText(applicationContext, "اطلاعات ترم ذخیره شد", Toast.LENGTH_SHORT).show()
//                    startActivity(Intent(applicationContext, Booking::class.java))
//                } catch (e: MyException) {
//                    Toast.makeText(applicationContext, e.message.toString(), Toast.LENGTH_LONG).show()
//                }
//
//            }
//
//            else -> {
//            }
//        }
//
//        return super.onOptionsItemSelected(item)
//
//    }
//
//    internal fun ChangeState(state: String) {
//        if (state === "AddNewTerm") {
//            type!!.isEnabled = true
//            start!!.isEnabled = true
//            end!!.isEnabled = true
//            //term_old = null;
//            type!!.text = null
//            start!!.text = null
//            end!!.text = null
//
//        } else if (state == "ShowTerm") {
//            type!!.setText(TermName)
//            type!!.isEnabled = true
//            start!!.setText(StartDate)
//            start!!.isEnabled = true
//            end!!.setText(EndDate)
//            end!!.isEnabled = true
//        }
//
//
//    }
//
//
//    override fun onDateSet(view: DatePickerDialog, year: Int, monthOfYear: Int, dayOfMonth: Int) {
//        val tag = view.tag
//        val d = PersianCalendar()
//        d.setPersianDate(year, monthOfYear, dayOfMonth)
//
//        if (tag == START) {
//            start!!.setText(d.persianShortDate)
//        } else if (tag == END) {
//            end!!.setText(d.persianShortDate)
//        }
//    }
//}
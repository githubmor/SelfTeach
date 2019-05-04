package morteza.darzi.SelfTeach

import BL.Book
import BL.MyException
import BL.Read
import BL.Teacher
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.activeandroid.query.Select
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar

/**
 * Created by M on 14/12/29.
 */
class Add_Read : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private var pageRead: EditText? = null
    private var readDate: EditText? = null
    //    private PersianDatePicker picker;
    private var bookListSpinner: Spinner? = null

    private val teacher = Teacher()
    private var selectedBook: Book? = null
    //    Term term;
    internal val books = Select().from(Book::class.java).execute<Book>()
    //    List<Read> reads;

    //    DBhelper dBhelper;
    //    TermDBhandeler termDBhandeler;
    //    BookDBhandeler bookDBhandeler;
    //    ReadDBhandeler readDBhandeler;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_read)


        //        picker = new D

        for (b in books) {
            b.LoadReads()
        }
        //selectedBook = new BookOld();

        bookListSpinner = findViewById<View>(R.id.BookNameList) as Spinner
        pageRead = findViewById<View>(R.id.PageReaded) as EditText
        readDate = findViewById<View>(R.id.ReadDate) as EditText

        readDate!!.setOnClickListener {
            val persianCalendar = PersianCalendar()
            val datePickerDialog = DatePickerDialog.newInstance(
                    this@Add_Read,
                    persianCalendar.persianYear,
                    persianCalendar.persianMonth,
                    persianCalendar.persianDay
            )
            datePickerDialog.show(fragmentManager, "Datepickerdialog")
        }
        readDate!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                if ((selectedBook != null) and selectedBook!!.NotFree()) {
                    if (readDate!!.text.toString() != "")
                        if (teacher.IsInTermRange(readDate!!.text.toString())!!.not()) {
                            Toast.makeText(applicationContext, "لطفا زمانی بین " +
                                    teacher.term!!.startDate + " تا" +
                                    teacher.term.endDate + " را انتخاب کنید", Toast.LENGTH_SHORT).show()
                            readDate!!.setText("")
                        }

                }


            }
        })

        val dataAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, books)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bookListSpinner!!.adapter = dataAdapter

        bookListSpinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                selectedBook = adapterView.getItemAtPosition(i) as Book
                //readList = selectedBook.getReads();
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.addreadmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.savingRead -> {

                val readcount: Int

                try {
                    if (pageRead!!.text.toString() == "") {
                        throw MyException("لطفا تعداد صفحات خوانده شده را وارد کنید")
                    } else {
                        readcount = Integer.valueOf(pageRead!!.text.toString())
                    }
                    //TO CHECK READDATE BETWIN TERM STARDATE TO ENDDATE
                    //term.setNow(readDate.getText().toString());

                    if (selectedBook!!.PageRemind() >= readcount) {
                        //Read read;
                        try {
                            val read = Read(readcount, readDate!!.text.toString())
                            read.book = selectedBook
                            read.save()
                            //readDBhandeler.InsertRead(read);
                            val intent = Intent(applicationContext, Reading::class.java)
                            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent)
                        } catch (e: MyException) {
                            Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        Toast.makeText(applicationContext, "فقط " + selectedBook!!.PageRemind().toString()
                                + " صفحه باقی مانده - لطفا اصلاح کنید", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: MyException) {
                    Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT).show()
                }

            }
            else -> {
            }
        }

        return super.onOptionsItemSelected(item)

    }

    override fun onDateSet(view: DatePickerDialog, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val d = PersianCalendar()
        d.setPersianDate(year, monthOfYear, dayOfMonth)
        readDate!!.setText(d.persianShortDate)
    }
}
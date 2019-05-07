package morteza.darzi.SelfTeach

import BL.Book
import BL.FirstChecker
import BL.Read
import BL.TermLevel
import DBAdapter.Read_Adapter
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import kotlinx.android.synthetic.main.fragment_reads.*
import kotlinx.android.synthetic.main.fragment_reads.view.*


class ReadsFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    var reads : MutableList<Read> = mutableListOf()
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(FirstChecker.checkLevel()!= TermLevel.Perfermance) {
            Toast.makeText(context!!, "هنوز ترم يا كتابي ثبت نشده", Toast.LENGTH_SHORT).show()
            listener!!.failRead()
        }else
            reads = FirstChecker.getReads()
    }

    private var selectedBook: Book? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_reads, container, false)

        if (reads.isNullOrEmpty()){
            arrangeForEmptyBook(v)
        }else{
            arrangeForShowList(v)
        }

        v.fab.setOnClickListener {
            arrangeToAddRead(v)
        }

        v.list.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        val adapter = Read_Adapter(context!!,reads)
        v.list.adapter = adapter

        v.read_date_lay.setOnClickListener {
            val persianCalendar = PersianCalendar()
            val datePickerDialog = DatePickerDialog.newInstance(
                    this@ReadsFragment,
                    persianCalendar.persianYear,
                    persianCalendar.persianMonth,
                    persianCalendar.persianDay
            )

            datePickerDialog.show(activity!!.fragmentManager, "")
        }

        val allbooks = FirstChecker.getBooks()

        val dataAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, allbooks!!)
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        v.spinner.adapter = dataAdapter

        v.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                selectedBook = adapterView.getItemAtPosition(i) as Book
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }

        textChangeListner(v.read_page_count_lay,"لطفا تعداد صفحات كتاب را وارد كنيد")
        textChangeListner(v.read_date_lay,"لطفا زمان خواندن را مشخص كنيد")

        v.read_save.setOnClickListener {
            if(selectedBook==null)
                Toast.makeText(context!!,"لطفا نام كتاب را وارد كنيد",Toast.LENGTH_SHORT).show()
            else if(v.read_page_count.text.isNullOrEmpty())
                v.read_page_count.error = "لطفا تعداد صفحات كتاب را وارد كنيد"
            else if(v.read_date.text.isNullOrEmpty())
                v.read_date.error = "لطفا زمان خواندن را مشخص كنيد"
            else {
                val read = Read(v.read_page_count!!.text.toString().toInt(), v.read_date!!.text.toString())
                read.book = selectedBook
                read.save()
                adapter.addNewRead(read)
                arrangeForShowList(v)

            }
        }

        return v
    }

    private fun arrangeToAddRead(v: View) {
        v.switcher.showNext()
        v.fab.hide()
    }

    private fun arrangeForShowList(v: View) {
        v.list.visibility = VISIBLE
        v.emptyText.visibility = GONE
        v.fab.show()
        v.switcher.showPrevious()
        v.read_page_count.setText("")
        v.read_page_count_lay.isErrorEnabled = false
        v.read_date.setText("")
        v.read_date_lay.isErrorEnabled = false
    }

    private fun arrangeForEmptyBook(v: View) {
        v.list.visibility = GONE
        v.emptyText.visibility = VISIBLE
        v.fab.show()
        v.switcher.showPrevious()
        v.read_page_count.setText("")
        v.read_page_count_lay.isErrorEnabled = false
        v.read_date.setText("")
        v.read_date_lay.isErrorEnabled = false
    }

    private fun textChangeListner(v: TextInputLayout, errorMes : String) {
        v.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isEmpty()) {
                    v.isErrorEnabled = true
                    v.error = errorMes
                }
                if (s.isNotEmpty()) {
                    v.error = null
                    v.isErrorEnabled = false
                }

            }

            override fun afterTextChanged(s: Editable) {

            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    override fun onDateSet(view: DatePickerDialog, year: Int, monthOfYear: Int, dayOfMonth: Int) {

        val d = PersianCalendar()
        d.setPersianDate(year, monthOfYear, dayOfMonth)

        read_date.setText(d.persianShortDate)

    }

    interface OnFragmentInteractionListener {
        fun failRead()
    }

}

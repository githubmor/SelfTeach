package morteza.darzi.SelfTeach



import BL.FirstChecker
import BL.Term
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_term.*
import kotlinx.android.synthetic.main.fragment_term.view.*


//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

class TermFragment : Fragment(), DatePickerDialog.OnDateSetListener {


    //    private var param1: String? = null
//    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var term :Term ? = null
    private val START = "start"
    private val END = "end"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        term = FirstChecker.getTerm()


//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_term, container, false)

//        val addNewTerm = v.findViewById<Button>(R.id.add_new_term)

        if (term!=null) {
            v.switcher.showNext()
            v.term_name.setText(term?.termName)
            v.term_start_date.setText(term?.startDate)
            v.term_end_date.setText(term?.endDate)
        }

        v.add_new_term.setOnClickListener {
            v.switcher.showNext()
        }

//        v.spinner.onItemSelectedListener = this

        v.term_start_date.setOnClickListener {
            val persianCalendar = PersianCalendar()
            val datePickerDialog = DatePickerDialog.newInstance(
                    this@TermFragment,
                    persianCalendar.persianYear,
                    persianCalendar.persianMonth,
                    persianCalendar.persianDay
            )

            datePickerDialog.show(activity!!.fragmentManager, START)
        }

        v.term_end_date.setOnClickListener {
            val persianCalendar = PersianCalendar()
            val datePickerDialog = DatePickerDialog.newInstance(
                    this@TermFragment,
                    persianCalendar.persianYear,
                    persianCalendar.persianMonth,
                    persianCalendar.persianDay
            )

            datePickerDialog.show(activity!!.fragmentManager, END)
        }

        v.term_save.setOnClickListener {

            if(v.term_name.text==null)
                v.term_name_lay.error = "لطفا نامی را انتخاب کنید"
            else if (v.term_start_date.text==null)
                Toast.makeText(context!!,"لطفا تاریخ شروع ترم را مشخص کنید", Toast.LENGTH_SHORT).show()
            else if (v.term_end_date.text==null)
                Toast.makeText(context!!,"لطفا تاریخ پایان ترم را مشخص کنید", Toast.LENGTH_SHORT).show()
            else if (term==null) {
                term = Term()
                assaginTerm(v)
            }else{
                assaginTerm(v)
            }


        }


        return v
    }

    private fun assaginTerm(v: View) {
        term!!.termName = v.term_name.text.toString()
        term!!.startDate = v.term_start_date.text.toString()
        term!!.endDate = v.term_end_date.text.toString()

        term!!.save()

        listener!!.onSaveTermComplete()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this.clearFindViewByIdCache()
    }

    override fun onDateSet(view: DatePickerDialog, year: Int, monthOfYear: Int, dayOfMonth: Int) {

        val tag = view.tag
        val d = PersianCalendar()
        d.setPersianDate(year, monthOfYear, dayOfMonth)

        if (tag == START) {
            term_start_date.setText(d.persianShortDate)
        } else if (tag == END) {
            term_end_date.setText(d.persianShortDate)
        }
    }
    override fun onDetach() {
        super.onDetach()
        listener = null
    }
//    override fun onNothingSelected(p0: AdapterView<*>?) {
//
//    }
//
//    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//        val va = resources.getStringArray(R.array.term_type)
//        val a = va.get(p2)
//        term_start_date.setText("1398/01/01")
//        term_end_date.setText("1398/10/01")
//    }

    interface OnFragmentInteractionListener {
        fun onSaveTermComplete()
    }

//    companion object {
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//                TermFragment().apply {
//                    arguments = Bundle().apply {
//                        putString(ARG_PARAM1, param1)
//                        putString(ARG_PARAM2, param2)
//                    }
//                }
//    }
}

package morteza.darzi.SelfTeach

import BL.Book
import BL.FirstChecker
import BL.Read
import DBAdapter.Book_Adapter
import DBAdapter.Read_Adapter
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import kotlinx.android.synthetic.main.fragment_books.view.*
import kotlinx.android.synthetic.main.fragment_term.*
import kotlinx.android.synthetic.main.item_book.view.*
import kotlinx.android.synthetic.main.readlist.*
import kotlinx.android.synthetic.main.readlist.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ReadsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ReadsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ReadsFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
    var reads : MutableList<Read>? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reads = FirstChecker.getReads()
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
    }

    private var selectedBook: Book? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_reads, container, false)

        if (reads.isNullOrEmpty()){
            arrangeForEmpty(v)
        }else{
            arrangeForNotEmpty(v)
        }

        v.fab.setOnClickListener {
            v.emptyText.visibility = GONE
            v.include.visibility = VISIBLE
            v.delbook.background = AppCompatResources.getDrawable(context!!,R.drawable.ic_add_white_48dp)
        }

        val adapter = Read_Adapter(context!!,reads)
        v.list.adapter = adapter

        v.read_date.setOnClickListener {
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

        v.delbook.setOnClickListener {
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
                arrangeForNotEmpty(v)
            }
        }

        return v
    }

    private fun arrangeForNotEmpty(v: View) {
        v.list.visibility = VISIBLE
        v.emptyText.visibility = GONE
        v.fab.visibility = VISIBLE
        v.include.visibility = GONE
    }

    private fun arrangeForEmpty(v: View) {
        v.list.visibility = GONE
        v.emptyText.visibility = VISIBLE
        v.fab.visibility = VISIBLE
        v.include.visibility = GONE
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
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)

        fun failPerformance()
    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment BooksFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//                BooksFragment().apply {
//                    arguments = Bundle().apply {
//                        putString(ARG_PARAM1, param1)
//                        putString(ARG_PARAM2, param2)
//                    }
//                }
//    }
}

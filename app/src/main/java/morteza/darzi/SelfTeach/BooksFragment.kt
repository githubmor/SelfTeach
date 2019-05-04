package morteza.darzi.SelfTeach

import BL.Book
import BL.FirstChecker
import DBAdapter.Book_Adapter
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_books.view.*
import kotlinx.android.synthetic.main.item_book.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [BooksFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [BooksFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class BooksFragment : Fragment() {
    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
    var books : MutableList<Book>? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        books = FirstChecker.getBooks()
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_books, container, false)

        if (books.isNullOrEmpty()){
            arrangeForEmpty(v)
        }else{
            arrangeForNotEmpty(v)
        }

        v.fab.setOnClickListener {
            v.emptyText.visibility = GONE
            v.include.visibility = VISIBLE
            v.delbook.background = AppCompatResources.getDrawable(context!!,R.drawable.ic_add_white_48dp)
        }

        val adapter = Book_Adapter(context!!,books)
        v.list.adapter = adapter

        v.delbook.setOnClickListener {
            if(v.bookname.text==null)
                v.bookname.error = "لطفا نام كتاب را وارد كنيد"
            else if(v.bookpagecount.text==null)
                v.bookpagecount.error = "لطفا تعداد صفحات كتاب را وارد كنيد"
            else {
                val b = Book(v.bookname.text.toString(),v.bookpagecount.text.toString().toInt(),false)
                b.save()
                adapter.addNewBook(b)
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

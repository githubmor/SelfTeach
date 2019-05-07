package morteza.darzi.SelfTeach

import BL.Book
import BL.FirstChecker
import BL.TermLevel
import DBAdapter.Book_Adapter
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_books.*
import kotlinx.android.synthetic.main.fragment_books.view.*

class BooksFragment : Fragment() {

    var books : MutableList<Book>? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (FirstChecker.checkLevel()==TermLevel.Term) {
            Toast.makeText(context!!,"هنوز ترمي ثبت نشده",Toast.LENGTH_SHORT).show()
            listener!!.FailOpenBooks()
        }else {
            books = FirstChecker.getBooks()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_books, container, false)

        if (books.isNullOrEmpty()){
            arrangeForEmptyBook(v)
        }else{
            arrangeForShowList(v)
        }

        v.fab.setOnClickListener {
            arrangeToAddBook(v)
        }

        v.list.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        val adapter = Book_Adapter(context!!,books)
        v.list.adapter = adapter

        textChangeListner(v.book_name_lay,"لطفا نام كتاب را وارد كنيد")
        textChangeListner(v.book_page_count_lay,"لطفا تعداد صفحات كتاب را وارد كنيد")

        v.book_save.setOnClickListener {
            if(v.book_name.text==null)
                v.book_name.error = "لطفا نام كتاب را وارد كنيد"
            else if(v.book_page_count.text==null)
                v.book_page_count.error = "لطفا تعداد صفحات كتاب را وارد كنيد"
            else {
                val b = Book(v.book_name.text.toString(),v.book_page_count.text.toString().toInt(),false)
                b.save()
                adapter.addNewBook(b)
                arrangeForShowList(v)
            }
        }

        return v
    }

    private fun arrangeToAddBook(v: View) {
        v.switcher.showNext()
        v.fab.hide()
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

    private fun arrangeForShowList(v: View) {
        v.list.visibility = VISIBLE
        v.emptyText.visibility = GONE
        v.fab.show()
        v.switcher.reset()
        v.book_name.setText("")
        v.book_name_lay.isErrorEnabled = false
        v.book_page_count.setText("")
        v.book_page_count_lay.isErrorEnabled = false
    }

    private fun arrangeForEmptyBook(v: View) {
        v.list.visibility = GONE
        v.emptyText.visibility = VISIBLE
        v.fab.show()
        v.switcher.reset()
        v.book_name.setText("")
        v.book_name_lay.isErrorEnabled = false
        v.book_page_count.setText("")
        v.book_page_count_lay.isErrorEnabled = false
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

    interface OnFragmentInteractionListener {

        fun FailOpenBooks()
    }

}

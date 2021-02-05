package morteza.darzi.SelfTeach2

//import DAL.AppDatabase
//import DAL.BookRepository
//import DAL.TermRepository
import DBAdapter.Book_Performance_Adapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import core.BookPerformance
import core.services.BookService
import core.services.TermService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import morteza.darzi.SelfTeach2.databinding.FragmentBookListBinding


class BooksPerformanceFragment : BaseFragment() {

    override val title: String
        get() = "كتاب ها"

    private var bookPerformances: MutableList<BookPerformance> = mutableListOf()
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var bookService: BookService
    private lateinit var performanceAdapter: Book_Performance_Adapter

    private var _binding: FragmentBookListBinding? = null
    private val fragmentBookListBinding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentBookListBinding.inflate(inflater, container, false)

        bookService = BookService(requireContext())

        intializeBeforeSuspend()
        intializeSuspend()

        return fragmentBookListBinding.root
    }

    private fun intializeBeforeSuspend() {
        showLoader(true)
    }

    private fun intializeSuspend() {
        launch {
            delay(500)
            val termService = TermService(requireContext())

            if (!termService.isTermExist()) {
                listener!!.failOpenBooks()
            }
            val term = termService.getTerm()

            for (book in bookService.getAllBookWithSumRead()) {
                bookPerformances.add(BookPerformance(term, book))
            }
            intializeAfterSuspend()

        }
    }

    private fun intializeAfterSuspend() {
        performanceAdapter = Book_Performance_Adapter(requireContext(), bookPerformances)
        fragmentBookListBinding.list.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        fragmentBookListBinding.list.adapter = performanceAdapter

        showLoader(false)
    }


    private fun showLoader(isShowder: Boolean) {
        if (isShowder) {
            fragmentBookListBinding.indicBookList.visibility = VISIBLE
            fragmentBookListBinding.list.visibility = GONE
        } else {
            fragmentBookListBinding.indicBookList.visibility = GONE
            fragmentBookListBinding.list.visibility = VISIBLE
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {

        fun failOpenBooks()
    }

}

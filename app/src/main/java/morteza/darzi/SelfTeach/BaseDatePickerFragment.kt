package morteza.darzi.SelfTeach

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar


abstract class BaseDatePickerFragment : BaseFragment(), DatePickerDialog.OnDateSetListener {
    abstract override val title: String

    abstract override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int)

    protected fun showDatapicker(tag: String) {
        val persianCalendar = PersianCalendar()
        val datePickerDialog = DatePickerDialog.newInstance(
                this,
                persianCalendar.persianYear,
                persianCalendar.persianMonth,
                persianCalendar.persianDay
        )

        datePickerDialog.show(activity!!.fragmentManager, tag)
    }
}
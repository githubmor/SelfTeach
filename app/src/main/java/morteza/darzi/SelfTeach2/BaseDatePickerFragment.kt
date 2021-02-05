package morteza.darzi.SelfTeach2

import com.sardari.daterangepicker.customviews.DateRangeCalendarView
import com.sardari.daterangepicker.dialog.DatePickerDialog
import com.sardari.daterangepicker.dialog.DatePickerDialog.OnRangeDateSelectedListener
import com.sardari.daterangepicker.dialog.DatePickerDialog.OnSingleDateSelectedListener
import com.sardari.daterangepicker.utils.PersianCalendar


abstract class BaseDatePickerFragment : BaseFragment(), OnRangeDateSelectedListener, OnSingleDateSelectedListener {
    abstract override val title: String
    abstract val mindate: PersianCalendar
    abstract val maxdate: PersianCalendar

    abstract override fun onRangeDateSelected(startDate: PersianCalendar, endDate: PersianCalendar)
    abstract override fun onSingleDateSelected(date: PersianCalendar?)

    protected fun showDatapicker(isRange: Boolean) {
        val datePickerDialog = DatePickerDialog(requireContext())
        if (isRange)
            datePickerDialog.selectionMode = DateRangeCalendarView.SelectionMode.Range
        else
            datePickerDialog.selectionMode = DateRangeCalendarView.SelectionMode.Single
        datePickerDialog.isDisableDaysAgo = false
        datePickerDialog.textSizeTitle = 10.0f
        datePickerDialog.textSizeWeek = 12.0f
        datePickerDialog.textSizeDate = 14.0f
        datePickerDialog.setCanceledOnTouchOutside(true)
        datePickerDialog.onRangeDateSelectedListener = this
        datePickerDialog.onSingleDateSelectedListener = this

        datePickerDialog.showDialog()
    }
}
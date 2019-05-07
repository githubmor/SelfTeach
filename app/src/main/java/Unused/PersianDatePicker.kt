//package BL;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.view.View;
//import android.view.Window;
//import android.widget.Button;
//import android.widget.TextView;
//import morteza.darzi.SelfTeach.R;
////import net.simonvt.numberpicker.NumberPicker;
//
///**
// * Created by M on 14/12/06.
// */
//public class PersianDatePicker {
//
//    // --Commented out by Inspection (15/02/02 22:42):public static final String G_DAY = "gDay";
//    // --Commented out by Inspection (15/02/02 22:42):public static final String G_MONTH = "gMonth";
//    // --Commented out by Inspection (15/02/02 22:42):public static final String G_YEAR = "gYear";
//    // --Commented out by Inspection (15/02/02 22:42):public static final String J_DAY = "jDay";
//    // --Commented out by Inspection (15/02/02 22:42):public static final String J_MONTH = "jMonth";
//    // --Commented out by Inspection (15/02/02 22:42):public static final String J_YEAR = "jYear";
//    private final String[] monthNames = { "فروردین", "اردیبهشت", "خرداد", "تیر",
//            "مرداد", "شهریور", "مهر", "آبان", "آذر", "دی", "بهمن", "اسفند" };
//    private NumberPicker npDay;
//    private NumberPicker npMonth;
//    private NumberPicker npYear;
//    private final Activity activity;
//
//    public PersianDatePicker(Activity activity) {
//       this.activity = activity;
//    }
//
//    public void showTarikhDialog(final TextView tarikh2) {
//        final Dialog dialog = new Dialog(activity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.date_dialog);
//        Button okBtn;
//
//        okBtn = (Button) dialog.findViewById(R.id.OkTarikh);
//        npYear = (NumberPicker) dialog.findViewById(R.id.npYear);
//        npMonth = (NumberPicker) dialog.findViewById(R.id.npMonth);
//        npDay = (NumberPicker) dialog.findViewById(R.id.npDay);
//
//        okBtn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                int newIrYear = npYear.getValue();
//                int newIrMonth = npMonth.getValue();
//                int newIrDay = npDay.getValue();
//
//                JDF jdf = new JDF();
//                jdf.setIranianDate(newIrYear, newIrMonth, newIrDay);
//                tarikh2.setText(jdf.getIranianDate());
//                dialog.dismiss();
//            }
//        });
//        NumberPicker.OnValueChangeListener rr = new NumberPicker.OnValueChangeListener() {
//
//
//
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal,
//                                      int newVal) {
//                if (picker == npMonth) {
//                    if (newVal <= 6) {
//                        npDay.setMaxValue(31);
//                    } else {
//                        npDay.setMaxValue(30);
//                    }
//                }
//
//
//            }
//        };
//
//        npMonth.setOnValueChangedListener(rr);
//        JDF jdf = new JDF();
//        int iranianYear = jdf.getIranianYear();
//        int iranianMonth = jdf.getIranianMonth();
//        int iranianDay = jdf.getIranianDay();
//
//        npYear.setMinValue(iranianYear-5);
//        npYear.setMaxValue(iranianYear+10);
//        npYear.setWrapSelectorWheel(true);
//        npMonth.setMinValue(1);
//        npMonth.setMaxValue(12);
//        npMonth.setDisplayedValues(monthNames);
//
//        npDay.setMinValue(1);
//        npDay.setMaxValue(31);
//
//        npYear.setValue(iranianYear);
//        npMonth.setValue(iranianMonth);
//        npDay.setValue(iranianDay);
//        dialog.show();
//    }
//}

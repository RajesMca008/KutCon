package kutumblink.appants.com.kutumblink.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import kutumblink.appants.com.kutumblink.R;

/**
 * Created by rrallabandi on 6/1/2017.
 */

public class DateTimePickerFragment extends DialogFragment {
    private Date mDate;
    private int year, month, day, hour, min;
    private String EXTRA_DATE="extra_date";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDate = new Date();//(Date)getArguments().getSerializable(EXTRA_DATE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);

        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_datetime, null);

        DatePicker datePicker = (DatePicker)v.findViewById(R.id.datePicker);
        TimePicker timePicker = (TimePicker)v.findViewById(R.id.timePicker);

        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            public void onDateChanged(DatePicker view, int year, int month, int day) {
                DateTimePickerFragment.this.year = year;
                DateTimePickerFragment.this.month = month;
                DateTimePickerFragment.this.day = day;
                updateDateTime();
            }
        });

        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(min);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            public void onTimeChanged(TimePicker view, int hour, int min) {
                DateTimePickerFragment.this.hour = hour;
                DateTimePickerFragment.this.min = min;
                updateDateTime();
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Select date and time")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //sendResult(Activity.RESULT_OK);
                    }
                })
                .create();
    }

    public void updateDateTime() {
        mDate = new GregorianCalendar(year, month, day, hour, min).getTime();
        getArguments().putSerializable(EXTRA_DATE, mDate);
    }
}
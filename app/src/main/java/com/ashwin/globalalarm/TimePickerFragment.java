package com.ashwin.globalalarm;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        //MainActivity.time = hourOfDay + " " + minute;
        MainActivity.tpHour = hourOfDay;
        MainActivity.tpMinute = minute;
        FragmentManager fm = getFragmentManager();
        AlarmDialogFragment editNameDialog = new AlarmDialogFragment();
        editNameDialog.show(fm, "fragment_edit_name");
    }
}
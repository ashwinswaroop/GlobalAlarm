package com.ashwin.globalalarm;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.TimeZone;

public class AlarmDialogFragment extends DialogFragment implements
        TextView.OnEditorActionListener {

    public interface EditNameDialogListener {
        void onFinishEditDialog(String inputText);
    }

    private EditText mEditText;

    public AlarmDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alarm_name, container);
        mEditText = (EditText) view.findViewById(R.id.txt_your_name);
        mEditText.getLayoutParams().width = 800;
        mEditText.requestFocus();
        mEditText.setOnEditorActionListener(this);

        return view;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
            //EditNameDialogListener activity = (EditNameDialogListener) getActivity();
            //activity.onFinishEditDialog(mEditText.getText().toString());
            MainActivity.name = mEditText.getText().toString();
            MainActivity.dbHelper.addAlarm(MainActivity.timezone+" | "+MainActivity.dpMonth+"/"+MainActivity.dpDay+"/"+MainActivity.dpYear+" |",MainActivity.tpHour+":"+MainActivity.tpMinute,MainActivity.name+ " |");
            ListView alarmList = (ListView) getActivity().findViewById(R.id.alarmList);
            Cursor cursor = MainActivity.dbHelper.getAlarms();
            AlarmCursorAdapter adapter = new AlarmCursorAdapter(getActivity().getApplicationContext(), cursor, 0);
            alarmList.setAdapter(adapter);
            adapter.changeCursor(cursor);
            Intent intent = new Intent("com.ashwin.globalalarm.myEvent");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(), MainActivity.alarmCounter, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmMgr = (AlarmManager) (getActivity().getApplicationContext().getSystemService(Context.ALARM_SERVICE));
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, MainActivity.dpYear);
            cal.set(Calendar.MONTH, MainActivity.dpMonth);
            cal.set(Calendar.DAY_OF_MONTH, MainActivity.dpDay);
            cal.set(Calendar.HOUR_OF_DAY, MainActivity.tpHour);
            cal.set(Calendar.MINUTE, MainActivity.tpMinute);
            cal.set(Calendar.SECOND, 0);
            cal.setTimeZone(TimeZone.getTimeZone(MainActivity.timezone));
            long mills = cal.getTimeInMillis();
            MainActivity.alarmCounter++;
            alarmMgr.set(AlarmManager.RTC_WAKEUP, mills, pendingIntent);
            //getActivity().setContentView(R.layout.activity_main);
            //alarmMgr.cancel(pendingIntent);
            //Toast.makeText(this, "Event scheduled at " + tpHour + ":" + tpMinute + " " + dpDay + "/" + dpMonth + "/" + dpYear, Toast.LENGTH_LONG).show();
            this.dismiss();
            startActivity(new Intent(getActivity(),MainActivity.class));
            //getActivity().setContentView(R.layout.activity_main);
            return true;
        }
        return false;
    }
}
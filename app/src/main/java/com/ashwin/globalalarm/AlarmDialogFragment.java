package com.ashwin.globalalarm;

import android.app.DialogFragment;
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
        getDialog().setTitle("Dialog Fragment Example");

        // Show soft keyboard automatically
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
            MainActivity.dbHelper.addAlarm(MainActivity.date,MainActivity.time,MainActivity.name);
            ListView alarmList = (ListView) getActivity().findViewById(R.id.alarmList);
            Cursor cursor = MainActivity.dbHelper.getAlarms();
            AlarmCursorAdapter adapter = new AlarmCursorAdapter(getActivity().getApplicationContext(), cursor, 0);
            alarmList.setAdapter(adapter);
            adapter.changeCursor(cursor);
            this.dismiss();
            return true;
        }
        return false;
    }
}
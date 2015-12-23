package com.ashwin.globalalarm;



import android.app.DialogFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    static AlarmDBHelper dbHelper;
    static String name;
    static int dpYear, dpMonth, dpDay, tpHour, tpMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbHelper = new AlarmDBHelper(getApplicationContext());
        //dbHelper.clearAlarms();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
                ListView alarmList = (ListView) findViewById(R.id.alarmList);
                Cursor cursor = dbHelper.getAlarms();
                AlarmCursorAdapter adapter = new AlarmCursorAdapter(getApplicationContext(), cursor, 0);
                alarmList.setAdapter(adapter);
                adapter.changeCursor(cursor);
            }
        });
        ListView alarmList = (ListView) findViewById(R.id.alarmList);
        Cursor cursor = dbHelper.getAlarms();
        AlarmCursorAdapter adapter = new AlarmCursorAdapter(getApplicationContext(), cursor, 0);
        alarmList.setAdapter(adapter);
        adapter.changeCursor(cursor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        ListView alarmList = (ListView) findViewById(R.id.alarmList);
        Cursor cursor = dbHelper.getAlarms();
        AlarmCursorAdapter adapter = new AlarmCursorAdapter(getApplicationContext(), cursor, 0);
        alarmList.setAdapter(adapter);
        adapter.changeCursor(cursor);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ListView alarmList = (ListView) findViewById(R.id.alarmList);
        Cursor cursor = dbHelper.getAlarms();
        AlarmCursorAdapter adapter = new AlarmCursorAdapter(getApplicationContext(), cursor, 0);
        alarmList.setAdapter(adapter);
        adapter.changeCursor(cursor);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

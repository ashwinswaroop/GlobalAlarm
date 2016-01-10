package com.ashwin.globalalarm;



import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity {

    static AlarmDBHelper dbHelper;
    static String name, timezone;
    static int dpYear, dpMonth, dpDay, tpHour, tpMinute;
    static int alarmCounter=0;
    static int flag;

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
                //DialogFragment newFragment = new DatePickerFragment();
                //newFragment.show(getFragmentManager(), "datePicker");
                TimeZoneFragment tzf = new TimeZoneFragment();
                tzf.show(getFragmentManager(), "timeZonePicker");
                ListView alarmList = (ListView) findViewById(R.id.alarmList);
                Cursor cursor = dbHelper.getAlarms();
                AlarmCursorAdapter adapter = new AlarmCursorAdapter(getApplicationContext(), cursor, 0);
                alarmList.setAdapter(adapter);
                adapter.changeCursor(cursor);
            }
        });
        final ListView alarmList = (ListView) findViewById(R.id.alarmList);
        alarmList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                // Yes button clicked
                                //Toast.makeText(MainActivity.this, "Yes Clicked",
                                //Toast.LENGTH_LONG).show();
                                if(dbHelper.getStatus(position) == 1) {
                                    dbHelper.disableAlarm(position);
                                    //flag=0;
                                }
                                else if(dbHelper.getStatus(position) == 0) {
                                    dbHelper.enableAlarm(position);
                                    //flag=1;
                                }

                                Intent intent = new Intent("com.ashwin.globalalarm.myEvent");
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), position, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                am.cancel(pendingIntent);

                                Cursor cursor = dbHelper.getAlarms();
                                AlarmCursorAdapter adapter = new AlarmCursorAdapter(getApplicationContext(), cursor, 0);
                                alarmList.setAdapter(adapter);
                                adapter.changeCursor(cursor);

                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                // No button clicked
                                // do nothing
                                //Toast.makeText(MainActivity.this, "No Clicked",
                                //Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                if(dbHelper.getStatus(position)==1) {
                    builder.setMessage("Disable this alarm?")
                            .setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
                else {
                    builder.setMessage("Enable this alarm?")
                            .setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
            }
        });
        alarmList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                // Yes button clicked
                                //Toast.makeText(MainActivity.this, "Yes Clicked",
                                //Toast.LENGTH_LONG).show();

                                dbHelper.deleteAlarm(position);
                                Intent intent = new Intent("com.ashwin.globalalarm.myEvent");
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), position, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                am.cancel(pendingIntent);
                                Cursor cursor = dbHelper.getAlarms();
                                AlarmCursorAdapter adapter = new AlarmCursorAdapter(getApplicationContext(), cursor, 0);
                                alarmList.setAdapter(adapter);
                                adapter.changeCursor(cursor);

                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                // No button clicked
                                // do nothing
                                //Toast.makeText(MainActivity.this, "No Clicked",
                                //Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Delete this alarm?")
                        .setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

                return true;
            }
        });

        Cursor cursor = dbHelper.getAlarms();
        final AlarmCursorAdapter adapter = new AlarmCursorAdapter(getApplicationContext(), cursor, 0);
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
        final ListView alarmList = (ListView) findViewById(R.id.alarmList);
        Cursor cursor = dbHelper.getAlarms();
        AlarmCursorAdapter adapter = new AlarmCursorAdapter(getApplicationContext(), cursor, 0);
        alarmList.setAdapter(adapter);
        adapter.changeCursor(cursor);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final ListView alarmList = (ListView) findViewById(R.id.alarmList);
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

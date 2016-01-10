package com.ashwin.globalalarm;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class AlarmCursorAdapter extends CursorAdapter {
    public AlarmCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Typeface type = Typeface.createFromAsset(context.getAssets(), "1.ttf");
        TextView Name = (TextView) view.findViewById(R.id.Name);
        TextView Lat = (TextView) view.findViewById(R.id.Date);
        TextView Lng = (TextView) view.findViewById(R.id.Time);
        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
        String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
        if(cursor.getColumnIndex(AlarmDBHelper.COLUMN_STATUS)!=-1&&cursor.getInt(cursor.getColumnIndex(AlarmDBHelper.COLUMN_STATUS))==1){
            Name.setTextColor(Color.BLACK);
            Lat.setTextColor(Color.BLACK);
            Lng.setTextColor(Color.BLACK);
        }
        else if(cursor.getColumnIndex(AlarmDBHelper.COLUMN_STATUS)!=-1&&cursor.getInt(cursor.getColumnIndex(AlarmDBHelper.COLUMN_STATUS))==0){
            Name.setTextColor(Color.GRAY);
            Lat.setTextColor(Color.GRAY);
            Lng.setTextColor(Color.GRAY);
        }
        else{
            Name.setTextColor(Color.BLUE);
            Lat.setTextColor(Color.BLUE);
            Lng.setTextColor(Color.BLUE);
        }
        Name.setTypeface(type);
        Name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        Name.setText(name);
        Lat.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        Lat.setTypeface(type);
        Lat.setText(date);
        Lng.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        Lng.setTypeface(type);
        Lng.setText(time);

    }
}
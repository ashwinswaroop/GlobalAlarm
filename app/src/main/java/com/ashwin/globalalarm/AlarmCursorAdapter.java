package com.ashwin.globalalarm;

import android.content.Context;
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
        Typeface type = Typeface.createFromAsset(context.getAssets(),"1.ttf");
        TextView Name = (TextView) view.findViewById(R.id.Name);
        TextView Lat = (TextView) view.findViewById(R.id.Date);
        TextView Lng = (TextView) view.findViewById(R.id.Time);
        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
        String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
        Name.setTypeface(type);
        Name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        Name.setTextColor(Color.BLACK);
        Name.setText(name);
        Lat.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        Lat.setTypeface(type);
        Lat.setTextColor(Color.BLACK);
        Lat.setText(date);
        Lng.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        Lng.setTypeface(type);
        Lng.setTextColor(Color.BLACK);
        Lng.setText(time);

    }
}
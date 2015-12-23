package com.ashwin.globalalarm;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import java.util.TimeZone;

public class TimeZoneFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.timezonepicker, container);

        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        spinner.getLayoutParams().height=200;
        String[] idArray = TimeZone.getAvailableIDs();
        ArrayAdapter<CharSequence> idAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.timezonelist,android.R.layout.simple_spinner_item);
        idAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Specify the layout to use when the list of choices appears
        spinner.setAdapter(idAdapter);    // Apply the adapter to the spinner
        //spinner.setSelection(5);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view2, int position, long id) {
                if(position>0) {
                    MainActivity.timezone = parent.getItemAtPosition(position).toString();
                    view.setVisibility(View.GONE);
                    //getActivity().setContentView(R.layout.activity_main);
                    DialogFragment newFragment = new DatePickerFragment();
                    newFragment.show(getFragmentManager(), "datePicker");
                    //getActivity().setContentView(R.layout.activity_main);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }
    /*
    @Override
    public void onDestroyView(){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }
    */
}
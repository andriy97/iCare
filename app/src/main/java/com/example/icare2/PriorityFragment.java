package com.example.icare2;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class PriorityFragment extends Fragment   {



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_priority_, container, false);

            Calendar c =Calendar.getInstance();
            int ora=c.get(Calendar.HOUR_OF_DAY);
            int minuti=c.get(Calendar.MINUTE);

            TimePickerDialog timedialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                }
            }, ora, minuti, true);
            timedialog.show();

            return view;
        }


}

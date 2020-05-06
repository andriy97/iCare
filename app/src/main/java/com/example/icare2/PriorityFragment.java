package com.example.icare2;

import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class PriorityFragment extends Fragment   {
    //creo shared preferences dove salvare le priorità dei valori corporei


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final SharedPreferences prioritySharedReference= getActivity().getSharedPreferences("priority", getContext().MODE_PRIVATE);
            final SharedPreferences.Editor editor=prioritySharedReference.edit();


            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_priority_, container, false);

           //priorità
            final NumberPicker tempPriority= view.findViewById(R.id.tempPriority);
            final NumberPicker freqPriority= view.findViewById(R.id.freqPriority);
            final NumberPicker pesoPriority= view.findViewById(R.id.pesoPriority);

            //setto range e valori se precedentemente settati, disabilito tastiera
            tempPriority.setMaxValue(5);tempPriority.setMinValue(1);tempPriority.setValue(prioritySharedReference.getInt("temperatura", 3));tempPriority.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            freqPriority.setMaxValue(5);freqPriority.setMinValue(1);freqPriority.setValue(prioritySharedReference.getInt("frequenza", 3));freqPriority.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            pesoPriority.setMaxValue(5);pesoPriority.setMinValue(1);pesoPriority.setValue(prioritySharedReference.getInt("peso", 3));pesoPriority.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

            Button salvaPriority= view.findViewById(R.id.salvapriority);

            salvaPriority.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                editor.putInt("temperatura", tempPriority.getValue());
                editor.putInt("frequenza", freqPriority.getValue());
                editor.putInt("peso", pesoPriority.getValue());
                editor.commit();
                Toast.makeText(getContext(), "Priorità salvate", Toast.LENGTH_SHORT).show();
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

                }
            });





            return view;
        }


}

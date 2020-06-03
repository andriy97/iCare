package com.example.icare2;

import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Range;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;


import static android.content.Context.MODE_PRIVATE;

public class PriorityFragment extends Fragment   {
 private Double minTemp, maxTemp, minFreq, maxFreq, minPeso, maxPeso;
 private int Days;



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final SharedPreferences prioritySharedReference= getActivity().getSharedPreferences("priority", getContext().MODE_PRIVATE);
            final SharedPreferences.Editor editor=prioritySharedReference.edit();

            editor.putBoolean("firstTime", false); //setto su false la prima apertura


            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_priority_, container, false);

           //priorità
            final NumberPicker tempPriority= view.findViewById(R.id.tempPriority);
            final NumberPicker freqPriority= view.findViewById(R.id.freqPriority);
            final NumberPicker pesoPriority= view.findViewById(R.id.pesoPriority);

            //monitoraggio valori
            final EditText minTemp = view.findViewById(R.id.minTemp);
            final EditText maxTemp = view.findViewById(R.id.maxTemp);
            final EditText minFreq = view.findViewById(R.id.minFreq);
            final EditText maxFreq = view.findViewById(R.id.maxFreq);
            final EditText minPeso = view.findViewById(R.id.minPeso);
            final EditText maxPeso = view.findViewById(R.id.maxPeso);

            minTemp.setFilters(new InputFilter[] { new InputFilter.LengthFilter(4) }); //max numero di caratteri
            maxTemp.setFilters(new InputFilter[] { new InputFilter.LengthFilter(4) }); //max numero di caratteri
            minFreq.setFilters(new InputFilter[] { new InputFilter.LengthFilter(4) }); //max numero di caratteri
            maxFreq.setFilters(new InputFilter[] { new InputFilter.LengthFilter(4) }); //max numero di caratteri
            minPeso.setFilters(new InputFilter[] { new InputFilter.LengthFilter(4) }); //max numero di caratteri
            maxPeso.setFilters(new InputFilter[] { new InputFilter.LengthFilter(4) }); //max numero di caratteri



            Spinner monitorDays=view.findViewById(R.id.monitorDays);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.periodoMonitoraggio, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            monitorDays.setAdapter(adapter);
            monitorDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch(position){
                        case 0:
                            Days=3;
                            break;
                        case 1:
                            Days=7;
                            break;
                        case 2:
                            Days=14;
                            break;
                        case 3:
                            Days=30;
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            //visualizzo valori se precedentemente settati, disabilito tastiera
            tempPriority.setMaxValue(5);tempPriority.setMinValue(1);tempPriority.setValue(prioritySharedReference.getInt("temperatura", 3));tempPriority.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            freqPriority.setMaxValue(5);freqPriority.setMinValue(1);freqPriority.setValue(prioritySharedReference.getInt("frequenza", 3));freqPriority.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            pesoPriority.setMaxValue(5);pesoPriority.setMinValue(1);pesoPriority.setValue(prioritySharedReference.getInt("peso", 3));pesoPriority.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);


            //visualizzo valori se già settati
            minTemp.setText(prioritySharedReference.getString("minTemp", "36.6"));
            maxTemp.setText(prioritySharedReference.getString("maxTemp", "36.9"));
            minFreq.setText(prioritySharedReference.getString("minFreq", "60"));
            maxFreq.setText(prioritySharedReference.getString("maxFreq", "100"));
            minPeso.setText(prioritySharedReference.getString("minPeso", "60"));
            maxPeso.setText(prioritySharedReference.getString("maxPeso", "70"));

            switch (prioritySharedReference.getInt("monitorDays", 3)){
                case 3:
                    monitorDays.setSelection(0);
                    break;
                case 7:
                    monitorDays.setSelection(1);
                    break;
                case 14:
                    monitorDays.setSelection(2);
                    break;
                case 30:
                    monitorDays.setSelection(3);
                    break;
            }












            Button salvaPriority= view.findViewById(R.id.salvapriority);

            salvaPriority.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //controllo stringhe vuote e se ci sono le setto
                    minTemp.setText(controllaStringheVuoteMin(minTemp));
                    minFreq.setText(controllaStringheVuoteMin(minFreq));
                    minPeso.setText(controllaStringheVuoteMin(minPeso));

                    maxTemp.setText(controllaStringheVuoteMax(maxTemp));
                    maxFreq.setText(controllaStringheVuoteMax(maxFreq));
                    maxPeso.setText(controllaStringheVuoteMax(maxPeso));


                // aggiungo i valori ai sharedPreferences
                editor.putInt("temperatura", tempPriority.getValue());
                editor.putInt("frequenza", freqPriority.getValue());
                editor.putInt("peso", pesoPriority.getValue());
                editor.putString("minTemp",minTemp.getText().toString());
                editor.putString("maxTemp", maxTemp.getText().toString());
                editor.putString("minFreq", minFreq.getText().toString());
                editor.putString("maxFreq", maxFreq.getText().toString());
                editor.putString("minPeso", minPeso.getText().toString());
                editor.putString("maxPeso", maxPeso.getText().toString());
                editor.putInt("monitorDays", Days);
                editor.commit();
                Toast.makeText(getContext(), "Preferenze salvate", Toast.LENGTH_SHORT).show();

                    if(MainActivity.fragmentManager.getBackStackEntryCount()!=0){
                        MainActivity.fragmentManager.popBackStackImmediate();
                    }else{
                        MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                    }
                }
            });





            return view;
        }

        public String controllaStringheVuoteMin(EditText temp){
            if(temp.getText().toString().isEmpty()){
                temp.setText("0");
                return temp.getText().toString();
            }else{
                return temp.getText().toString();
            }


        }

    public String controllaStringheVuoteMax(EditText temp){
        if(temp.getText().toString().isEmpty()){
            temp.setText("10000");
            return temp.getText().toString();
        }else{
            return temp.getText().toString();
        }


    }


}

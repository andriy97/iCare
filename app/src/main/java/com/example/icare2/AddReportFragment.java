package com.example.icare2;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.InputFilter;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddReportFragment extends Fragment {
    private ListView campilistView;
    String data;
    private Button addButton;
    Double valoreInserito;
    String mese, giorno, anno;
    public AddReportFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_report, container, false);

        campilistView= view.findViewById(R.id.campiList);

        //creo i campi che saranno messi nella listview
        Campo Data = new Campo("Data");
        Campo Temperatura = new Campo("Temperatura (C°)");
        Campo Pressione = new Campo("Frequenza Cardiaca (bpm)");
        Campo Peso = new Campo("Peso (Kg)");

        final List<Campo> listadicampi= new ArrayList<>();
        listadicampi.add(Data);
        listadicampi.add(Temperatura);
        listadicampi.add(Pressione);
        listadicampi.add(Peso);

        final CampiListAdapter campiListAdapter = new CampiListAdapter(getContext(), listadicampi);
        campilistView.setAdapter(campiListAdapter);
        addButton = view.findViewById(R.id.addbutton);



        campilistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    //creo calendarAlert per inserire data
                if(listadicampi.get(position).getTitolo()=="Data"){
                    Calendar calendar =Calendar.getInstance();

                    DatePickerDialog datedialog = new  DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            anno=""+year;
                            mese=""+month+1;
                            giorno=""+dayOfMonth;
                            //se non hanno lo 0 davanti glielo aggiungo
                            if(String.valueOf(month).length()<2)
                                mese= String.format("%02d", month+1);
                            if(String.valueOf(dayOfMonth).length()<2)
                                giorno=String.format("%02d", dayOfMonth);

                            data=anno+"-"+mese+"-"+giorno;
                            listadicampi.get(position).setValoredata(data);
                            campilistView.setAdapter(campiListAdapter); //aggiorno la listview con il valore inserito

                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    datedialog.show();
                }else{

                    //creo finestra di dialogo per inserire il dato
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Light_Dialog);
                    builder.setTitle(listadicampi.get(position).getTitolo());
                    final EditText input = new EditText(getContext());
                    input.setInputType(InputType.TYPE_CLASS_PHONE);
                    input.setKeyListener(DigitsKeyListener.getInstance(true,true)); //no spazi, solo un punto
                    input.setFilters(new InputFilter[] { new InputFilter.LengthFilter(4) }); //max numero di caratteri
                    builder.setView(input);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                valoreInserito = Double.parseDouble(input.getText().toString());
                                listadicampi.get(position).setValore(valoreInserito);
                                campilistView.setAdapter(campiListAdapter); //aggiorno la listview con il valore inserito
                            }catch (Exception e){
                                Toast.makeText(getContext(), "Inserisci un valore valido", Toast.LENGTH_SHORT).show();
                            }



                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.create();
                    builder.show();
                }


            }
        });


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(listadicampi.get(0).getValoredata()==null || listadicampi.get(1).getValore()==0.0 || listadicampi.get(2).getValore()==0.0 || listadicampi.get(3).getValore()==0.0){
                   Toast.makeText(getContext(), "Compila il report", Toast.LENGTH_SHORT).show();
               }else {

                   Double temperatura_ = listadicampi.get(1).getValore();
                   Double pressione_ = listadicampi.get(2).getValore();
                   Double peso_ = listadicampi.get(3).getValore();


                   //creo l'oggetto Report passandogli i dati presi in input da utente
                   Report report = new Report();
                   report.setData(data);
                   report.setFrequenza(pressione_);
                   report.setTemperatura(temperatura_);
                   report.setPeso(peso_);

                   MainActivity.MyDatabase.myDao().addReport(report); //aggiungo il report al database
                   Toast.makeText(getActivity(), "Report aggiunto", Toast.LENGTH_SHORT).show();
                   //ritorno alla home
                  // MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                   MainActivity.fragmentManager.popBackStackImmediate();
               }
            }
        });
        return view;
    }
}

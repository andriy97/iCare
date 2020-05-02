package com.example.icare2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
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
    private CalendarView calendario;
    int anno, mese, giorno;
    private Button addButton;
    double valoreInserito;
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
        calendario=view.findViewById(R.id.calendario);


        //creo i campi che saranno messi nella listview
        Campo Temperatura = new Campo("Temperatura");
        Campo Pressione = new Campo("Pressione");
        Campo Peso = new Campo("Peso");

        final List<Campo> listadicampi= new ArrayList<>();
        listadicampi.add(Temperatura);
        listadicampi.add(Pressione);
        listadicampi.add(Peso);

        final CampiListAdapter campiListAdapter = new CampiListAdapter(getContext(), listadicampi);
        campilistView.setAdapter(campiListAdapter);
        addButton = view.findViewById(R.id.addbutton);

        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                anno= year;
                mese=month;
                giorno=dayOfMonth;
            }
        });
        campilistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                //creo finestra di dialogo per inserire il dato

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
                builder.setTitle(listadicampi.get(position).getTitolo());
                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_PHONE);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        valoreInserito = Double.parseDouble(input.getText().toString());
                        listadicampi.get(position).setValore(valoreInserito);
                        campilistView.setAdapter(campiListAdapter); //aggiorno la listview con il valore inserito



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
        });


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                double pressione_= listadicampi.get(0).getValore();
                double temperatura_= listadicampi.get(1).getValore();
                int id_ = (int)(temperatura_+pressione_)+rand.nextInt(100000);

                if(anno!=0) {

                    //creo l'oggetto Report passandogli i dati presi in input da utente
                    Report report = new Report();
                    report.setId(id_);
                    report.setAnno(anno);
                    report.setMese(mese);
                    report.setGiorno(giorno);
                    report.setPressione(pressione_);
                    report.setTemperatura(temperatura_);

                    MainActivity.MyDatabase.myDao().addReport(report); //aggiungo il report al database
                    Toast.makeText(getActivity(), "Report aggiunto", Toast.LENGTH_SHORT).show();
                    //ritorno alla home
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

                }else{
                    //Todo: prendere data di oggi
                }

            }
        });
        return view;
    }
}

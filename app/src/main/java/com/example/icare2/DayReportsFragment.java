package com.example.icare2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DayReportsFragment extends Fragment {

    private List<Report> dayReports; //lista di report in un giorno specifico
    private List<Report> reports; //tutti i report
    private ListView dayreportlistview;
    private TextView titoloFragment;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_day_reports, container, false);
        reports= MainActivity.MyDatabase.myDao().getReportsDesc();
        //prendo il dato passato dall'altro fragment

        Bundle bundle=getArguments();
        final int positionPassed=bundle.getInt("position");
        dayReports=MainActivity.MyDatabase.myDao().getDayReports(reports.get(positionPassed).getData());
        dayreportlistview = view.findViewById(R.id.dayreportlist);

        titoloFragment= view.findViewById(R.id.titoloFragment);
        titoloFragment.setText("Tutti i report del giorno "+reports.get(positionPassed).getData()+":");
        //popolo la listview
        final ReportAdapter reportAdapter = new ReportAdapter(getContext(), dayReports);
       dayreportlistview.setAdapter(reportAdapter);




        //reazione al click lungo
        dayreportlistview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {


                //menu con opzioni
                PopupMenu menu = new PopupMenu(getContext(), view);
                menu.getMenuInflater().inflate(R.menu.options_menu, menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.modify_option:
                                //creo alertdialog
                                final AlertDialog.Builder alertbuilder = new AlertDialog.Builder(getContext());
                                View alertview = getLayoutInflater().inflate(R.layout.customalert, null);
                                final EditText Temperatura = alertview.findViewById(R.id.Temperatura);
                                final EditText Battito = alertview.findViewById(R.id.Battito);
                                final EditText Peso = alertview.findViewById(R.id.Peso);
                                Button modifica = alertview.findViewById(R.id.modifyalert);
                                Button annulla = alertview.findViewById(R.id.cancelalert);

                                DecimalFormat intero = new DecimalFormat("####0");//approssimo a intero
                                //setto i suoi campi con i valori del report cliccato
                                Temperatura.setText("" + dayReports.get(position).getTemperatura());
                                Battito.setText("" + intero.format(dayReports.get(position).getFrequenza()));
                                Peso.setText("" + dayReports.get(position).getPeso());


                                alertbuilder.setView(alertview);
                                final AlertDialog dialog = alertbuilder.create();

                                //clicco modifica
                                modifica.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        //creo oggetto temporaneo con i dati modificati
                                        Report reportTempo = new Report();
                                        reportTempo.setId(dayReports.get(position).getId());
                                        reportTempo.setTemperatura((Double.parseDouble(Temperatura.getText().toString())));
                                        reportTempo.setFrequenza((Double.parseDouble(Battito.getText().toString())));
                                        reportTempo.setPeso((Double.parseDouble(Peso.getText().toString())));
                                        reportTempo.setData(dayReports.get(position).getData());


                                        //aggiorno database
                                        MainActivity.MyDatabase.myDao().updateReport(reportTempo);
                                        //aggiorno listview
                                        dayReports=MainActivity.MyDatabase.myDao().getDayReports(reports.get(positionPassed).getData());
                                        reportAdapter.updateList(dayReports);
                                        dialog.dismiss();
                                        Toast.makeText(getContext(), "Report modificato", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                //clicco annulla
                                annulla.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();

                                    }
                                });
                                dialog.show();
                                break;

                            case R.id.delete_option:

                                AlertDialog.Builder alert = new AlertDialog.Builder(
                                        getContext());

                                alert.setMessage("Eliminare questo report?");
                                alert.setPositiveButton("Sì", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        MainActivity.MyDatabase.myDao().deleteReport(dayReports.get(position)); //elimino report cliccato dal database
                                        reportAdapter.removeElementFromList(position); //rimuovo il report dalla listview

                                        dialog.dismiss();
                                        Toast.makeText(getContext(), "Report eliminato", Toast.LENGTH_SHORT).show();

                                    }
                                });
                                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.dismiss();
                                    }
                                });

                                alert.show();


                                break;
                        }
                        return false;
                    }
                });
                menu.show();
                return true;

            }

        });




        dayreportlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                String nota=dayReports.get(position).getNota();

                if (nota != null) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setMessage(nota);
                    alert.show();

                } else {
                    Toast.makeText(getContext(), "Non è presente alcuna nota", Toast.LENGTH_SHORT).show();
                }
                }

        });



        return view;
    }
}

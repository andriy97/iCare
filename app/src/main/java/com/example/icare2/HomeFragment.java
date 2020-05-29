package com.example.icare2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private FloatingActionButton newReport;
    private TextView ifvuototext;

    //variabili
    private ListView listViewReport; //listview da legare all'id nel file xml
    private List<Report> reports; //lista di oggetti report


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        //collego i buttons a quelli nel file xml
        newReport = view.findViewById(R.id.newreportbutton);


        newReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //metto AddReportFragment nel container al posto di HomeFragment
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new AddReportFragment()).
                        addToBackStack(null).commit(); //aggiungo al backstack
            }
        });


        listViewReport = view.findViewById(R.id.reportlist);
        ifvuototext = view.findViewById(R.id.ifvuototext);


        //salvo tutti i report del database in una lista
        reports = MainActivity.MyDatabase.myDao().getReportsDesc();
        if (reports.size() == 0) {
            ifvuototext.setText("Non è presente alcun report");
        }

        //popolo la listview
        final ReportAdapter reportAdapter = new ReportAdapter(getContext(), reports);
        listViewReport.setAdapter(reportAdapter);


        //reazione al click lungo
        listViewReport.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
                                if (reports.get(position).getNumero() > 1) {
                                    //se il giorno ha più di un report apro un fragment con quei report e gli passo la posizione
                                    DayReportsFragment dayReportsFragment = new DayReportsFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("position", position);
                                    dayReportsFragment.setArguments(bundle);
                                    MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, dayReportsFragment).
                                            addToBackStack(null).commit(); //aggiungo al backstack


                                } else {
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
                                    Temperatura.setText("" + reports.get(position).getTemperatura());
                                    Battito.setText("" + intero.format(reports.get(position).getFrequenza()));
                                    Peso.setText("" + reports.get(position).getPeso());


                                    alertbuilder.setView(alertview);
                                    final AlertDialog dialog = alertbuilder.create();

                                    //clicco modifica
                                    modifica.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            //creo oggetto temporaneo con i dati modificati
                                            Report reportTempo = new Report();
                                            reportTempo.setId(reports.get(position).getId());
                                            reportTempo.setTemperatura((Double.parseDouble(Temperatura.getText().toString())));
                                            reportTempo.setFrequenza((Double.parseDouble(Battito.getText().toString())));
                                            reportTempo.setPeso((Double.parseDouble(Peso.getText().toString())));
                                            reportTempo.setData(reports.get(position).getData());


                                            //aggiorno database
                                            MainActivity.MyDatabase.myDao().updateReport(reportTempo);
                                            //aggiorno listview
                                            reports = MainActivity.MyDatabase.myDao().getReportsDesc();
                                            reportAdapter.updateList(reports);
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
                                }
                                break;
                            case R.id.delete_option:
                                //chiedo conferma e elimino report
                                AlertDialog.Builder alert = new AlertDialog.Builder(
                                        getContext());

                                alert.setMessage("Eliminare questo report?");
                                alert.setPositiveButton("Sì", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        MainActivity.MyDatabase.myDao().deleteReportsByDate(reports.get(position).getData()); //elimino report cliccato dal database
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


        //reazione al click corto (modifica report)
        listViewReport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nota=reports.get(position).getNota();

                if (reports.get(position).getNumero() > 1) {
                    //se il giorno ha più di un report apro un fragment con quei report e gli passo la posizione
                    DayReportsFragment dayReportsFragment = new DayReportsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", position);
                    dayReportsFragment.setArguments(bundle);
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, dayReportsFragment).
                            addToBackStack(null).commit(); //aggiungo al backstack
                }else {
                    if (nota != null) {

                        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                        alert.setMessage(nota);
                        alert.show();

                    } else {
                        Toast.makeText(getContext(), "Non è presente alcuna nota", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        return view;
    }
}

package com.example.icare2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 *
 *
 */


public class ModifyReportFragment extends Fragment{
    //variabili
    private ListView listViewReport; //listview da legare all'id nel file xml
    private List<Report> reports; //lista di oggetti report


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_modify_report, container, false);

        listViewReport = view.findViewById(R.id.reportlist);

        //salvo tutti i report del database in una lista
        reports = MainActivity.MyDatabase.myDao().getReports();

        //popolo la listview
        final ReportAdapter reportAdapter = new ReportAdapter(getContext(), reports);
        listViewReport.setAdapter(reportAdapter);

        //reazione al click lungo
        listViewReport.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder alert = new AlertDialog.Builder(
                        getContext());

                alert.setMessage("Eliminare questo report?");
                alert.setPositiveButton("Sì", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        MainActivity.MyDatabase.myDao().deleteReport(reports.get(position)); //elimino report cliccato dal database
                        reportAdapter.removeElementFromList(position); //rimuovo il report dalla listview

                        dialog.dismiss();

                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                alert.show();



                return false;
            }
        });

        //reazione al click corto (modifica report)
        listViewReport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                //creo alertdialog
                final AlertDialog.Builder alertbuilder = new AlertDialog.Builder(getContext());
                View alertview=getLayoutInflater().inflate(R.layout.customalert, null);
                final EditText Temperatura= alertview.findViewById(R.id.Temperatura);
                final EditText Pressione= alertview.findViewById(R.id.Pressione);
                final EditText Peso= alertview.findViewById(R.id.Peso);
                Button modifica = alertview.findViewById(R.id.modifyalert);
                Button annulla = alertview.findViewById(R.id.cancelalert);

                //setto i suoi campi con i valori del report cliccato
                Temperatura.setText(""+reports.get(position).getTemperatura());
                Pressione.setText(""+reports.get(position).getPressione());
                Peso.setText(""+reports.get(position).getPeso());


                alertbuilder.setView(alertview);
                final AlertDialog dialog= alertbuilder.create();

                //clicco modifica
                modifica.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //creo oggetto temporaneo con i dati modificati
                        Report reportTempo=new Report();
                        reportTempo.setId(reports.get(position).getId());
                        reportTempo.setTemperatura((Double.parseDouble(Temperatura.getText().toString())));
                        reportTempo.setPressione((Double.parseDouble(Pressione.getText().toString())));
                        reportTempo.setPeso((Double.parseDouble(Peso.getText().toString())));
                        reportTempo.setData(reports.get(position).getData());


                        //aggiorno database
                        MainActivity.MyDatabase.myDao().updateReport(reportTempo);
                        //aggiorno listview
                        reports = MainActivity.MyDatabase.myDao().getReports();
                        reportAdapter.updateList(reports);
                        dialog.dismiss();
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
        });
        return view;

    }


}

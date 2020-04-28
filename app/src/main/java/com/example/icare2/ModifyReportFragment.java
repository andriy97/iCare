package com.example.icare2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

        //reazione al click corto
        listViewReport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //reports.remove(position);




                //Log.d("clicked","id: " + mioid);

            }
        });
        return view;

    }

}

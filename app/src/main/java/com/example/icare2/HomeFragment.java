package com.example.icare2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener { //aggiungo implements view.Onclicklistener
    //Tasti per fare le tre azioni principali
    private Button newReport, modifyReport, deleteReport;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //collego i buttons a quelli nel file xml
        newReport=view.findViewById(R.id.newreportbutton);
        modifyReport = view.findViewById(R.id.modifybutton);

        //anziché scrivere per ogni button la funzione onClick, ne creo una con uno switch,
        // perciò setOnclickListener prende this come parametro
        newReport.setOnClickListener(this);
        modifyReport.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.newreportbutton: //se clicco aggiungi report
                //metto AddReportFragment nel container al posto di HomeFragment
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new AddReportFragment()).
                        addToBackStack(null).commit(); //aggiungo al backstack
                break;
            case R.id.modifybutton:
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new ModifyReportFragment()).
                        addToBackStack(null).commit();
                break;
        }
    }
}

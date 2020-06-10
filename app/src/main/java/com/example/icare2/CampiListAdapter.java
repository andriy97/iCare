package com.example.icare2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class CampiListAdapter extends BaseAdapter {

    private Context context;
    private List<Campo> campi;
    private static LayoutInflater inflater = null;

    public CampiListAdapter(Context context_, List<Campo> campi) {
        this.context = context_;
        this.campi = campi;
        inflater= (LayoutInflater) context_.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount(){
        return campi.size();
    }
    @Override
    public Campo getItem(int position){
        return campi.get(position);
    }
    @Override
    public long getItemId(int position){
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        convertView= inflater.inflate(R.layout.campi_list, null);
        TextView titolo = convertView.findViewById(R.id.titolocampo);
        TextView valore = convertView.findViewById(R.id.valorecampo);
        Campo selectedReport= campi.get(position);
        //setto titolo
        titolo.setText(selectedReport.getTitolo());
        //setto valore
        if(selectedReport.getTitolo()=="Data") {
            valore.setText("" + selectedReport.getValoredata());
            if(selectedReport.getValoredata()==null){
                valore.setText("clicca per aggiungere");
            }
        } else if(selectedReport.getTitolo()=="Note"){
            valore.setText(""+selectedReport.getNota());
            if(selectedReport.getNota()==null){
                valore.setText("clicca per aggiungere");
            }
        }else{
            if(selectedReport.getValore()==0.0){
                valore.setText("clicca per aggiungere");
            }else{
                valore.setText("" + selectedReport.getValore());
            }
        }

        return convertView;
    }

}

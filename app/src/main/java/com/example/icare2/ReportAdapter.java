package com.example.icare2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ReportAdapter extends BaseAdapter {

    private Context context;
    private List<Report> report;
    private static LayoutInflater inflater = null;


    public ReportAdapter(Context context_, List<Report> report_) {
            this.context = context_;
            this.report = report_;
            inflater= (LayoutInflater) context_.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
   public int getCount(){
        return report.size();
    }

    @Override
    public Report getItem(int position){
        return report.get(position);
    }
    @Override
    public long getItemId(int position){
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        convertView= inflater.inflate(R.layout.customlayout, null);
        TextView giorno = convertView.findViewById(R.id.giorno);
        TextView temperatura = convertView.findViewById(R.id.temp);
        TextView pressione = convertView.findViewById(R.id.pressione);
        TextView peso = convertView.findViewById(R.id.peso);

        Report selectedReport= report.get(position);
        giorno.setText("giorno: "+selectedReport.getGiorno());
        temperatura.setText("temperatura: "+selectedReport.getTemperatura());
        pressione.setText("pressione: "+selectedReport.getPressione());
        peso.setText("peso: "+selectedReport.getPeso());


        return convertView;
    }

    //elimina elemento da listview
    public void removeElementFromList(int index) {
        report.remove(index);
        this.notifyDataSetChanged();
    }

    public void updateList(List<Report> newlist) {
        report.clear();
        report.addAll(newlist);
        this.notifyDataSetChanged();
    }
}






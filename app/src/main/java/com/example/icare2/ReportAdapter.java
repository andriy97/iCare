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
        TextView textViewData = convertView.findViewById(R.id.cazzotroia);
        TextView textViewData1 = convertView.findViewById(R.id.temp);
        Report selectedReport= report.get(position);
        textViewData.setText("giorno: "+selectedReport.getGiorno());
        textViewData1.setText("anno: "+selectedReport.getAnno());


        return convertView;
    }

    //elimina elemento da listview
    public void removeElementFromList(int index) {
        report.remove(index);
        this.notifyDataSetChanged();
    }
}






package com.example.icare2;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class GraphsFragment extends Fragment {

    private LineChart graficotemp, graficofreq, graficopeso;
    private BarChart graficoreport;
    ArrayList<BarEntry> valoriYreport;
    ArrayList<Entry> valoriY;
    ArrayList<String> valoriX;
    XAxis xAxis;
    YAxis yAxis;
    List<Report> reports, sublistReport;
    LineDataSet lineDataSet;
    int i = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_graphs, container, false);

        //prendo report da database
        reports = MainActivity.MyDatabase.myDao().getReportsAsc(); //tutti i report
        if (reports.size() > 6) {
            sublistReport = reports.subList(reports.size() - 7, reports.size()); //gli ultimi 7 report
        } else {
            sublistReport = reports;
        }

        //valori x di tutti i grafici (data)
        valoriX = new ArrayList<>();
        for (Report rep : sublistReport) {

            valoriX.add(rep.getData().split("-")[2] + "-" + rep.getData().split("-")[1]);
        }


        //grafici linechart
        graficotemp= view.findViewById(R.id.graficotemp);
        graficofreq= view.findViewById(R.id.graficofreq);
        graficopeso= view.findViewById(R.id.graficopeso);
        drawLineChart(graficotemp, "Temperatura");
        drawLineChart(graficofreq, "Frequenza");
        drawLineChart(graficopeso, "Peso");

        //grafico barchart
        graficoreport=view.findViewById(R.id.graficoreport);
        drawBarChart(graficoreport);
        return view;
    }


    public void drawLineChart (LineChart grafico, String titolo){

        switch (titolo){
            case "Temperatura":
                valoriY = new ArrayList<>();
                //valori y
                for (Report rep : sublistReport) {
                    valoriY.add(new Entry(i, rep.getTemperatura().floatValue()));
                    i++;
                }
                i=0;
                 lineDataSet= new LineDataSet(valoriY, "Temperatura");
                break;
            case "Frequenza":
                valoriY = new ArrayList<>();
                //valori y
                for (Report rep : sublistReport) {
                    valoriY.add(new Entry(i, rep.getFrequenza().floatValue()));
                    i++;
                }
                i=0;
                lineDataSet= new LineDataSet(valoriY, "Frequenza");
                break;
            case "Peso":
                valoriY = new ArrayList<>();
                //valori y
                for (Report rep : sublistReport) {
                    valoriY.add(new Entry(i, rep.getPeso().floatValue()));
                    i++;
                }
                i=0;
                lineDataSet= new LineDataSet(valoriY, "Peso");
                break;

        }
        grafico.setDragEnabled(true);
        grafico.setScaleEnabled(false);

        //metto come valori di x le date dei report e sposto i valori in basso
        xAxis = grafico.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(valoriX));
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //disabilito valori y sulla destra
        yAxis = grafico.getAxisRight();
        yAxis.setEnabled(false);

        lineDataSet.setFillAlpha(110);
        lineDataSet.setColor(Color.RED);
        lineDataSet.setLineWidth(3f);
        lineDataSet.setCircleColor(Color.BLACK);
        lineDataSet.setValueTextSize(14f);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        LineData data = new LineData(dataSets);

        //metto valori nel grafico
        grafico.setData(data);
    }

    public void drawBarChart(BarChart grafico){
        valoriYreport=new ArrayList<>();
        for (Report rep : sublistReport) {
            valoriYreport.add(new BarEntry(i, (float) rep.getNumero()));
            i++;

        }
        i=0;

        xAxis = grafico.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(valoriX));
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        yAxis = grafico.getAxisRight();
        yAxis.setEnabled(false);
        Description description=new Description();
        description.setText("");
        BarDataSet barDataSet= new BarDataSet(valoriYreport, "Report");
        barDataSet.setColor(Color.RED);
        barDataSet.setValueTextSize(14f);
        BarData data= new BarData(barDataSet);
        graficoreport.setData(data);
        graficoreport.setDescription(description);
    }
}


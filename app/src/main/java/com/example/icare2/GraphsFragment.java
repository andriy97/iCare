package com.example.icare2;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class GraphsFragment extends Fragment  {

    private LineChart grafico1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_graphs, container, false);

        //creo grafico
        grafico1=view.findViewById(R.id.grafico1);
        grafico1.setDragEnabled(true);
        grafico1.setScaleEnabled(false);

        //creo valori grafico
        ArrayList<Entry> temperaturaList= new ArrayList<>();
        temperaturaList.add(new Entry(1, 36.6f));
        temperaturaList.add(new Entry(2, 36.9f));
        temperaturaList.add(new Entry(3, 36.6f));
        temperaturaList.add(new Entry(4, 39.7f));

        LineDataSet setTemperatura = new LineDataSet(temperaturaList, "Temperatura");
        setTemperatura.setFillAlpha(110);
        setTemperatura.setColor(Color.RED);
        setTemperatura.setLineWidth(3f);
        setTemperatura.setCircleColor(Color.BLACK);
        setTemperatura.setValueTextSize(14f);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(setTemperatura);
        LineData data = new LineData(dataSets);

        //metto valori nel grafico
        grafico1.setData(data);


        return view;
    }
}

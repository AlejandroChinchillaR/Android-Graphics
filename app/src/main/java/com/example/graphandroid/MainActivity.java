package com.example.graphandroid;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.graphandroid.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private BarChart barChart;
    private PieChart pieChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        barChart = findViewById(R.id.bar_chart);
        pieChart = findViewById(R.id.pie_chart);
        ArrayList<BarEntry> barEntries = new ArrayList();
        ArrayList<PieEntry> pieEntries = new ArrayList();

        for(int i=0 ; i<10; i++){
            float value = (float) (i*10.0);
            BarEntry barEntry = new BarEntry(i,value);
            PieEntry pieEntry = new PieEntry(i,value);

            barEntries.add(barEntry);
            pieEntries.add(pieEntry);
        }

        //Barchar dataset
        BarDataSet barDataSet = new BarDataSet(barEntries,"Employees");
        barDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        barDataSet.setDrawValues(false);
        barChart.setData(new BarData(barDataSet));
        barChart.animateY(5000);
        barChart.getDescription().setText("Employees chart");
        barChart.getDescription().setTextColor(Color.BLUE);

        //PieChart dataSet

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Student");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.setData(new PieData(pieDataSet));
        pieChart.animateXY(5000,5000);
        pieChart.getDescription().setEnabled(false);

    }

}
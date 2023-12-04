package com.example.graphandroid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import com.example.graphandroid.viewmodel.ChartViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.graphandroid.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private BarChart barChart;
    private PieChart pieChart;
    private Button loadCsvButton;
    private ChartViewModel viewModel;
    private static final int READ_REQUEST_CODE = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(ChartViewModel.class);
        barChart = findViewById(R.id.bar_chart);
        pieChart = findViewById(R.id.pie_chart);
        loadCsvButton = findViewById(R.id.load_csv_button);
        ArrayList<BarEntry> barEntries = new ArrayList();
        ArrayList<PieEntry> pieEntries = new ArrayList();



        loadCsvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilePicker();
            }
        });
        viewModel.checkCSV().observe(this, checked -> {

            if (checked) {

                barChart.setData(new BarData(viewModel.getBarDataSet()));
                barChart.animateY(5000);
                barChart.getDescription().setText("Employees chart");
                barChart.getDescription().setTextColor(Color.BLUE);

                pieChart.setData(new PieData(viewModel.getPieDataSet()));
                pieChart.animateXY(5000,5000);
                pieChart.getDescription().setEnabled(false);
            }else{
                Toast.makeText(this, "Error reading CSV.", Toast.LENGTH_SHORT).show();
            }

        });
        viewModel.getToastMessage().observe(this, message -> {
            if (message != null) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                viewModel.showToast(null); // Clear the message after displaying the Toast
            }
        });

    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/*"); // Set your required MIME type here
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == READ_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedFileUri = data.getData();
            Log.d("Tyrants",data.getData().toString());
            viewModel.loadCsvData(selectedFileUri, getContentResolver());
        }
    }
}
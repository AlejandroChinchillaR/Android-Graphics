package com.example.graphandroid.viewmodel;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ChartViewModel extends ViewModel {

    private MutableLiveData<String> toastMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> checkedCSV = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private ArrayList<BarEntry> barEntries = new ArrayList();
    private ArrayList<PieEntry> pieEntries = new ArrayList();
    private BarDataSet barDataSet;
    private PieDataSet pieDataSet;
    public LiveData<Boolean> isLoading() {
        return loading;
    }
    public LiveData<Boolean> checkCSV() {
        return checkedCSV;
    }

    public ChartViewModel() {
    }

    public void loadCsvData(Uri fileUri, ContentResolver contentResolver) {
        List<String[]> dataList = new ArrayList<>();
        if (isCSVFile(fileUri, contentResolver) ) {
            try {
                InputStream inputStream = contentResolver.openInputStream(fileUri);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while ((line = reader.readLine()) != null) {
                    String[] rowData = line.split(","); // Customize delimiter if needed
                    dataList.add(rowData);
                }
                inputStream.close();
                handleCsvData(dataList);
            } catch (IOException e) {
                // Handle file reading error
                e.printStackTrace();
            }
        } else {
            showToast("Invalid file");
        }

    }

    private void handleCsvData(List<String[]> csvData) {
        if (csvData != null && !csvData.isEmpty()) {
            for (String[] rowData : csvData) {
                try {
                    BarEntry barEntry = new BarEntry(Float.parseFloat(rowData[0]), Float.parseFloat(rowData[1]));
                    PieEntry pieEntry = new PieEntry(Float.parseFloat(rowData[1]), rowData[0]);
                    barEntries.add(barEntry);
                    pieEntries.add(pieEntry);
                } catch (NumberFormatException e) {
                    showToast("Error reading CSV row.");
                }

            }

            //Barchar dataset
            barDataSet = new BarDataSet(barEntries, "Employees");
            barDataSet.setColors(ColorTemplate.PASTEL_COLORS);
            barDataSet.setDrawValues(false);
            //PieChart dataSet
            pieDataSet = new PieDataSet(pieEntries, "Student");
            pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            checkedCSV.setValue(true);
        } else {
            showToast("Invalid file");
        }
    }


    private boolean isCSVFile(Uri uri,ContentResolver contentResolver) {
        String mimeType = contentResolver.getType(uri);
        return mimeType.equalsIgnoreCase("text/csv") || mimeType.equalsIgnoreCase("text/comma-separated-values");
    }

    public LiveData<String> getToastMessage() {
        return toastMessage;
    }

    public void showToast(String message) {
        toastMessage.setValue(message);
    }

    public BarDataSet getBarDataSet(){
        return  barDataSet;
    }

    public PieDataSet getPieDataSet(){
        return  pieDataSet;
    }
}
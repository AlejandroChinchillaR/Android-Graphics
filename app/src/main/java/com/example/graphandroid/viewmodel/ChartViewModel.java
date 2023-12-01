package com.example.graphandroid.viewmodel;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ChartViewModel extends ViewModel {


    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private MutableLiveData<List<String[]>> csvData = new MutableLiveData<>();

    public LiveData<Boolean> isLoading() {
        return loading;
    }
    public LiveData<List<String[]>> getCsvData() {
        return csvData;
    }
    public  ChartViewModel(){
    }

    public void loadCsvData(Uri fileUri, ContentResolver contentResolver) {
        List<String[]> dataList = new ArrayList<>();
        try {
            InputStream inputStream = contentResolver.openInputStream(fileUri);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(","); // Customize delimiter if needed
                dataList.add(rowData);
            }
            inputStream.close();
            csvData.setValue(dataList);
            Log.d("Test",csvData.toString());
        } catch (IOException e) {
            // Handle file reading error
            e.printStackTrace();
        }
    }
}

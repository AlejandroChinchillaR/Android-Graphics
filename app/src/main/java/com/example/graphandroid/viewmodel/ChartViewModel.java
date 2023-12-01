package com.example.graphandroid.viewmodel;

import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChartViewModel extends ViewModel {

    private MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public LiveData<Boolean> isLoading() {
        return loading;
    }
    public  ChartViewModel(){
    }

    public void loadCsv(){
        loading.setValue(true);
        Log.d("","");
        loading.setValue(false);
    }
}

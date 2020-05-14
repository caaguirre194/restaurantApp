package com.e.restaurant.ui.reporteVenta;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReporteVentaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ReporteVentaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Generar reportes de ventas");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
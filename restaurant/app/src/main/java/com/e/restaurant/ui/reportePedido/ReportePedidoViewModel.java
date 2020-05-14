package com.e.restaurant.ui.reportePedido;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReportePedidoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ReportePedidoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Reporte Pedidos");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
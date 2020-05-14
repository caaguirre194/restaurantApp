package com.e.restaurant.ui.pedido;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PedidoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PedidoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Solicita tu pedido");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
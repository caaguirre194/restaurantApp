package com.e.restaurant.ui.entrega;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EntregaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EntregaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Programar Entrega");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
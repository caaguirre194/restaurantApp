package com.e.restaurant.ui.carta;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CartaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CartaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Carta del restaurante");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
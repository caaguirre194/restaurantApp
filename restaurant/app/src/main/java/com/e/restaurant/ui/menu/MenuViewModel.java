package com.e.restaurant.ui.menu;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MenuViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MenuViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Crea tu propio menú");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
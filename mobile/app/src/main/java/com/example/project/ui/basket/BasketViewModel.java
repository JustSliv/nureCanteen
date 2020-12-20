package com.example.project.ui.basket;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BasketViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BasketViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
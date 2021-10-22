package com.example.homeworkoutapp.ui.restart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RestartViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public RestartViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is restart fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
package com.kokonut.NCNC.Home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<String> message = new MutableLiveData<>();

    public void setMessage(String msg){
        message.setValue(msg);
    }

    public LiveData<String> getMessage(){
        return message;
    }
    /*
    public MutableLiveData getMessage(){
        return message;
    }

    /*
    public SharedViewModel(MutableLiveData<String> message) {
        this.message = message;
    }


    public void init(){
        message = new MutableLiveData<>();
        message.setValue("Default Message");
    }


    public void sendMessage(String msg){
        message.setValue(msg);
    }

    public LiveData<String> getMessage(){
        return message;
    }
    /*
     */


}

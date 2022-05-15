package com.apps.dbrah_delivery.mvvm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class GeneralMvvm extends AndroidViewModel {
    private MutableLiveData<Boolean> onCurrentOrderRefreshed;



    public GeneralMvvm(@NonNull Application application) {
        super(application);
    }


    public MutableLiveData<Boolean> getOnCurrentOrderRefreshed() {
        if (onCurrentOrderRefreshed == null) {
            onCurrentOrderRefreshed = new MutableLiveData<>();
        }
        return onCurrentOrderRefreshed;
    }

}

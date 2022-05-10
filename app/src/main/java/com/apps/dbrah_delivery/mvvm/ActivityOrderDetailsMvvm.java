package com.apps.dbrah_delivery.mvvm;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.apps.dbrah_delivery.model.OrderModel;
import com.apps.dbrah_delivery.model.SingleOrderDataModel;
import com.apps.dbrah_delivery.remote.Api;
import com.apps.dbrah_delivery.tags.Tags;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivityOrderDetailsMvvm extends AndroidViewModel {

    private MutableLiveData<Boolean> isOrderDataLoading;
    private MutableLiveData<OrderModel> onOrderDetailsSuccess;
    private MutableLiveData<Integer> onStatusSuccess;

    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivityOrderDetailsMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getIsOrderDataLoading() {
        if (isOrderDataLoading == null) {
            isOrderDataLoading = new MutableLiveData<>();
        }
        return isOrderDataLoading;
    }

    public MutableLiveData<OrderModel> getOnOrderDetailsSuccess() {
        if (onOrderDetailsSuccess == null) {
            onOrderDetailsSuccess = new MutableLiveData<>();
        }
        return onOrderDetailsSuccess;
    }

    public void getOrderDetails(String order_id,String provider_id) {
        getIsOrderDataLoading().setValue(true);
       // Log.e("oooo",provider_id);
        Api.getService(Tags.base_url).getOrderDetails(order_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<SingleOrderDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<SingleOrderDataModel> response) {
                        getIsOrderDataLoading().setValue(false);
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                onOrderDetailsSuccess.setValue(response.body().getData());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("error", e.getMessage());
                    }
                });
    }

    public MutableLiveData<Integer> getOnOrderStatusSuccess() {
        if (onStatusSuccess == null) {
            onStatusSuccess = new MutableLiveData<>();
        }
        return onStatusSuccess;
    }


}

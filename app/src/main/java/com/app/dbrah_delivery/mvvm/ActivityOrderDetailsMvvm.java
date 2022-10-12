package com.app.dbrah_delivery.mvvm;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.app.dbrah_delivery.R;
import com.app.dbrah_delivery.model.OrderModel;
import com.app.dbrah_delivery.model.SingleOrderDataModel;
import com.app.dbrah_delivery.remote.Api;
import com.app.dbrah_delivery.share.Common;
import com.app.dbrah_delivery.tags.Tags;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivityOrderDetailsMvvm extends AndroidViewModel {

    private MutableLiveData<Boolean> isOrderDataLoading;
    private MutableLiveData<OrderModel> onOrderDetailsSuccess;
    private MutableLiveData<String> onStatusSuccess;

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
        Api.getService(Tags.base_url).getOrderDetails(order_id,provider_id)
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

    public MutableLiveData<String> getOnOrderStatusSuccess() {
        if (onStatusSuccess == null) {
            onStatusSuccess = new MutableLiveData<>();
        }
        return onStatusSuccess;
    }
    public void cheangeOrderStatus(String order_id,String representative_id, String status, Context context) {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        Api.getService(Tags.base_url).changeOrderStatus(order_id,representative_id, status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<SingleOrderDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<SingleOrderDataModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatus() == 200) {

                                        getOnOrderStatusSuccess().setValue(response.body().getData().getStatus());


                                }
                                else if(response.body().getStatus()==420){
                                    Toast.makeText(context, context.getString(R.string.dlivery_take), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("error", e.getMessage());
                        dialog.dismiss();
                    }
                });
    }


}

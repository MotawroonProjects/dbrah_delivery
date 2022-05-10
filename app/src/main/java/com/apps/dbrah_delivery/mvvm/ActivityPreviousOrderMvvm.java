package com.apps.dbrah_delivery.mvvm;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.dbrah_delivery.model.OrdersModel;
import com.apps.dbrah_delivery.model.UserModel;
import com.apps.dbrah_delivery.remote.Api;
import com.apps.dbrah_delivery.tags.Tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivityPreviousOrderMvvm  extends AndroidViewModel {

    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<List<OrdersModel.Data>> onOrderDataSuccess;
    private MutableLiveData<String> filterBy;
    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivityPreviousOrderMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<OrdersModel.Data>> getOnOrderDataSuccess() {
        if (onOrderDataSuccess==null){
            onOrderDataSuccess=new MutableLiveData<>();
        }
        return onOrderDataSuccess;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        if (isLoading == null) {
            isLoading = new MutableLiveData<>();
        }
        return isLoading;
    }

    public MutableLiveData<String> getFilterBy() {
        if (filterBy == null) {
            filterBy = new MutableLiveData<>();
        }
        return filterBy;
    }

    public void setFilterBy(String filter){
        getFilterBy().setValue(filter);
    }

    public void getPreviousOrders(UserModel userModel,String filterBy) {
        Log.e("iddf",userModel.getData().getId());
        if (userModel == null) {
            getIsLoading().setValue(false);
            getOnOrderDataSuccess().setValue(new ArrayList<>());
            return;
        }
        getIsLoading().setValue(true);
        Api.getService(Tags.base_url)
                .getPreviousOrders(userModel.getData().getId(),filterBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<OrdersModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<OrdersModel> response) {
                        getIsLoading().setValue(false);
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatus() == 200) {
                                    getOnOrderDataSuccess().setValue(response.body().getData());


                                }
                            }

                        } else {
                            try {
                                Log.e("error", response.errorBody().string() + "__" + response.code());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("error", e.getMessage());
                        getIsLoading().setValue(false);
                    }
                });
    }
}

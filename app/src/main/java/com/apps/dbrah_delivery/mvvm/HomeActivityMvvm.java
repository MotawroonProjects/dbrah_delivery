package com.apps.dbrah_delivery.mvvm;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.dbrah_delivery.R;
import com.apps.dbrah_delivery.model.StatusResponse;
import com.apps.dbrah_delivery.model.UserModel;
import com.apps.dbrah_delivery.remote.Api;
import com.apps.dbrah_delivery.share.Common;
import com.apps.dbrah_delivery.tags.Tags;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class HomeActivityMvvm extends AndroidViewModel {
    private Context context;

    public MutableLiveData<String> firebase = new MutableLiveData<>();

    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<UserModel> onUserDataSuccess;

    public HomeActivityMvvm(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();


    }
    public MutableLiveData<UserModel> getUserData() {
        if (onUserDataSuccess == null) {
            onUserDataSuccess = new MutableLiveData<>();
        }
        return onUserDataSuccess;
    }
    public void updateFirebase(Context context, UserModel userModel) {
       /* FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener((Activity) context, task -> {
            if (task.isSuccessful()) {
                String token = task.getResult().getToken();

                Api.getService(Tags.base_url).updateFirebasetoken("Bearer " + userModel.getData().getToken(), Tags.api_key, token, userModel.getData().getId() + "", "android").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io()).subscribe(new SingleObserver<Response<StatusResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<StatusResponse> statusResponseResponse) {
                        if (statusResponseResponse.isSuccessful()) {
                            if (statusResponseResponse.body().getStatus() == 200) {
                                firebase.postValue(token);
                                Log.e("token", "updated successfully");
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {

                    }
                });
            }
        });*/


    }
    public void changeStatus(Context context, String user_id) {

        Api.getService(Tags.base_url).updateStatus(user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<UserModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<UserModel> response) {

                        if (response.isSuccessful()) {
                            Log.e("status", response.body().getStatus() + "");
                            if (response.body() != null) {

                                if (response.body().getStatus() == 200) {

                                    getUserData().setValue(response.body());
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

                    }
                });
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}

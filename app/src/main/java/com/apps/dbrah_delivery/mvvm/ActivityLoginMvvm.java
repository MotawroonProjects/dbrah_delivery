package com.apps.dbrah_delivery.mvvm;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.apps.dbrah_delivery.R;
import com.apps.dbrah_delivery.model.CountryModel;
import com.apps.dbrah_delivery.model.LoginModel;
import com.apps.dbrah_delivery.model.UserModel;
import com.apps.dbrah_delivery.remote.Api;
import com.apps.dbrah_delivery.share.Common;
import com.apps.dbrah_delivery.tags.Tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivityLoginMvvm extends AndroidViewModel {
    private static final String TAG = "ActivityVerificationMvvm";
    private Context context;

    private MutableLiveData<String> onSmsCodeSuccess;
    private MutableLiveData<String> onTimeStarted;
    private MutableLiveData<Boolean> onCanResend;

    public MutableLiveData<UserModel> userModelMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<CountryModel>> coListMutableLiveData;
    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<UserModel> onUserDataSuccess;

    public ActivityLoginMvvm(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();


    }
    public MutableLiveData<UserModel> getUserData() {
        if (onUserDataSuccess == null) {
            onUserDataSuccess = new MutableLiveData<>();
        }
        return onUserDataSuccess;
    }
    public MutableLiveData<String> getSmsCode() {
        if (onSmsCodeSuccess == null) {
            onSmsCodeSuccess = new MutableLiveData<>();
        }
        return onSmsCodeSuccess;
    }

    public MutableLiveData<String> getTime() {
        if (onTimeStarted == null) {
            onTimeStarted = new MutableLiveData<>();
        }
        return onTimeStarted;
    }

    public MutableLiveData<Boolean> canResend() {
        if (onCanResend == null) {
            onCanResend = new MutableLiveData<>();
        }
        return onCanResend;
    }
    public MutableLiveData<UserModel> getUserModelMutableLiveData() {
        if (userModelMutableLiveData == null) {
            userModelMutableLiveData = new MutableLiveData<>();
        }
        return userModelMutableLiveData;
    }

    public MutableLiveData<List<CountryModel>> getCoListMutableLiveData() {
        if (coListMutableLiveData == null) {
            coListMutableLiveData = new MutableLiveData<>();
        }
        return coListMutableLiveData;
    }

    public void sendSmsCode(LoginModel model) {
        startTimer();


    }

    public void reSendSmsCode(LoginModel model) {
        startTimer();

    }
    private void startTimer() {
        canResend().setValue(false);
        Observable.intervalRange(1, 60, 0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {
                        long diff = 60 - aLong;
                        int min = ((int) diff / 60);
                        int sec = ((int) diff % 60);
                        String time = String.format(Locale.ENGLISH, "%02d:%02d", min, sec);
                        getTime().setValue(time);


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("onErrorVerCode", e.getMessage() + "_");
                    }

                    @Override
                    public void onComplete() {
                        getTime().setValue("00:00");
                        canResend().setValue(true);

                    }
                });

    }


    public void setCountry() {
        CountryModel[] countries = new CountryModel[]{
                new CountryModel("EG", context.getResources().getString(R.string.egypt), "+20", R.drawable.flag_eg, "EGP"), new CountryModel("SA", "Saudi Arabia", "+966", R.drawable.flag_sa, "SAR")};
        coListMutableLiveData.postValue(new ArrayList<>(Arrays.asList(countries)));
    }

    public void login(Context context, String phone_code, String phone) {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getResources().getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService(Tags.base_url).login(phone_code, phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<UserModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<UserModel> response) {
                        dialog.dismiss();

                        if (response.isSuccessful()) {
                            Log.e("status", response.body().getStatus() + "");
                            if (response.body() != null) {

                                if (response.body().getStatus() == 200) {

                                    getUserData().setValue(response.body());
                                } else if (response.body().getStatus() == 400) {
                                    getUserData().setValue(null);

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
                        dialog.dismiss();

                    }
                });
    }


    public void stopTimer(){
        disposable.clear();
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}

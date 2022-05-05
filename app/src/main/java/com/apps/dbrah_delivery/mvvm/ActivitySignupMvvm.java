package com.apps.dbrah_delivery.mvvm;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.dbrah_delivery.R;
import com.apps.dbrah_delivery.model.SignUpModel;
import com.apps.dbrah_delivery.model.UserModel;
import com.apps.dbrah_delivery.remote.Api;
import com.apps.dbrah_delivery.share.Common;
import com.apps.dbrah_delivery.tags.Tags;

import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class ActivitySignupMvvm extends AndroidViewModel {
    private Context context;
    public MutableLiveData<UserModel> userModelMutableLiveData = new MutableLiveData<>();

    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivitySignupMvvm(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();


    }



    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}

package com.app.dbrah_delivery.mvvm;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.app.dbrah_delivery.R;
import com.app.dbrah_delivery.model.CountryModel;
import com.app.dbrah_delivery.model.NationalitiesModel;
import com.app.dbrah_delivery.model.SignUpModel;
import com.app.dbrah_delivery.model.UserModel;
import com.app.dbrah_delivery.remote.Api;
import com.app.dbrah_delivery.share.Common;
import com.app.dbrah_delivery.tags.Tags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class ActivitySignupMvvm extends AndroidViewModel {
    private Context context;

    public MutableLiveData<UserModel> userModelMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<NationalitiesModel.Data>> onNationalitiesSuccess;
    private MutableLiveData<List<CountryModel>> coListMutableLiveData;
    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivitySignupMvvm(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();


    }



    public MutableLiveData<List<NationalitiesModel.Data>> getOnNationalitiesSuccess() {
        if (onNationalitiesSuccess==null){
            onNationalitiesSuccess=new MutableLiveData<>();
        }
        return onNationalitiesSuccess;
    }

    public MutableLiveData<List<CountryModel>> getCoListMutableLiveData() {
        if (coListMutableLiveData == null) {
            coListMutableLiveData = new MutableLiveData<>();
        }
        return coListMutableLiveData;
    }


    public void getNationalities(){
        Api.getService(Tags.base_url).getNationalities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<NationalitiesModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<NationalitiesModel> response) {

                        if (response.isSuccessful() && response.body()!=null){
                            if (response.body().getData()!=null && response.body().getStatus()==200){
                                getOnNationalitiesSuccess().setValue(response.body().getData());

                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("error",e.toString());
                    }
                });
    }

    public void signUp(SignUpModel model,Context context){
        ProgressDialog dialog = Common.createProgressDialog(context, context.getResources().getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();

        RequestBody name_part = Common.getRequestBodyText(model.getFirst_name()+" "+model.getSecond_name());
        RequestBody phone_part = Common.getRequestBodyText(model.getPhone());
        RequestBody phone_code_part = Common.getRequestBodyText(model.getPhone_code());
        RequestBody provider_code_part = Common.getRequestBodyText(model.getProvider_code());
        RequestBody nationality_id_part = Common.getRequestBodyText(model.getNationality_id()+"");
        RequestBody town_id_part = Common.getRequestBodyText(model.getTown_id()+"");
        RequestBody residence_number_part = Common.getRequestBodyText(model.getResidence_number());
        RequestBody delivery_range_part = Common.getRequestBodyText(model.getDelivery_range());

        MultipartBody.Part image = null;
        if (model.getImage() != null && !model.getImage().isEmpty()) {
            image = Common.getMultiPart(context, Uri.parse(model.getImage()), "image");
        }

        Api.getService(Tags.base_url).signUp(phone_part,phone_code_part,name_part,
                provider_code_part,nationality_id_part,town_id_part,
                residence_number_part,delivery_range_part,image)
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
//                        Log.e("sttt",response.code()+" ");
                        if (response.isSuccessful()) {
                            if (response.body().getStatus() == 200) {
                                userModelMutableLiveData.postValue(response.body());
                            }
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dialog.dismiss();
                        Log.e("error", e.toString());
                    }
                });
    }

    public void setCountry() {
        CountryModel[] countries = new CountryModel[]{
                new CountryModel("EG", "Egypt", "+20", R.drawable.flag_eg, "EGP"), new CountryModel("SA", "Saudi Arabia", "+966", R.drawable.flag_sa, "SAR")};
        coListMutableLiveData.postValue(new ArrayList<>(Arrays.asList(countries)));
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}

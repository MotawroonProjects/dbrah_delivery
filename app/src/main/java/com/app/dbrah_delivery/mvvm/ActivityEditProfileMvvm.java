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
import com.app.dbrah_delivery.model.EditProfileModel;
import com.app.dbrah_delivery.model.NationalitiesModel;
import com.app.dbrah_delivery.model.UserModel;
import com.app.dbrah_delivery.remote.Api;
import com.app.dbrah_delivery.share.Common;
import com.app.dbrah_delivery.tags.Tags;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class ActivityEditProfileMvvm extends AndroidViewModel {

    public MutableLiveData<UserModel> userModelMutableLiveData = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivityEditProfileMvvm(@NonNull Application application) {
        super(application);
    }

    public void update(EditProfileModel model, Context context,UserModel userModel){

        ProgressDialog dialog = Common.createProgressDialog(context, context.getResources().getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();

        RequestBody representative_id_part=Common.getRequestBodyText(userModel.getData().getId()+"");
        RequestBody name_part = Common.getRequestBodyText(model.getFirst_name()+" "+model.getSecond_name());
        RequestBody phone_part = Common.getRequestBodyText(model.getPhone());
        RequestBody phone_code_part = Common.getRequestBodyText(model.getPhone_code());
        RequestBody provider_code_part = Common.getRequestBodyText(model.getProvider_code());
        RequestBody delivery_range_part = Common.getRequestBodyText(model.getDelivery_range());

        MultipartBody.Part image = null;
        if (model.getImage() != null && !model.getImage().isEmpty()) {
            image = Common.getMultiPart(context, Uri.parse(model.getImage()), "image");
        }

        Api.getService(Tags.base_url).update(representative_id_part,phone_part,
                phone_code_part,name_part,delivery_range_part,provider_code_part,image)
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
//                        Log.e("status",response.code()+""+response.body().getStatus());
                        if (response.isSuccessful() && response.body()!=null){
                            if (response.body().getData()!=null && response.body().getStatus()==200){
                                userModelMutableLiveData.postValue(response.body());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dialog.dismiss();
                        Log.e("error",e.toString());
                    }
                });

    }
}

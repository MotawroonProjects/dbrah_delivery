package com.app.dbrah_delivery.model;

import android.content.Context;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.app.dbrah_delivery.BR;
import com.app.dbrah_delivery.R;


public class SignUpModel extends BaseObservable {
    private String phone_code;
    private String phone;
    private String first_name;
    private String second_name;
    private String provider_code;
    private int nationality_id;
    private int town_id;
    private String residence_number;
    private String image;
    private String delivery_range;
    private boolean checked;




    public ObservableField<String> error_phone = new ObservableField<>();
    public ObservableField<String> error_first_name = new ObservableField<>();
    public ObservableField<String> error_second_name = new ObservableField<>();
    public ObservableField<String> error_residence_number = new ObservableField<>();
    public ObservableField<String> error_provider_code = new ObservableField<>();


    public boolean isDataValid(Context context) {
        if (!first_name.trim().isEmpty() &&
                !second_name.trim().isEmpty() &&
                !phone.trim().isEmpty()&&
                 nationality_id!=0 &&
                town_id!=0&&
                (!checked || !provider_code.isEmpty())&&
                !residence_number.isEmpty()
        ) {
            error_phone.set(null);
            error_first_name.set(null);
            error_second_name.set(null);
            error_residence_number.set(null);
            error_provider_code.set(null);

            return true;
        } else {
            if (phone.isEmpty()){
                error_phone.set(context.getString(R.string.field_required));
            }else {
                error_phone.set(null);
            }
            if (first_name.trim().isEmpty()) {
                error_first_name.set(context.getString(R.string.field_required));

            } else {
                error_first_name.set(null);

            }
            if (second_name.trim().isEmpty()) {
                error_second_name.set(context.getString(R.string.field_required));

            } else {
                error_second_name.set(null);

            }
            if (residence_number.trim().isEmpty()){
                error_residence_number.set(context.getString(R.string.field_required));
            }else {
                error_residence_number.set(null);
            }
            if (nationality_id==0){
                Toast.makeText(context, R.string.choose_nationality, Toast.LENGTH_SHORT).show();
            }
            if (town_id==0){
                Toast.makeText(context, R.string.choose_town, Toast.LENGTH_SHORT).show();
            }
            if (checked &&provider_code.isEmpty() ){
                error_provider_code.set(context.getString(R.string.field_required));
            }else {
                error_provider_code.set(null);
            }
            return false;
        }
    }

    public SignUpModel(String phone_code, String phone) {

        setPhone(phone);
        setPhone_code(phone_code);
        setFirst_name("");
        setSecond_name("");
        setNationality_id(0);
        setTown_id(0);
        setResidence_number("");
        setImage("");
        setProvider_code("");
        setChecked(false);

    }


    @Bindable
    public String getPhone_code() {
        return phone_code;
    }

    public void setPhone_code(String phone_code) {
        this.phone_code = phone_code;
        notifyPropertyChanged(BR.phone_code);
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);

    }


    @Bindable
    public String getProvider_code() {
        return provider_code;
    }

    public void setProvider_code(String provider_code) {
        this.provider_code = provider_code;
        notifyPropertyChanged(BR.provider_code);
    }

    @Bindable
    public int getNationality_id() {
        return nationality_id;
    }

    public void setNationality_id(int nationality_id) {
        this.nationality_id = nationality_id;
        notifyPropertyChanged(BR.nationality_id);
    }

    @Bindable
    public int getTown_id() {
        return town_id;
    }

    public void setTown_id(int town_id) {
        this.town_id = town_id;
        notifyPropertyChanged(BR.town_id);
    }

    @Bindable
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
        notifyPropertyChanged(BR.image);
    }

    @Bindable
    public String getResidence_number() {
        return residence_number;
    }

    public void setResidence_number(String residence_number) {
        this.residence_number = residence_number;
        notifyPropertyChanged(BR.residence_number);
    }

    @Bindable
    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
        notifyPropertyChanged(BR.first_name);

    }

    @Bindable
    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
        notifyPropertyChanged(BR.second_name);

    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getDelivery_range() {
        return delivery_range;
    }

    public void setDelivery_range(String delivery_range) {
        this.delivery_range = delivery_range;
    }


}
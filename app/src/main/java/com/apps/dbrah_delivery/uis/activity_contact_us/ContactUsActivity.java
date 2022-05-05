package com.apps.dbrah_delivery.uis.activity_contact_us;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.apps.dbrah_delivery.R;
import com.apps.dbrah_delivery.databinding.ActivityContactUsBinding;
import com.apps.dbrah_delivery.model.ContactUsModel;
import com.apps.dbrah_delivery.model.UserModel;
import com.apps.dbrah_delivery.mvvm.ContactusActivityMvvm;
import com.apps.dbrah_delivery.preferences.Preferences;
import com.apps.dbrah_delivery.uis.activity_base.BaseActivity;

public class ContactUsActivity extends BaseActivity {
    private ActivityContactUsBinding binding;
    private ContactUsModel contactUsModel;
    private ContactusActivityMvvm contactusActivityMvvm;
    private UserModel userModel;
    private Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_us);
        initView();

    }

    private void initView() {
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        contactusActivityMvvm = ViewModelProviders.of(this).get(ContactusActivityMvvm.class);
        setUpToolbar(binding.toolbar, getString(R.string.contact_us), R.color.colorPrimary, R.color.white);
        binding.toolbar.llBack.setOnClickListener(view -> finish());

        contactUsModel = new ContactUsModel();
        if (userModel != null) {
            contactUsModel.setName(userModel.getData().getName());

        }

        binding.setContactModel(contactUsModel);
        binding.btnSend.setOnClickListener(view -> {
            if (contactUsModel.isDataValid(this)) {
                contactusActivityMvvm.contactus(this, contactUsModel);
            }
        });
        contactusActivityMvvm.send.observe(this, aBoolean -> {
            if (aBoolean) {
                Toast.makeText(ContactUsActivity.this, getResources().getString(R.string.suc), Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }


}
package com.app.dbrah_delivery.uis.activity_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.databinding.DataBindingUtil;

import com.app.dbrah_delivery.R;
import com.app.dbrah_delivery.databinding.ActivityAppBinding;
import com.app.dbrah_delivery.uis.activity_base.BaseActivity;


public class AppActivity extends BaseActivity {
    private ActivityAppBinding binding;
    private String type;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_app);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent(){
        Intent intent=getIntent();
        type = intent.getStringExtra("data");
        url=intent.getStringExtra("url");

    }

    private void initView() {
        binding.setLang(getLang());

        if (type.equals("terms")) {
            setUpToolbar(binding.toolbar, getString(R.string.terms_and_conditions), R.color.white, R.color.black);
        } else{
            setUpToolbar(binding.toolbar, getString(R.string.privacy_policy), R.color.white, R.color.black);
        }
        binding.toolbar.llBack.setOnClickListener(view -> finish());
        setUpWebView();
    }

    private void setUpWebView() {
        binding.setData(url);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        back();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public void back() {
        finish();
    }
}
package com.apps.dbrah_delivery.uis.activity_order_details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.apps.dbrah_delivery.R;
import com.apps.dbrah_delivery.adapter.ProductAdapter;
import com.apps.dbrah_delivery.databinding.ActivityOrderDetailsBinding;
import com.apps.dbrah_delivery.uis.activity_base.BaseActivity;

public class OrderDetailsActivity extends BaseActivity {
    private ActivityOrderDetailsBinding binding;
    private ProductAdapter productAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_order_details);
        initView();
    }

    private void initView() {
        setUpToolbar(binding.toolbar, getString(R.string.delivery_details), R.color.colorPrimary, R.color.white);
        binding.toolbar.llBack.setOnClickListener(view -> finish());
        binding.setLang(getLang());
        productAdapter=new ProductAdapter(this,getLang());
        binding.recViewProducts.setLayoutManager(new LinearLayoutManager(this));
        binding.recViewProducts.setAdapter(productAdapter);
    }
}
package com.apps.dbrah_delivery.uis.activity_order_details;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;

import com.apps.dbrah_delivery.R;
import com.apps.dbrah_delivery.adapter.ProductAdapter;
import com.apps.dbrah_delivery.databinding.ActivityOrderDetailsBinding;
import com.apps.dbrah_delivery.model.OrderModel;
import com.apps.dbrah_delivery.mvvm.ActivityOrderDetailsMvvm;
import com.apps.dbrah_delivery.uis.activity_base.BaseActivity;

public class OrderDetailsActivity extends BaseActivity {
    private ActivityOrderDetailsBinding binding;
    private ProductAdapter productAdapter;
    private ActivityOrderDetailsMvvm activityOrderDetailsMvvm;
    private String order_id;
    private OrderModel orderModel;
    private ActivityResultLauncher<Intent> launcher;
    private boolean isDatachanged;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_order_details);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        order_id = intent.getStringExtra("order_id");
    }
    private void initView() {
        setUpToolbar(binding.toolbar, getString(R.string.delivery_details), R.color.colorPrimary, R.color.white);
        binding.toolbar.llBack.setOnClickListener(view -> finish());
        binding.setLang(getLang());
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                setResult(RESULT_OK);
                finish();
            }
        });
        activityOrderDetailsMvvm = ViewModelProviders.of(this).get(ActivityOrderDetailsMvvm.class);
        activityOrderDetailsMvvm.getIsOrderDataLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.llData.setVisibility(View.GONE);
                    binding.progBar.setVisibility(View.VISIBLE);
                } else {
                    binding.progBar.setVisibility(View.GONE);
                    binding.llData.setVisibility(View.VISIBLE);
                }
            }
        });
        activityOrderDetailsMvvm.getOnOrderStatusSuccess().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                    isDatachanged = true;
                    activityOrderDetailsMvvm.getOrderDetails(order_id, getUserModel().getData().getId());

            }
        });
        activityOrderDetailsMvvm.getOnOrderDetailsSuccess().observe(this, new Observer<OrderModel>() {
            @Override
            public void onChanged(OrderModel orderModel) {
                if (orderModel != null) {
                    OrderDetailsActivity.this.orderModel = orderModel;
                    binding.setModel(orderModel);
                    productAdapter.updateList(orderModel.getOffer().getOffer_details());

                }
            }
        });
        activityOrderDetailsMvvm.getOrderDetails(order_id, getUserModel().getData().getId());
        productAdapter = new ProductAdapter(this, getLang());
        binding.recViewProducts.setLayoutManager(new LinearLayoutManager(this));
        binding.recViewProducts.setAdapter(productAdapter);

    }
    @Override
    public void onBackPressed() {
        if (isDatachanged) {
            Intent intent = getIntent();
            setResult(RESULT_OK, intent);
        }
        finish();

    }
}
package com.apps.dbrah_delivery.uis.activity_order_details;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.apps.dbrah_delivery.R;
import com.apps.dbrah_delivery.adapter.ProductAdapter;
import com.apps.dbrah_delivery.databinding.ActivityOrderDetailsBinding;
import com.apps.dbrah_delivery.model.ChatUserModel;
import com.apps.dbrah_delivery.model.OrderModel;
import com.apps.dbrah_delivery.mvvm.ActivityOrderDetailsMvvm;
import com.apps.dbrah_delivery.uis.activity_base.BaseActivity;
import com.apps.dbrah_delivery.uis.activity_chat.ChatActivity;

import java.util.Locale;

public class OrderDetailsActivity extends BaseActivity {
    private ActivityOrderDetailsBinding binding;
    private ProductAdapter productAdapter;
    private ActivityOrderDetailsMvvm activityOrderDetailsMvvm;
    private String order_id;
    private OrderModel orderModel;
    private ActivityResultLauncher<Intent> launcher;
    private boolean isDatachanged;
    private Intent intent;
    private static final int REQUEST_PHONE_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_details);
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
        activityOrderDetailsMvvm.getOnOrderStatusSuccess().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    isDatachanged = true;
                    orderModel.setStatus(s);
                    if (s.equals("rejected")) {
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        binding.setModel(orderModel);
                        updateui(orderModel.getStatus());
                    }
                }
            }
        });
        activityOrderDetailsMvvm.getOnOrderDetailsSuccess().observe(this, new Observer<OrderModel>() {
            @Override
            public void onChanged(OrderModel orderModel) {
                if (orderModel != null) {
                    OrderDetailsActivity.this.orderModel = orderModel;
                    binding.setModel(orderModel);
                    productAdapter.updateList(orderModel.getOffer().getOffer_details());
                    updateui(orderModel.getStatus());
                }
            }
        });
        activityOrderDetailsMvvm.getOrderDetails(order_id, getUserModel().getData().getId());
        productAdapter = new ProductAdapter(this, getLang());
        binding.recViewProducts.setLayoutManager(new LinearLayoutManager(this));
        binding.recViewProducts.setAdapter(productAdapter);
        binding.llAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityOrderDetailsMvvm.cheangeOrderStatus(order_id, getUserModel().getData().getId(), "accepted", OrderDetailsActivity.this);
            }
        });
        binding.llReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityOrderDetailsMvvm.cheangeOrderStatus(order_id, getUserModel().getData().getId(), "rejected", OrderDetailsActivity.this);
            }
        });
        binding.flStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = null;
                if (orderModel.getStatus().equals("accepted")) {
                    status = "picked_up";
                } else if (orderModel.getStatus().equals("picked_up")) {
                    status = "on_the_way";
                } else if (orderModel.getStatus().equals("on_the_way")) {
                    status = "ended";
                }
                activityOrderDetailsMvvm.cheangeOrderStatus(order_id, getUserModel().getData().getId(), status, OrderDetailsActivity.this);
            }
        });
        binding.imCallUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderModel.getUser() != null) {
                    intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", orderModel.getUser().getPhone_code() + orderModel.getUser().getPhone(), null));
                }
                if (intent != null) {
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(OrderDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(OrderDetailsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                        } else {
                            startActivity(intent);
                        }
                    } else {
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(OrderDetailsActivity.this, getResources().getString(R.string.not_avail_now), Toast.LENGTH_SHORT).show();

                    // Common.CreateAlertDialog(QuestionsActivity.this, getResources().getString(R.string.phone_not_found));
                }

            }
        });
        binding.imCallProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderModel.getProvider() != null) {
                    intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", orderModel.getProvider().getPhone_code() + orderModel.getProvider().getPhone(), null));
                }
                if (intent != null) {
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(OrderDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(OrderDetailsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                        } else {
                            startActivity(intent);
                        }
                    } else {
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(OrderDetailsActivity.this, getResources().getString(R.string.not_avail_now), Toast.LENGTH_SHORT).show();

                    // Common.CreateAlertDialog(QuestionsActivity.this, getResources().getString(R.string.phone_not_found));
                }

            }
        });
        binding.llMapUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", Double.parseDouble(orderModel.getAddress().getLatitude()), Double.parseDouble(orderModel.getAddress().getLongitude()));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });
        binding.llMapProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", Double.parseDouble(orderModel.getProvider().getLatitude()), Double.parseDouble(orderModel.getProvider().getLongitude()));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });
        binding.imChatProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderDetailsActivity.this, ChatActivity.class);
                ChatUserModel model = new ChatUserModel(orderModel.getProvider_id(), "",getUserModel().getData().getId(),orderModel.getProvider().getName(),orderModel.getProvider().getPhone(),orderModel.getProvider().getImage() , order_id);
                intent.putExtra("data", model);

                startActivity(intent);
            }
        });
        binding.imChatUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderDetailsActivity.this, ChatActivity.class);
                ChatUserModel model = new ChatUserModel("", orderModel.getUser_id(),getUserModel().getData().getId(),orderModel.getUser().getName(),orderModel.getUser().getPhone(),orderModel.getUser().getImage() , order_id);
                intent.putExtra("data", model);
                startActivity(intent);
            }
        });
    }

    private void updateui(String status) {
        if (status.equals("new")) {

            binding.imageStep1.setBackgroundResource(R.drawable.circle_accent);
            binding.imageStep1.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary));
        } else if (status.equals("accepted")) {

            binding.imageStep2.setBackgroundResource(R.drawable.circle_accent);
            binding.imageStep2.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary));
            binding.viewStep1.setBackgroundResource(R.color.colorPrimary);


        } else {

            binding.imageStep2.setBackgroundResource(R.drawable.circle_accent);
            binding.imageStep2.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary));
            binding.viewStep2.setBackgroundResource(R.color.colorPrimary);
            binding.imageStep3.setBackgroundResource(R.drawable.circle_accent);
            binding.imageStep3.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary));
            binding.viewStep1.setBackgroundResource(R.color.colorPrimary);

        }
    }

    @Override
    public void onBackPressed() {
        if (isDatachanged) {
            Intent intent = getIntent();
            setResult(RESULT_OK, intent);
        }
        finish();

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PHONE_CALL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    Activity#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for Activity#requestPermissions for more details.
                            return;
                        }
                    }
                    startActivity(intent);
                } else {

                }
                return;
            }
        }
    }
}
package com.app.dbrah_delivery.uis.activity_previous_order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.dbrah_delivery.R;
import com.app.dbrah_delivery.adapter.NotificationAdapter;
import com.app.dbrah_delivery.adapter.OrderAdapter;
import com.app.dbrah_delivery.databinding.ActivityNotificationBinding;
import com.app.dbrah_delivery.databinding.ActivityPreviousOrderBinding;
import com.app.dbrah_delivery.model.OrdersModel;
import com.app.dbrah_delivery.mvvm.ActivityNotificationMvvm;
import com.app.dbrah_delivery.mvvm.ActivityPreviousOrderMvvm;
import com.app.dbrah_delivery.mvvm.FragmentNewOrderMvvm;
import com.app.dbrah_delivery.uis.activity_base.BaseActivity;
import com.app.dbrah_delivery.uis.activity_order_details.OrderDetailsActivity;

import java.util.ArrayList;

public class PreviousOrderActivity extends BaseActivity {

    private ActivityPreviousOrderBinding binding;
    private OrderAdapter orderAdapter;
    private ActivityPreviousOrderMvvm mvvm;
    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_previous_order);
        initView();


    }


    private void initView() {
          launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {

                mvvm.getPreviousOrders(getUserModel(),mvvm.getFilterBy().getValue());

            }
        });
        setUpToolbar(binding.toolbar, getString(R.string.last_orders), R.color.colorPrimary, R.color.white);
        binding.toolbar.llBack.setOnClickListener(view -> finish());
        binding.setLang(getLang());
        mvvm = ViewModelProviders.of(this).get(ActivityPreviousOrderMvvm.class);
        mvvm.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                binding.swipeRefresh.setRefreshing(aBoolean);

            }
        });
        mvvm.getOnOrderDataSuccess().observe(this, dataList -> {
            if (orderAdapter != null && dataList != null && dataList.size() > 0) {
                orderAdapter.updateList(dataList);
            } else {
                orderAdapter.updateList(new ArrayList<>());
                binding.llNoData.setVisibility(View.VISIBLE);

            }
        });

        mvvm.setFilterBy("all");
        orderAdapter = new OrderAdapter(this, getLang(), null);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        binding.recView.setAdapter(orderAdapter);

        mvvm.getPreviousOrders(getUserModel(), mvvm.getFilterBy().getValue());
        binding.swipeRefresh.setOnRefreshListener(() -> mvvm.getPreviousOrders(getUserModel(), mvvm.getFilterBy().getValue()));

        binding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary);

        binding.flFilter.setOnClickListener(v -> {
            if (binding.flFilterDialog.getVisibility() == View.VISIBLE) {
                binding.flFilterDialog.setVisibility(View.GONE);

            } else {
                binding.flFilterDialog.setVisibility(View.VISIBLE);

            }
        });

        binding.rbWeek.setOnClickListener(v -> {
            mvvm.setFilterBy("week");
            binding.flFilterDialog.setVisibility(View.GONE);
            binding.tvFilter.setText(R.string.last_week);
            mvvm.getPreviousOrders(getUserModel(), mvvm.getFilterBy().getValue());

        });
        binding.rbMonth.setOnClickListener(v -> {
            mvvm.setFilterBy("month");
            binding.tvFilter.setText(R.string.last_month);
            binding.flFilterDialog.setVisibility(View.GONE);
            mvvm.getPreviousOrders(getUserModel(), mvvm.getFilterBy().getValue());

        });
        binding.rbYear.setOnClickListener(v -> {
            mvvm.setFilterBy("year");
            binding.tvFilter.setText(R.string.last_year);
            binding.flFilterDialog.setVisibility(View.GONE);
            mvvm.getPreviousOrders(getUserModel(), mvvm.getFilterBy().getValue());

        });
        binding.rbAll.setOnClickListener(v -> {
            mvvm.setFilterBy("all");
            binding.tvFilter.setText(R.string.all);
            binding.flFilterDialog.setVisibility(View.GONE);
            mvvm.getPreviousOrders(getUserModel(), mvvm.getFilterBy().getValue());

        });

        binding.cons.setOnTouchListener((v, event) -> {
            binding.flFilterDialog.setVisibility(View.GONE);
            return false;
        });

        binding.swipeRefresh.setOnTouchListener((v, event) -> {
            binding.flFilterDialog.setVisibility(View.GONE);
            return false;
        });

        binding.recView.setOnTouchListener((v, event) -> {
            binding.flFilterDialog.setVisibility(View.GONE);
            return false;
        });
        mvvm.getPreviousOrders(getUserModel(), mvvm.getFilterBy().getValue());

    }
    public void navigateToDetails(OrdersModel.Data data) {
        Intent intent = new Intent(this, OrderDetailsActivity.class);
        intent.putExtra("order_id", data.getOrder_id());
        launcher.launch(intent);
    }
}
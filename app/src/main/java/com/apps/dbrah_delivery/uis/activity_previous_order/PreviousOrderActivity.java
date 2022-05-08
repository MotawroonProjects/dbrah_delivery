package com.apps.dbrah_delivery.uis.activity_previous_order;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.apps.dbrah_delivery.R;
import com.apps.dbrah_delivery.adapter.NotificationAdapter;
import com.apps.dbrah_delivery.adapter.OrderAdapter;
import com.apps.dbrah_delivery.databinding.ActivityNotificationBinding;
import com.apps.dbrah_delivery.databinding.ActivityPreviousOrderBinding;
import com.apps.dbrah_delivery.mvvm.ActivityNotificationMvvm;
import com.apps.dbrah_delivery.uis.activity_base.BaseActivity;

public class PreviousOrderActivity extends BaseActivity {

    private ActivityPreviousOrderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_previous_order);
        initView();


    }


    private void initView() {
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        binding.recView.setAdapter(new OrderAdapter(this,getLang()));
        binding.flFilter.setOnClickListener(v -> {
            if (binding.flFilterDialog.getVisibility() == View.VISIBLE) {
                binding.flFilterDialog.setVisibility(View.GONE);

            } else {
                binding.flFilterDialog.setVisibility(View.VISIBLE);

            }
        });

        binding.rbWeek.setOnClickListener(v -> {
//            fragmentPreviousOrderMvvm.setFilterBy("week");
            binding.flFilterDialog.setVisibility(View.GONE);
            binding.tvFilter.setText(R.string.last_week);
//            fragmentPreviousOrderMvvm.getOrder(getUserModel().getData().getAccess_token(), fragmentPreviousOrderMvvm.getFilterBy().getValue());

        });
        binding.rbMonth.setOnClickListener(v -> {
//            fragmentPreviousOrderMvvm.setFilterBy("month");
            binding.tvFilter.setText(R.string.last_month);
            binding.flFilterDialog.setVisibility(View.GONE);
//            fragmentPreviousOrderMvvm.getOrder(getUserModel().getData().getAccess_token(), fragmentPreviousOrderMvvm.getFilterBy().getValue());

        });
        binding.rbYear.setOnClickListener(v -> {
//            fragmentPreviousOrderMvvm.setFilterBy("year");
            binding.tvFilter.setText(R.string.last_year);
            binding.flFilterDialog.setVisibility(View.GONE);
//            fragmentPreviousOrderMvvm.getOrder(getUserModel().getData().getAccess_token(), fragmentPreviousOrderMvvm.getFilterBy().getValue());

        });
        binding.rbAll.setOnClickListener(v -> {
//            fragmentPreviousOrderMvvm.setFilterBy(null);
            binding.tvFilter.setText(R.string.all);
            binding.flFilterDialog.setVisibility(View.GONE);

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

    }

}
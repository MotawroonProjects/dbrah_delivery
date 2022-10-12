package com.app.dbrah_delivery.uis.activity_home.fragments_home_navigaion.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.dbrah_delivery.R;

import com.app.dbrah_delivery.adapter.OrderAdapter;
import com.app.dbrah_delivery.databinding.FragmentOrderBinding;
import com.app.dbrah_delivery.model.OrderModel;
import com.app.dbrah_delivery.model.OrdersModel;
import com.app.dbrah_delivery.mvvm.FragmentNewOrderMvvm;
import com.app.dbrah_delivery.mvvm.FragmentNewOrderMvvm;
import com.app.dbrah_delivery.mvvm.GeneralMvvm;
import com.app.dbrah_delivery.uis.activity_base.BaseFragment;
import com.app.dbrah_delivery.uis.activity_home.HomeActivity;
import com.app.dbrah_delivery.uis.activity_order_details.OrderDetailsActivity;

import java.util.ArrayList;
import java.util.List;


public class FragmentNewOrders extends BaseFragment {
    private FragmentOrderBinding binding;
    private HomeActivity activity;
    private OrderAdapter orderAdapter;
    private FragmentNewOrderMvvm mvvm;
    private GeneralMvvm generalMvvm;
    private ActivityResultLauncher<Intent> launcher;

    public static FragmentNewOrders newInstance() {
        FragmentNewOrders fragment = new FragmentNewOrders();
        Bundle args = new Bundle();
//        args.putSerializable("data", model);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {

                mvvm.getOrders(getUserModel());

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        binding.setTitleno(getResources().getString(R.string.no_new_order));

        mvvm = ViewModelProviders.of(this).get(FragmentNewOrderMvvm.class);
        generalMvvm = ViewModelProviders.of(activity).get(GeneralMvvm.class);
        generalMvvm.getOnCurrentOrderRefreshed().observe(activity, isRefreshed -> {
            if (isRefreshed) {
                mvvm.getOrders(getUserModel());
            }
        });
        mvvm.getIsLoading().observe(activity, aBoolean -> {
            if (aBoolean) {
                binding.llNoData.setVisibility(View.GONE);
            }
            binding.swipeRefresh.setRefreshing(aBoolean);
        });
        mvvm.getOnDataSuccess().observe(activity, new Observer<List<OrdersModel.Data>>() {
            @Override
            public void onChanged(List<OrdersModel.Data> data) {
                orderAdapter.updateList(new ArrayList<>());
                if (data != null && data.size() > 0) {
                    orderAdapter.updateList(data);
                    binding.llNoData.setVisibility(View.GONE);
                } else {
                    binding.llNoData.setVisibility(View.VISIBLE);
                }
            }
        });

        orderAdapter = new OrderAdapter(activity, getLang(), this);
        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recView.setAdapter(orderAdapter);

        mvvm.getOrders(getUserModel());
        binding.swipeRefresh.setOnRefreshListener(() -> mvvm.getOrders(getUserModel()));
        binding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary);

    }

    public void navigateToDetails(OrdersModel.Data orderModel) {
        Log.e("dkdkkd", orderModel.getOrder_id() + "");
        Intent intent = new Intent(activity, OrderDetailsActivity.class);
        intent.putExtra("order_id", orderModel.getOrder_id());
        launcher.launch(intent);
    }

}
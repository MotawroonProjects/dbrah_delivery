package com.apps.dbrah_delivery.uis.activity_home.fragments_home_navigaion.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.apps.dbrah_delivery.R;
import com.apps.dbrah_delivery.adapter.OrderAdapter;
import com.apps.dbrah_delivery.databinding.FragmentOrderBinding;
import com.apps.dbrah_delivery.model.OrderModel;
import com.apps.dbrah_delivery.mvvm.FragmentCurrentOrderMvvm;
import com.apps.dbrah_delivery.uis.activity_base.BaseFragment;
import com.apps.dbrah_delivery.uis.activity_home.HomeActivity;

import java.util.ArrayList;
import java.util.List;


public class FragmentCurrentOrders extends BaseFragment {
    private FragmentOrderBinding binding;
    private HomeActivity activity;
    private FragmentCurrentOrderMvvm mvvm;
    private OrderAdapter orderAdapter;
    private List<OrderModel> orderModelList;

    public static FragmentCurrentOrders newInstance() {
        FragmentCurrentOrders fragment = new FragmentCurrentOrders();
        Bundle args = new Bundle();
//        args.putSerializable("data", model);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
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
        Log.e("nothing", "aknscljkan");
        binding.setTitleno(getResources().getString(R.string.no_current_order));

        orderModelList = new ArrayList<>();
        mvvm = ViewModelProviders.of(this).get(FragmentCurrentOrderMvvm.class);
        mvvm.getIsLoading().observe(activity, aBoolean -> {
           if (aBoolean) {
                binding.llNoData.setVisibility(View.GONE);
            }
            binding.swipeRefresh.setRefreshing(aBoolean);
        });
        mvvm.getOnDataSuccess().observe(activity, orderModels -> {
            orderAdapter.updateList(new ArrayList<>());
            if (orderModels!=null && orderModels.size()>0){
                orderAdapter.updateList(orderModels);
                binding.llNoData.setVisibility(View.GONE);
            }else {
                binding.llNoData.setVisibility(View.VISIBLE);
            }
        });

        orderAdapter = new OrderAdapter( activity, getLang());
        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recView.setAdapter(orderAdapter);

        mvvm.getOrders(getUserModel());
        binding.swipeRefresh.setOnRefreshListener(() -> mvvm.getOrders(getUserModel()));
        binding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary);


    }

}
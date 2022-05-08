package com.apps.dbrah_delivery.uis.activity_home.fragments_home_navigaion.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.apps.dbrah_delivery.uis.activity_base.BaseFragment;
import com.apps.dbrah_delivery.uis.activity_home.HomeActivity;

import java.util.ArrayList;
import java.util.List;


public class FragmentNewOrders extends BaseFragment {
    private FragmentOrderBinding binding;
    private HomeActivity activity;
    private List<Object> orderModelList;
//    private OrderAdapter orderAdapter;
//    private List<OrderModel> orderModelList;

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
        orderModelList = new ArrayList<>();
        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recView.setAdapter(new OrderAdapter(activity,getLang()));
//        mvvm = ViewModelProviders.of(this).get(FragmentOrdersMvvm.class);
//
//        mvvm.getIsLoading().observe(activity, aBoolean -> {
//            if (aBoolean){
//                binding.tvNoSearchData.setVisibility(View.GONE);
//            }
//            binding.swipeRefresh.setRefreshing(aBoolean);
//        });
//        mvvm.getMutableLiveData().observe(activity, orderModels -> {
//            orderAdapter.updateList(new ArrayList<>());
//        if (orderModels!=null && orderModels.size()>0){
//            orderAdapter.updateList(orderModels);
//            binding.tvNoSearchData.setVisibility(View.GONE);
//        }else {
//            binding.tvNoSearchData.setVisibility(View.VISIBLE);
//        }
//        });
//
//        orderAdapter = new OrderAdapter(orderModelList, activity, this,getLang());
//        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
//        binding.recView.setAdapter(orderAdapter);
//
//        mvvm.getPreviousOrder(getUserModel(),getLang());
//        binding.swipeRefresh.setOnRefreshListener(() -> mvvm.getPreviousOrder(getUserModel(),getLang()));
//        binding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary);

    }


}
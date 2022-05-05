package com.apps.dbrah_delivery.uis.activity_home.fragments_home_navigaion;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.dbrah_delivery.R;

import com.apps.dbrah_delivery.adapter.MyPagerAdapter;
import com.apps.dbrah_delivery.mvvm.FragmentHomeMvvm;
import com.apps.dbrah_delivery.uis.activity_base.BaseFragment;
import com.apps.dbrah_delivery.databinding.FragmentHomeBinding;
import com.apps.dbrah_delivery.uis.activity_home.HomeActivity;
import com.apps.dbrah_delivery.uis.activity_home.fragments_home_navigaion.fragments.FragmentCurrentOrders;
import com.apps.dbrah_delivery.uis.activity_home.fragments_home_navigaion.fragments.FragmentNewOrders;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class FragmentHome extends BaseFragment {
    private static final String TAG = FragmentHome.class.getName();
    private HomeActivity activity;
    private FragmentHomeBinding binding;
    private FragmentHomeMvvm fragmentHomeMvvm;
    private List<Fragment> fragmentList;
    private List<String> titles;
    private MyPagerAdapter pagerAdapter;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Observable.timer(130, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {
                        initView();

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void initView() {

        fragmentHomeMvvm = ViewModelProviders.of(this).get(FragmentHomeMvvm.class);
        titles = new ArrayList<>();
        fragmentList = new ArrayList<>();
        titles.add(getString(R.string.new_order));
        titles.add(getString(R.string.current));
        binding.tab.setupWithViewPager(binding.pager);
        fragmentList.add(FragmentNewOrders.newInstance());
        fragmentList.add(FragmentCurrentOrders.newInstance());
        pagerAdapter =new MyPagerAdapter(getChildFragmentManager(), PagerAdapter.POSITION_UNCHANGED,fragmentList,titles);
        binding.pager.setAdapter(pagerAdapter);
        binding.pager.setOffscreenPageLimit(fragmentList.size());
        for (int i = 0; i < binding.tab.getChildCount(); i++) {
            View view = ((ViewGroup) binding.tab.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            params.setMargins(10, 0, 10, 0);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposable.clear();
    }


}
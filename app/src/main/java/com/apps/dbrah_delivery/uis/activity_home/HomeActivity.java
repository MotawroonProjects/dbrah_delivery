package com.apps.dbrah_delivery.uis.activity_home;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import com.apps.dbrah_delivery.interfaces.Listeners;
import com.apps.dbrah_delivery.model.UserModel;
import com.apps.dbrah_delivery.mvvm.HomeActivityMvvm;
import com.apps.dbrah_delivery.uis.activity_base.BaseActivity;

import com.apps.dbrah_delivery.R;

import com.apps.dbrah_delivery.databinding.ActivityHomeBinding;
import com.apps.dbrah_delivery.language.Language;
import com.apps.dbrah_delivery.uis.activity_login.LoginActivity;
import com.apps.dbrah_delivery.uis.activity_notification.NotificationActivity;
import com.apps.dbrah_delivery.uis.activity_previous_order.PreviousOrderActivity;

import io.paperdb.Paper;

public class HomeActivity extends BaseActivity implements Listeners.VerificationListener {
    private ActivityHomeBinding binding;
    private NavController navController;
    private HomeActivityMvvm homeActivityMvvm;
    private ActionBarDrawerToggle toggle;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        initView();


    }


    private void initView() {
        binding.setGreeting(getResources().getString(R.string.welcome));
        homeActivityMvvm = ViewModelProviders.of(this).get(HomeActivityMvvm.class);
        if (getLang().equals("ar")) {
            binding.toolBar.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        } else {
            binding.toolBar.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        }
        navController = Navigation.findNavController(this, R.id.navHostFragment);

        appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()) //Pass the ids of fragments from nav_graph which you dont want to show back button in toolbar
                        .setDrawerLayout(binding.drawerLayout)
                        .build();

        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout);
        NavigationUI.setupWithNavController(binding.navigationView, navController);
        NavigationUI.setupWithNavController(binding.toolBar, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolBar, R.string.open, R.string.close);
        // toggle.setDrawerIndicatorEnabled(true);

        toggle.setHomeAsUpIndicator(R.drawable.ic_menu);

        toggle.syncState();

        binding.toolBar.setNavigationIcon(R.drawable.ic_menu);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (binding.toolBar.getNavigationIcon() != null) {

                binding.toolBar.setNavigationIcon(R.drawable.ic_menu);


            }
        });


        homeActivityMvvm.firebase.observe(this, token -> {
            if (getUserModel() != null) {
                UserModel userModel = getUserModel();
                userModel.getData().setFirebase_token(token);
                setUserModel(userModel);
            }
        });

        binding.llPreviousOrder.setOnClickListener(v -> {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(this, PreviousOrderActivity.class);
            startActivity(intent);

        });
        if (getUserModel() != null) {
            homeActivityMvvm.updateFirebase(this, getUserModel());
        }
    }


    public void refreshActivity(String lang) {
        Paper.book().write("lang", lang);
        Language.setNewLocale(this, lang);
        new Handler()
                .postDelayed(() -> {

                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }, 500);


    }


    @Override
    public void onBackPressed() {
        int currentFragmentId = navController.getCurrentDestination().getId();
        if (currentFragmentId == R.id.home) {
            finish();

        } else {
            navController.popBackStack();
        }

    }

    @Override
    public void onVerificationSuccess() {

    }


    public void updateFirebase() {
        if (getUserModel() != null) {
            homeActivityMvvm.updateFirebase(this, getUserModel());
        }
    }


}

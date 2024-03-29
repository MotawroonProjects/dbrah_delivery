package com.app.dbrah_delivery.uis.activity_home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import com.app.dbrah_delivery.interfaces.Listeners;
import com.app.dbrah_delivery.model.NotiFire;
import com.app.dbrah_delivery.model.SettingDataModel;
import com.app.dbrah_delivery.model.UserModel;
import com.app.dbrah_delivery.mvvm.GeneralMvvm;
import com.app.dbrah_delivery.mvvm.HomeActivityMvvm;
import com.app.dbrah_delivery.tags.Tags;
import com.app.dbrah_delivery.uis.activity_app.AppActivity;
import com.app.dbrah_delivery.uis.activity_base.BaseActivity;

import com.app.dbrah_delivery.R;

import com.app.dbrah_delivery.databinding.ActivityHomeBinding;
import com.app.dbrah_delivery.language.Language;
import com.app.dbrah_delivery.uis.activity_contact_us.ContactUsActivity;
import com.app.dbrah_delivery.uis.activity_edit_profile.EditProfileActivity;
import com.app.dbrah_delivery.uis.activity_login.LoginActivity;
import com.app.dbrah_delivery.uis.activity_notification.NotificationActivity;
import com.app.dbrah_delivery.uis.activity_previous_order.PreviousOrderActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.paperdb.Paper;

public class HomeActivity extends BaseActivity implements Listeners.VerificationListener {
    private ActivityHomeBinding binding;
    private NavController navController;
    private HomeActivityMvvm homeActivityMvvm;
    private ActionBarDrawerToggle toggle;
    private AppBarConfiguration appBarConfiguration;
    private ActivityResultLauncher<Intent> launcher;
    private int req = 1;
    private GeneralMvvm generalMvvm;
    private SettingDataModel.Data setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        initView();


    }


    private void initView() {
        binding.setGreeting(getResources().getString(R.string.welcome));
        binding.setLang(getLang());
        binding.setModel(getUserModel());
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (req == 1 && result.getResultCode() == Activity.RESULT_OK) {
                binding.setModel(getUserModel());
            }
        });
        homeActivityMvvm = ViewModelProviders.of(this).get(HomeActivityMvvm.class);
        generalMvvm = ViewModelProviders.of(this).get(GeneralMvvm.class);
        homeActivityMvvm.getUserData().observe(this, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModel) {
                if (userModel != null) {
                    setUserModel(userModel);
                    binding.setModel(getUserModel());
                    // navigateToHomeActivity();
                }
            }
        });
        binding.swStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeActivityMvvm.changeStatus(HomeActivity.this, getUserModel().getData().getId());
            }
        });
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

        homeActivityMvvm.getOnDataSuccess().observe(this, model -> {
            setting = model;
        });
        homeActivityMvvm.getSettings(this);
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
        binding.llEditProfile.setOnClickListener(v -> {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(this, EditProfileActivity.class);
            launcher.launch(intent);

        });
        binding.llContactUs.setOnClickListener(view -> {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(this, ContactUsActivity.class);
            launcher.launch(intent);
        });

        binding.llTerms.setOnClickListener(view -> navigateToAppActivity("terms", setting.getTerms_en()));

        binding.llPrivacy.setOnClickListener(view -> navigateToAppActivity("privacy", setting.getPrivacy_en()));

        binding.tvLang.setOnClickListener(view -> {
            if (getLang().equals("en")) {
                refreshActivity("ar");
            } else {
                refreshActivity("en");
            }
        });
        if (getUserModel() != null) {
            homeActivityMvvm.updateFirebase(this, getUserModel());
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
        }
        binding.llLogout.setOnClickListener(view -> {
            if (getUserModel() == null) {
                logout();
            } else {
                homeActivityMvvm.logout(this, getUserModel());

            }
        });

        homeActivityMvvm.logout.observe(this, aBoolean -> {
            if (aBoolean) {
                logout();
            }
        });
    }

    private void logout() {
        clearUserModel(this);
        binding.setModel(null);
        setUserModel(null);
        navigateToLoginActivity();
    }

    private void navigateToLoginActivity() {

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();


    }

    private void navigateToAppActivity(String type, String url) {
        Intent intent = new Intent(this, AppActivity.class);
        intent.putExtra("data", type);
        intent.putExtra("url", url);
        startActivity(intent);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOrderStatusChanged(NotiFire model) {
        if (!model.getOrder_status().isEmpty()) {
            String status = model.getOrder_status();
            generalMvvm.getOnCurrentOrderRefreshed().setValue(true);

        }
    }

}

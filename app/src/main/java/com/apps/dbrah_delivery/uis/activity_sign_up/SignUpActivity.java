package com.apps.dbrah_delivery.uis.activity_sign_up;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.apps.dbrah_delivery.R;
import com.apps.dbrah_delivery.adapter.CountryAdapter;
import com.apps.dbrah_delivery.databinding.ActivitySignUpBinding;
import com.apps.dbrah_delivery.databinding.DialogCountriesBinding;
import com.apps.dbrah_delivery.model.CountryModel;
import com.apps.dbrah_delivery.model.SignUpModel;
import com.apps.dbrah_delivery.mvvm.ActivitySignupMvvm;
import com.apps.dbrah_delivery.preferences.Preferences;
import com.apps.dbrah_delivery.share.Common;
import com.apps.dbrah_delivery.uis.activity_base.BaseActivity;
import com.apps.dbrah_delivery.uis.activity_home.HomeActivity;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SignUpActivity extends BaseActivity {
    private ActivitySignUpBinding binding;
    private SignUpModel model;
    private Preferences preferences;
    private ActivitySignupMvvm activitySignupMvvm;
    private String phone_code, phone;
    private AlertDialog dialog;
    private List<CountryModel> countryModelList = new ArrayList<>();
    private CountryAdapter countriesAdapter;
    private ActivityResultLauncher<Intent> launcher;
    private final String READ_PERM = Manifest.permission.READ_EXTERNAL_STORAGE;
    private final String write_permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private final String camera_permission = Manifest.permission.CAMERA;
    private final int READ_REQ = 1, CAMERA_REQ = 2;
    private int selectedReq = 0;
    private Uri uri = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        phone_code = intent.getStringExtra("phone_code");
        phone = intent.getStringExtra("phone");
    }

    private void initView() {
        preferences = Preferences.getInstance();
        activitySignupMvvm = ViewModelProviders.of(this).get(ActivitySignupMvvm.class);
        model = new SignUpModel();
        binding.setModel(model);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                if (selectedReq == READ_REQ) {
                    binding.icon.setVisibility(View.GONE);

                    uri = result.getData().getData();
                    File file = new File(Common.getImagePath(this, uri));
                    Picasso.get().load(file).fit().into(binding.image);
//                    model.setImage(uri.toString());
                    binding.setModel(model);

                } else if (selectedReq == CAMERA_REQ) {
                    Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                    binding.icon.setVisibility(View.GONE);
                    uri = getUriFromBitmap(bitmap);
                    if (uri != null) {
                        String path = Common.getImagePath(this, uri);

                        if (path != null) {
                            Picasso.get().load(new File(path)).fit().into(binding.image);

                        } else {
                            Picasso.get().load(uri).fit().into(binding.image);

                        }
//                        model.setImage(uri.toString());
                        binding.setModel(model);
                    }
                }
            }
        });


        activitySignupMvvm.userModelMutableLiveData.observe(this, userModel -> {
            setUserModel(userModel);
            setResult(RESULT_OK);
            finish();
        });

        activitySignupMvvm.getCoListMutableLiveData().observe(this, countryModels -> {
            if (countryModels != null && countryModels.size() > 0) {
                countryModelList.clear();
                countryModelList.addAll(countryModels);
            }
        });
        activitySignupMvvm.setCountry();
        binding.flImage.setOnClickListener(view -> openSheet());
        binding.flGallery.setOnClickListener(view -> {
            closeSheet();
            checkReadPermission();
        });

        binding.flCamera.setOnClickListener(view -> {
            closeSheet();
            checkCameraPermission();
        });

        binding.btnCancel.setOnClickListener(view -> closeSheet());
        binding.llCountry.setOnClickListener(view -> dialog.show());

        //createCountriesDialog();
        sortCountries();
        binding.imFalg.setImageDrawable(getResources().getDrawable(R.drawable.flag_sa));
        binding.txtCountry.setText("Saudi Arabia");
        binding.btnSignup.setOnClickListener(view -> {
//            if (model.isDataValid(this)) {
//                if (model.isDataValid(this)) {
//
//                }
//            }
            navigateTOHomeActivity();
        });

    }

    private void navigateTOHomeActivity() {
        Intent intent=new Intent(SignUpActivity.this, HomeActivity.class);
        startActivity(intent);
    }

//    private void createCountriesDialog() {
//
//        dialog = new AlertDialog.Builder(this)
//                .create();
//        countriesAdapter = new CountryAdapter(this);
//        countriesAdapter.updateList(countryModelList);
//        DialogCountriesBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_countries, null, false);
//        binding.recView.setLayoutManager(new LinearLayoutManager(this));
//        binding.recView.setAdapter(countriesAdapter);
//
//        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_congratulation_animation;
//        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_window_bg);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setView(binding.getRoot());
//    }
    private void sortCountries() {
        Collections.sort(countryModelList, (country1, country2) -> {
            return country1.getName().trim().compareToIgnoreCase(country2.getName().trim());
        });
    }


    public void openSheet() {
        binding.expandLayout.setExpanded(true, true);
    }

    public void closeSheet() {
        binding.expandLayout.collapse(true);

    }

    public void checkReadPermission() {
        closeSheet();
        if (ActivityCompat.checkSelfPermission(this, READ_PERM) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{READ_PERM}, READ_REQ);
        } else {
            SelectImage(READ_REQ);
        }
    }

    public void checkCameraPermission() {

        closeSheet();

        if (ContextCompat.checkSelfPermission(this, write_permission) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, camera_permission) == PackageManager.PERMISSION_GRANTED
        ) {
            SelectImage(CAMERA_REQ);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{camera_permission, write_permission}, CAMERA_REQ);
        }
    }

    private void SelectImage(int req) {
        selectedReq = req;
        Intent intent = new Intent();

        if (req == READ_REQ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            } else {
                intent.setAction(Intent.ACTION_GET_CONTENT);

            }

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("image/*");

            launcher.launch(intent);

        } else if (req == CAMERA_REQ) {
            try {
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                launcher.launch(intent);
            } catch (SecurityException e) {
                Toast.makeText(this, R.string.perm_image_denied, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, R.string.perm_image_denied, Toast.LENGTH_SHORT).show();

            }


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_REQ) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                SelectImage(requestCode);
            } else {
                Toast.makeText(this, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == CAMERA_REQ) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                SelectImage(requestCode);
            } else {
                Toast.makeText(this, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Uri getUriFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        return Uri.parse(MediaStore.Images.Media.insertImage(this.getContentResolver(), bitmap, "", ""));
    }

    public void setItemData(CountryModel countryModel) {
        dialog.dismiss();
//        model.setPhone_code(countryModel.getDialCode());
        binding.setModel(model);
        binding.imFalg.setImageResource(countryModel.getFlag());
        binding.txtCountry.setText(countryModel.getName());
//        binding.phoneCode.setText(countryModel.getDialCode());
    }
}
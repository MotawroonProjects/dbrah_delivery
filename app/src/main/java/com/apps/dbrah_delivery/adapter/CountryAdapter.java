package com.apps.dbrah_delivery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.apps.dbrah_delivery.R;
import com.apps.dbrah_delivery.databinding.CountriesRowBinding;
import com.apps.dbrah_delivery.model.CountryModel;
import com.apps.dbrah_delivery.uis.activity_login.LoginActivity;
import com.apps.dbrah_delivery.uis.activity_sign_up.SignUpActivity;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CountryModel> list;
    private Context context;
    private LayoutInflater inflater;

    public CountryAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CountriesRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.countries_row, parent, false);
        return new MyHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        myHolder.itemView.setOnClickListener(view -> {
            if (context instanceof LoginActivity) {
                LoginActivity activity = (LoginActivity) context;
                activity.setItemData(list.get(holder.getLayoutPosition()));
            }else if (context instanceof SignUpActivity){
                SignUpActivity signUpActivity=(SignUpActivity) context;
                signUpActivity.setItemData(list.get(holder.getLayoutPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private CountriesRowBinding binding;

        public MyHolder(CountriesRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }

    public void updateList(List<CountryModel> list) {
        if (list != null) {
            this.list = list;
        }
        notifyDataSetChanged();
    }

}

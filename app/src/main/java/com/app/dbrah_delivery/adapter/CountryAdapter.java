package com.app.dbrah_delivery.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.app.dbrah_delivery.R;
import com.app.dbrah_delivery.databinding.CountriesRowBinding;
import com.app.dbrah_delivery.model.CountryModel;
import com.app.dbrah_delivery.uis.activity_login.LoginActivity;
import com.app.dbrah_delivery.uis.activity_sign_up.SignUpActivity;

import java.util.List;

public class CountryAdapter extends BaseAdapter {
    private List<CountryModel> list;
    private Context context;
    private LayoutInflater inflater;

    public CountryAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list != null ? list.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") CountriesRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.countries_row, parent, false);
        binding.setModel(list.get(position));
        return binding.getRoot();
    }

    public void updateList(List<CountryModel> list) {
        if (list != null) {
            this.list = list;

        } else {
            this.list.clear();
        }
        notifyDataSetChanged();
    }
}

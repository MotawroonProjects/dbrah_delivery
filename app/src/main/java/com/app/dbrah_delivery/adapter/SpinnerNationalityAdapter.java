package com.app.dbrah_delivery.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.BaseObservable;
import androidx.databinding.DataBindingUtil;

import com.app.dbrah_delivery.R;
import com.app.dbrah_delivery.databinding.SpinnerNationalityRowBinding;
import com.app.dbrah_delivery.model.NationalitiesModel;

import java.util.List;

public class SpinnerNationalityAdapter extends BaseAdapter {
    private List<NationalitiesModel.Data> dataList;
    private Context context;
    private String lang;

    public SpinnerNationalityAdapter(Context context,String lang) {
        this.context = context;
        this.lang=lang;

    }

    @Override
    public int getCount() {
        if (dataList == null)
            return 0;
        else
            return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder") SpinnerNationalityRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.spinner_nationality_row, viewGroup, false);
        binding.setModel(dataList.get(i));
        binding.setLang(lang);
        return binding.getRoot();
    }

    public void updateList(List<NationalitiesModel.Data> dataList){
        if (dataList!=null){
            this.dataList = dataList;
        }
        notifyDataSetChanged();
    }
}

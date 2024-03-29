package com.app.dbrah_delivery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.dbrah_delivery.R;
import com.app.dbrah_delivery.databinding.ProductRowBinding;
import com.app.dbrah_delivery.model.OrderModel;

import java.util.List;

public class ProductAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<OrderModel.Offers.OfferDetail> list;
    private Context context;
    private LayoutInflater inflater;
    private String lang;

    public ProductAdapter( Context context,String lang) {
        this.context = context;
        this.lang=lang;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductRowBinding binding= DataBindingUtil.inflate(inflater, R.layout.product_row,parent,false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder=(MyHolder) holder;
        myHolder.binding.setLang(lang);
        myHolder.binding.setModel(list.get(position));
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public ProductRowBinding binding;

        public MyHolder(ProductRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public void updateList(List<OrderModel.Offers.OfferDetail> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}

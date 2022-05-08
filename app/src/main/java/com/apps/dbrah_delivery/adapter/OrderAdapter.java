package com.apps.dbrah_delivery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.dbrah_delivery.R;
import com.apps.dbrah_delivery.databinding.NewOrderRowBinding;
import com.apps.dbrah_delivery.databinding.NotificationRowBinding;
import com.apps.dbrah_delivery.model.NotificationModel;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Object> list;
    private Context context;
    private LayoutInflater inflater;
    public OrderAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        NewOrderRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.new_order_row, parent, false);
        return new MyHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;


    }

    @Override
    public int getItemCount() {
        return 8;
        //return list!=null?list.size():0;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private NewOrderRowBinding binding;

        public MyHolder(NewOrderRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }

    public void updateList(List<Object> list){
        if (list!=null){
            this.list = list;
        }
        notifyDataSetChanged();
    }

}

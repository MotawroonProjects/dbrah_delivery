package com.apps.dbrah_delivery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.dbrah_delivery.R;
import com.apps.dbrah_delivery.databinding.OrderRowBinding;
import com.apps.dbrah_delivery.model.OrdersModel;
import com.apps.dbrah_delivery.uis.activity_home.fragments_home_navigaion.fragments.FragmentCurrentOrders;
import com.apps.dbrah_delivery.uis.activity_home.fragments_home_navigaion.fragments.FragmentNewOrders;
import com.apps.dbrah_delivery.uis.activity_previous_order.PreviousOrderActivity;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private String lang;
    private List<OrdersModel.Data> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment fragment;

    public OrderAdapter(Context context, String lang,Fragment fragment) {
        this.context = context;
        this.lang = lang;
        this.fragment=fragment;
        inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        OrderRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.order_row, parent, false);
        return new MyHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setLang(lang);
        myHolder.binding.setModel(list.get(position));
        myHolder.binding.llDetails.setOnClickListener(view -> {
            if (fragment instanceof FragmentNewOrders) {
                FragmentNewOrders fragmentNew = (FragmentNewOrders) fragment;
                fragmentNew.navigateToDetails(list.get(holder.getAdapterPosition()));
            }
        });
        myHolder.itemView.setOnClickListener(view -> {
            if (fragment instanceof FragmentCurrentOrders) {
                FragmentCurrentOrders fragmentNew = (FragmentCurrentOrders) fragment;
                fragmentNew.navigateToDetails(list.get(holder.getAdapterPosition()));
            }
            else if(context instanceof PreviousOrderActivity){
                PreviousOrderActivity activity=(PreviousOrderActivity) context;

                activity.navigateToDetails(list.get(holder.getAdapterPosition()));
            }
        });
    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private OrderRowBinding binding;

        public MyHolder(OrderRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

    }

    public void updateList(List<OrdersModel.Data> list) {
        if (list != null) {
            this.list = list;
        }
        notifyDataSetChanged();
    }

}

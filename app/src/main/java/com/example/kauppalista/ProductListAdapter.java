package com.example.kauppalista;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListHolder> {

    private Context context;
    private ArrayList<Product> products = new ArrayList<>();

    private MainActivity mainActivity;

    public ProductListAdapter(Context applicationContext, ArrayList<Product> products, MainActivity mainActivity) {
        this.context = applicationContext;
        this.products = products;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public ProductListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductListHolder(LayoutInflater.from(context).inflate(R.layout.product_view,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListHolder holder, int position) {
        holder.productName.setText(products.get(position).getProductName());
        holder.editProductName.setText(products.get(position).getProductName());

        holder.removeImage.setOnClickListener(view -> {
            int pos = holder.getAdapterPosition();
            Storage.getInstance().removeProduct(products.get(pos).getProductId());
            Storage.getInstance().saveProducts(context);
            notifyItemRemoved(pos);
        });

        holder.editImage.setOnClickListener(view -> {
            int pos = holder.getAdapterPosition();
            if(holder.editProductName.getVisibility() == View.VISIBLE){
                Product p = Storage.getInstance().getProductByIdWithoutRemove(pos);
                p.setProductName(holder.editProductName.getText().toString());
                mainActivity.checkOrder();
                holder.editProductName.setVisibility(View.GONE);
                notifyDataSetChanged();
            }else{
                holder.editProductName.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

}

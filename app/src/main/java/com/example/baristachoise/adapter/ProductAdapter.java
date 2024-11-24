package com.example.baristachoise.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baristachoise.databinding.FoodItemBinding;
import com.example.baristachoise.formMenu;
import com.example.baristachoise.model.Product;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private final ArrayList<Product> productList;
    private final Context context;

    public ProductAdapter(ArrayList<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FoodItemBinding listItem = FoodItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ProductViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        int uniqueId = View.generateViewId();
        holder.itemView.setId(uniqueId);

        holder.binding.imgCafe1.setBackgroundResource(product.getImgProduct());
        holder.binding.txtnameProduct2.setText(product.getProductName());
        holder.binding.txtpriceProduct2.setText(product.getPrice());


        holder.itemView.setOnClickListener(v -> {
            Intent intentP = new Intent(context, formMenu.class);


            intentP.putExtra("product_position", position);


            context.startActivity(intentP);
        });
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        FoodItemBinding binding;

        public ProductViewHolder(FoodItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

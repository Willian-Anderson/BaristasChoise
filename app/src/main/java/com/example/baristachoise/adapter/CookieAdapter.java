package com.example.baristachoise.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baristachoise.databinding.CookieItemBinding;
import com.example.baristachoise.formMenu;
import com.example.baristachoise.model.Cookie;

import java.util.ArrayList;

public class CookieAdapter extends RecyclerView.Adapter<CookieAdapter.CookieViewHolder> {
    private final ArrayList<Cookie> cookieList;
    private final Context context;

    public CookieAdapter(ArrayList<Cookie> cookieList, Context context) {
        this.cookieList = cookieList;
        this.context = context;
    }

    @NonNull
    @Override
    public CookieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CookieItemBinding listItem;
        listItem = CookieItemBinding.inflate(LayoutInflater.from(context),parent,false);
        return new CookieViewHolder(listItem);
    }

    @Override
    public int getItemCount() {
        return cookieList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull CookieViewHolder holder, int position) {
        Cookie cookie = cookieList.get(position);
        int uniqueID = View.generateViewId();
        holder.itemView.setId(uniqueID);

        holder.binding.imgCookie1.setBackgroundResource(cookieList.get(position).getImgCookie());
        holder.binding.txtnameCookie1.setText(cookieList.get(position).getCookieName());
        holder.binding.txtpriceCookie1.setText(cookieList.get(position).getCookiePrice());

        holder.itemView.setOnClickListener(view -> {
            Intent intentC = new Intent(context, formMenu.class);

            intentC.putExtra("cookie_position", position);

            context.startActivity(intentC);
        });


    }

    public static class CookieViewHolder extends RecyclerView.ViewHolder {

        CookieItemBinding binding;

        public CookieViewHolder(CookieItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}

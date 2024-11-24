package com.example.baristachoise.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baristachoise.R;
import com.example.baristachoise.model.ProdutoFn;

import java.util.List;

public class ProdutoFnAdapter extends RecyclerView.Adapter<ProdutoFnAdapter.ProdutoFnViewHolder> {

    private List<ProdutoFn> produtoFnList;
    private Context context;

    public ProdutoFnAdapter(List<ProdutoFn> produtoFnList, Context context) {
        this.produtoFnList = produtoFnList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProdutoFnAdapter.ProdutoFnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.produtos_fn_compra, parent, false);
        return new ProdutoFnViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoFnAdapter.ProdutoFnViewHolder holder, int position) {
        ProdutoFn produtoFn = produtoFnList.get(position);
        holder.nomeProduto.setText(produtoFn.getNome());
        holder.precoProduto.setText(String.format("R$ %.2f", produtoFn.getPreco()));
        holder.imagemProduto.setImageResource(produtoFn.getImagem());

    }

    @Override
    public int getItemCount() {
        return produtoFnList.size();
    }

    public static class ProdutoFnViewHolder extends RecyclerView.ViewHolder {

        TextView nomeProduto;
        TextView precoProduto;
        ImageView imagemProduto;

        public ProdutoFnViewHolder(@NonNull View produtoView) {
            super(produtoView);
             nomeProduto = produtoView.findViewById(R.id.txt_nome_produto_fn_compra);
             precoProduto = produtoView.findViewById(R.id.txt_preco_fn_compra);
             imagemProduto = produtoView.findViewById(R.id.img_produto_fn_compra);
        }
    }
}

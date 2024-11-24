package com.example.baristachoise.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baristachoise.R;
import com.example.baristachoise.model.ProdutoC;


import java.util.List;

public class ProdutoCAdapter extends RecyclerView.Adapter<ProdutoCAdapter.ProdutoCViewHolder> {

    private List<ProdutoC> produtoCList;
    private Context context;
    private OnProdutoCChangeListener changeListener;

    public ProdutoCAdapter(List<ProdutoC> produtoCList, Context context, OnProdutoCChangeListener changeListener) {
        this.produtoCList = produtoCList;
        this.context = context;
        this.changeListener = changeListener;
    }

    @NonNull
    @Override
    public ProdutoCViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.produto_carrinho, parent,false);
        return new ProdutoCViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoCViewHolder holder, int position) {
        ProdutoC produtoC = produtoCList.get(position);

        holder.produtoName.setText(produtoC.getNome());
        holder.precoProduto.setText(String.format("R$ %.2f", produtoC.getPreco()));
        holder.imagemProduto.setImageResource(produtoC.getImagem());

        holder.btnExcluirItem.setOnClickListener(v -> {

            produtoCList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, produtoCList.size());

            if (changeListener != null) {
                changeListener.onProdutoCChanged();
            }

        });

    }

    @Override
    public int getItemCount() {
        return produtoCList.size();
    }

    public static class ProdutoCViewHolder extends RecyclerView.ViewHolder {
        TextView produtoName;
        TextView precoProduto;
        ImageView imagemProduto;
        ImageButton btnExcluirItem;


        public ProdutoCViewHolder(@NonNull View produtoView) {
            super(produtoView);

            produtoName = produtoView.findViewById(R.id.txt_nome_produto_carrinho);
            precoProduto = produtoView.findViewById(R.id.txt_preco_produto_carrinho);
            imagemProduto = produtoView.findViewById(R.id.img_produto_carrinho);
            btnExcluirItem = produtoView.findViewById(R.id.btn_excluir_item_carrinho);

        }
    }

    public interface OnProdutoCChangeListener {
        void onProdutoCChanged();
    }
}

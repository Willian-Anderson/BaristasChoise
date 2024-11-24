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
import com.example.baristachoise.model.ProdutoE;

import java.util.List;

public class ProdutoEAdapter extends RecyclerView.Adapter<ProdutoEAdapter.ProdutoEViewHolder> {

    private List<ProdutoE> produteEList;
    private Context context;

    public ProdutoEAdapter(List<ProdutoE> produtoEList, Context context) {
        this.produteEList = produtoEList;
        this.context = context;
    }


    @NonNull
    @Override
    public ProdutoEAdapter.ProdutoEViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.produto_entrega, parent, false);
        return new ProdutoEViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoEAdapter.ProdutoEViewHolder holder, int position) {
        ProdutoE produtoE = produteEList.get(position);
        holder.produtoName.setText(produtoE.getNome());
        holder.produtoPreco.setText(String.format("R$ %.2f", produtoE.getPreco()));
        holder.produtoData.setText(produtoE.getDataAdicao());
        holder.produtoImagem.setImageResource(produtoE.getImagem());
        holder.totalPedido.setText(String.format("Total pedido: R$ %.2f", produtoE.getTotalPedido()));
    }

    @Override
    public int getItemCount() {
        return produteEList.size();
    }

    public static class ProdutoEViewHolder extends RecyclerView.ViewHolder {
        TextView produtoName;
        TextView produtoPreco;
        TextView produtoData;
        ImageView produtoImagem;
        TextView totalPedido;

        public ProdutoEViewHolder(@NonNull View produtoView) {
            super(produtoView);
            produtoName = produtoView.findViewById(R.id.txt_nome_produto_entrega);
            produtoPreco = produtoView.findViewById(R.id.txt_preco_entrega);
            produtoData = produtoView.findViewById(R.id.txt_data_produto_entrega);
            produtoImagem = produtoView.findViewById(R.id.img_produto_entrega);
            totalPedido = produtoView.findViewById(R.id.txt_total_pedido);

        }
    }
}


package com.example.baristachoise;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baristachoise.adapter.ProdutoCAdapter;
import com.example.baristachoise.model.ProdutoC;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class formCarrinho extends AppCompatActivity {

    private ImageView img_home_carrinho;
    private ImageView img_perfil_carrinho;
    private ImageView img_entrega_carrinho;

    private static List<ProdutoC> produtoCList = new ArrayList<>();
    private ProdutoCAdapter produtoCAdapter;

    private TextView precoTotal;
    private TextView quantidadeTotalC;
    private int cont = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_carrinho);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainCarrinho), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        quantidadeTotalC = findViewById(R.id.txt_contador_carrinho);
        atualizarQuantidadeTotal();


        String nomeProduto = getIntent().getStringExtra("nomeProduto");
        double precoProduto = getIntent().getDoubleExtra("precoProduto",0.0);
        int imagemProduto = getIntent().getIntExtra("imagemProduto",0);
        Button btnFinalizarCompra = findViewById(R.id.bt_finalizar_compra_carrinho);


        // Itens temporários
        RecyclerView recyclerView = findViewById(R.id.recycler_carrinho);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        produtoCAdapter = new ProdutoCAdapter(produtoCList, this, new ProdutoCAdapter.OnProdutoCChangeListener() {
            @Override
            public void onProdutoCChanged() {
                calcularPrecoTotal();
                atualizarQuantidadeTotal();
                atualizarEstadoBotaoFinalizar(btnFinalizarCompra);
            }
        });
        recyclerView.setAdapter(produtoCAdapter);


        precoTotal = findViewById(R.id.txt_preco_total_carrinho);


        if (nomeProduto != null) {
            ProdutoC produtoC = new ProdutoC(nomeProduto, precoProduto, imagemProduto);
            produtoCList.add(produtoC);
            produtoCAdapter.notifyDataSetChanged();
            atualizarQuantidadeTotal();
            atualizarEstadoBotaoFinalizar(btnFinalizarCompra);

        }


        atualizarEstadoBotaoFinalizar(btnFinalizarCompra);
        calcularPrecoTotal();



        btnFinalizarCompra.setOnClickListener(view -> {

            double precoTotalT = calcularPrecoTotal();

            Intent finalizarCompraIntent = new Intent(formCarrinho.this, formFinalizar_Compra.class);
            finalizarCompraIntent.putExtra("nomeProduto", nomeProduto);
            finalizarCompraIntent.putExtra("precoProduto", precoProduto);
            finalizarCompraIntent.putExtra("imagemProduto", imagemProduto);
            finalizarCompraIntent.putExtra("valorTotal", precoTotalT);
            finalizarCompraIntent.putExtra("produtoClist", (Serializable) produtoCList);
            startActivity(finalizarCompraIntent);

            produtoCList.clear();
            produtoCAdapter.notifyDataSetChanged();
            calcularPrecoTotal();
            atualizarQuantidadeTotal();
            atualizarEstadoBotaoFinalizar(btnFinalizarCompra);
        });


// Navegação


        IniciarMenuCarrinho();
        IniciarPerfilCarrinho();
        IniciarEntragaCarrinho();

        img_home_carrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intHome = new Intent(formCarrinho.this,formMenu.class);
                startActivity(intHome);
            }
        });

        img_perfil_carrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intPerfil = new Intent(formCarrinho.this, formUsuario.class);
                startActivity(intPerfil);
            }
        });

        img_entrega_carrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intCarrinho = new Intent(formCarrinho.this, formEntrega.class);
                startActivity(intCarrinho);
            }
        });

    }




    public void IniciarMenuCarrinho() {
        img_home_carrinho = findViewById(R.id.ic_home_carrinho);

    }

    public void IniciarPerfilCarrinho() {
        img_perfil_carrinho = findViewById(R.id.ic_person_carrinho);
    }

    public void IniciarEntragaCarrinho() {
        img_entrega_carrinho = findViewById(R.id.ic_moto_carrinho);
    }

    private double calcularPrecoTotal() {
        double precoTotalT = 0.0;

        for (ProdutoC produtoC : produtoCList) {
            precoTotalT += produtoC.getPreco();
        }


        precoTotal.setText(String.format("R$ %.2f", precoTotalT));
        return precoTotalT;
    }


    private void atualizarQuantidadeTotal() {

        if (produtoCList != null) {
            int totalItens = produtoCList.size();

            if (totalItens > cont) {
                quantidadeTotalC.setText(String.format("(%d produtos)", totalItens));
            } else {
                quantidadeTotalC.setText(String.format("(%d produto)", totalItens));
            }
        }
    }

    private void atualizarEstadoBotaoFinalizar(Button btnFinalizarCompra) {
        if (produtoCList.isEmpty()) {
            btnFinalizarCompra.setEnabled(false);
            btnFinalizarCompra.setAlpha(0.5f);
        } else {
            btnFinalizarCompra.setEnabled(true);
            btnFinalizarCompra.setAlpha(1.0f);
        }
    }

}
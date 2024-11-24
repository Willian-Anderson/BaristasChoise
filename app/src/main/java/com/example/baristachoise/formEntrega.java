package com.example.baristachoise;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baristachoise.adapter.ProdutoEAdapter;
import com.example.baristachoise.model.ProdutoC;
import com.example.baristachoise.model.ProdutoE;
import com.example.baristachoise.model.ProdutoFn;

import java.util.ArrayList;
import java.util.List;

public class formEntrega extends AppCompatActivity {

    private ImageView img_home_entrega;

    private ImageView img_usuario_entraga;

    private ImageView img_carrinho_entrega;

    private static List<ProdutoE> produtoEList = new ArrayList<>();
    private ProdutoEAdapter produtoEAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_entrega);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_entrega), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });




//Lista temporária de itens



        RecyclerView recyclerView = findViewById(R.id.recycler_Entrega);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        produtoEAdapter = new ProdutoEAdapter(produtoEList,this);
        recyclerView.setAdapter(produtoEAdapter);



        Intent intent = getIntent();
        List<ProdutoC> produtoCList = (List<ProdutoC>) intent.getSerializableExtra("produtoClist");
        String nomeProduto = intent.getStringExtra("nomeProduto");
        double precoProduto = intent.getDoubleExtra("precoProduto", 0.0);
        int imagemProduto = intent.getIntExtra("imagemProduto", 0);
        String dataAdicao = intent.getStringExtra("dataAdicao");
        double precoTotal = intent.getDoubleExtra("precoTotal",0.0);
        String nomeProdutoS = intent.getStringExtra("nomeProdutoS");
        double precoProdutoS = intent.getDoubleExtra("precoProdutoS", 0.0);
        int imagemProdutoS = intent.getIntExtra("imagemProdutoS", 0);



        if (produtoCList != null && !produtoCList.isEmpty()) {
            for (ProdutoC produtoC : produtoCList) {
                ProdutoE produtoE = new ProdutoE(produtoC.getNome(), produtoC.getPreco(), produtoC.getImagem(), dataAdicao, precoTotal);
                produtoEList.add(produtoE);
            }
        }

        if (nomeProdutoS != null) {
            ProdutoE produtoESeparado = new ProdutoE(nomeProdutoS, precoProdutoS, imagemProdutoS, dataAdicao, precoTotal);
            produtoEList.add(produtoESeparado);
        }



        produtoEAdapter.notifyDataSetChanged();
// navegação



        IniciarHomeEntrega();

        IniciarUsuarioEntrega();

        IniciarCarrinhoEntrega();

        img_home_entrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(formEntrega.this,formMenu.class);
                startActivity(intent);
            }
        });

        img_usuario_entraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(formEntrega.this, formUsuario.class);
                startActivity(intent);
            }
        });

        img_carrinho_entrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(formEntrega.this, formCarrinho.class);
                startActivity(intent);
            }
        });

    }

    public void IniciarHomeEntrega() {

        img_home_entrega = findViewById(R.id.ic_home_entrega);
    }

    public void IniciarUsuarioEntrega() {
        img_usuario_entraga = findViewById(R.id.ic_person_entrega);
    }

    public  void IniciarCarrinhoEntrega() {

        img_carrinho_entrega = findViewById(R.id.ic_basket_entrega);
    }

}
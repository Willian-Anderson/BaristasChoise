package com.example.baristachoise;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baristachoise.adapter.ProdutoFnAdapter;
import com.example.baristachoise.model.ProdutoC;
import com.example.baristachoise.model.ProdutoFn;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class formFinalizar_Compra extends AppCompatActivity {


    private static List<ProdutoFn> produtoFnList = new ArrayList<>();
    private ProdutoFnAdapter produtoFnAdapter;
    private TextView quantidadeTotalFC, txtSubtotalProduto, txtTotalGeral;
    private double cont = 1;
    private double valorTotalS, novoPrecoS, tnovopreco, novoPrecoR, valorTotalG, novoPrecoG, valorTotalR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_finalizar_compra);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainFinalizar_Compra), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_produto_FC);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        produtoFnAdapter = new ProdutoFnAdapter(produtoFnList, this);
        recyclerView.setAdapter(produtoFnAdapter);

        quantidadeTotalFC = findViewById(R.id.txt_quantidade_FC);
        Button btnFinalizarCompra = findViewById(R.id.bt_finalizar_compra_FC);
        txtSubtotalProduto = findViewById(R.id.txt_preco_subtotal_FC);
        txtTotalGeral = findViewById(R.id.txt_total_geral_FC);




        Intent intent = getIntent();
        String nomeProduto = intent.getStringExtra("nomeProduto");
        double precoProduto = intent.getDoubleExtra("precoProduto",0.0);
        int imagemProduto = intent.getIntExtra("imagemProduto",0);
        valorTotalR = intent.getDoubleExtra("valorTotal", 0.0);
        String nomeProdutoS = intent.getStringExtra("nomeProdutoS");
        double precoProdutoS = intent.getDoubleExtra("precoProdutoS", 0.0);
        int imagemProdutoS = intent.getIntExtra("imagemProdutoS", 0);
        List<ProdutoC> produtoCList = (List<ProdutoC>) intent.getSerializableExtra("produtoClist");
        String dataAdicao = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());



        if (produtoCList != null && !produtoCList.isEmpty()) {
            for (ProdutoC produtoC : produtoCList) {
                ProdutoFn produtoFn = new ProdutoFn(produtoC.getNome(), produtoC.getPreco(), produtoC.getImagem());
                produtoFnList.add(produtoFn);
            }

        }



        if (produtoCList != null || valorTotalR > 1) {
            tnovopreco += 10;
            novoPrecoR = valorTotalR + tnovopreco;
        }


        if (nomeProdutoS != null) {
            ProdutoFn produtoSeparado = new ProdutoFn(nomeProdutoS, precoProdutoS, imagemProdutoS);
            valorTotalS = precoProdutoS;
            novoPrecoS = valorTotalS + 10;
            produtoFnList.add(produtoSeparado);
        } else {
            valorTotalS = 0;
            novoPrecoS = 0;
        }

        if (valorTotalR > 1) {
            valorTotalG = valorTotalR;
            novoPrecoG = novoPrecoR;
        }

        if (nomeProdutoS != null) {
            valorTotalG = valorTotalS;
            novoPrecoG = novoPrecoS;
        }


        produtoFnAdapter.notifyDataSetChanged();
        atualizarQuantidadeTotal();


        txtSubtotalProduto.setText(String.format("R$ %.2f", valorTotalG));
        txtTotalGeral.setText(String.format("R$ %.2f", novoPrecoG));


        btnFinalizarCompra.setOnClickListener(view -> {
            Intent finalizarCompraIntent = new Intent(formFinalizar_Compra.this,form_Compra_Bem_Sucedida.class);

            finalizarCompraIntent.putExtra("nomeProduto", nomeProduto);
            finalizarCompraIntent.putExtra("precoProduto", precoProduto);
            finalizarCompraIntent.putExtra("precoTotal", novoPrecoG);
            finalizarCompraIntent.putExtra("valorTotal", valorTotalG);
            finalizarCompraIntent.putExtra("dataAdicao", dataAdicao);
            finalizarCompraIntent.putExtra("imagemProduto", imagemProduto);
            finalizarCompraIntent.putExtra("nomeProdutoS", nomeProdutoS);
            finalizarCompraIntent.putExtra("precoProdutoS", precoProdutoS);
            finalizarCompraIntent.putExtra("imagemProdutoS", imagemProdutoS);
            finalizarCompraIntent.putExtra("produtoClist", (Serializable) produtoCList);


            startActivity(finalizarCompraIntent);
            produtoFnAdapter.notifyDataSetChanged();
            produtoFnList.clear();
            atualizarQuantidadeTotal();
        });



    }


    private void atualizarQuantidadeTotal() {
        int totalItens = produtoFnList.size();

        if (totalItens == cont) {
            quantidadeTotalFC.setText(String.format("(%d produto)", totalItens));
        } else {
            quantidadeTotalFC.setText(String.format("(%d produtos)", totalItens));
        }
    }

}
package com.example.baristachoise;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.baristachoise.model.ProdutoC;
import com.example.baristachoise.model.ProdutoFn;

import java.io.Serializable;
import java.util.List;

public class form_Compra_Bem_Sucedida extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_compra_bem_sucedida);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainCBS), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView txtNomeProduto = findViewById(R.id.txt_nome_produto_CBS);
        TextView txtPrecoProduto = findViewById(R.id.txt_preco_CBS);
        TextView txtPrecoTotal = findViewById(R.id.txt_preco_total_CBS);
        Button btnIrEntrega = findViewById(R.id.btn_ir_pedidos_CBS);

        Intent intent = getIntent();
        String nomeProduto = intent.getStringExtra("nomeProduto");
        double precoProduto = intent.getDoubleExtra("precoProduto", 0.0);
        double totalProduto = intent.getDoubleExtra("precoTotal",0.0);
        double valorTotal = intent.getDoubleExtra("valorTotal", 0.0);
        String dataAdicao = intent.getStringExtra("dataAdicao");
        int imagemProduto = intent.getIntExtra("imagemProduto",0);
        String nomeProdutoS = intent.getStringExtra("nomeProdutoS");
        double precoProdutoS = intent.getDoubleExtra("precoProdutoS", 0.0);
        int imagemProdutoS = intent.getIntExtra("imagemProdutoS", 0);
        List<ProdutoC> produtoCList = (List<ProdutoC>) intent.getSerializableExtra("produtoClist");


        txtPrecoProduto.setText(String.format("R$ %.2f", valorTotal));
        txtPrecoTotal.setText(String.format("R$ %.2f", totalProduto));


        btnIrEntrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent irEntregaIntent = new Intent(form_Compra_Bem_Sucedida.this,formEntrega.class);

                irEntregaIntent.putExtra("nomeProduto", nomeProduto);
                irEntregaIntent.putExtra("precoTotal", totalProduto);
                irEntregaIntent.putExtra("precoProduto", precoProduto);
                irEntregaIntent.putExtra("dataAdicao", dataAdicao);
                irEntregaIntent.putExtra("imagemProduto", imagemProduto);
                irEntregaIntent.putExtra("produtoClist", (Serializable) produtoCList);
                irEntregaIntent.putExtra("nomeProdutoS", nomeProdutoS);
                irEntregaIntent.putExtra("precoProdutoS", precoProdutoS);
                irEntregaIntent.putExtra("imagemProdutoS", imagemProdutoS);

                startActivity(irEntregaIntent);
            }
        });

    }


}
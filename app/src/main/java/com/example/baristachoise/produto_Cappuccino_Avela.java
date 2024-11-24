package com.example.baristachoise;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class produto_Cappuccino_Avela extends AppCompatActivity {

    private ImageView img_home_CAvela;
    private ImageView img_usuario_CAvela;
    private ImageView img_carrinho_CAvela;
    private ImageView img_entrega_CAvela;
    private FirebaseFirestore db;
    private TextView nomeProduto, precoProduto;
    private double precoProdutoValor, precoQuantidade;
    private CheckBox ad_1, ad_2;
    private boolean isChecked1, isChecked2;
    private TextView txtQuantidade;
    private Button btnIncrementar, btnDecrementar;
    private int quantidade = 1;
    private int MAX_QUANTIDADE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_produto_cappuccino_avela);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainCAvela), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = FirebaseFirestore.getInstance();
        nomeProduto = findViewById(R.id.txt_nome_cappuccino_CAvela);
        precoProduto = findViewById(R.id.txt_preco_cappuccino_CAvela);
        Button btAdicionarCarrinho = findViewById(R.id.bt_ad_carrinho_cappuccino_CAvela);
        int imagemProduto = R.drawable.img_cafe_3;
        txtQuantidade = findViewById(R.id.txt_quantidade_produto_CAvela);
        btnIncrementar = findViewById(R.id.btn_incrementar_produto_CAvela);
        btnDecrementar = findViewById(R.id.btn_decrementar_CAvela);
        Button btComprarAgora = findViewById(R.id.bt_comprar_agora_cappuccino_CAvela);



        DocumentReference documentReference = db.collection("Produtos").document("iGOBrBcBruuH42pjTQvD");
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }
                if (value != null && value.exists()) {
                    nomeProduto.setText(value.getString("nome"));
                    precoProdutoValor = value.getDouble("preco");
                    precoProduto.setText(String.format("R$ %.2f", precoProdutoValor));
                    precoQuantidade = value.getDouble("preco");
                }
            }
        });

        double precoProduto = getIntent().getDoubleExtra("preco", 0.0);

        ad_1 = findViewById(R.id.check_ad_1_CAvela);
        ad_2 = findViewById(R.id.check_ad_2_CAvela);

        isChecked1 = ad_1.isChecked();
        isChecked2 = ad_2.isChecked();

        ad_1.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            isChecked1 = isChecked;
            atualizarPreco();
        });

        ad_2.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            isChecked2 = isChecked;
            atualizarPreco();
        });

        btnIncrementar.setOnClickListener( view -> {
            if (quantidade < MAX_QUANTIDADE) {
                quantidade++;

                atualizarPreco();
            } else {
                Toast.makeText(this, "Quantidade máxima é 5", Toast.LENGTH_SHORT).show();
            }
        });

        btnDecrementar.setOnClickListener(view -> {
            if (quantidade > 1) {
                quantidade--;
                atualizarPreco();
            }
        });

        btAdicionarCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent adcarrinho = new Intent(produto_Cappuccino_Avela.this, formCarrinho.class);

                adcarrinho.putExtra("imagemProduto", imagemProduto);
                adcarrinho.putExtra("nomeProduto", nomeProduto.getText().toString());
                adcarrinho.putExtra("precoProduto", precoProdutoValor);

                startActivity(adcarrinho);
            }
        });

        btComprarAgora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent comprar = new Intent(produto_Cappuccino_Avela.this, formFinalizar_Compra.class);

                comprar.putExtra("imagemProdutoS", imagemProduto);
                comprar.putExtra("nomeProdutoS", nomeProduto.getText().toString());
                comprar.putExtra("precoProdutoS", precoProdutoValor);

                startActivity(comprar);
            }
        });





        //Navegação

        IniciarMenuCA();
        IniciarUsuarioCA();
        IniciarCarrinhoCA();
        IniciarEntregaCA();

        img_home_CAvela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(produto_Cappuccino_Avela.this, formMenu.class);
                startActivity(intent);
            }
        });

        img_usuario_CAvela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(produto_Cappuccino_Avela.this, formUsuario.class);
                startActivity(intent);
            }
        });

        img_carrinho_CAvela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(produto_Cappuccino_Avela.this, formCarrinho.class);
                startActivity(intent);
            }
        });

        img_entrega_CAvela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(produto_Cappuccino_Avela.this, formEntrega.class);
                startActivity(intent);
            }
        });


    }

    private void IniciarMenuCA() {
        img_home_CAvela = findViewById(R.id.img_come_back_CAvela);
    }

    private void IniciarUsuarioCA() {
        img_usuario_CAvela = findViewById(R.id.ic_person_CAvela);
    }

    private void IniciarCarrinhoCA() {
        img_carrinho_CAvela = findViewById(R.id.ic_basket_CAvela);
    }

    private void IniciarEntregaCA() {
        img_entrega_CAvela = findViewById(R.id.ic_moto_CAvela);
    }

    private void atualizarPreco() {
        double precoBase = precoQuantidade * quantidade;

        if (ad_1.isChecked()) {
            precoBase += 10.00 * quantidade;
        }
        if (ad_2.isChecked()) {
            precoBase += 10.50 * quantidade;
        }

        precoProdutoValor = precoBase;

        precoProduto.setText(String.format("R$ %.2f", precoProdutoValor));
        txtQuantidade.setText(String.valueOf(quantidade));
    }

}
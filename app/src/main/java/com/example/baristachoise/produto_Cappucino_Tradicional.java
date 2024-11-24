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

public class produto_Cappucino_Tradicional extends AppCompatActivity {

    private ImageView img_home_Ctradicional;
    private ImageView img_carrinho_Ctradicional;
    private ImageView img_entrega_Ctradicional;
    private ImageView img_perfil_Ctradicional;
    private FirebaseFirestore db;
    private TextView nameProduto, precoProduto;
    private double precoProdutoValor, precoQuantidade ;
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
        setContentView(R.layout.activity_produto_cappucino_tradicional);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainCtradicional), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });




        db = FirebaseFirestore.getInstance();
        nameProduto = findViewById(R.id.txt_cappuccino_tradicional);
        precoProduto = findViewById(R.id.txt_preco_cappuccino_tradicional);

        Button btAdicionarCarrinho = findViewById(R.id.bt_ad_carrinho_cappuccino_tradicional);
        Button btComprarAgora = findViewById(R.id.bt_comprar_agora_cappuccino_tradicional);

        int imagemProduto = R.drawable.img_cafe_2;

        DocumentReference documentReference = db.collection("Produtos").document("9nvJzTWXmhiTJPBvPFs6");
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }
                if (value != null && value.exists()) {
                    nameProduto.setText(value.getString("nome"));
                    precoProdutoValor = value.getDouble("preco");
                    precoProduto.setText(String.format("R$ %.2f", precoProdutoValor));
                    precoQuantidade = value.getDouble("preco");

                }

            }
        });

        double precoProduto = getIntent().getDoubleExtra("preco",0.0);

        ad_1 = findViewById(R.id.check_ad_1_tradicional);
        ad_2 = findViewById(R.id.check_ad_2_tradicional);

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


        txtQuantidade = findViewById(R.id.txt_quantidade_produto);
        btnIncrementar = findViewById(R.id.btn_incrementar_produto);
        btnDecrementar = findViewById(R.id.btn_decrementar_produto);

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
                Intent adcarrinho = new Intent(produto_Cappucino_Tradicional.this, formCarrinho.class);

                adcarrinho.putExtra("imagemProduto", imagemProduto);
                adcarrinho.putExtra("nomeProduto", nameProduto.getText().toString());
                adcarrinho.putExtra("precoProduto", precoProdutoValor);

                startActivity(adcarrinho);
            }
        });

        btComprarAgora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent adcarrinho = new Intent(produto_Cappucino_Tradicional.this, formFinalizar_Compra.class);

                adcarrinho.putExtra("imagemProdutoS", imagemProduto);
                adcarrinho.putExtra("nomeProdutoS", nameProduto.getText().toString());
                adcarrinho.putExtra("precoProdutoS", precoProdutoValor);

                startActivity(adcarrinho);
            }
        });



        // Navegação

        IniciarHomeCtradicional();
        IniciarCarrinhoCtradicional();
        IniciarPerfilCtradicional();
        IniciarEntregaCtradicional();

        img_home_Ctradicional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(produto_Cappucino_Tradicional.this, formMenu.class);
                startActivity(intent);
            }
        });

        img_carrinho_Ctradicional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(produto_Cappucino_Tradicional.this, formCarrinho.class);
                startActivity(intent);
            }
        });

        img_perfil_Ctradicional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(produto_Cappucino_Tradicional.this, formUsuario.class);
                startActivity(intent);
            }
        });

        img_entrega_Ctradicional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(produto_Cappucino_Tradicional.this, formEntrega.class);
                startActivity(intent);
            }
        });


    }

    private void IniciarHomeCtradicional() {
        img_home_Ctradicional = findViewById(R.id.img_come_back_tradicional);
    }

    private void IniciarPerfilCtradicional() {
        img_perfil_Ctradicional = findViewById(R.id.ic_person_Ctradicional);
    }

    private void IniciarCarrinhoCtradicional() {
        img_carrinho_Ctradicional = findViewById(R.id.ic_basket_Ctradicional);
    }

    private void IniciarEntregaCtradicional() {
        img_entrega_Ctradicional = findViewById(R.id.ic_moto_Ctradicional);
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
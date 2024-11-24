package com.example.baristachoise;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class produto_Cookie_tradicional extends AppCompatActivity {

    private ImageView img_home_CookieT;
    private ImageView img_usuario_CookieT;
    private ImageView img_carrinho_CookieT;
    private ImageView img_entrega_CookieT;

    private FirebaseFirestore db;
    private TextView nomeProduto, precoProduto;
    private double precoProdutoValor, precoQuantidade;
    private TextView txtQuantidade;
    private Button btnIncrementar, btnDecrementar;
    private int quantidade = 1;
    private int MAX_QUANTIDADE = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_produto_cookie_tradicional);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainCookie_tradicional), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = FirebaseFirestore.getInstance();
        nomeProduto = findViewById(R.id.txt_Cookie_tradicional);
        precoProduto = findViewById(R.id.txt_preco_Cookie_tradicional);
        Button btnAdicionarCarrinho = findViewById(R.id.bt_ad_carrinho_Cookie_tradicional);
        Button btnComprarAgora = findViewById(R.id.bt_comprar_agora_Cookie_tradicional);
        int imagemProduto = R.drawable.img_cookie_1;
        txtQuantidade = findViewById(R.id.txt_quantidade_produto_Cookie_tradicional);
        btnIncrementar = findViewById(R.id.btn_incrementar_produto_Cookie_tradicional);
        btnDecrementar = findViewById(R.id.btn_decrementar_Cookie_tradicional);

        DocumentReference documentReference = db.collection("Produtos").document("QXFtK9ekioOVtQHFrtzs");
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

        btnIncrementar.setOnClickListener(view -> {
            if (quantidade < MAX_QUANTIDADE) {
                quantidade++;
                precoProdutoValor += precoQuantidade;
                txtQuantidade.setText(String.valueOf(quantidade));
                atualizarPrecoProduto();
            } else {
                Toast.makeText(this, "Quantidade máxima é 5", Toast.LENGTH_SHORT).show();
            }

        });

        btnDecrementar.setOnClickListener(view -> {
            if (quantidade > 1) {
                quantidade--;
                precoProdutoValor -= precoQuantidade;
                txtQuantidade.setText(String.valueOf(quantidade));
                atualizarPrecoProduto();
            }
        });

        btnAdicionarCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent adcarrinho = new Intent(produto_Cookie_tradicional.this, formCarrinho.class);

                adcarrinho.putExtra("imagemProduto", imagemProduto);
                adcarrinho.putExtra("nomeProduto", nomeProduto.getText().toString());
                adcarrinho.putExtra("precoProduto", precoProdutoValor);

                startActivity(adcarrinho);
            }
        });

        btnComprarAgora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent adcarrinho = new Intent(produto_Cookie_tradicional.this, formFinalizar_Compra.class);

                adcarrinho.putExtra("imagemProdutoS", imagemProduto);
                adcarrinho.putExtra("nomeProdutoS", nomeProduto.getText().toString());
                adcarrinho.putExtra("precoProdutoS", precoProdutoValor);

                startActivity(adcarrinho);
            }
        });


        //Navegação

        IniciarMenuCookieT();
        IniciarUsuarioCookieT();
        IniciarCarrinhoCookieT();
        IniciarEntregaCookieT();

        img_home_CookieT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(produto_Cookie_tradicional.this, formMenu.class);
                startActivity(intent);
            }
        });

        img_usuario_CookieT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(produto_Cookie_tradicional.this, formUsuario.class);
                startActivity(intent);
            }
        });

        img_carrinho_CookieT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(produto_Cookie_tradicional.this, formCarrinho.class);
                startActivity(intent);
            }
        });

        img_entrega_CookieT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(produto_Cookie_tradicional.this, formEntrega.class);
                startActivity(intent);
            }
        });
    }

    private void IniciarMenuCookieT() {
        img_home_CookieT = findViewById(R.id.img_come_back_Cookie_tradicional);
    }

    private void IniciarUsuarioCookieT() {
        img_usuario_CookieT = findViewById(R.id.ic_person_Cookie_tradicional);
    }

    private void IniciarCarrinhoCookieT() {
        img_carrinho_CookieT = findViewById(R.id.ic_basket_Cookie_tradicional);
    }

    private void IniciarEntregaCookieT() {
        img_entrega_CookieT = findViewById(R.id.ic_moto_Cookie_tradicional);
    }

    private void atualizarPrecoProduto() {
        precoProduto.setText(String.format("R$ %.2f", precoProdutoValor));
    }


}
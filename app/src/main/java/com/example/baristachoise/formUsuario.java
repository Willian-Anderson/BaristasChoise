package com.example.baristachoise;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class formUsuario extends AppCompatActivity {

    private ImageView img_home_usuario;

    private ImageView img_carrinho_usuario;

    private ImageView img_entrega_usuario;

    private TextView nomeUsuario, emailUsuario, telefoneUsuario;
    private Button bt_deslogar;
    FirebaseFirestore db  = FirebaseFirestore.getInstance();
    String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_usuario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainPerfil), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        IniciarMenuUsuario();

        IniciarCarrinhoUsuario();

        IniciarEntregaUsuario();

        IniciarDeslogue();

        bt_deslogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(formUsuario.this,formLogin.class);
                startActivity(intent);
                finish();
            }
        });

        img_home_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(formUsuario.this,formMenu.class);
                startActivity(intent);
            }
        });

        img_carrinho_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(formUsuario.this, formCarrinho.class);
                startActivity(intent);
            }
        });

        img_entrega_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(formUsuario.this, formEntrega.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null) {
                    nomeUsuario.setText(documentSnapshot.getString("nome"));
                    telefoneUsuario.setText(documentSnapshot.getString("telefone"));
                    emailUsuario.setText(email);
                }
            }
        });
    }

    private void IniciarDeslogue() {
        nomeUsuario = findViewById(R.id.txt_nome_fire_usuario);
        emailUsuario = findViewById(R.id.txt_email_fire_usuario);
        telefoneUsuario = findViewById(R.id.txt_numero_fire_usuario);
        bt_deslogar = findViewById(R.id.bt_deslogar);
    }

    private void IniciarMenuUsuario() {
        img_home_usuario = findViewById(R.id.ic_home_usuario);

    }

    private void IniciarCarrinhoUsuario() {
        img_carrinho_usuario = findViewById(R.id.ic_basket_usuario);
    }

    private void IniciarEntregaUsuario() {
        img_entrega_usuario = findViewById(R.id.ic_moto_usuario);
    }
}
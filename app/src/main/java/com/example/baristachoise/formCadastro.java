package com.example.baristachoise;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class formCadastro extends AppCompatActivity {

    private TextView cLogin;
    private EditText et_nome, et_email, et_senha, et_numero;
    private Button bt_cadastrar;
    String[] mensagens = {"Preencha todos os campos.","Cadastro realizado com sucesso."};
    String usuarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainCadastro), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        IniciarCadastro();
        IniciarLogin();

        bt_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = et_nome.getText().toString();
                String email = et_email.getText().toString();
                String senha = et_senha.getText().toString();
                String numero = et_numero.getText().toString();

                if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || numero.isEmpty()){
                    Snackbar snackbar = Snackbar.make(v,mensagens[0],Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
                else {
                    CadastrarUsuario(v);
                }
            }
        });

        cLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(formCadastro.this, formLogin.class);
                startActivity(intent);
            }
        });


    }

    private void CadastrarUsuario(View v) {
        String email = et_email.getText().toString();
        String senha = et_senha.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {

                    SalvarDadosUsuario();

                    Snackbar snackbar = Snackbar.make(v,mensagens[1],Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            TelaUsuario();
                        }
                    },1000);
                }
                else {
                    String erro;
                    try {
                        throw task.getException();
                    }
                    catch (FirebaseAuthWeakPasswordException e){
                        erro = "Senha deve ter no mínimo 6 caracteres.";
                    }
                    catch (FirebaseAuthUserCollisionException e){
                        erro = "Conta já cadastrada.";
                    }
                    catch (FirebaseAuthInvalidCredentialsException e) {
                        erro = "Email inválido.";
                    }
                    catch (Exception e){
                        erro = "Erro ao cadastrar usuário.";
                    }

                    Snackbar snackbar = Snackbar.make(v,erro,Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            }
        });

    }

    private void SalvarDadosUsuario() {
        String nome = et_nome.getText().toString();
        String numero = et_numero.getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String,Object> usuarios = new HashMap<>();
        usuarios.put("nome",nome);
        usuarios.put("telefone",numero);

        usuarioId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioId);
        documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("db","Dados salvos com sucesso.");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("db","Erro ao salvar os dados." + e.toString());
                    }
                });
    }

    private void TelaUsuario() {
        Intent intent = new Intent(formCadastro.this,formUsuario.class);
        startActivity(intent);
        finish();
    }

    private void IniciarCadastro() {

        et_nome = findViewById(R.id.et_nome_cadastro);
        et_email = findViewById(R.id.et_email_cadastro);
        et_senha = findViewById(R.id.et_senha_cadastro);
        et_numero = findViewById(R.id.et_telefone_cadastro);
        bt_cadastrar = findViewById(R.id.bt_cadastrar);

    }

    private void IniciarLogin() {
        cLogin = findViewById(R.id.txt_login_cadastro);
    }
}
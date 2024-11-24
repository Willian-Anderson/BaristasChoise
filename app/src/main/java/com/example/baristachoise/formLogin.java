package com.example.baristachoise;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class formLogin extends AppCompatActivity {


    private TextView text_cadastro;

    private EditText et_email, et_senha;

    private Button bt_entrar;

    private ProgressBar progressBar_login;

    String[] mensagens = {"Preencha todos os campos."};

    private TextView txt_menu_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainLogin), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        IniciarPularLogin();
        IniciarCadastroLogin();

        txt_menu_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(formLogin.this, formMenu.class);
                startActivity(intent);
            }
        });


        text_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {

                Intent intent = new Intent(formLogin.this,formCadastro.class);
                startActivity(intent);
            }

        });

        FirebaseApp.initializeApp(this);

        bt_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString();
                String senha = et_senha.getText().toString();

                if (email.isEmpty() || senha.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(v,mensagens[0],Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
                else {
                    AutenticarUsuario(v);
                }
            }
        });

    }

    private void AutenticarUsuario(View view) {
        String email = et_email.getText().toString();
        String senha = et_senha.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressBar_login.setVisibility(View.VISIBLE);
                    bt_entrar.setVisibility(View.INVISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            TelaMenu();
                        }
                    },3000);
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
                        erro = "Login inválido.";
                    }
                    catch (Exception e){
                        erro = "Erro ao cadastrar usuário.";
                    }

                    Snackbar snackbar = Snackbar.make(view,erro,Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();

        if (usuarioAtual != null) {
            TelaMenu();
        }
    }


    private void TelaMenu() {
        Intent intent = new Intent(formLogin.this,formMenu.class);
        startActivity(intent);
        finish();
    }

    private void IniciarCadastroLogin() {
        text_cadastro = findViewById(R.id.txt_cadastro);

        et_email = findViewById(R.id.et_email_login);
        et_senha = findViewById(R.id.et_password_login);
        bt_entrar = findViewById(R.id.bt_entrar_login);
        progressBar_login = findViewById(R.id.pb_tela1);
    }

    private void IniciarPularLogin() {
        txt_menu_login = findViewById(R.id.txt_pular_login);
    }




}
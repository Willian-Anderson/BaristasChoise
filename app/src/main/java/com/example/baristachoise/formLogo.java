package com.example.baristachoise;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class formLogo extends AppCompatActivity {

    private ProgressBar progressBar_logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_logo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainLogo), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        progressBar_logo = findViewById(R.id.pb_logo);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(this, formLogin.class);
            progressBar_logo.setVisibility(View.VISIBLE);
            startActivity(intent);
        }, 4000);
    }
}
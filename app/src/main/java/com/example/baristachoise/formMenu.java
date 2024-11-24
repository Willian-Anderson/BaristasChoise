package com.example.baristachoise;

import android.content.Intent;
import android.os.Bundle;
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

import com.example.baristachoise.adapter.CookieAdapter;
import com.example.baristachoise.adapter.ProductAdapter;
import com.example.baristachoise.databinding.ActivityFormMenuBinding;
import com.example.baristachoise.model.Cookie;
import com.example.baristachoise.model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class formMenu extends AppCompatActivity {

    private ActivityFormMenuBinding binding;
    private ProductAdapter productAdapter;
    private ArrayList<Product> productList = new ArrayList<>();

    private CookieAdapter cookieAdapter;
    private ArrayList<Cookie> cookieList = new ArrayList<>();


    private ImageView img_usuario_menu;
    private ImageView img_carrinho_menu;
    private ImageView img_entrega_menu;

    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormMenuBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_form_menu);
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainMenu), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        String nomeProduto = intent.getStringExtra("nomeProduto");
        double precoProduto = intent.getDoubleExtra("precoProduto",0.0);
        int imagemProduto = intent.getIntExtra("imagemProduto",0);



        auth = FirebaseAuth.getInstance();


        // O layout do form_Menu

        Intent intentP = getIntent();
        int productPosition = intent.getIntExtra("product_position", -1);

        if (productPosition == 0) {
            verificarEProsseguir(produto_Cappucino_Italiano.class);
        } else if (productPosition == 1) {
            verificarEProsseguir(produto_Cappucino_Tradicional.class);
        } else if (productPosition == 2) {
            verificarEProsseguir(produto_Cappuccino_Avela.class);
        }

        Intent intentC = getIntent();
        int cookiePosition = intentC.getIntExtra("cookie_position", -1);

        if (cookiePosition == 0) {
            verificarEProsseguir(produto_Cookie_tradicional.class);
        } else if (cookiePosition == 1) {
            verificarEProsseguir(produto_Cookie_MM.class);
        } else if (cookiePosition == 2) {
            verificarEProsseguir(produto_Cookie_Red_Velvet.class);
        }



        RecyclerView recyclerViewProduct = binding.recyclerviewProducts1;
        recyclerViewProduct.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recyclerViewProduct.setHasFixedSize(true);
        productAdapter = new ProductAdapter(productList,this);
        recyclerViewProduct.setAdapter(productAdapter);
        getProduct();


        RecyclerView recyclerViewCookie = binding.recyclerviewCookies;
        recyclerViewCookie.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recyclerViewCookie.setHasFixedSize(true);
        cookieAdapter = new CookieAdapter(cookieList,this);
        recyclerViewCookie.setAdapter(cookieAdapter);
        getCookie();




        //Navegação

        IniciarUsuarioMenu();

        IniciarCarrinhoMenu();

        IniciarEntregaMenu();


        img_usuario_menu.setOnClickListener(view -> verificarEProsseguir(formUsuario.class));
        img_carrinho_menu.setOnClickListener(view -> verificarEProsseguir(formCarrinho.class));
        img_entrega_menu.setOnClickListener(view -> verificarEProsseguir(formEntrega.class));


    }


    private void getProduct() {

        Product product1 = new Product(
            R.drawable.img_cafe_1,
                "Cappuccino Italiano",
                "R$8,00"
        );
        productList.add(product1);

        Product product2 = new Product(
                R.drawable.img_cafe_2,
                "Cappuccino Tradicional",
                "R$5,00"
        );
        productList.add(product2);

        Product product3 = new Product(
                R.drawable.img_cafe_3,
                "Cappuccino Avelã",
                "R$12,00"

        );
        productList.add(product3);

    }

    private void getCookie() {

        Cookie cookie1 = new Cookie(
                R.drawable.img_cookie_1,
                "Cookie tradicional",
                "R$3,00"
        );
        cookieList.add(cookie1);

        Cookie cookie2 = new Cookie(
                R.drawable.img_cookie_2,
                "Cookie M&M",
                "R$8,00"
        );
        cookieList.add(cookie2);

        Cookie cookie3 = new Cookie(
                R.drawable.img_cookie_3,
                "Cookie Red Velvet",
                "R$10,00"
        );
        cookieList.add(cookie3);

    }


    public void IniciarUsuarioMenu() {
        img_usuario_menu = findViewById(R.id.ic_person_menu);
    }

    public void IniciarCarrinhoMenu() {
        img_carrinho_menu = findViewById(R.id.ic_basket_menu);
    }

    public void IniciarEntregaMenu() {
        img_entrega_menu = findViewById(R.id.ic_moto_menu);
    }


    private void verificarEProsseguir (Class<?> destino) {
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            Intent intent = new Intent(this, destino);
            startActivity(intent);

        } else {
            Intent intent = new Intent(this, formLogin.class);
            Toast.makeText(this, "Efetue o login primeiro", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
    }


}
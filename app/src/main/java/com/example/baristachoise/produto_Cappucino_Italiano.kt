package com.example.baristachoise

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class produto_Cappucino_Italiano : AppCompatActivity() {

    private lateinit var homeCappuccino: ImageView

    private lateinit var carrinhoCappuccino: ImageView

    private lateinit var usuarioCappuccino: ImageView

    private lateinit var entregaCappuccino: ImageView

    private lateinit var db: FirebaseFirestore
    private lateinit var nomeProduto: TextView
    private lateinit var precoProduto: TextView

    private lateinit var txtQuantidade: TextView
    private lateinit var btnIncrementar: Button
    private lateinit var btnDecrementar: Button

    private var precoProdutoValor: Double = 0.0
    private var precoQuantidade: Double = 0.0
    private var quantidade: Int = 1
    private var MAX_QUANTIDADE = 5

    private lateinit var ad_1: CheckBox
    private lateinit var ad_2: CheckBox

    private var isChecked1: Boolean = false
    private var isChecked2: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_produto_cappucino_italiano)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainProduto)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = FirebaseFirestore.getInstance()
        nomeProduto = findViewById(R.id.txt_cappuccino_italiano)
        precoProduto = findViewById(R.id.txt_preco_cappuccino_italiano)
        txtQuantidade = findViewById(R.id.txt_quantidade_produto_italiano);
        btnIncrementar = findViewById(R.id.btn_incrementar_produto_italiano);
        btnDecrementar = findViewById(R.id.btn_decrementar_produto_italiano);
        val imagemProduto = R.drawable.img_cafe_1


        val documentReference: DocumentReference = db.collection("Produtos").document("T5pvCUdQrhH6Na2ZfzWQ")
        documentReference.addSnapshotListener { value, error ->
            if ( error != null) {
                return@addSnapshotListener
            }
            if (value != null && value.exists()) {
                nomeProduto.text = value.getString("nome")
                precoProdutoValor = value.getDouble("preco") ?:0.0
                precoProduto.text = String.format("R$ %.2f", precoProdutoValor)
                precoQuantidade = precoProdutoValor
                atualizarPreco()
            }

        }

        val precoProduto = intent.getDoubleExtra("preco", 0.0)

        //Adicionais

        ad_1 = findViewById(R.id.check_ad_1_italiano)
        ad_2 = findViewById(R.id.check_ad_2_italiano)

        isChecked1 = ad_1.isChecked
        isChecked2 = ad_2.isChecked

        ad_1.setOnCheckedChangeListener { _, _ -> atualizarPreco() }

        ad_2.setOnCheckedChangeListener { _, _ -> atualizarPreco() }

        btnIncrementar.setOnClickListener {
            if (quantidade < MAX_QUANTIDADE) {
                quantidade++
                atualizarPreco()
            } else {
                Toast.makeText(this, "Quantidade máxima é 5", Toast.LENGTH_SHORT).show()
            }
        }

        btnDecrementar.setOnClickListener {
            if (quantidade > 1) {
                quantidade--
                atualizarPreco()
            }
        }



        val btnAdicionarAoCarrinho: Button = findViewById(R.id.bt_ad_carrinho_cappuccino_italiano)

        btnAdicionarAoCarrinho.setOnClickListener {
            val intent = Intent(this@produto_Cappucino_Italiano, formCarrinho::class.java)

            intent.putExtra("nomeProduto", nomeProduto.text.toString())
            intent.putExtra("precoProduto", precoProdutoValor)
            intent.putExtra("imagemProduto", imagemProduto)
            startActivity(intent)
        }

        val btnComprarAgora: Button = findViewById(R.id.bt_comprar_agora_cappuccino_italiano)

        btnComprarAgora.setOnClickListener {
            val intent = Intent(this@produto_Cappucino_Italiano, formFinalizar_Compra::class.java)

            intent.putExtra("nomeProdutoS", nomeProduto.text.toString())
            intent.putExtra("precoProdutoS", precoProdutoValor)
            intent.putExtra("imagemProdutoS", imagemProduto)
            startActivity(intent)

        }




        // Navegação

        homeCappuccino = findViewById(R.id.img_come_back_italiano)

        carrinhoCappuccino = findViewById(R.id.ic_basket_cappuccino1)

        usuarioCappuccino = findViewById(R.id.ic_person_cappuccino1)

        entregaCappuccino = findViewById(R.id.ic_moto_cappuccino1)


        homeCappuccino.setOnClickListener {

            val intent = Intent(this@produto_Cappucino_Italiano, formMenu::class.java)
            startActivity(intent)
        }

        carrinhoCappuccino.setOnClickListener {

            val intent = Intent(this@produto_Cappucino_Italiano, formCarrinho::class.java)
            startActivity(intent)
        }

        usuarioCappuccino.setOnClickListener {

            val intent = Intent(this@produto_Cappucino_Italiano, formUsuario::class.java)
            startActivity(intent)
        }

        entregaCappuccino.setOnClickListener {

            val intent = Intent(this@produto_Cappucino_Italiano, formEntrega::class.java)
            startActivity(intent)
        }

    }

    private fun atualizarPreco() {
        var precoBase = precoQuantidade * quantidade

        if (ad_1.isChecked) {
            precoBase += 10.00 * quantidade
        }

        if (ad_2.isChecked) {
            precoBase += 10.50 * quantidade
        }

        precoProdutoValor = precoBase
        precoProduto.text = String.format("R$ %.2f", precoProdutoValor)
        txtQuantidade.text = quantidade.toString()
    }


}
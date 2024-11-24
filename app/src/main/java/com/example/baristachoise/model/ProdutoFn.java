package com.example.baristachoise.model;

import java.io.Serializable;

public class ProdutoFn implements Serializable {
    private String nome;
    private double preco;
    private int imagem;


    public ProdutoFn(String nome, double preco, int imagem) {
        this.nome = nome;
        this.preco = preco;
        this.imagem = imagem;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getImagem() {
        return imagem;
    }

}

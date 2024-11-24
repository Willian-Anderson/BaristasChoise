package com.example.baristachoise.model;

import java.io.Serializable;

public class ProdutoE implements Serializable {
    private String nome;
    private double preco;
    private int imagem;
    private String dataAdicao;
    private double totalPedido;

    public ProdutoE(String nome, double preco, int imagem, String dataAdicao, double totalPedido) {
        this.nome = nome;
        this.preco = preco;
        this.imagem = imagem;
        this.dataAdicao = dataAdicao;
        this.totalPedido = totalPedido;
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

    public String getDataAdicao() {
        return dataAdicao;
    }

    public double getTotalPedido() {
        return totalPedido;
    }
}

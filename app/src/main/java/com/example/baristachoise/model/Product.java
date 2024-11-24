package com.example.baristachoise.model;

import androidx.constraintlayout.widget.ConstraintSet;

public class Product {

    int idProduct;

    int imgProduct;

    String productName;

    String price;

    public Product(int imgProduct, String productName, String price) {
        this.imgProduct = imgProduct;
        this.productName = productName;
        this.price = price;
    }

    public int getImgProduct() {
        return imgProduct;
    }


    public String getProductName() {
        return productName;
    }


    public String getPrice() {
        return price;
    }

    public int getId() {
        return idProduct;
    }
}

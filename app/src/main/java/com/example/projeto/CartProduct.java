package com.example.projeto;

public class CartProduct {
    private int id;
    private String name;
    private float price;
    private String imgUrl;
    private int quantity;
    private String cartUser;

    public CartProduct(int id, String name, float price, String imgUrl, int quantity, String cartUser) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
        this.quantity = quantity;
        this.cartUser = cartUser;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() { return quantity; }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getCartUser() {
        return cartUser;
    }
}

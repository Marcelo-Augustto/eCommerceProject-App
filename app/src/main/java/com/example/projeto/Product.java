package com.example.projeto;

public class Product {
    private int id;
    private String name;
    private Float price;
    private String imgUrl;

    public Product(int id, String name, Float price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Float getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}

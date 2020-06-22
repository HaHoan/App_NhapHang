package com.example.myapplication.models;

public class Item {
    private  String name;
    private String categoryId;
    private int quantity;
    private float price;
    private String note;
    private String itemId;
    public Item() {
    }

    public String getItemId() {
        return itemId;
    }
    public Item( String itemId,String name, float price, int quantity, String categoryId, String note) {
        this.name = name;
        this.categoryId = categoryId;
        this.quantity = quantity;
        this.price = price;
        this.note = note;
        this.itemId = itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

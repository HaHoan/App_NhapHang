package com.example.myapplication.models;

import java.util.List;

public class Category {
    private String categoryId;
    private String name;
    private List<Item> items;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, List<Item> items) {
        this.name = name;
        this.items = items;
    }

    public Category(String categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public Category(String categoryId, String name, List<Item> items) {
        this.categoryId = categoryId;
        this.name = name;
        this.items = items;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
    public String numberItems(){
        if(items != null && items.size() > 0){
            return String.valueOf(items.size()) + " items";
        } else{
            return "";
        }
    }
}

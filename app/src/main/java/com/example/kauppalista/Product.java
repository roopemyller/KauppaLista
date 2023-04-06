package com.example.kauppalista;

import java.io.Serializable;
import java.util.Comparator;

public class Product implements Serializable {
    private String productName;
    private String productId;

    public Product(String name){
        this.productName = name;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public static class NameComparator implements Comparator<Product>{
        // Products in alphabetical order
        @Override
        public int compare(Product product, Product t1) {
            return product.getProductName().compareTo(t1.getProductName());
        }
    }

    public static class TimeComparator implements Comparator<Product>{
        // Most recently added product is shown first
        @Override
        public int compare(Product product, Product t1) {
            return t1.getProductId().compareTo(product.getProductId());
        }
    }

    public static class OriginalComparator implements Comparator<Product>{
        // Most recently added product is shown first
        @Override
        public int compare(Product product, Product t1) {
            return product.getProductId().compareTo(t1.getProductId());
        }
    }
}

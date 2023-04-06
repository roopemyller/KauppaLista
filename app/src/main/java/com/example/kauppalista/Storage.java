package com.example.kauppalista;

import android.content.Context;

import org.intellij.lang.annotations.JdkConstants;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Storage {
    private ArrayList<Product> products = new ArrayList<>();

    private static Storage storage = null;

    private Storage(){
    }

    public static Storage getInstance(){
        if(storage == null){
            storage = new Storage();
        }
        return storage;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product){
        product.setProductId(String.valueOf(products.size()));
        products.add(product);
    }

    public void removeProduct(String id){
        int i = 0;
        for(Product p : products){
            if(p.getProductId().equals(id)){
                break;
            }
            i++;
        }
        products.remove(i);
    }

    public void saveProducts(Context context) {
        try {
            ObjectOutputStream productWriter = new ObjectOutputStream(context.openFileOutput("products.data", Context.MODE_PRIVATE));
            productWriter.writeObject(products);
            productWriter.close();
        } catch (IOException e) {
            System.out.println("Tietojen tallentaminen ei onnistunut");
        }
    }

    public void loadProducts(Context context) {
        try {
            ObjectInputStream productReader = new ObjectInputStream(context.openFileInput("products.data"));
            products = (ArrayList<Product>) productReader.readObject();
            productReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Tietojen lukeminen ei onnistunut");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Tietojen lukeminen ei onnistunut");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Tietojen lukeminen ei onnistunut");
            e.printStackTrace();
        }
    }

    public Product getProductByIdWithoutRemove(int pos) {
        return products.get(pos);
    }
}

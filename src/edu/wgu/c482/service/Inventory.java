/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wgu.c482.service;

import edu.wgu.c482.model.Part;
import edu.wgu.c482.model.Product;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author scott
 */
public class Inventory {

    private ObservableList products;

    private ObservableList allParts; 
    
    private int partSequence; 
    
    private int productSequence;

    public int getPartSequence() {
        return ++partSequence;
    }

    public void setPartSequence(int partSequence) {
        this.partSequence = partSequence;
    }

    public int getProductSequence() {
        return productSequence;
    }

    public void setProductSequence(int productSequence) {
        this.productSequence = productSequence;
    }

    public Inventory(ObservableList products, ObservableList allParts) {
        this.products = products;
        this.allParts = allParts;
        this.productSequence = products.size();
        this.partSequence = allParts.size();
    }
    
    public Inventory(ArrayList products, ArrayList allParts) {
        this.products = FXCollections.observableArrayList(products);
        this.allParts = FXCollections.observableArrayList(allParts);
        this.productSequence = products.size();
        this.partSequence = allParts.size();
    }

    public ObservableList getProducts() {
        return products;
    }

    public void setProducts(ObservableList products) {
        this.products = products;
    }

    public ObservableList getAllParts() {
        return allParts;
    }

    public void setAllParts(ObservableList allParts) {
        this.allParts = allParts;
    }

    public void addProduct(Product product) {
        ObservableList list = this.getProducts();
        list.add(product);
    }

    public boolean removeProduct(int productId) {
        ObservableList<Product> list = this.getProducts();
        Product p = this.lookupProduct(productId);
        return list.remove(p);
    }

    public Product lookupProduct(int productId) {
        ObservableList<Product> list = this.getProducts();
        Product product = null;
        for (Product p : list) {
            if (p.getProductID() == productId ) {           
                product = p;
                break;
            }
        }
        return product;
    }

    //this is in the UML diagram, but we do not use it. 
    public void updateProduct(int productId) {
        throw new UnsupportedOperationException("Not supported, use updateProduct(Product product)");
    }

    //this is what we use instead of updateProduct(int productId)
    public Product updateProduct(Product product) {
        ObservableList<Product> list = this.getProducts();
        int index = -1;
        
        for (Product p : list) {
            index++;
            if (p.getProductID() == product.getProductID()) {           
                list.set(index, product);
                break;
            }
        }
        return product;
    }

    public void addPart(Part part) {
        
        ObservableList list = this.getAllParts();
        part.setPartID(this.getPartSequence());
        list.add(part);
    }

    public boolean deletePart(Part part) {
        ObservableList<Part> list = this.getAllParts();
        return list.remove(part);
    }

    public Part lookupPart(int partId) {
        ObservableList<Part> list = this.getAllParts();
        Part part = null;
        for (Part p : list) {
            if (p.getPartID() == partId ) {           
                part = p;
                break;
            }
        }
        return part;
    }

    //this is in the UML diagram, but we do not use it. 
    public void updatePart(int partId) {
        throw new UnsupportedOperationException("Not supported, use updatePart(Part part)");
    }

    //this is what we use instead of updatePart(int partId)
    public Part updatePart(Part part) {
        ObservableList<Part> list = this.getAllParts();
        int index = -1;
        
        for (Part p : list) {
            index++;
            if (p.getPartID() == part.getPartID()) {           
                list.set(index, part);
                break;
            }
        }
        return part;
    }

}

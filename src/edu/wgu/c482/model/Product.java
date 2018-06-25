/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wgu.c482.model;

import java.util.ArrayList;

/**
 *
 * @author scott
 */
public class Product {
    private ArrayList associatedParts;
    
    private int productID;
    
    private String name;
    
    private double price;
    
    private int inStock;
    
    private int min;
    
    private int max;

    public ArrayList getAssociatedParts() {
        return associatedParts;
    }

    public void setAssociatedParts(ArrayList associatedParts) {
        this.associatedParts = associatedParts;
    }

    public Product(ArrayList associatedParts, int productID, String name, double price, int inStock, int min, int max) {
        this.associatedParts = associatedParts;
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;
    }



    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
    
    public void addAssociatedPart(Part part){
        
    }
    
    public boolean removeAssociatedPart(int partId){
      return true;  
    }
    
    public Part lookupAssociatedPart(int partId){
        return (Part) new Object();
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wgu.c482.service;

import edu.wgu.c482.model.Part;
import edu.wgu.c482.model.Product;
import java.util.ArrayList;
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

    private int getPartSequence() {
        return ++partSequence;
    }

    private void setPartSequence(int partSequence) {
        this.partSequence = partSequence;
    }

    private int getProductSequence() {
        return ++productSequence;
    }

    private void setProductSequence(int productSequence) {
        this.productSequence = productSequence;
    }

    /**
     * Constructs the Inventory Service class. 
     * @param products
     * @param allParts 
     */
    public Inventory(ObservableList products, ObservableList allParts) {
        this.setProducts(products);
        this.setAllParts(allParts);
        this.setProductSequence(products.size());
        this.setPartSequence(allParts.size());
    }
    /**
     * Constructs the Inventory Service class.
     * @param products
     * @param allParts 
     */
    public Inventory(ArrayList products, ArrayList allParts) {
        this.setProducts(FXCollections.observableArrayList(products));
        this.setAllParts(FXCollections.observableArrayList(allParts));
        this.setProductSequence(products.size());
        this.setPartSequence(allParts.size());
    }
    
    /**
     * This returns a list of products.
     * @return ObservableList 
     */
    public ObservableList getProducts() {
        return products;
    }

    private void setProducts(ObservableList products) {
        this.products = products;
    }
    /**
     * This returns a list of parts.
     * @return ObservableList
     */
    public ObservableList getAllParts() {
        return allParts;
    }

    private void setAllParts(ObservableList allParts) {
        this.allParts = allParts;
    }
    
    /**
     * This adds a new product to the Inventory.
     * @param product 
     */
    public void addProduct(Product product) {
        ObservableList list = this.getProducts();
        product.setProductID(this.getProductSequence());
        list.add(product);
    }

    /**
     * This removes a product from Inventory by productId. 
     * @param productId
     * @return 
     */
    public boolean removeProduct(int productId) {
        ObservableList<Product> list = this.getProducts();
        Product p = this.lookupProduct(productId);
        return list.remove(p);
    }

    /**
     * This returns a single product by productId.
     * @param productId
     * @return 
     */
    public Product lookupProduct(int productId) {
        ObservableList<Product> list = this.getProducts();
        Product product = null;
        for (Product p : list) {
            if (p.getProductID() == productId) {
                product = p;
                break;
            }
        }
        return product;
    }

    
    /**
     * This is in the UML diagram but is not supported;
     * Instead use updateProduct(Product product).
     * @param productId 
     * @deprecated
     */
    @Deprecated
    public void updateProduct(int productId) {
        throw new UnsupportedOperationException("Not supported, use updateProduct(Product product)");
    }

    /**
     * This updates a product in Inventory.
     * @param product
     * @return 
     */
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

    /**
     * This adds a new part to Inventory. 
     * @param part 
     */
    public void addPart(Part part) {
        ObservableList list = this.getAllParts();
        part.setPartID(this.getPartSequence());
        list.add(part);
    }

    /**
     * This deletes a part from Inventory.
     * @param part
     * @return 
     */
    public boolean deletePart(Part part) {
        ObservableList<Part> list = this.getAllParts();
        return list.remove(part);
    }

    /**
     * This returns a single part by partId.
     * @param partId
     * @return 
     */
    public Part lookupPart(int partId) {
        ObservableList<Part> list = this.getAllParts();
        Part part = null;
        for (Part p : list) {
            if (p.getPartID() == partId) {
                part = p;
                break;
            }
        }
        return part;
    }

    /**
     * This is in the UML diagram but is not supported;
     * Instead use updatePart(Part part).
     * @param partId
     * @deprecated
     */
    @Deprecated
    public void updatePart(int partId) {
        throw new UnsupportedOperationException("Not supported, use updatePart(Part part)");
    }

    /**
     * This updates a part in Inventory.
     * @param part
     * @return 
     */
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

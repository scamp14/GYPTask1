/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wgu.c482.model;

/**
 *
 * @author scott
 */
public class Outsourced extends Part{
    private String companyName;

    public Outsourced(String companyName, int partID, String name, double price, int inStock, int min, int max) {
        super(partID, name, price, inStock, min, max);
        this.companyName = companyName;
    }

    
    public String getCompanyName() {
        return companyName;
    }

   
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    
    /*int getMachineId() {
        throw new UnsupportedOperationException("Not supported for Outsourced Part.");
    }

     
    void setMachineId(int machineId) {
        throw new UnsupportedOperationException("Not supported for Outsourced Part.");
    }*/
}

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
public class Inhouse extends Part{
    private int machineId;

    public Inhouse(int machineId, int partID, String name, double price, int inStock, int min, int max) {
        super(partID, name, price, inStock, min, max);
        this.machineId = machineId;
    }

   
    /*public String getCompanyName() {
        throw new UnsupportedOperationException("Not supported for InHouse Part."); 
    }

   
    public void setCompanyName(String companyName) {
        throw new UnsupportedOperationException("Not supported for InHouse Part."); 
    }*/
     
    public int getMachineId() {
        return this.machineId;
    }

   
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
    
}

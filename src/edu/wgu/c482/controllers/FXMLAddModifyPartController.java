/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wgu.c482.controllers;

import edu.wgu.c482.model.Inhouse;
import edu.wgu.c482.model.Outsourced;
import edu.wgu.c482.model.Part;
import edu.wgu.c482.service.Inventory;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author scott
 */
public class FXMLAddModifyPartController implements Initializable {

    private Inventory inventoryService;

    @FXML
    private Label mainLabel;
    @FXML
    private Label companyNameLabel;
    @FXML
    private TextField companyName;
    @FXML
    private Label machineIdLabel;
    @FXML
    private TextField machineID;
    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField inventory;
    @FXML
    private TextField price;
    @FXML
    private TextField max;
    @FXML
    private TextField min;
    
    @FXML
    private ToggleGroup partToggleGroup;
    
    @FXML
    private RadioButton radioInhouse;
   
    @FXML
    private RadioButton radioOutsourced;

    public RadioButton getRadioInhouse() {
        return radioInhouse;
    }

    public void setRadioInhouse(RadioButton radioInhouse) {
        this.radioInhouse = radioInhouse;
    }

    public RadioButton getRadioOutsourced() {
        return radioOutsourced;
    }

    public void setRadioOutsourced(RadioButton radioOutsourced) {
        this.radioOutsourced = radioOutsourced;
    }

    @FXML
    private Part part;

    @FXML
    Stage stage;
    
    

    public Stage getStage() {
        return stage;
    }

    public Label getCompanyNameLabel() {
        return companyNameLabel;
    }

    public void setCompanyNameLabel(Label companyNameLabel) {
        this.companyNameLabel = companyNameLabel;
    }

    public TextField getCompanyName() {
        return companyName;
    }

    public void setCompanyName(TextField companyName) {
        this.companyName = companyName;
    }

    public Label getMachineIdLabel() {
        return machineIdLabel;
    }

    public void setMachineIdLabel(Label machineIdLabel) {
        this.machineIdLabel = machineIdLabel;
    }

    public TextField getMachineID() {
        return machineID;
    }

    public void setMachineID(TextField machineID) {
        this.machineID = machineID;
    }

    public TextField getId() {
        return id;
    }

    public void setId(TextField id) {
        this.id = id;
    }

    public TextField getName() {
        return name;
    }

    public void setName(TextField name) {
        this.name = name;
    }

    public TextField getInventory() {
        return inventory;
    }

    public void setInventory(TextField inventory) {
        this.inventory = inventory;
    }

    public TextField getPrice() {
        return price;
    }

    public void setPrice(TextField price) {
        this.price = price;
    }

    public TextField getMax() {
        return max;
    }

    public void setMax(TextField max) {
        this.max = max;
    }

    public TextField getMin() {
        return min;
    }

    public void setMin(TextField min) {
        this.min = min;
    }

    public ToggleGroup getPartToggleGroup() {
        return partToggleGroup;
    }

    public void setPartToggleGroup(ToggleGroup partToggleGroup) {
        this.partToggleGroup = partToggleGroup;
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    /**
     * Initializes the controller class.
     */
    @FXML
    private void save() {
        RadioButton rb = (RadioButton) partToggleGroup.getSelectedToggle();
        String type = rb.getText();
        Part part = this.getPart();
        if (part != null) {
            
            if ("In-House".equals(type)) {

                part = new Inhouse(Integer.parseInt(machineID.textProperty().getValue()),
                        part.getPartID(), this.name.getText(), Double.parseDouble(price.getText()),
                        Integer.parseInt(inventory.getText()),
                        Integer.parseInt(min.getText()), Integer.parseInt(max.getText()));

            } else if ("Outsourced".equals(type)) {
                part = new Outsourced(companyName.getText(),
                        part.getPartID(), this.name.getText(), Double.parseDouble(price.getText()),
                        Integer.parseInt(inventory.getText()),
                        Integer.parseInt(min.getText()), Integer.parseInt(max.getText()));
            }
            /*
            TextField field = this.getName();
            part.setName(field.getText());
            field = this.getInventory();
            part.setInStock(Integer.parseInt(field.getText()));
            field = this.getPrice();
            part.setPrice(Double.parseDouble(field.getText()));
            field = this.getMax();
            part.setMax(Integer.parseInt(field.getText()));
            field = this.getMin();
            part.setMin(Integer.parseInt(field.getText()));
            if (part instanceof Inhouse) {
            field = this.getMachineID();
            ((Inhouse) part).setMachineId(Integer.parseInt(field.getText()));
            } else if (part instanceof Outsourced) {
                field = this.getCompanyName();
                ((Outsourced) part).setCompanyName(field.getText());
            }
            */
            
            
            
            inventoryService.updatePart(part);
        } else {
            if ("In-House".equals(type)) {

                part = new Inhouse(Integer.parseInt(machineID.textProperty().getValue()),
                        -1, this.name.getText(), Double.parseDouble(price.getText()),
                        Integer.parseInt(inventory.getText()),
                        Integer.parseInt(min.getText()), Integer.parseInt(max.getText()));

            } else if ("Outsourced".equals(type)) {
                part = new Outsourced(companyName.getText(),
                        -1, this.name.getText(), Double.parseDouble(price.getText()),
                        Integer.parseInt(inventory.getText()),
                        Integer.parseInt(min.getText()), Integer.parseInt(max.getText()));
            }

            System.out.println(part.toString());
            inventoryService.addPart(part);
        }

        Stage stage = this.getStage();
        stage.close();
    }

    @FXML
    private void disableMachineIdField(ActionEvent event) {

        this.companyName.setDisable(false);
        this.companyNameLabel.setDisable(false);
        this.machineID.setDisable(true);
        this.machineIdLabel.setDisable(true);
    }

    @FXML
    private void disableCompanyNameField(ActionEvent event) {
        this.companyName.setDisable(true);
        this.companyNameLabel.setDisable(true);
        this.machineID.setDisable(false);
        this.machineIdLabel.setDisable(false);
    }

    public Label getMainLabel() {
        return mainLabel;
    }

    public void setMainLabel(Label mainLabel) {
        System.out.println("set main lable " + mainLabel.getText());
        this.mainLabel = mainLabel;
    }

    void setInventoryService(Inventory inventoryService) {
        this.inventoryService = inventoryService;
    }

    public Inventory getInventoryService() {
        return inventoryService;
    }

    void setPart(Part part) {
        this.part = part;
    }

    public Part getPart() {
        return part;
    }

    void initialize() {
        Part part = this.getPart();

        if (part != null) {
            RadioButton rbInhouse = this.getRadioInhouse();
            RadioButton rbOutsourced = this.getRadioOutsourced();
            TextField field = this.getName();
            field.setText(part.getName());
            field = this.getId();
            field.setText(String.valueOf(part.getPartID()));
            field = this.getInventory();
            field.setText(String.valueOf(part.getInStock()));
            field = this.getPrice();
            field.setText(String.valueOf(part.getPrice()));
            field = this.getMax();
            field.setText(String.valueOf(part.getMax()));
            field = this.getMin();
            field.setText(String.valueOf(part.getMin()));         
            if (part instanceof Inhouse) {
                field = this.getMachineID();
                field.setText(String.valueOf(((Inhouse)part).getMachineId()));
                this.disableCompanyNameField(null);
                
                rbInhouse.setSelected(true);
                //rbInhouse.setDisable(true); 
                //rbOutsourced.setDisable(true);
            }
            if (part instanceof Outsourced) {
                field = this.getCompanyName();
                field.setText(((Outsourced) part).getCompanyName());
                this.disableMachineIdField(null);
                rbOutsourced.setSelected(true);
                //rbOutsourced.setDisable(true); 
                //rbInhouse.setDisable(true);
            }
        }

    }

    @FXML
    private void cancel() {
        Stage stage = this.getStage();
        stage.close();
    }

    void setStage(Stage stage) {
        this.stage = stage;
    }

}

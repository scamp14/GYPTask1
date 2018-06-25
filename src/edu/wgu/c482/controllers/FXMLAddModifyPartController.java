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
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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

    @FXML
    private Part part;

    @FXML
    private Stage stage;

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

    private Stage getStage() {
        return stage;
    }

    private Label getCompanyNameLabel() {
        return companyNameLabel;
    }

    private void setCompanyNameLabel(Label companyNameLabel) {
        this.companyNameLabel = companyNameLabel;
    }

    private TextField getCompanyName() {
        return companyName;
    }

    private void setCompanyName(TextField companyName) {
        this.companyName = companyName;
    }

    private Label getMachineIdLabel() {
        return machineIdLabel;
    }

    private void setMachineIdLabel(Label machineIdLabel) {
        this.machineIdLabel = machineIdLabel;
    }

    private TextField getMachineID() {
        return machineID;
    }

    private void setMachineID(TextField machineID) {
        this.machineID = machineID;
    }

    private TextField getId() {
        return id;
    }

    private void setId(TextField id) {
        this.id = id;
    }

    private TextField getName() {
        return name;
    }

    private void setName(TextField name) {
        this.name = name;
    }

    private TextField getInventory() {
        return inventory;
    }

    private void setInventory(TextField inventory) {
        this.inventory = inventory;
    }

    private TextField getPrice() {
        return price;
    }

    private void setPrice(TextField price) {
        this.price = price;
    }

    private TextField getMax() {
        return max;
    }

    private void setMax(TextField max) {
        this.max = max;
    }

    private TextField getMin() {
        return min;
    }

    private void setMin(TextField min) {
        this.min = min;
    }

    private ToggleGroup getPartToggleGroup() {
        return partToggleGroup;
    }

    private void setPartToggleGroup(ToggleGroup partToggleGroup) {
        this.partToggleGroup = partToggleGroup;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //No Operation
    }

    @FXML
    private void save() {
        RadioButton rb = (RadioButton) partToggleGroup.getSelectedToggle();
        String type = rb.getText();
        Part part = this.getPart();
        if (part != null) {
            /**
             * Update Existing Part
             */
            if ("In-House".equals(type)) {

                part = new Inhouse(Integer.parseInt(this.getMachineID().getText()),
                        part.getPartID(), this.getName().getText(), Double.parseDouble(this.getPrice().getText()),
                        Integer.parseInt(this.getInventory().getText()),
                        Integer.parseInt(this.getMin().getText()), Integer.parseInt(this.getMax().getText()));

            } else if ("Outsourced".equals(type)) {
                part = new Outsourced(this.getCompanyName().getText(),
                        part.getPartID(), this.getName().getText(), Double.parseDouble(this.getPrice().getText()),
                        Integer.parseInt(this.getInventory().getText()),
                        Integer.parseInt(this.getMin().getText()), Integer.parseInt(this.getMax().getText()));
            }
            inventoryService.updatePart(part);
        } else {
            /**
             * Add New Part
             */
            if ("In-House".equals(type)) {

                part = new Inhouse(Integer.parseInt(this.getMachineID().getText()),
                        -1, this.getName().getText(), Double.parseDouble(this.getPrice().getText()),
                        Integer.parseInt(this.getInventory().getText()),
                        Integer.parseInt(this.getMin().getText()), Integer.parseInt(this.getMax().getText()));

            } else if ("Outsourced".equals(type)) {
                part = new Outsourced(this.getCompanyName().getText(),
                        -1, this.getName().getText(), Double.parseDouble(this.getPrice().getText()),
                        Integer.parseInt(this.getInventory().getText()),
                        Integer.parseInt(this.getMin().getText()), Integer.parseInt(this.getMax().getText()));
            }
            inventoryService.addPart(part);
        }

        /**
         * Close the Add/Modify Part screen and return to the Main Screen.
         */
        Stage stage = this.getStage();
        stage.close();
    }

    @FXML
    private void disableMachineIdField(ActionEvent event) {
        this.getCompanyName().setDisable(false);
        this.getCompanyNameLabel().setDisable(false);
        this.getMachineID().setDisable(true);
        this.getMachineIdLabel().setDisable(true);
    }

    @FXML
    private void disableCompanyNameField(ActionEvent event) {
        this.getCompanyName().setDisable(true);
        this.getCompanyNameLabel().setDisable(true);
        this.getMachineID().setDisable(false);
        this.getMachineIdLabel().setDisable(false);
    }

    /**
     * This returns the main label of the Add/Modify Parts screen. This is used
     * by the main controller.
     *
     * @return
     */
    public Label getMainLabel() {
        return mainLabel;
    }

    private void setMainLabel(Label mainLabel) {
        this.mainLabel = mainLabel;
    }

    /**
     * This sets the Inventory service for this controller.
     *
     * @param inventoryService
     */
    public void setInventoryService(Inventory inventoryService) {
        this.inventoryService = inventoryService;
    }

    private Inventory getInventoryService() {
        return inventoryService;
    }

    /**
     * This sets Part that is used in the Add/Modify Part screens.
     *
     * @param part
     */
    public void setPart(Part part) {
        this.part = part;
    }

    private Part getPart() {
        return part;
    }

    /**
     *
     * This initializes the Add/Modify Part screens
     */
    public void initialize() {
        Part part = this.getPart();
        /**
         * Populate the TextFields and Radio Buttons with Part data.
         */
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
                field.setText(String.valueOf(((Inhouse) part).getMachineId()));
                this.disableCompanyNameField(null);
                rbInhouse.setSelected(true);
            }
            if (part instanceof Outsourced) {
                field = this.getCompanyName();
                field.setText(((Outsourced) part).getCompanyName());
                this.disableMachineIdField(null);
                rbOutsourced.setSelected(true);
            }
        }
    }

    @FXML
    private void cancel() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Stage stage = this.getStage();
            stage.close();
        }
    }

    /**
     * This sets the Stage that is used in cancel and exit operations.
     *
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wgu.c482.controllers;

import edu.wgu.c482.controllers.FXMLAddModifyPartController;
import edu.wgu.c482.model.Part;
import edu.wgu.c482.service.Inventory;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author scott
 */
public class FXMLMainController implements Initializable {

    private Inventory inventoryService;

    @FXML
    private TableView partsTable;

    @FXML
    private TableColumn partIDColumn;

    @FXML
    private TableColumn partNameColumn;

    @FXML
    private TableColumn partInvColumn;

    @FXML
    private TableColumn partPriceColumn;

    @FXML
    private TableView productsTable;

    @FXML
    private TableColumn productIDColumn;

    @FXML
    private TableColumn productNameColumn;

    @FXML
    private TableColumn productInvColumn;

    @FXML
    private TableColumn productPriceColumn;

    @FXML
    private TextField partsFilter;

    @FXML
    private Button partSearchButton;

    private TextField partSearchTerm = new TextField();
    
    Stage stage;
    @FXML
    private Button addPartsButton;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public TextField getPartSearchTerm() {
        return this.partSearchTerm;
    }

    public void setPartSearchTerm(TextField partSearchTerm) {
        this.partSearchTerm = partSearchTerm;
    }

    public Button getPartSearchButton() {
        return partSearchButton;
    }

    public void setPartSearchButton(Button partSearchButton) {
        this.partSearchButton = partSearchButton;
    }

    public TextField getPartsFilter() {
        return partsFilter;
    }

    public void setPartsFilter(TextField partsFilter) {
        this.partsFilter = partsFilter;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void initialize() {
        //initialize
        this.initializePartsTable();
        this.initializeProductsTable();
    }

    private void showPartModal(ActionEvent event, Part part) throws Exception {
        System.out.println("You clicked me!" + event.toString());

        Button button = (Button) event.getSource();

        Stage modalStage = new Stage();
        FXMLLoader modalLoader = new FXMLLoader(getClass().getResource("/edu/wgu/c482/fxml/FXMLAddModifyPart.fxml"));
        Parent modalRoot = modalLoader.load();
        FXMLAddModifyPartController modalController = modalLoader.getController();
        String title = button.getText() + " Part";
        Label mainLabel = modalController.getMainLabel();
        mainLabel.setText(title);
        //get selected part and setPart in controller. 
        modalController.setInventoryService(this.getInventoryService());
        modalController.setPart(part);

        modalStage.setScene(new Scene(modalRoot));
        modalStage.setTitle(title);
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalController.setStage(modalStage);
        modalController.initialize();
        //modalStage.initOwner(btn1.getScene().getWindow());
        modalStage.showAndWait();

    }

    @FXML
    public void addPartModal(ActionEvent event) throws Exception {
        this.showPartModal(event, null);
    }

    @FXML
    public void modifyPartModal(ActionEvent event) throws Exception {
        TableView<Part> table = this.getPartsTable();
        Part part = table.getSelectionModel().getSelectedItem();
        this.showPartModal(event, part);
    }

    @FXML
    public void deletePart(ActionEvent event) throws Exception {
        TableView<Part> table = this.getPartsTable();
        Part part = table.getSelectionModel().getSelectedItem();
        Inventory service = this.getInventoryService();
        service.deletePart(part);    
    }

    @FXML
    private void exit(){
       this.getStage().close();
    }
            
            
    public void setInventoryService(Inventory inventoryService) {
        this.inventoryService = inventoryService;
    }

    public Inventory getInventoryService() {
        return inventoryService;
    }

    private void initializePartsTable() {
        //columns
        TableColumn partIdCol = this.getPartIDColumn();
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("partID"));

        TableColumn partNameCol = this.getPartNameColumn();
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn partInvCol = this.getPartInvColumn();
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("inStock"));

        TableColumn partPriceCol = this.getPartPriceColumn();
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //data service
        Inventory service = this.getInventoryService();
        ObservableList<Part> list = service.getAllParts();

        //Filter
        FilteredList<Part> filteredList = new FilteredList<>(list, p -> true);

        TextField searchTerm = this.getPartSearchTerm();
        searchTerm.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredList.setPredicate(part -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (part.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if ((String.valueOf(part.getPartID()).toLowerCase()).contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(part.getInStock()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(part.getPrice()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                return false;

            });
        });

        TableView table = this.getPartsTable();
        table.setItems(filteredList);
    }

    private void initializeProductsTable() {
        Inventory service = this.getInventoryService();
        ObservableList<Part> list = FXCollections.observableArrayList(service.getProducts());
        TableColumn productIdCol = this.getProductIDColumn();
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("productID"));

        TableColumn productNameCol = this.getProductNameColumn();
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn productInvCol = this.getProductInvColumn();
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("inStock"));

        TableColumn productPriceCol = this.getProductPriceColumn();
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableView table = this.getProductsTable();
        table.setItems(list);
    }

    @FXML
    private void searchParts() {

        System.out.println(" search Parts");
        TextField searchTerm = this.getPartSearchTerm();
        TextField partsFilterTextField = this.getPartsFilter();

        searchTerm.setText(partsFilterTextField.getText());
    }


    public TableView getPartsTable() {
        return partsTable;
    }

    public TableColumn getPartIDColumn() {
        return partIDColumn;
    }

    public TableColumn getPartNameColumn() {
        return partNameColumn;
    }

    public TableColumn getPartInvColumn() {
        return partInvColumn;
    }

    public TableColumn getPartPriceColumn() {
        return partPriceColumn;
    }

    public TableView getProductsTable() {
        return productsTable;
    }

    public TableColumn getProductIDColumn() {
        return productIDColumn;
    }

    public TableColumn getProductNameColumn() {
        return productNameColumn;
    }

    public TableColumn getProductInvColumn() {
        return productInvColumn;
    }

    public TableColumn getProductPriceColumn() {
        return productPriceColumn;
    }

}

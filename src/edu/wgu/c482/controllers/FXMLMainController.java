/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wgu.c482.controllers;

import edu.wgu.c482.model.Part;
import edu.wgu.c482.model.Product;
import edu.wgu.c482.service.Inventory;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    private TextField productsFilter;

    @FXML
    private Button partSearchButton;
    @FXML
    private TextField partSearchTerm = new TextField();
    @FXML
    private TextField productSearchTerm = new TextField();
    @FXML
    Stage stage;

    @FXML
    private Button addPartsButton;

    private Stage getStage() {
        return stage;
    }

    /**
     * This sets the Stage that is used in cancel and exit operations.
     *
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private TextField getPartSearchTerm() {
        return this.partSearchTerm;
    }

    private void setPartSearchTerm(TextField partSearchTerm) {
        this.partSearchTerm = partSearchTerm;
    }

    private Button getPartSearchButton() {
        return partSearchButton;
    }

    private void setPartSearchButton(Button partSearchButton) {
        this.partSearchButton = partSearchButton;
    }

    private TextField getPartsFilter() {
        return partsFilter;
    }

    private void setPartsFilter(TextField partsFilter) {
        this.partsFilter = partsFilter;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //No operation.
    }

    /**
     * This initializes the main screen
     */
    public void initialize() {
        //initialize tables
        this.initializePartsTable();
        this.initializeProductsTable();
    }

    private void showPartModal(ActionEvent event, Part part) throws Exception {
        Button button = (Button) event.getSource();
        String title = button.getText() + " Part";
        FXMLLoader modalLoader = new FXMLLoader(getClass().getResource("/edu/wgu/c482/fxml/FXMLAddModifyPart.fxml"));
        Parent modalRoot = modalLoader.load();
        Stage modalStage = new Stage();

        /**
         * This initializes the controller for the Add/Modify Part screens of
         * the application.
         */
        FXMLAddModifyPartController modalController = modalLoader.getController();
        Label mainLabel = modalController.getMainLabel();
        mainLabel.setText(title);
        modalController.setInventoryService(this.getInventoryService());
        modalController.setPart(part);
        modalStage.setScene(new Scene(modalRoot));
        modalStage.setTitle(title);
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalController.setStage(modalStage);
        modalController.initialize();

        /**
         * Show the applicable Add/Modify Part screen of the application.
         */
        modalStage.showAndWait();

    }

    @FXML
    private void addPartModal(ActionEvent event) throws Exception {
        this.showPartModal(event, null);
    }

    @FXML
    private void modifyPartModal(ActionEvent event) throws Exception {
        TableView<Part> table = this.getPartsTable();
        Part part = table.getSelectionModel().getSelectedItem();
        this.showPartModal(event, part);
    }

    @FXML
    private void deletePart(ActionEvent event) throws Exception {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirm Delete");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            TableView<Part> table = this.getPartsTable();
            Part part = table.getSelectionModel().getSelectedItem();
            Inventory service = this.getInventoryService();
            service.deletePart(part);
        }
    }

    @FXML
    private void deleteProduct(ActionEvent event) throws Exception {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirm Delete");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            TableView<Product> table = this.getProductsTable();
            Product product = table.getSelectionModel().getSelectedItem();
            Inventory service = this.getInventoryService();
            service.removeProduct(product.getProductID());
        }
    }

    private void showProductModal(ActionEvent event, Product product) throws Exception {
        Button button = (Button) event.getSource();
        String title = button.getText() + " Product";
        FXMLLoader modalLoader = new FXMLLoader(getClass().getResource("/edu/wgu/c482/fxml/FXMLAddModifyProduct.fxml"));
        Parent modalRoot = modalLoader.load();
        Stage modalStage = new Stage();

        /**
         * This initializes the controller for the Add/Modify Product screens of
         * the application.
         */
        FXMLAddModifyProductController modalController = modalLoader.getController();
        Label mainLabel = modalController.getMainLabel();
        mainLabel.setText(title);
        modalController.setInventoryService(this.getInventoryService());
        modalController.setProduct(product);
        modalStage.setScene(new Scene(modalRoot));
        modalStage.setTitle(title);
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalController.setStage(modalStage);
        modalController.initialize();

        /**
         * Show the applicable Add/Modify Product screen of the application.
         */
        modalStage.showAndWait();

    }

    @FXML
    private void addProductModal(ActionEvent event) throws Exception {
        this.showProductModal(event, null);
    }

    @FXML
    private void modifyProductModal(ActionEvent event) throws Exception {
        TableView<Product> table = this.getProductsTable();
        Product product = table.getSelectionModel().getSelectedItem();
        this.showProductModal(event, product);
    }

    @FXML
    private void exit() {
        this.getStage().close();
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
        /**
         * columns
         */
        TableColumn productIdCol = this.getProductIDColumn();
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("productID"));

        TableColumn productNameCol = this.getProductNameColumn();
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn productInvCol = this.getProductInvColumn();
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("inStock"));

        TableColumn productPriceCol = this.getProductPriceColumn();
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        /**
         * service
         */
        Inventory service = this.getInventoryService();
        ObservableList<Product> list = service.getProducts();

        /**
         * This provides the filter functionality for the Product table of the
         * main screen.
         */
        FilteredList<Product> filteredList = new FilteredList<>(list, p -> true);
        TextField searchTerm = this.getProductSearchTerm();
        searchTerm.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredList.setPredicate(product -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (product.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if ((String.valueOf(product.getProductID()).toLowerCase()).contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(product.getInStock()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(product.getPrice()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        /**
         * Populate the products table with data.
         */
        TableView table = this.getProductsTable();
        table.setItems(filteredList);
    }

    @FXML
    private void searchParts() {
        TextField searchTerm = this.getPartSearchTerm();
        TextField partsFilterTextField = this.getPartsFilter();
        searchTerm.setText(partsFilterTextField.getText());
    }

    @FXML
    private void searchProducts() {
        TextField searchTerm = this.getProductSearchTerm();
        TextField partsFilterTextField = this.getProductsFilter();
        searchTerm.setText(partsFilterTextField.getText());
    }

    private TableView getPartsTable() {
        return partsTable;
    }

    private TableColumn getPartIDColumn() {
        return partIDColumn;
    }

    private TableColumn getPartNameColumn() {
        return partNameColumn;
    }

    private TableColumn getPartInvColumn() {
        return partInvColumn;
    }

    private TableColumn getPartPriceColumn() {
        return partPriceColumn;
    }

    private TableView getProductsTable() {
        return productsTable;
    }

    private TableColumn getProductIDColumn() {
        return productIDColumn;
    }

    private TableColumn getProductNameColumn() {
        return productNameColumn;
    }

    private TableColumn getProductInvColumn() {
        return productInvColumn;
    }

    private TableColumn getProductPriceColumn() {
        return productPriceColumn;
    }

    private TextField getProductSearchTerm() {
        return productSearchTerm;
    }

    private TextField getProductsFilter() {
        return productsFilter;
    }

}

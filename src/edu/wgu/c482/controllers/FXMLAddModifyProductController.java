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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author scott
 */
public class FXMLAddModifyProductController implements Initializable {

    @FXML
    private Button partSearchButton;
    @FXML
    private TextField partsFilter;
    @FXML
    private TableView<?> partsTable;
    @FXML
    private TableColumn<?, ?> partIDColumn;
    @FXML
    private TableColumn<?, ?> partNameColumn;
    @FXML
    private TableColumn<?, ?> partInvColumn;
    @FXML
    private TableColumn<?, ?> partPriceColumn;
    @FXML
    private Button addPartsButton;
    @FXML
    private Label mainLabel;
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
    private TextField searchTerm = new TextField();
    @FXML
    private TableView<?> productPartsTable;
    @FXML
    private TableColumn<?, ?> partIDColumn1;
    @FXML
    private TableColumn<?, ?> partNameColumn1;
    @FXML
    private TableColumn<?, ?> partInvColumn1;
    @FXML
    private TableColumn<?, ?> partPriceColumn1;
    @FXML
    private Button deleteButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Product product;
    @FXML
    private Inventory inventoryService;

    @FXML
    private Stage stage;

    private Product getProduct() {
        return product;
    }

    private Inventory getInventoryService() {
        return inventoryService;
    }

    /**
     * This returns the main label of the Add/Modify Products screen. This is
     * used by the main controller.
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
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //No Operation
    }

    @FXML
    private void searchParts(ActionEvent event) {
        TextField searchTerm = this.getSearchTerm();
        TextField partsFilterTextField = this.getPartsFilter();
        searchTerm.setText(partsFilterTextField.getText());
    }

    @FXML
    private void add(ActionEvent event) {
        TableView productPartsTable = this.getProductPartsTable();
        TableView partsTable = this.getPartsTable();
        productPartsTable.getItems().add((Part) partsTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void delete(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirm Delete");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            TableView productPartsTable = this.getProductPartsTable();
            productPartsTable.getItems().remove((Part) productPartsTable.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    private void save(ActionEvent event) {
        Inventory service = this.getInventoryService();
        Product product = this.getProduct();
        ArrayList<Product> productParts = new ArrayList((List) this.getProductPartsTable().getItems());
        if (productParts.size() < 1) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Unable to Save!");
            alert.setContentText("Product must have at least 1 associated part.");
            alert.showAndWait();
            return;
        }
        if (product != null) {
            /**
             * Update Existing Product
             */
            product = new Product(productParts, product.getProductID(),
                    this.getName().getText(),
                    Double.parseDouble(this.getPrice().getText()),
                    Integer.parseInt(this.getInventory().getText()),
                    Integer.parseInt(this.getMin().getText()),
                    Integer.parseInt(this.getMax().getText()));

            service.updateProduct(product);
        } else {
            /**
             * Add New Product
             */
            product = new Product(productParts, -1, this.getName().getText(),
                    Double.parseDouble(this.getPrice().getText()),
                    Integer.parseInt(this.getInventory().getText()),
                    Integer.parseInt(this.getMin().getText()),
                    Integer.parseInt(this.getMax().getText()));
            service.addProduct(product);
        }

        /**
         * Close the Add/Modify Product screen and return to the Main Screen.
         */
        this.getStage().close();
    }

    @FXML
    private void cancel(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirm Cancel");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            this.getStage().close();
        }
    }

    public void setInventoryService(Inventory inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setStage(Stage modalStage) {
        this.stage = modalStage;
    }

    /**
     *
     * This initializes the Add/Modify Product screens
     */
    public void initialize() {
        /**
         * Populate TextFields with Product data.
         */
        Product product = this.getProduct();
        if (product != null) {
            TextField field = this.getName();
            field.setText(product.getName());
            field = this.getId();
            field.setText(String.valueOf(product.getProductID()));
            field = this.getInventory();
            field.setText(String.valueOf(product.getInStock()));
            field = this.getPrice();
            field.setText(String.valueOf(product.getPrice()));
            field = this.getMax();
            field.setText(String.valueOf(product.getMax()));
            field = this.getMin();
            field.setText(String.valueOf(product.getMin()));
        }
        /**
         * Initialize tables
         */
        this.initializePartsTable();
        this.initializeProductPartsTable();
    }

    private void initializePartsTable() {
        /**
         * columns
         */
        TableColumn partIdCol = this.getPartIDColumn();
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("partID"));

        TableColumn partNameCol = this.getPartNameColumn();
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn partInvCol = this.getPartInvColumn();
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("inStock"));

        TableColumn partPriceCol = this.getPartPriceColumn();
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        /**
         * service
         */
        Inventory service = this.getInventoryService();
        ObservableList<Part> list = service.getAllParts();

        /**
         * This provides the filter functionality for the Part table of the
         * Product screen.
         */
        FilteredList<Part> filteredList = new FilteredList<>(list, p -> true);
        TextField searchTerm = this.getSearchTerm();
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

        /**
         * Populate the parts table with data.
         */
        TableView table = this.getPartsTable();
        table.setItems(filteredList);
    }

    private void initializeProductPartsTable() {
        /**
         * columns
         */
        TableColumn partIdCol = this.getPartIDColumn1();
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("partID"));

        TableColumn partNameCol = this.getPartNameColumn1();
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn partInvCol = this.getPartInvColumn1();
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("inStock"));

        TableColumn partPriceCol = this.getPartPriceColumn1();
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        /**
         * service
         */
        Inventory service = this.getInventoryService();
        Product product = this.getProduct();
        if (product != null) {
            /**
             * Populate the product parts table with data.
             */
            ObservableList list = FXCollections.observableArrayList(product.getAssociatedParts());
            TableView table = this.getProductPartsTable();
            table.setItems(list);
        }
    }

    private TableView<?> getPartsTable() {
        return partsTable;
    }

    private TextField getSearchTerm() {
        return this.searchTerm;
    }

    private TableColumn<?, ?> getPartIDColumn() {
        return partIDColumn;
    }

    private TableColumn<?, ?> getPartNameColumn() {
        return partNameColumn;
    }

    private TableColumn<?, ?> getPartInvColumn() {
        return partInvColumn;
    }

    private TableColumn<?, ?> getPartPriceColumn() {
        return partPriceColumn;
    }

    private TableColumn<?, ?> getPartIDColumn1() {
        return partIDColumn1;
    }

    private TableColumn<?, ?> getPartNameColumn1() {
        return partNameColumn1;
    }

    private TableColumn<?, ?> getPartInvColumn1() {
        return partInvColumn1;
    }

    private TableColumn<?, ?> getPartPriceColumn1() {
        return partPriceColumn1;
    }

    private TextField getPartsFilter() {
        return partsFilter;
    }

    private TextField getId() {
        return id;
    }

    private TextField getName() {
        return name;
    }

    private TextField getInventory() {
        return inventory;
    }

    private TextField getPrice() {
        return price;
    }

    private TextField getMax() {
        return max;
    }

    private TextField getMin() {
        return min;
    }

    private TableView<?> getProductPartsTable() {
        return productPartsTable;
    }

    private Stage getStage() {
        return stage;
    }

}

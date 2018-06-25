/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wgu.c482;

import edu.wgu.c482.controllers.FXMLAddModifyPartController;
import edu.wgu.c482.controllers.FXMLMainController;
import edu.wgu.c482.model.Inhouse;
import edu.wgu.c482.model.Outsourced;
import edu.wgu.c482.model.Product;
import edu.wgu.c482.service.Inventory;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author scott
 */
public class GYPTask1 extends Application {
   
    @Override
    public void start(Stage stage) throws Exception {
        
       
        /**
         * This is a list of parts data used to seed the Inventory.
         */
        ArrayList allParts = new ArrayList() {
            {
                //Inhouse
                add(new Inhouse(100, 1, "Part 1", 10.00, 20, 1, 12));
                add(new Inhouse(100, 2, "Part 2", 20.00, 20, 1, 12));
                add(new Inhouse(100, 3, "Part 3", 30.00, 20, 1, 12));
                //Outsourced
                add(new Outsourced("Acme", 4, "Part 4", 40.00, 20, 1, 12));

            }
        };

        /**
         * This is a list of product data used to seed the Inventory.
         */
        ArrayList products = new ArrayList() {
            {
                add(
                        new Product(new ArrayList() {{add(allParts.get(0));add(allParts.get(3));}}, 1, "Acme Bundle", 100.00, 2, 1, 12)
                );
            }
        };
        
        /**
         * This creates a new instance of the Inventory Service
         */
        Inventory inventoryService = new Inventory(products, allParts);
        
        FXMLLoader rootLoader = new FXMLLoader(getClass().getResource("/edu/wgu/c482/fxml/FXMLMain.fxml"));
        Parent root = rootLoader.load();  
        
        /**
         * This initializes the controller for the main screen of the application.
         */
        FXMLMainController controller = rootLoader.getController();
        controller.setInventoryService(inventoryService);
        controller.setStage(stage);
        controller.initialize();
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        
        /**
         * Show the main screen of the application. 
         */
        stage.show(); 
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

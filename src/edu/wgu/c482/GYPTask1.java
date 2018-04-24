/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wgu.c482;

import javafx.application.Application;
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
        Parent root = FXMLLoader.load(getClass().getResource("/edu/wgu/c482/main/FXMLMain.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
   Stage  modalStage = new Stage();
   Parent modalRoot = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
   modalStage.setScene(new Scene(modalRoot));
   modalStage.setTitle("My modal window");
   modalStage.initModality(Modality.APPLICATION_MODAL);
   //modalStage.initOwner(btn1.getScene().getWindow());
   modalStage.showAndWait();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

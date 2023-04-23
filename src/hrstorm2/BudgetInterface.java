/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hrstorm2;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author ihebt
 */
public class BudgetInterface extends Application {
    
    @Override
    public void start(Stage primaryStage) {
      Parent root = null;
        try {
    
             
             //root = FXMLLoader.load(getClass().getResource("/gui/AfficherDepense.fxml"));
               root = FXMLLoader.load(getClass().getResource("/gui/AfficherBudget.fxml"));
                 
                  
        } catch (IOException ex) {
            Logger.getLogger( BudgetInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root, 900, 900);
        
        primaryStage.setTitle("HRstorm");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

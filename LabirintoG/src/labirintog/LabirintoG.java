/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirintog;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Utente
 */
public class LabirintoG extends Application {

    public static Scene scene;
    public static Labirinto lab;
    public static Stage stage;
    
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;

        Parent root;
        root = FXMLLoader.load(getClass().getResource("GUI.fxml"));

        stage.setTitle("Labirinto");
        scene = new Scene(root, 970, 720);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}

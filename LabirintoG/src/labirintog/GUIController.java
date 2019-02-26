/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirintog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Utente
 */
public class GUIController implements Initializable {

    @FXML
    private SubScene subscene;
    @FXML
    private Label CREAZIONE;
    @FXML
    private Label CREAZIONE0;
    @FXML
    private TextField CREAZNumRighe;
    @FXML
    private TextField CREAZNumColonne;
    @FXML
    private Label CREAZIONE1;
    @FXML
    private Button CREAZBottoneCrea;
    @FXML
    private Label UTILITY;
    @FXML
    private Label UTILITY1;
    @FXML
    private Button UTILSALVA;
    @FXML
    private Button UTILcarica;
    @FXML
    private Label UTILFileSelezionato;
    @FXML
    private Label LABIRINTO;
    @FXML
    private Button LABRigenera;
    @FXML
    private Button LABreset;
    @FXML
    private Label LAB0;
    @FXML
    private Label LAB1;
    @FXML
    private Button LABrisolvi;
    @FXML
    private Label LAB2;
    @FXML
    private RadioButton UTILAbilita;
    @FXML
    private RadioButton LABAbilita;
    
    private Labirinto labirinto;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LabirintoG.lab = labirinto;
        noShowLab(true);
        noShowUtil(true);
        LABAbilita.setDisable(true);
        UTILAbilita.setDisable(true);
    }

    private void noShowUtil(boolean b) {
        if (!b) {
            UTILITY1.setText("Salva il labirinto");
        }
        UTILFileSelezionato.setDisable(b);
        UTILITY.setDisable(b);
        UTILITY1.setDisable(b);
        UTILSALVA.setDisable(b);
        UTILcarica.setDisable(b);
    }

    private void noShowCreaz(boolean b) {
        CREAZBottoneCrea.setDisable(b);
        CREAZIONE.setDisable(b);
        CREAZIONE0.setDisable(b);
        CREAZIONE1.setDisable(b);
        CREAZNumColonne.setDisable(b);
        CREAZNumRighe.setDisable(b);
    }

    private void noShowLab(boolean b) {
        LAB0.setDisable(b);
        LAB1.setDisable(b);
        LAB2.setDisable(b);
        LABIRINTO.setDisable(b);
        LABRigenera.setDisable(b);
        LABreset.setDisable(b);
        LABrisolvi.setDisable(b);
    }

    @FXML
    private void creaLabirinto(ActionEvent event) {
        String col = CREAZNumColonne.getText(), row = CREAZNumRighe.getText();
        try {
            int NumRighe = Integer.parseInt(row);
            int NumCol = Integer.parseInt(col);
            if (NumRighe * NumCol > 0) {
                System.out.println("Labirinto " + row + "x" + col);
                noShowCreaz(true);
                LABAbilita.setDisable(false);
                UTILAbilita.setDisable(false);
                labirinto = new Labirinto(NumCol, NumRighe, (int) subscene.getWidth(), (int) subscene.getHeight());
                subscene.setRoot(labirinto.render());
                LabirintoG.scene.setOnKeyTyped(e -> {
                    labirinto.comunica(e.getCharacter().toUpperCase());
                });
            }
        } catch (NumberFormatException | NullPointerException e) {
            e.printStackTrace();
            System.out.println("Input non validi");
        }
    }

    @FXML
    private void salva(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Salva labirinto");
        File lab = fc.showSaveDialog(LabirintoG.stage);
        labirinto.save(lab);
        UTILITY1.setText("Labirinto salvato");
    }

    @FXML
    private void leggiDaFile(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Carica labirinto");
        File lab = fc.showOpenDialog(LabirintoG.stage);
        try {
            labirinto.getFromSave(lab);
            labirinto.reset();
        } catch (IOException ex) {
            System.out.println("Errore");
        }
    }

    @FXML
    private void rigenera(ActionEvent event) {
        labirinto.reset();
    }

    @FXML
    private void reset(ActionEvent event) {
        labirinto.restart();
    }

    @FXML
    private void risolvi(ActionEvent event) {

    }

    @FXML
    private void abilitaUtility(ActionEvent event) {
        if (UTILITY1.isDisabled()) {
            noShowUtil(false);
            UTILAbilita.setText("Disabilita");
        } else {
            noShowUtil(true);
            UTILAbilita.setText("Abilita");
        }
    }

    @FXML
    private void abilitaLabirinto(ActionEvent event) {
        if (LABRigenera.isDisabled()) {
            LABAbilita.setText("Disabilita");
            noShowLab(false);
        } else {
            LABAbilita.setText("Abilita");
            noShowLab(true);
        }
    }
}

package sample;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller {
    public ObservableList list = FXCollections.observableArrayList();

    // Mode button
    public RadioButton hexMode;
    public RadioButton decMode;
    public RadioButton binMode;

    // Mode
    public String oldMode = "";
    public String currentMode = "DEC"; // The decimal is selected by default
    

    // Fields
    public TextField address;
    public TextField mask;
    public TextField network;
    public TextField broadcast;
    public TextField higher;
    public TextField lower;
    public TextField naa;


    public void initialize() {

    }

    @FXML
    public void stop(ActionEvent event) {
        System.exit(0);
    }

    private void warnMessage(String text, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Attention !");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public void searchConfig(ActionEvent event) {
        warnMessage("Cette option n'est pas encore disponible", Alert.AlertType.ERROR);
    }

    public void calculConfig(ActionEvent event) {
        warnMessage("Cette option n'est pas encore disponible", Alert.AlertType.ERROR);
    }

    public void modeSelected(ActionEvent event) {

    }

    public void newMode(ActionEvent event) {
        this.oldMode = this.currentMode;
        this.currentMode = this.getSelectedMode();

        if (!this.fieldsEmpty()) {
            System.out.println("NON-VIDE");
        }

        System.out.println("VIDE");
    }

    private boolean fieldsEmpty() {
        TextField[] fields = {this.address, this.mask, this.broadcast, this.network, this.lower, this.higher};

        for (int i = 0; i < fields.length; i++) {
            CharSequence content = fields[i].getCharacters();
            System.out.println("content: " + content);
            /*
            if (content.) {
                //String content = (String) fields[i].getCharacters();
                System.out.println(fields[i].getId() + " : " );
                //if (!content.equals("")) {
                //    return false;
                //}
            }

             */
        }
        
        return true;
    }

    public String getSelectedMode() {
        if (hexMode.isSelected()) {
            return "HEX";
        } else if (decMode.isSelected()) {
            return  "DEC";
        } else if (binMode.isSelected()) {
            return  "BIN";
        }
        return null;
    }
}

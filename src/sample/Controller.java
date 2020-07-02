package sample;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.sound.midi.Soundbank;
import java.net.URL;
import java.util.ArrayList;
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

    // Will store errors
    public ArrayList<String> errors;


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

        ArrayList<TextField> noEmptyFields = this.fieldsEmpty();
        if (!noEmptyFields.isEmpty()) {
            convertIntoNewMode(noEmptyFields, this.currentMode, this.oldMode);
        }
    }

    public void convertIntoNewMode(ArrayList<TextField> noEmptyFields, String currentMode, String oldMode) {
        /**
         * Convert an address into the current mode
         */
        this.errors = new ArrayList<String>();
        for (TextField field:
             noEmptyFields) {
            String address = field.getCharacters().toString();
            if (checkAddress(address, oldMode)) {
                System.out.println("Good address");
            } else {
                errors.add(field.getId());
                errors.add("Not valable address");
            }
        }
    }

    private boolean checkAddress(String address, String mode) {
        /**
         * Check the address format
         * !!! No mater if it is a mask or not !!!
         */
        if (mode.equals("HEX")) {
            try {
                String[] splitAddress = address.split(":");
                if (splitAddress.length != 4) { return false; }
                for (String bte: splitAddress) {
                    try {
                        if (!(0 <= Integer.parseInt(bte, 16) && Integer.parseInt(bte, 16) <= 255)) {
                            return false;
                        }
                    } catch (Exception e) {
                        return false;
                    }
                }
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            if (mode.equals("BIN")) {
                try {
                    String[] splitAddress = address.split("\\.");
                    if (splitAddress.length != 4) { return false; }
                    for (String bte: splitAddress) {
                        try {
                            if (!(0 <= Integer.parseInt(bte, 2) && Integer.parseInt(bte, 2) <= 255)) {
                                return false;
                            }
                        } catch (Exception e) {
                            return false;
                        }
                    }
                    return true;
                } catch (Exception e) {
                    return false;
                }
            } else if (mode.equals("DEC")) {
                try {
                    String[] splitAddress = address.split("\\.");
                    if (splitAddress.length != 4) { return false; }
                    for (String bte: splitAddress) {
                        try {
                            if (!(0 <= Integer.parseInt(bte) && Integer.parseInt(bte) <= 255)) {
                                return false;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            return false;
                        }
                    }
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            } else {
                throw new IllegalArgumentException("WRONG MODE");
            }
        }
    }

    public ArrayList<TextField> fieldsEmpty() {
        /**
         * Check if fields are empty. If it is not place the field into a arraylist.
         */
        TextField[] fields = {this.address, this.mask, this.broadcast, this.network, this.lower, this.higher};
        ArrayList<TextField> textFieldArrayList = new ArrayList<TextField>();
        for (TextField field: fields) {
            CharSequence content = field.getCharacters();
            if (content.length() != 0) {
                textFieldArrayList.add(field);
            }
        }
        return textFieldArrayList;
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

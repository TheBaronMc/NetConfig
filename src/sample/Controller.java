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
    public ArrayList<String> errors = new ArrayList<String>();


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
        CharSequence address = this.address.getCharacters();
        CharSequence mask = this.mask.getCharacters();
        String strAddress;
        String strMask;
        NetConfig netConfig;

        if ((address.length() ==0 ) || (mask.length() == 0)) {
            System.out.println("Mask and Address length problem");
            return;
        }

        strAddress = address.toString();
        strMask = mask.toString();

        if (checkAddress(strAddress, this.currentMode) && checkMask(strMask, this.currentMode)) {
            if (this.currentMode.equals("DEC")) {
                netConfig = new NetConfig(strAddress, strMask);
                broadcast.setText(netConfig.getBroadcast());
                network.setText(netConfig.getNetwork());
                higher.setText(netConfig.getHigher());
                lower.setText(netConfig.getLower());
            } else {
                netConfig = new NetConfig(convert(strAddress, 10, modeStringToInt(this.currentMode)), convert(strMask, 10, modeStringToInt(this.currentMode)));
                broadcast.setText(convert(netConfig.getBroadcast(), modeStringToInt(this.currentMode),10 ));
                network.setText(convert(netConfig.getNetwork(), modeStringToInt(this.currentMode), 10));
                higher.setText(convert(netConfig.getHigher(), modeStringToInt(this.currentMode), 10));
                lower.setText(convert(netConfig.getLower(), modeStringToInt(this.currentMode), 10));
            }
        } else {
            System.out.println("Mask and Address dosen't pass the check");
            return;
        }
        this.naa.setText(Double.toString(netConfig.getNbAvailable()));
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
        this.errors.clear();
        // ADDRESS PART
        for (TextField field:
             noEmptyFields) {
            String address = field.getCharacters().toString();
            if (checkAddress(address, oldMode) && !(field.getId().equals(this.mask.getId())) ) {
                System.out.println("Good address");
                String convertedAddress = convert(address, modeStringToInt(currentMode), modeStringToInt(oldMode));
                field.setText(convertedAddress);
            } else if (field.getId().equals(this.mask.getId())) { // in that the address is the mask
                if (checkMask(address, oldMode)) {
                    System.out.println("MASK : OK");
                    String convertedAddress = convert(address, modeStringToInt(currentMode), modeStringToInt(oldMode));
                    field.setText(convertedAddress);
                } else {
                    errors.add(field.getId());
                    errors.add("Not valable mask");
                }
            } else {
                errors.add(field.getId());
                errors.add("Not valable address");
            }
        }
        // MASK PART

        // ERROR PART
        if (errors.size() != 0) {
            String errorMessage = "Some values haven't been convert :\n ";
            for (int i = 0; i < this.errors.size(); i = i + 2) {
                errorMessage = errorMessage + errors.get(i) + " : " + errors.get(i + 1) + "\n ";
            }
            warnMessage(errorMessage, Alert.AlertType.WARNING);
        }
    }

    private String convert(String address, int currentMode, int oldMode) {
        String[] splitAddress;
        String[] convertedAddressArray = new String[4];
        String convertedAddress = "";

        if (oldMode != 16) {
            splitAddress = address.split("\\.");
        } else {
            splitAddress = address.split(":");
        }

        for (int i = 0; i < splitAddress.length; i++) {
            if (currentMode == 10) {
                convertedAddressArray[i] = Integer.toString(Integer.parseInt(splitAddress[i], oldMode));
            } else if (currentMode == 16) {
                convertedAddressArray[i] = Integer.toHexString(Integer.parseInt(splitAddress[i], oldMode));
            } else if (currentMode == 2) {
                convertedAddressArray[i] = Integer.toBinaryString(Integer.parseInt(splitAddress[i], oldMode));
            }
        }

        if (currentMode == 2) {
            for (int i = 0; i < convertedAddressArray.length; i++) {
                while (convertedAddressArray[i].length() < 8) {
                    convertedAddressArray[i] = "0" + convertedAddressArray[i];
                }
            }
        } else if (currentMode == 16) {
            for (int i = 0; i < convertedAddressArray.length; i++) {
                if (convertedAddressArray[i].length() == 1) {
                    convertedAddressArray[i] = "0" + convertedAddressArray[i];
                }
            }
        }

        for (int i = 0; i < convertedAddressArray.length; i++) {
            if (currentMode != 16) {
                convertedAddress = convertedAddress + convertedAddressArray[i] + ".";
            } else {
                convertedAddress = convertedAddress + convertedAddressArray[i] + ":";
            }
        }

        return convertedAddress.substring(0, (convertedAddress.length()-1));
    }

    private boolean checkMask(String mask, String oldMode) {
        /**
         * Check if the mask in param is a valable one
         */
        String[] splitMask;
        String[] binMask = new String[4];

        if (oldMode.equals("HEX")) {
            splitMask = mask.split(":");
            if (splitMask.length != 4) {
                return false;
            }
            for (int i=0; i < splitMask.length; i++) {
                try {
                    if (!(0 <= Integer.parseInt(splitMask[i], 16) && Integer.parseInt(splitMask[i], 16) <= 255)) {
                        return false;
                    }
                } catch (Exception e) {
                    return false;
                }
                binMask[i] = Integer.toBinaryString(Integer.parseInt(splitMask[i], 16));
            }
        } else {
            splitMask = mask.split("\\.");
            if (splitMask.length != 4) {
                return false;
            }
            if (oldMode.equals("DEC")) {
                for (int i=0; i < splitMask.length; i++) {
                    try {
                        if (!(0 <= Integer.parseInt(splitMask[i]) && Integer.parseInt(splitMask[i]) <= 255)) {
                            return false;
                        }
                    } catch (Exception e) {
                        return false;
                    }
                    binMask[i] = Integer.toBinaryString(Integer.parseInt(splitMask[i]));
                }
            } else if (oldMode.equals("BIN")) {
                for (int i=0; i < splitMask.length; i++) {
                    try {
                        if (!(0 <= Integer.parseInt(splitMask[i], 2) && Integer.parseInt(splitMask[i], 2) <= 255)) {
                            return false;
                        }
                    } catch (Exception e) {
                        return false;
                    }
                }
                binMask = splitMask;
            }
        }

        for (int i=0; i < binMask.length; i++) {
            while (binMask[i].length() < 8) {
                binMask[i] = "0" + binMask[i];
            }
        }
        if (binMask[0].startsWith("0")) {
            return false;
        }

        // LAST CHECK
        boolean x = false;
        for (String elt: binMask) {
            for (char bit: elt.toCharArray()) {
                if (bit == '1' && x) {
                    return false;
                } else if (bit != '1') {
                    x = true;
                }
            }
        }

        return true;
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

    public int modeStringToInt(String mode) {
        if (mode == "DEC") {
            return 10;
        } else if (mode == "HEX") {
            return 16;
        } else if (mode == "BIN") {
            return 2;
        } else {
            throw new IllegalArgumentException();
        }
    }
}

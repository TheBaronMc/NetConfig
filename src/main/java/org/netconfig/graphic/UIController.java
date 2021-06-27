package org.netconfig.graphic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import org.netconfig.model.NetConfig;
import org.netconfig.model.IPv4Format;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class UIController implements PropertyChangeListener {

    // Mode button
    @FXML
    public RadioButton hexMode;
    @FXML
    public RadioButton decMode;
    @FXML
    public RadioButton binMode;

    // Fields
    @FXML
    public TextField address;
    @FXML
    public TextField mask;
    @FXML
    public TextField network;
    @FXML
    public TextField broadcast;
    @FXML
    public TextField higher;
    @FXML
    public TextField lower;
    @FXML
    public TextField naa;

    private NetConfig model;

    public UIController(NetConfig model) {
        this.model = model;
    }

    public void setModel(NetConfig model) {
        this.model = model;
        this.model.addPropertyChangeListener(this);
    }

    @FXML
    private void setMask(KeyEvent keyEvent) {
        try {
            this.model.setMask(this.mask.getText(), this.getSelectedMode());
            this.mask.setStyle("-fx-text-inner-color: black;");
        } catch (Exception e) {
            System.out.println("error" + this.mask.getText());
            this.mask.setStyle("-fx-text-inner-color: red;");
        }
    }

    @FXML
    private void setAddress(KeyEvent keyEvent) {
        try {
            this.model.setAddress(this.address.getText(), this.getSelectedMode());
            this.address.setStyle("-fx-text-inner-color: black;");
        } catch (Exception e) {
            System.out.println("error" + this.address.getText());
            this.address.setStyle("-fx-text-inner-color: red;");
        }
    }

    @FXML
    public void stop(ActionEvent event) {
        System.exit(0);
    }


    public void newMode(ActionEvent event) {
        int base = this.getSelectedMode();

        try {
            this.address.setText(this.model.getAddress(base));
            this.mask.setText(this.model.getMask(base));
            this.network.setText(this.model.getNetwork(base));
            this.broadcast.setText(this.model.getBroadcast(base));
            this.higher.setText(this.model.getHigher(base));
            this.lower.setText(this.model.getLower(base));
        } catch (Exception ignored) {}
    }


    public int getSelectedMode() {
        if (hexMode.isSelected()) {
            return IPv4Format.BASE16;
        } else if (decMode.isSelected()) {
            return IPv4Format.BASE10;
        } else {
            return IPv4Format.BASE2;
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        int base = this.getSelectedMode();

        this.network.setText(this.model.getNetwork(base));
        this.broadcast.setText(this.model.getBroadcast(base));
        this.higher.setText(this.model.getHigher(base));
        this.lower.setText(this.model.getLower(base));
        this.naa.setText(String.valueOf((int) this.model.getAvailable()));
    }
}

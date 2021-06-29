package org.netconfig.graphic;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.DirectoryChooser;
import org.netconfig.App;
import org.netconfig.model.NetConfig;
import org.netconfig.model.IPv4Format;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class UIController implements PropertyChangeListener {

    // Mode button
    @FXML
    private RadioButton hexMode;
    @FXML
    private RadioButton decMode;
    @FXML
    private RadioButton binMode;

    // Fields
    @FXML
    private TextField address;
    @FXML
    private TextField mask;
    @FXML
    private TextField network;
    @FXML
    private TextField broadcast;
    @FXML
    private TextField higher;
    @FXML
    private TextField lower;
    @FXML
    private TextField naa;

    @FXML
    private Button toJSON;

    private NetConfig model;

    private Stage stage;

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
            this.mask.setStyle("-fx-text-inner-color: red;");
        }
    }

    @FXML
    private void setAddress(KeyEvent keyEvent) {
        try {
            this.model.setAddress(this.address.getText(), this.getSelectedMode());
            this.address.setStyle("-fx-text-inner-color: black;");
        } catch (Exception e) {
            this.address.setStyle("-fx-text-inner-color: red;");
        }
    }

    @FXML
    public void stop(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    public void saveConfig(ActionEvent event) {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(toJSON.getScene().getWindow());
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            File f = new File(selectedDirectory.getPath() + File.separator +
                    "NetConfig-save_" + new Date().getTime() + ".json");
            System.out.println(f.getPath());
            try {
                String content = this.model.toJson();
                content = content.replace("{", "{\n");
                content = content.replace("}", "}\n");
                PrintWriter pw = new PrintWriter(f);
                pw.write(content);
                pw.close();
            } catch (IOException e) {

            }
        }
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

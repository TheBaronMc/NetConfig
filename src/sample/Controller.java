package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class Controller {
    public RadioButton modeBin;
    public RadioButton modeDec;
    public RadioButton modeHex;

    public ToggleGroup affichage;

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
        warnMessage("Cette option n'est pas encore disponible", Alert.AlertType.ERROR);
    }
}

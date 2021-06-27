package org.netconfig;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.netconfig.graphic.UIController;
import org.netconfig.model.NetConfig;


public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        NetConfig model = new NetConfig();
        UIController controller = new UIController(model);
        model.addPropertyChangeListener(controller);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/NetConfig.fxml"));
        loader.setController(controller);

        Parent root = loader.load();
        primaryStage.setTitle("NetConfig");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        // primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

package Winder.winderconfig;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ConfigFrame {
    private Stage stage;
    private Parent root;

    public static ConfigController searchController;

    public ConfigFrame (Stage rootStage) {
        try {
            stage = new Stage();
            stage.initOwner(rootStage);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/Config.fxml"));
            root = loader.load();
            searchController = loader.getController();
            searchController.SetStage(stage);
            stage.setTitle("Config");

            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
        } catch (Exception e) {e.printStackTrace();}

    }
}
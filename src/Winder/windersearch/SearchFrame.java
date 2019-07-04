package Winder.windersearch;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SearchFrame {
    private Stage stage;
    private Parent root;

    public static SearchController searchController;

    public SearchFrame (Stage rootStage) {
        try {
            stage = new Stage();
            stage.initOwner(rootStage);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/Search.fxml"));
            root = loader.load();
            searchController = loader.getController();
            searchController.SetStage(stage);
            stage.setTitle("Chat");

            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
        } catch (Exception e) {e.printStackTrace();}

    }
}
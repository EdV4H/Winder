package Winder.register;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RegisterFrame {
    private Stage stage;
    private Parent root;

    private RegisterController registerController = null;

    public RegisterFrame (Stage rootStage) {
        try {
            stage = new Stage();
            stage.initOwner(rootStage);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/Register.fxml"));
            root = loader.load();
            registerController = loader.getController();
            registerController.setStage(stage);
            stage.setTitle("Registration");

            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
        } catch (Exception e) {e.printStackTrace();}

    }
}
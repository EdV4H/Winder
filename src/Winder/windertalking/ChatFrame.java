package Winder.windertalking;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChatFrame {
    private Stage stage;
    private Parent root;

    public static ChatController chatController;

    public ChatFrame (Stage rootStage) {
        try {
            stage = new Stage();
            stage.initOwner(rootStage);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/Chat.fxml"));
            root = loader.load();
            chatController = loader.getController();
            chatController.setStage(stage);
            stage.setTitle("Registration");

            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
        } catch (Exception e) {e.printStackTrace();}

    }
}
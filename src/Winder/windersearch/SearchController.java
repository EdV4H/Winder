package Winder.windersearch;

import Winder.main.MainFrame;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SearchController {

    private Stage stage;

    private int op_id;
    private String[] op_info;

    @FXML
    private ImageView img_user;

    @FXML
    private Label label_name;

    @FXML
    private Label label_birth;

    @FXML
    private Label label_comment;

    @FXML
    private Button button_like;

    @FXML
    private Button button_dislike;

    @FXML
    void Dislike(MouseEvent event) {
        String msg = "DISLIKE " + op_id;
        MainFrame.client.out.println(msg);
        MainFrame.client.out.flush();
        MainFrame.client.out.println("NEXTINFO");
    }

    @FXML
    void Like(MouseEvent event) {
        String msg = "LIKE " + op_id;
        MainFrame.client.out.println(msg);
        MainFrame.client.out.flush();
        MainFrame.client.out.println("NEXTINFO");
    }

    @FXML
    void initialize () {
        MainFrame.client.out.println("NEXTINFO");
        MainFrame.client.out.flush();
    }

    public void SetStatus (int id, String name, String birth, String comment) {
        op_id = id;
        try {
            Platform.runLater(() -> label_name.setText(name));
            Platform.runLater(() -> label_birth.setText(birth));
            Platform.runLater(() -> label_comment.setText(comment));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void SetStage (Stage stage) {
        this.stage = stage;
    }
}

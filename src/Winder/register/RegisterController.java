package Winder.register;

import Winder.main.MainFrame;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class RegisterController {

    private Stage stage;

    @FXML
    private ImageView img_icon;

    @FXML
    private Button button_select_photo;

    @FXML
    private TextField entry_first_name;

    @FXML
    private TextField entry_last_name;

    @FXML
    private TextField entry_comment;

    @FXML
    private ChoiceBox<Integer> choice_year;

    @FXML
    private ChoiceBox<Integer> choice_month;

    @FXML
    private ChoiceBox<Integer> choice_day;

    @FXML
    private Button button_register;

    @FXML
    void initialize () {
        for (int i=2019; i>1900; i--) choice_year.getItems().add(i);
        for (int i=1; i<=12; i++) choice_month.getItems().add(i);
        for (int i=1; i<=31; i++) choice_day.getItems().add(i);

        choice_year.getSelectionModel().select(0);
        choice_month.getSelectionModel().select(0);
        choice_day.getSelectionModel().select(0);
    }

    @FXML
    private void setDays (MouseEvent e) {
        choice_day.getItems().clear();
        switch (choice_month.getValue()) {
            case 2:
                int year = choice_year.getValue();
                if (year%4==0 && (year%100!=0 || (year%100==0 && year%400==0))) {
                    for (int i=1; i<=29; i++) choice_day.getItems().add(i);
                } else {
                    for (int i=1; i<=28; i++) choice_day.getItems().add(i);
                }
                break;
            case 4: case 6: case 9: case 11:
                for (int i=1; i<=30; i++) choice_day.getItems().add(i);
                break;
            default:
                for (int i=1; i<=31; i++) choice_day.getItems().add(i);
                break;
        }
    }

    @FXML
    private void Registration () {
        String msg = "REGISTRATION " + entry_first_name.getText() + " " + entry_last_name.getText() + " " + String.valueOf(choice_year.getValue()) + "/" + String.valueOf(choice_month.getValue()) + "/" + String.valueOf(choice_day.getValue()) + " " + entry_comment.getText().replace(" ", "_");
        MainFrame.client.out.println(msg);
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    public void setStage (Stage stage) { this.stage = stage; }
}

package Winder.windertalking;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import Winder.main.MainFrame;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ChatController {

    private Stage stage;

    private String[] op_info;

    @FXML
    private ListView<String> list_opponents;

    @FXML
    private Label label_opponent;

    @FXML
    private TextArea text_talk;

    @FXML
    private TextField entry_input;

    @FXML
    private Button button_send;

    @FXML
    void initialize () {
        try {
            File file = new File("dat/chat/chatlist.dat");
            BufferedReader br = new BufferedReader(new FileReader(file));

            String str = br.readLine();
            while (str != null) {
                list_opponents.getItems().add(str);
                str = br.readLine();
            }

            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void SelectOpponent (MouseEvent event) {
        Save();

        String content = list_opponents.getSelectionModel().getSelectedItem();
        if (content == null) return;
        op_info = content.split(":");
        String opponent = op_info[0];
        String op_name = op_info[1];

        
        label_opponent.setText(op_name);
        text_talk.clear();
        try {
            File file = new File("dat/chat/" + opponent + ".ct");
            BufferedReader br = new BufferedReader(new FileReader(file));

            String str = br.readLine();
            while (str != null) {
                PrintTalk(str);
                str = br.readLine();
            }

            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void SendMessage (MouseEvent e) {
        String msg = "[" + MainFrame.mainController.getUserName() + "] " + entry_input.getText();
        entry_input.clear();

        PrintTalk(msg);

        MainFrame.client.out.println("MESSAGE " + op_info[0] + " " + msg.replace(" ", "_"));
    }

    private void PrintTalk (String msg) {
        text_talk.setText(msg + "\n" + text_talk.getText());
    }

    public void Save () {
        if (op_info == null) return;

        String opponent = op_info[0];
        String op_name = op_info[1];

        try {
            File file = new File("dat/chat/" + opponent + ".ct");
            FileWriter fw = new FileWriter(file);
            PrintWriter pw = new PrintWriter(new BufferedWriter(fw));

            String talk[] = text_talk.getText().split("\n");
            for (int i=0; i < talk.length; i++) {
                pw.println(talk[(talk.length-1)-i]);
            }

            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Reload () {
        text_talk.clear();
        try {
            File file = new File("dat/chat/" + op_info[0] + ".ct");
            BufferedReader br = new BufferedReader(new FileReader(file));

            String str = br.readLine();
            while (str != null) {
                PrintTalk(str);
                str = br.readLine();
            }

            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStage (Stage stage) {
        this.stage = stage;
        stage.showingProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue == true && newValue == false) {
                Save();
            }
        });
    }
}

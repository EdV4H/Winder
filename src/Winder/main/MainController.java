package Winder.main;

import Winder.windertalking.ChatFrame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import Winder.register.RegisterFrame;
import Winder.winderconfig.ConfigFrame;
import Winder.windersearch.SearchFrame;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainController {

    private Stage stage;

    @FXML
    private Button button_search;

    @FXML
    private Button button_message;

    @FXML
    private Button button_edit;

    @FXML
    private Button button_register;

    @FXML
    private Button button_setting;

    @FXML
    private Button button_exit;

    @FXML
    private ImageView img_icon;

    @FXML
    private Label label_name;

    @FXML
    private Label label_birth;

    @FXML
    private Label label_comment;

    @FXML
    private TextArea entry_log;

    @FXML
    private void initialize () {
        try {
            File file = new File("dat/userdata.dat");
            BufferedReader br = new BufferedReader(new FileReader(file));

            MainFrame.id = Integer.parseInt(br.readLine());

            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CheckMessage();
    }

    @FXML
    private void OpenSearch (MouseEvent e) {
        PrintLog("[MainController] Open search window");
        SearchFrame searchFrame = new SearchFrame(this.stage);
    }

    @FXML
    private void OpenChat (MouseEvent e) {
        PrintLog("[MainController] Open chat window");
        ChatFrame chatFrame = new ChatFrame(this.stage);
    } 

    @FXML
    private void OpenConfig (MouseEvent e) {
        PrintLog("[MainController] Open chat window");
        ConfigFrame configFrame = new ConfigFrame(this.stage);
    } 

    @FXML
    private void Registration (MouseEvent e) {
        PrintLog("[MainController] Registration");
        RegisterFrame registerFrame = new RegisterFrame(this.stage);
    }

    @FXML
    private void Exit (MouseEvent e) {
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    public void PrintLog (String msg) {
        entry_log.setText(msg + "\n" + entry_log.getText());
    }

    public void setStatus (String name, String birth, String comment) {
        try {
            Platform.runLater(() -> label_name.setText(name));
            Platform.runLater(() -> label_birth.setText(birth));
            Platform.runLater(() -> label_comment.setText(comment));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStage (Stage stage) { this.stage = stage; }
    public String getUserName () { return label_name.getText(); }

    public void StartNewChat (int id, String name) {
        MainFrame.mainController.PrintLog("[ChatController] Start New Chat");
        try {
            File file = new File("dat/chat" + MainFrame.id + "/" + String.valueOf(id) + ".ct");
            file.createNewFile();
            
            FileWriter fw = new FileWriter(file);
            PrintWriter pw = new PrintWriter(new BufferedWriter(fw));

            pw.println("[ServerMessage] Let's start a new conversation !");

            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            File file = new File("dat/chat" + MainFrame.id + "/chatlist.dat");
            
            FileWriter fw = new FileWriter(file,true);
            PrintWriter pw = new PrintWriter(new BufferedWriter(fw));

            pw.println(String.valueOf(id) + ":" + name);

            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        button_message.setDisable(false);
    }

    public void ReceiveMessage (int id, String opponent, String recv_msg) {
        String msg = recv_msg.replace("_", " ");

        if (ChatFrame.chatController != null) {
            ChatFrame.chatController.Save();
        }
        
        try {
            File file = new File("dat/chat" + MainFrame.id + "/" + String.valueOf(id) + ".ct");
            
            FileWriter fw = new FileWriter(file,true);
            PrintWriter pw = new PrintWriter(new BufferedWriter(fw));

            pw.println(msg);

            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (ChatFrame.chatController != null) {
            ChatFrame.chatController.Reload();
        }
    }

    public void CheckMessage () {
        try {
            File file = new File ("dat/chat" + MainFrame.id + "/chatlist.dat");
            BufferedReader br = new BufferedReader(new FileReader(file));

            String str = br.readLine();
            if (str == null) button_message.setDisable(true);

            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

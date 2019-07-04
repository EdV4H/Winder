package Winder.winderconfig;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ConfigController {

    private Stage stage;

    @FXML
    private TextField entry_address;

    @FXML
    private TextField entry_id;

    @FXML
    private Button button_confirm;

    @FXML
    void Confirm(MouseEvent event) {
        try {
            File usr = new File("dat/userdata.dat");
            File cfg = new File("dat/configure.dat");

            FileWriter usr_fw = new FileWriter(usr);
            PrintWriter usr_pw = new PrintWriter(new BufferedWriter(usr_fw));

            FileWriter cfg_fw = new FileWriter(cfg);
            PrintWriter cfg_pw = new PrintWriter(new BufferedWriter(cfg_fw));

            usr_pw.println(entry_id.getText());
            cfg_pw.println(entry_address.getText());

            usr_pw.close();
            cfg_pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
    }  

    @FXML
    void initialize () {
        try {
            File usr = new File("dat/userdata.dat");
            File cfg = new File("dat/configure.dat");

            BufferedReader usr_br = new BufferedReader(new FileReader(usr));
            BufferedReader cfg_br = new BufferedReader(new FileReader(cfg));

            String ad = cfg_br.readLine();
            String id = usr_br.readLine();

            entry_address.setText(ad);
            entry_id.setText(id);

            usr_br.close();
            cfg_br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 

    public void SetStage (Stage stage) {
        this.stage = stage;
    }

}

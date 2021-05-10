package net.ayoentem.storagemanager.main;

import java.io.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import net.ayoentem.storagemanager.utils.backup.BackUp;
import net.ayoentem.storagemanager.utils.backup.BackUpInterval;
import net.ayoentem.storagemanager.utils.backup.IntervalEnum;
import net.ayoentem.storagemanager.utils.database.MySQLConnection;


public class MainController {

    @FXML
    private ListView<String> chartList;
    @FXML
    private ChoiceBox<IntervalEnum> listInterval;
    @FXML
    private ChoiceBox<String> listBackups;
    @FXML
    private Button btnChoose1;
    @FXML
    private Button btnChoose2;
    @FXML
    private TextField txtPathBackup;
    @FXML
    private TextField txtPathData;

    private Stage stage;
    private MySQLConnection connection;

    
    public void init2(Stage stage, MySQLConnection connection) {
        this.stage = stage;
        this.connection = connection;
    }

    @FXML
    public void initialize() {
        File[] listRoots = File.listRoots();

        //Load RestoreList
        BackUp backUp = new BackUp();
        backUp.init();

        //Load IntervalList

        for (IntervalEnum intervals : IntervalEnum.values()) {
            listInterval.getItems().add(intervals);
        }

        //Aktuell abgespeichertes
        listInterval.getSelectionModel().select(IntervalEnum.valueOf(""));

        //Load Drivers
        for (int i = 0; i < listRoots.length; i++) {
            System.out.println(listRoots[i].getPath().substring(0, 1));
            chartList.getItems().add(listRoots[i].getPath().substring(0, 1) + "-Laufwerk");
        }
    }

    @FXML
    public void chooseDirectory1(MouseEvent event){
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Choose the Directory");
        File file = chooser.showDialog(null);
        if(file == null)return;
        txtPathData.setText(file.getPath());
    }

    @FXML
    public void chooseDirectory2(MouseEvent event){
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Choose the Directory");
        File file = chooser.showDialog(null);
        if(file == null)return;
        txtPathBackup.setText(file.getPath());
    }

    @FXML
    public void clickedON(MouseEvent event) {
        File file = new File(chartList.getSelectionModel().getSelectedItem().substring(0, 1) + ":/");
        try {
            App.switchScreen(file, "../utils/fxml/stats.fxml", this.stage, "../utils/css/Stats.css");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}

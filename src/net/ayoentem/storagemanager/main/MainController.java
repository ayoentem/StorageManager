package net.ayoentem.storagemanager.main;

import java.io.*;
import java.text.SimpleDateFormat;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import net.ayoentem.storagemanager.utils.backup.BackUp;
import net.ayoentem.storagemanager.utils.database.MySQLConnection;

public class MainController {

    @FXML
    private ListView<String> chartList;
    @FXML
    private ChoiceBox<String> listBackups;
    @FXML
    private Button btnChoose1;
    @FXML
    private Button btnChoose2;
    @FXML
    private TextField txtPathData;
    @FXML
    private Label lblLastBackup;
    @FXML
    private Button btnBackup;

    private Stage stage;
    private MySQLConnection connection;
    private BackUp backUp;
    private App app;

    
    public void init2(App app, Stage stage, MySQLConnection connection) {
        this.app = app;
        this.stage = stage;
        this.connection = connection;

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.YYYY");

        File[] listRoots = File.listRoots();

        //Load RestoreList
        backUp = new BackUp(connection, listBackups);
        backUp.init();

        //Load Drivers
        for (int i = 0; i < listRoots.length; i++) {
            System.out.println(listRoots[i].getPath().substring(0, 1));
            chartList.getItems().add(listRoots[i].getPath().substring(0, 1) + "-Laufwerk");
        }
    }

    @FXML
    public void initialize() {

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
    public void clickedON(MouseEvent event) {
        File file = new File(chartList.getSelectionModel().getSelectedItem().substring(0, 1) + ":/");
        try {
            app.switchScreen(file, StatsController.class.getResource("../utils/fxml/stats.fxml"), this.stage);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void doBackup(MouseEvent event){
        this.backUp.createBackup(this.txtPathData.getText());
    }

}

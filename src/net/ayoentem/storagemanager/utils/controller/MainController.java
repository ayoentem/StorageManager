package net.ayoentem.storagemanager.utils.controller;

import java.io.*;
import java.text.SimpleDateFormat;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
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

    private BackUp backUp;

    public void init2() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.YYYY");

        File[] listRoots = File.listRoots();

        //Load RestoreList
        backUp = new BackUp(MySQLConnection.getMysql(), listBackups);
        backUp.init();

        //Load Drivers
        for (int i = 0; i < listRoots.length; i++) {
            System.out.println(listRoots[i].getPath().substring(0, 1));
            chartList.getItems().add(listRoots[i].getPath().substring(0, 1) + "-Laufwerk");
        }

        /**
         * TODO: Set Image close to every single data in listview
         */
        /*
        chartList.setCellFactory(param -> new ListCell<String>(){

            private ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                //imageView.setImage(new Image("../utils/icons/flash-drive.png"));

                setGraphic(imageView);
            }
        });
         */

    }

    @FXML
    public void chooseDirectory1(MouseEvent event) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Choose the Directory");
        File file = chooser.showDialog(null);
        if (file == null) return;
        txtPathData.setText(file.getPath());
    }

    @FXML
    public void clickedON(MouseEvent event) {
        File file = new File(chartList.getSelectionModel().getSelectedItem().substring(0, 1) + ":/");
        /**
         * TODO: Switch to Stats Screen
         */
        //app.switchScreen(file, StatsController.class.getResource("../utils/fxml/stats.fxml"), this.stage);
    }

    @FXML
    public void doBackup(MouseEvent event) {
        this.backUp.createBackup(this.txtPathData.getText());
    }

}

package net.ayoentem.storagemanager.main;

import java.io.*;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private ListView<String> chartList;


    private Stage stage;

    
    public void init2(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        File[] listRoots = File.listRoots();

        for (int i = 0; i < listRoots.length; i++) {
            System.out.println(listRoots[i].getPath().substring(0, 1));
            chartList.getItems().add(listRoots[i].getPath().substring(0, 1) + "-Laufwerk");
        }

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

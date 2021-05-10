/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ayoentem.storagemanager.main;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author ayoentem
 */
public class StatsController {

    @FXML
    private TableView<Volume> tableView;
    @FXML
    private ListView<String> listView;
    @FXML
    private CheckBox rdiListFiles;
    @FXML
    private TextField txtSearch;
    @FXML
    private ProgressBar progessbar;
    @FXML
    private Label lblError;
    @FXML
    private Label lblPath;

    private Stage stage;
    private File file;
    private ObservableList<String> fileList = FXCollections.observableArrayList();
    private HashMap<String, File> fileMap = new HashMap<>();
    private Timeline task;

    public void init2(File file, Stage stage) {
        this.stage = stage;
        lblPath.setText("Search for Files in " + file.getPath());
        loading();
        task.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                success();
            }
        });

        this.file = file;

        loadTableView();
    }

    @FXML
    public void initialize() {

    }

    @FXML
    public void openFile(ActionEvent event) {
        if (listView.getSelectionModel().getSelectedItem() != null) {
            File file = fileMap.get(listView.getSelectionModel().getSelectedItem());
            try {
                System.out.println(file.getPath());
                Runtime runtime = Runtime.getRuntime();
                Process process = runtime.exec(file.getPath());
                Thread.sleep(5000);
                process.destroy();
            } catch (IOException ex) {
                lblError.setText(ex.getMessage());
            } catch (InterruptedException ex) {
                lblError.setText(ex.getMessage());
            }
        }

    }

    @FXML
    public void recursiveDirectory(MouseEvent event) {
        /**
         * @TODO:
         * - Adding button to get in the last screen
         * - Adding error Message for no available files for listing them
         */
        Volume selectedItem = tableView.getSelectionModel().getSelectedItem();
        File file = new File(selectedItem.getDirectory());
        if (selectedItem.getFiDiContain() != 0) {
            try {
                App.switchScreen(file, "../utils/fxml/stats.fxml", this.stage, "../utils/css/Stats.css");
            } catch (IOException ex) {
                lblError.setText(ex.getMessage());
            }
        } else {
            lblError.setText("Dieses Verzeichnis hat keine Dateien");
        }

    }

    @FXML
    public void deleteFile(ActionEvent event) {
        try {
            fileMap.get(listView.getSelectionModel().getSelectedItem()).delete();
            task.playFromStart();
        } catch (Throwable ex) {
            error(ex.getMessage());
        }
    }

    @FXML
    public void listFiles(ActionEvent event) {
        if (rdiListFiles.isSelected()) {
            loadFiles();
        }
        if (!rdiListFiles.isSelected()) {
            listView.getItems().clear();
        }
    }

    @FXML
    public void trytoSearch(KeyEvent event) {

        if (rdiListFiles.isSelected()) {
            listView.setItems(
                    fileList.filtered((t) -> {
                        return t.contains(txtSearch.getText());
                    })
            );
        }

    }

    private double ByteToKb(long bytes) {
        return bytes / 1000;
    }

    private void loadFiles() {

        fileList.clear();
        fileMap.clear();
        for (File files : file.listFiles()) {
            if (files.isFile()) {
                fileList.add(files.getName());
                fileMap.put(files.getName(), files);
            }
        }
        listView.setItems(fileList);

    }

    private void loadTableView() {

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Volume, String> volume = new TableColumn<>("Directory");
        volume.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Volume, Long> totalCap = new TableColumn<>("Files/Directorys contains");
        totalCap.setCellValueFactory(new PropertyValueFactory<>("fiDiContain"));

        TableColumn<Volume, Long> freeSpace = new TableColumn<>("Is Hidden");
        freeSpace.setCellValueFactory(new PropertyValueFactory<>("isHidden"));

        TableColumn<Volume, Long> lastModified = new TableColumn<>("Last modified");
        lastModified.setCellValueFactory(new PropertyValueFactory<>("lastModified"));

        tableView.setEditable(true);
        tableView.getColumns().add(volume);
        tableView.getColumns().add(totalCap);
        tableView.getColumns().add(freeSpace);
        tableView.getColumns().add(lastModified);

        for (int i = 0; i < file.listFiles().length; i++) {
            File data = file.listFiles()[i];
            Date date = new Date(data.lastModified());
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.YYYY");
            if (data.isDirectory()) {
                tableView.getItems().add(new Volume(
                        data.getName(),
                        1, //data.listFiles().length
                        data.isHidden(),
                        sdf.format(date),
                        data.getPath())
                );
            }
        }
    }

    private void loading() {
//        progessbar.progressProperty().unbind();
//        progessbar.progressProperty().bind();

        task = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(progessbar.progressProperty(), 0)
                ),
                new KeyFrame(
                        Duration.seconds(2),
                        new KeyValue(progessbar.progressProperty(), 1)
                )
        );
    }

    private void success() {
        lblError.setTextFill(Paint.valueOf("GREEN"));
        lblError.setText("Successfully done!");
        loadFiles();
    }

    private void error(String message) {
        lblError.setText(message);
        loadFiles();
    }
}

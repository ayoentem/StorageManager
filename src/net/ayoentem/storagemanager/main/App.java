package net.ayoentem.storagemanager.main;

import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import net.ayoentem.storagemanager.utils.controller.MainController;
import net.ayoentem.storagemanager.utils.database.MySQLConnection;
import net.ayoentem.storagemanager.utils.screen.FXMLEnum;
import net.ayoentem.storagemanager.utils.screen.SceneSwitcher;

/**
 * JavaFX App
 */
public class App extends Application {

    public static SceneSwitcher switcher;

    public void start(Stage stage) throws IOException {
        stage.setTitle("StorageManager v 1.0");

        //FXML Paths laden
        FXMLEnum.loadFXML();

        //SceneSwitch initialisieren und füllen
        switcher = new SceneSwitcher();
        switcher.setStage(stage);

        //Icon setzen
        InputStream iconStream = getClass().getResourceAsStream("../utils/icons/icon.png");
        Image image = new Image(iconStream);

        stage.getIcons().add(image);

        //Zuhause MySQL
        //mysql = new MySQLConnection("localhost", "storagemanager", "root", "", "3306");
        new MySQLConnection("mysql.dvs-plattling.de", "db_ayoentem", "ayoentem", "ayoentem", "3306");
        MySQLConnection.getMysql().connect();

        //Start first time screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../utils/fxml/main.fxml"));
        Parent load = loader.load();



        MainController mainController = loader.getController();
        //mainController.init2();
        switcher.switchScene(MainController.class, "init2");

        Scene scene = new Scene(load);

        addJMetroStyle(scene);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void addJMetroStyle(Scene scene) {
        JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setAutomaticallyColorPanes(true);
        jMetro.setScene(scene);
    }

}
package net.ayoentem.storagemanager.main;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

import net.ayoentem.storagemanager.utils.database.MySQLConnection;
import net.ayoentem.storagemanager.utils.fxml.SceneSwitcher;

/**
 * JavaFX App
 */
public class App extends Application {

    public static SceneSwitcher switcher;

    public void start(Stage stage) throws IOException {
        System.out.println(System.getProperty("user.dir"));
        stage.setTitle("StorageManager v 1.0");

        //SceneSwitch initialisieren und füllen
        switcher = new SceneSwitcher(stage);
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
        switcher.switchToMain();
    }

    public static void main(String[] args) {
        launch();
    }

}
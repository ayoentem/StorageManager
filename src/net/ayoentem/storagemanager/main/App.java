package net.ayoentem.storagemanager.main;

import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import net.ayoentem.storagemanager.utils.database.MySQLConnection;

/**
 * JavaFX App
 */
public class App extends Application {

    public static int stage_depth = 0;

    private MySQLConnection mysql;

    public void start(Stage stage) throws IOException {

        stage.setTitle("StorageManager v 1.0");

        mysql = new MySQLConnection("localhost", "storagemanager", "root", "", "3306");
        mysql.connect();

        switchScreen(null, App.class.getResource("../utils/fxml/main.fxml"), stage);
    }

    public static void main(String[] args) {
        launch();
    }

    public void switchScreen(File file, URL url, Stage stage) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(url);

            if (App.stage_depth > 1) {

                StatsController statsController = loader.getController();
                statsController.init2(this, file, stage, mysql);

            } else if (App.stage_depth <= 1) {

                MainController controller = loader.getController();
                controller.init2(this, stage, mysql);

                if(App.stage_depth == 0){
                    App.stage_depth++;
                    return;
                }


                App.stage_depth--;

            }

            Parent newView = loader.load();

            Scene scene = new Scene(newView);

            addJMetroStyle(scene);
            stage.setScene(scene);
            stage.show();

        } catch (NullPointerException ex) {

        }
    }

    private void addJMetroStyle(Scene scene) {
        JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setAutomaticallyColorPanes(true);
        jMetro.setScene(scene);
    }

}
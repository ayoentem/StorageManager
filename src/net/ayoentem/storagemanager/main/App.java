package net.ayoentem.storagemanager.main;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.image.Image;
import net.ayoentem.storagemanager.utils.backup.BackUp;
import net.ayoentem.storagemanager.utils.backup.BackUpInterval;
import net.ayoentem.storagemanager.utils.database.MySQLConnection;

/**
 * JavaFX App
 */
public class App extends Application {

    public void start(Stage stage) throws IOException {

        MySQLConnection mysql = new MySQLConnection("localhost", "storagemanager", "root", "", "3306");
        mysql.connect();

        //stage.getIcons().add(new Image("icon.png"));
        FXMLLoader loadFXML = loadFXML("../utils/fxml/main");
        Parent view = loadFXML.load();
        view.getStylesheets().add("/Stats.css");

        MainController controller = loadFXML.getController();
        controller.init2(stage, mysql);

        Scene scene = new Scene(view);
        stage.setScene(scene);
        stage.setTitle("StorageManager v1.0.0");
        stage.show();
    }

    public FXMLLoader loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader;
    }

    public static void main(String[] args) {
        launch();
    }
    
    public static void switchScreen(File file, String fxml, Stage stage, String pathStyleSheet, MySQLConnection connection) throws IOException{
            FXMLLoader loader = new FXMLLoader(StatsController.class.getResource(fxml));

            Parent neueView = loader.load();

            StatsController statsController = loader.getController();
            statsController.init2(file, stage, connection);

            Scene scene = new Scene(neueView);
            
            if(!pathStyleSheet.equalsIgnoreCase(" ")) scene.getStylesheets().add(pathStyleSheet);
            
            stage.setScene(scene);
            stage.show();
    }

    public static void switchToMainScreen(Stage stage, MySQLConnection connection) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainController.class.getResource("../utils/fxml/main.fxml"));

        Parent neueView = loader.load();

        MainController mainController = new MainController();
        mainController.init2(stage, connection);

        Scene scene = new Scene(neueView);

        scene.getStylesheets().add("/Stats.css");

        stage.setScene(scene);
        stage.show();
    }

}
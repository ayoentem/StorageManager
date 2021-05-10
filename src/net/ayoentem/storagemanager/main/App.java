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
import net.ayoentem.storagemanager.utils.database.MySQLConnection;

/**
 * JavaFX App
 */
public class App extends Application {

    public void start(Stage stage) throws IOException {

        MySQLConnection mysql = new MySQLConnection("localhost", "db_ayoentem", "root", "", "3306");
        mysql.connect();

        System.out.println(System.getProperty("user.home"));

        //stage.getIcons().add(new Image("icon.png"));
        FXMLLoader loadFXML = loadFXML("../utils/fxml/main");
        Parent view = loadFXML.load();

        MainController controller = loadFXML.getController();
        controller.init2(stage);

        Scene scene = new Scene(view);
        stage.setScene(scene);
        stage.setTitle("StorageManager v1.0");
        stage.show();
    }

    public FXMLLoader loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader;
    }

    public static void main(String[] args) {
        launch();
    }
    
    public static void switchScreen(File file, String fxml, Stage stage, String pathStyleSheet) throws IOException{
            FXMLLoader loader = new FXMLLoader(StatsController.class.getResource(fxml));

            Parent neueView = loader.load();

            StatsController statsController = loader.getController();
            statsController.init2(file, stage);

            Scene scene = new Scene(neueView);
            
            if(!pathStyleSheet.equalsIgnoreCase(" ")) scene.getStylesheets().add(pathStyleSheet);
            
            stage.setScene(scene);
            stage.show();
    }

}
package net.ayoentem.storagemanager.utils.fxml;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import lombok.Data;
import net.ayoentem.storagemanager.utils.controller.MainController;
import net.ayoentem.storagemanager.utils.controller.StatsController;

import java.io.File;
import java.io.IOException;

@Data
public class SceneSwitcher {

    private Stage stage;

    public SceneSwitcher(Stage stage){
        this.stage = stage;
    }

    public void switchToMain() throws IOException {
        FXMLLoader loader = getLoader("main.fxml");

        Parent view = loader.load();
        MainController controller = loader.getController();
        controller.init2();

        showView(view);
    }

    public void switchToStats(File file) throws IOException {
        FXMLLoader loader = getLoader("stats.fxml");

        Parent view = loader.load();
        StatsController controller = loader.getController();
        controller.init(file);

        showView(view);
    }

    private void showView(Parent view){
        Scene scene = new Scene(view);

        addJMetroStyle(scene);
        stage.setScene(scene);
        stage.show();
    }

    private FXMLLoader getLoader(String fxml){
        return new FXMLLoader(getClass().getResource(fxml));
    }

    private void addJMetroStyle(Scene scene) {
        JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setAutomaticallyColorPanes(true);
        jMetro.setScene(scene);
    }
}

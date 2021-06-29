package net.ayoentem.storagemanager.utils.screen;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Data;
import net.ayoentem.storagemanager.utils.controller.MainController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

@Data
public class SceneSwitcher {

    private Stage stage;

    private Scene currentScene;
    private FXMLEnum fxmlEnum;

    private int stage_depth = 0;

    public FXMLLoader switchScene(Class controller, String methodeName){
        try {
            Method declaredMethod = MainController.class.getMethod(methodeName);
            MainController c = new MainController();
            declaredMethod.invoke(c);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

}

package net.ayoentem.storagemanager.utils.screen;

import javafx.fxml.FXML;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.net.URL;


@Getter
public enum FXMLEnum {

    MAIN    ("main.fxml", ""),
    STATS   ("stats.fxml", "");

    @Setter
    private String name;
    @Setter
    private String path;

    FXMLEnum(String name, String path){
        this.name = name;
        this.path = path;
    }

    public static void loadFXML(){
        for (File file:
             new File("./src/net/ayoentem/storagemanager/utils/fxml").listFiles()) {
            if(file.getName().contains("main")){
                MAIN.setPath(file.getPath());
            }else if(file.getName().contains("stats")){
                STATS.setPath(file.getPath());
            }
        }
    }

}

package net.ayoentem.storagemanager.utils.backup;

import javafx.scene.control.ChoiceBox;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@Getter
@Setter
public class BackUp {

    private String pathData;
    private String pathBackUp;
    private Date date;
    private ChoiceBox<String> listBackups;


    public BackUp(){

    }

    public void init(){
        File file = new File(System.getProperty("user.home") + "//.storageManager//backups");
        if(!file.exists()){
            file.mkdirs();
            return;
        }

        String[] fileArray = file.list();
        for (String backup : fileArray) {
            listBackups.getItems().add(backup);
        }
    }

    public void createBackup(){
        try{
            File existingFile = new File(this.pathData);
            File backupDir = new File(System.getProperty("user.home") + "//.storageManager//backups//" + new Date().toString());
            backupDir.mkdirs();

            if(existingFile.exists() && existingFile.isDirectory()){
                for (String file : existingFile.list()) {
                    Files.copy(backupDir.toPath(), Paths.get(file), StandardCopyOption.REPLACE_EXISTING);
                }
            }

        }catch(Exception io){
            io.printStackTrace();
        }

    }

}





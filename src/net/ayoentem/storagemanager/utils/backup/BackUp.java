package net.ayoentem.storagemanager.utils.backup;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BackUp {

    private String pathData;
    private String pathBackUp;
    private Enum<BackUpInterval> interval;
    private Date lastBackup;

    public BackUp(String pathData, String pathBackup, Enum<BackUpInterval> interval){

    }

    private void init(){

    }



}

enum BackUpInterval{

    DAY, WEEK, MONTH;

}



package net.ayoentem.storagemanager.utils.backup;

import lombok.Getter;
import net.ayoentem.storagemanager.utils.database.MySQLConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Getter
public class BackUpInterval{

    private boolean isActiveBackup;
    private Date lastBackUp;
    private IntervalEnum interval;

    public BackUpInterval() {

    }

    public void init(MySQLConnection connection, String table){

        if(!connection.existTable(table)) {

            connection.update(
                    "CREATE TABLE IF NOT EXISTS " + table + "("
                            + "id int(5) AUTO_INCREMENT,"
                            + "abstand enum('DAY','WEEK','MONTH'),"
                            + "lastBackup datetime,"
                            + "active int(1),"
                            + "PRIMARY KEY(id)"
                            + ");"
            );

            connection.update("INSERT INTO " + table + " VALUES" +
                    "(" + null + ", 'MONTH', "
                     + null + "," +
                    0 + ");");
        }


        //initialize vars
        ResultSet rs = null;

        try{
            rs = connection.query("SELECT * FROM " + table);

            if(rs.next()){
                interval = IntervalEnum.valueOf(rs.getString("abstand"));
                isActiveBackup = rs.getInt("active") == 0 ? false : true;
                lastBackUp = rs.getDate("lastBackup");
            }

        }catch(SQLException ex){
            ex.printStackTrace();
        }


    }

    public boolean checkForBackUp(){
        return new Date().compareTo(lastBackUp) > 0;
    }

}



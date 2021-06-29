package net.ayoentem.storagemanager.utils.database;

import lombok.Getter;

import java.sql.*;

public class MySQLConnection {

    @Getter
    private static MySQLConnection mysql;

    private String host;
    private String database;
    private String user;
    private String password;
    private String port;

    public Connection connection;

    public MySQLConnection(String host, String database, String user, String password, String port){
        MySQLConnection.mysql = this;

        this.host = host;
        this.database = database;
        this.user = user;
        this.password = password;
        this.port = port;
    }

    public void connect(){
            try{

                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://"+ host + ":"+ port + "/" + database, user, password);
                System.out.println("MySQL Connnected");

            }catch(Exception io){
                io.printStackTrace();
            }
    }

    public void close() {
        try {
            if(this.connection != null) {
                connection.close();
                System.out.println("[MySQL] Die Verbindung zur MySQL wurde Erfolgreich beendet!");
            }
        } catch (SQLException e) {
            System.out.println("[MySQL] Fehler beim beenden der Verbindung zur MySQL! Fehler: " + e.getMessage());
        }
    }

    public void update(String qry) {
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(qry);
            st.close();
        } catch (SQLException e) {
            connect();
            System.err.println(e);
        }
    }

    public ResultSet query(String qry) {
        ResultSet rs = null;
        try {
            Statement st = connection.createStatement();
            rs = st.executeQuery(qry);
        } catch (SQLException e) {
            connect();
            System.err.println(e);
        }
        return rs;
    }

    public boolean existTable(String tableName) {
        DatabaseMetaData meta = null;
        ResultSet resultSet = null;
        boolean exist = false;
        try{
                meta = connection.getMetaData();
                resultSet = meta.getTables(null, null, tableName, new String[] {"TABLE"});
                exist = resultSet.next();
        }catch(SQLException ex){
            return false;
        }
        return exist;
    }

}

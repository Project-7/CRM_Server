
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author MarkusH
 */
public class CRM_Server_DB_Connection {

    Connection con = null;

    public Connection getConnection() throws SQLException {
        try {
            System.out.println("Verbindung mit Datenbank wird hergestellt.");
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            System.err.println("Treiber konnte nicht gefunden werden." + e.getMessage());
        }
        return con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LAeRacing", "root", "0000");

    }

}

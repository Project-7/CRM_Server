
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
/**
 *
 * @author MarkusH und RobertK
 * @version 1.0
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


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author MarkusH
 */
public class CRM_Server_Methoden extends UnicastRemoteObject implements CRM_Interface {

    public CRM_Server_Methoden() throws RemoteException {
        super();
    }

    @Override
    public String insertMitglied(Mitglied m) throws RemoteException {
        try {
            CRM_Server_DB_Connection c = new CRM_Server_DB_Connection();
            Connection con = c.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("INSERT INTO Mitglied (vorname,  name,  telefonnr,  email,  Strasse_HsNr,  plz,  ort, angemeldet,  studiumGenerale,  email_eRacing,  fuehrerschein,  vermerk,  werkstattregeln,  serverzugang,  staatsangehoerigkeit,  foto_vorhanden, Position) VALUES ('" + m.getVorname() + "','" + m.getName() + "', '" + m.getTelefonnr() + "', '" + m.getEmail() + "', '" + m.getStrasse_Hsnr() + "', '" + m.getPlz() + "', '" + m.getOrt() + "', '" + m.isAngemeldet() + "', '" + m.getStudiumGenerale() + "', '" + m.getEmail_eRacing() + "', '" + m.getFuehrerschein() + "', '" + m.getVermerk() + "', '" + m.isWerkstattregeln() + "', '" + m.isServerzugang() + "', '" + m.getStaatsangehoerigkeit() + "', '" + m.isFoto_vorhanden() + "', '" + m.getPosition() + "')");
            System.out.println("Datensatz erfolgreich angelegt.");
        } catch (SQLException e) {
            System.err.println("Fehler: " + e);
        }
        return "Nachricht vom Server: Datensatz wurde erfolgreich in die Datenbank gespeichert!";
    }

    @Override
    public void selectMitglied(String[] args) throws RemoteException {
        try {
            CRM_Server_DB_Connection c = new CRM_Server_DB_Connection();
            Connection con = c.getConnection();
            String abfrage = "SELECT "+args[0]+" FROM Mitglied WHERE "+args[1]+" ="+args[2]+";";
            PreparedStatement pstmt = con.prepareStatement(abfrage);
            ResultSet rs = pstmt.executeQuery();
            
            //TODO Objekte erzeugen und an Client übergeben
            while (rs.next()){
                String i = "1";
                int mitglID = rs.getInt(1);
                System.out.println(mitglID);
                String m = "m"+i;
                //Mitglied m = new Mitglied();
                //m++;
            }
            System.out.println("Abfrage ausgeführt");
        } catch (SQLException e) {
            System.err.println("Fehler: " + e);
        }
        //return m;
    }
    @Override
    public String updateMitglied(Mitglied m) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String deleteMitglied(Mitglied m) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

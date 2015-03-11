
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

    /**@Override
    public void selectMitglied(String[] args) throws RemoteException {
        try {
            CRM_Server_DB_Connection c = new CRM_Server_DB_Connection();
            Connection con = c.getConnection();
            String abfrage = "SELECT "+args[0]+" FROM Mitglied WHERE "+args[1]+" = '"+args[2]+"';";
            System.out.println(abfrage);
            PreparedStatement pstmt = con.prepareStatement(abfrage);
            ResultSet rs = pstmt.executeQuery();
            
            //TODO Objekte erzeugen und an Client übergeben
            while (rs.next()){
                int mitglID = rs.getInt(1);
                System.out.println(mitglID);
                //Mitglied m = new Mitglied();
                //m++;
            }
            System.out.println("Abfrage ausgeführt");
        } catch (SQLException e) {
            System.err.println("Fehler: " + e);
        }
        //return m;
    }*/
    
    @Override
    public ArrayList<Mitglied> selectMitglied(String[] args) throws RemoteException {
        try {
            CRM_Server_DB_Connection c = new CRM_Server_DB_Connection();
            Connection con = c.getConnection();
            ArrayList <Mitglied> mitglieder = new ArrayList <Mitglied>();
            
            if(args.length==0){ //wenn keine Auswahl getroffen wird im Client, werden alle Mitglieder ohne Einschränkung ausgegeben
                String abfrage = "SELECT * FROM Mitglied;";
                PreparedStatement pstmt = con.prepareStatement(abfrage);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()){
                int mitglID = rs.getInt(1);
                String vorname = rs.getString(2);
                String name = rs.getString(3);
                String telefonnr = rs.getString(4);
                String email = rs.getString(5);
                String strasse_Hsnr = rs.getString(6);
                int plz = rs.getInt(7);
                String ort = rs.getString(8);
                int angemeldet = rs.getInt(9);
                String studiumGenerale = rs.getString(10);
                String email_eRacing = rs.getString(11);
                String fuehrerschein = rs.getString(12);
                String vermerk = rs.getString(13);
                int werkstattregeln = rs.getInt(14);
                int serverzugang = rs.getInt(15);
                String staatsangehoerigkeit = rs.getString(16);
                int foto_vorhanden = rs.getInt(17);
                String Position = rs.getString(18);
                Mitglied m = new Mitglied(mitglID, vorname,  name,  telefonnr,  email,  strasse_Hsnr,  plz,  ort, angemeldet,  studiumGenerale,  email_eRacing,  fuehrerschein,  vermerk,  werkstattregeln,  serverzugang,  staatsangehoerigkeit,  foto_vorhanden, Position);
                System.out.println(m.getEmail());
                System.out.println(m.getOrt());
                mitglieder.add(m);
            }
            }if(args.length==2){ //wenn Client ein Attribut abfrägt
                String abfrage = "SELECT * FROM Mitglied WHERE "+args[0]+" = '"+args[1]+"';";;
                PreparedStatement pstmt = con.prepareStatement(abfrage);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()){
                int mitglID = rs.getInt(1);
                String vorname = rs.getString(2);
                String name = rs.getString(3);
                String telefonnr = rs.getString(4);
                String email = rs.getString(5);
                String strasse_Hsnr = rs.getString(6);
                int plz = rs.getInt(7);
                String ort = rs.getString(8);
                int angemeldet = rs.getInt(9);
                String studiumGenerale = rs.getString(10);
                String email_eRacing = rs.getString(11);
                String fuehrerschein = rs.getString(12);
                String vermerk = rs.getString(13);
                int werkstattregeln = rs.getInt(14);
                int serverzugang = rs.getInt(15);
                String staatsangehoerigkeit = rs.getString(16);
                int foto_vorhanden = rs.getInt(17);
                String Position = rs.getString(18);
                Mitglied m = new Mitglied(mitglID, vorname,  name,  telefonnr,  email,  strasse_Hsnr,  plz,  ort, angemeldet,  studiumGenerale,  email_eRacing,  fuehrerschein,  vermerk,  werkstattregeln,  serverzugang,  staatsangehoerigkeit,  foto_vorhanden, Position);
                System.out.println(m.getEmail());
                //System.out.println(m.getOrt());
                mitglieder.add(m);
                //SELECT Geburtsdatum FROM Geburtsdaten WHERE mitgliederID = mitglID
            }
            }
            System.out.println("Abfrage ausgeführt");
            return mitglieder;
            /*String abfrage = "SELECT * FROM Mitglied WHERE";
            System.out.println(abfrage);
            PreparedStatement pstmt = con.prepareStatement(abfrage);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                int mitglID = rs.getInt(1);
                System.out.println(mitglID);
            }*/
        } catch (SQLException e) {
            System.err.println("Fehler: " + e);
        }
        return null;
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


import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author MarkusH
 */

//TODO neue Klassen und Packages erstellen
public class CRM_Server extends UnicastRemoteObject implements CRM_Interface  {

    
    public CRM_Server() throws RemoteException {
        super();
    }
    
    @Override
    public String writeNewMitgliedInDatabase(Mitglied m) throws RemoteException {
        
        // CRM_DatabaseCon c = new CRM
        // c.addNewMitglied(m);
        
        //am besten Interface
        //evtl. Hibernate doch noch versuche, da über RMI Objekt mehrmals umgewandelt werden muss
        
        Connection con = null;
        try {
            System.out.println("Verbindung mit Datenbank wird hergestellt.");
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            System.err.println("Treiber konnte nicht gefunden werden."+e.getMessage());
        }
        System.out.println("Angekommen: "+m.getEmail());
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LAeRacing", "root", "");
            
            Statement stmt = con.createStatement();   //(m) kann von Datenbank nicht erkannt werden
            //INSERT INTO fertig schreiben
            stmt.executeUpdate("INSERT INTO Mitglied (Vorname) VALUES ('"+m.getVorname()+"','"+m.getName()+"', '"+m.getTelefonnr()+"', '"+m.getEmail()+"', '"+m.getStrasseHsnr()+"', '"+m.getPlz()+"', '"+m.getOrt()+"', '"+m.isAngemeldet()+"', '"+m.getStudiumGenerale()+"', '"+m.getEmail_eRacing()+"', '"+m.getFuererschein()+"', '"+m.getVermerk()+"', '"+m.isWerkstattregeln()+"', '"+m.isServerzugang()+"', '"+m.getStaatsangehoerigkeit()+"', '"+m.isFoto_vorhanden()+"', '"+m.getPosition()+"')");
            System.out.println("Datensatz erfolgreich angelegt.");
        } catch (SQLException e) {
            System.err.println("Fehler: "+e);
        }
        
        return "Datensatz wurde erfolgreich in die Datenbank gespeichert";
                
    }
    
    
    
}

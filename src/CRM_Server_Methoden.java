
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public String insertMitglied(Mitglied m, Geburtsdaten g, Kontodaten k, Mitgliedsstatus st, Studiuminfo si, Team t) throws RemoteException {
        try {
            CRM_Server_DB_Connection c = new CRM_Server_DB_Connection();
            Connection con = c.getConnection();
            Statement stmt = con.createStatement();

            stmt.executeUpdate("INSERT INTO Mitglied (vorname,  name,  telefonnr,  email,  Strasse_HsNr,  plz,  ort, angemeldet, email_eRacing, fuehrerschein,  vermerk,  werkstattregeln,  serverzugang,  staatsangehoerigkeit,  foto_vorhanden, Position) VALUES ('" + m.getVorname() + "','" + m.getName() + "', '" + m.getTelefonnr() + "', '" + m.getEmail() + "', '" + m.getStrasse_Hsnr() + "', '" + m.getPlz() + "', '" + m.getOrt() + "', '" + m.isAngemeldet() + "', '" + m.getEmail_eRacing() + "', '" + m.getFuehrerschein() + "', '" + m.getVermerk() + "', '" + m.isWerkstattregeln() + "', '" + m.isServerzugang() + "', '" + m.getStaatsangehoerigkeit() + "', '" + m.isFoto_vorhanden() + "', '" + m.getPosition() + "')");
            String selectID = "SELECT MitgliederID FROM Mitglied WHERE vorname= '" + m.getVorname() + "' AND  name= '" + m.getName() + "' AND  TelefonNr= '" + m.getTelefonnr() + "';";
            ResultSet rs = stmt.executeQuery(selectID);
            int mitglID = 0;
            while (rs.next()) {
                mitglID = rs.getInt(1);
            }
            stmt.executeUpdate("INSERT INTO Geburtsdaten (GeburtsID, Geburtsdatum, Geburtsort) VALUES ('" + mitglID + "','" + g.getGeburtsdatum() + "','" + g.getGeburtsort() + "')");
            stmt.executeUpdate("INSERT INTO Kontodaten (kontoID, kreditinstitut, kontonr, blz, iban, bic) VALUES ('" + mitglID + "','" + k.getKreditinstitut() + "','" + k.getKontonr() + "','" + k.getBlz() + "','" + k.getIban() + "','" + k.getBic() + "')");
            if (st.getAustrittsdatum() == null) {
                stmt.executeUpdate("INSERT INTO Mitgliedsstatus (statusID, mitglied_seit, austrittsdatum, mitgliedsstatus) VALUES ('" + mitglID + "','" + st.getMitglied_seit() + "', null ,'" + st.getMitgliedsstatus() + "')");
            } else {
                stmt.executeUpdate("INSERT INTO Mitgliedsstatus (statusID, mitglied_seit, austrittsdatum, mitgliedsstatus) VALUES ('" + mitglID + "','" + st.getMitglied_seit() + "','" + st.getAustrittsdatum() + "','" + st.getMitgliedsstatus() + "')");
            }
            stmt.executeUpdate("INSERT INTO Studiuminfo (studID, aktSemester, studiengang) VALUES ('" + mitglID + "','" + si.getAktSemester() + "','" + si.getStudiengang() + "')");
            stmt.executeUpdate("INSERT INTO mitgliederteam (MitgliederID, Team) VALUES ('" + mitglID + "','" + t.getTeam() + "')");
            System.out.println("Datensatz erfolgreich angelegt.");
        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getMessage());
        }
        return "Nachricht vom Server: Datensatz wurde erfolgreich in die Datenbank gespeichert!";
    }

    /**
     * @Override public void selectMitglied(String[] args) throws
     * RemoteException { try { CRM_Server_DB_Connection c = new
     * CRM_Server_DB_Connection(); Connection con = c.getConnection(); String
     * abfrage = "SELECT "+args[0]+" FROM Mitglied WHERE "+args[1]+" =
     * '"+args[2]+"';"; System.out.println(abfrage); PreparedStatement pstmt =
     * con.prepareStatement(abfrage); ResultSet rs = pstmt.executeQuery();
     *
     * //TODO Objekte erzeugen und an Client übergeben while (rs.next()){ int
     * mitglID = rs.getInt(1); System.out.println(mitglID); //Mitglied m = new
     * Mitglied(); //m++; } System.out.println("Abfrage ausgeführt"); } catch
     * (SQLException e) { System.err.println("Fehler: " + e); } //return m; }
     */
    @Override
    public ArrayList<Mitglied> selectMitglied(String[] args) throws RemoteException {
        try {
            CRM_Server_DB_Connection c = new CRM_Server_DB_Connection();
            Connection con = c.getConnection();
            ArrayList<Mitglied> mitglieder = new ArrayList<>();

            //wenn keine Auswahl getroffen wird im Client, werden alle Mitglieder ohne Einschränkung ausgegeben
            if (args.length == 0) {
                String abfrage = "SELECT * FROM Mitglied;";
                PreparedStatement pstmt = con.prepareStatement(abfrage);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    int mitglID = rs.getInt(1);
                    String vorname = rs.getString(2);
                    String name = rs.getString(3);
                    String telefonnr = rs.getString(4);
                    String email = rs.getString(5);
                    String strasse_Hsnr = rs.getString(6);
                    int plz = rs.getInt(7);
                    String ort = rs.getString(8);
                    int angemeldet = rs.getInt(9);
                    String email_eRacing = rs.getString(10);
                    String fuehrerschein = rs.getString(11);
                    String vermerk = rs.getString(12);
                    int werkstattregeln = rs.getInt(13);
                    int serverzugang = rs.getInt(14);
                    String staatsangehoerigkeit = rs.getString(15);
                    int foto_vorhanden = rs.getInt(16);
                    String Position = rs.getString(17);
                    Mitglied m = new Mitglied(mitglID, vorname, name, telefonnr, email, strasse_Hsnr, plz, ort, angemeldet, email_eRacing, fuehrerschein, vermerk, werkstattregeln, serverzugang, staatsangehoerigkeit, foto_vorhanden, Position);
                    mitglieder.add(m);
                }

            }
            //wenn Client EIN Attribut abfrägt
            if (args.length == 2) {
                if ("Team".equals(args[0])) {
                    String abfrage = "SELECT MitgliederID FROM mitgliederteam WHERE " + args[0] + " = '" + args[1] + "';";
                    PreparedStatement pstmt = con.prepareStatement(abfrage);
                    ResultSet rs1 = pstmt.executeQuery();
                    int mitglID = 0;
                    while (rs1.next()) {
                        mitglID = rs1.getInt(1);
                        abfrage = "SELECT * FROM Mitglied WHERE MitgliederID = '" + mitglID + "';";
                        pstmt = con.prepareStatement(abfrage);
                        ResultSet rs2 = pstmt.executeQuery();

                        while (rs2.next()) {
                            int mitgliedID = rs2.getInt(1);
                            String vorname = rs2.getString(2);
                            String name = rs2.getString(3);
                            String telefonnr = rs2.getString(4);
                            String email = rs2.getString(5);
                            String strasse_Hsnr = rs2.getString(6);
                            int plz = rs2.getInt(7);
                            String ort = rs2.getString(8);
                            int angemeldet = rs2.getInt(9);
                            String email_eRacing = rs2.getString(10);
                            String fuehrerschein = rs2.getString(11);
                            String vermerk = rs2.getString(12);
                            int werkstattregeln = rs2.getInt(13);
                            int serverzugang = rs2.getInt(14);
                            String staatsangehoerigkeit = rs2.getString(15);
                            int foto_vorhanden = rs2.getInt(16);
                            String Position = rs2.getString(17);
                            Mitglied m = new Mitglied(mitgliedID, vorname, name, telefonnr, email, strasse_Hsnr, plz, ort, angemeldet, email_eRacing, fuehrerschein, vermerk, werkstattregeln, serverzugang, staatsangehoerigkeit, foto_vorhanden, Position);
                            mitglieder.add(m);
                        }
                    }

                } else {

                    String abfrage = "SELECT * FROM Mitglied WHERE " + args[0] + " = '" + args[1] + "';";
                    PreparedStatement pstmt = con.prepareStatement(abfrage);
                    ResultSet rs = pstmt.executeQuery();

                    while (rs.next()) {
                        int mitglID = rs.getInt(1);
                        String vorname = rs.getString(2);
                        String name = rs.getString(3);
                        String telefonnr = rs.getString(4);
                        String email = rs.getString(5);
                        String strasse_Hsnr = rs.getString(6);
                        int plz = rs.getInt(7);
                        String ort = rs.getString(8);
                        int angemeldet = rs.getInt(9);
                        String email_eRacing = rs.getString(10);
                        String fuehrerschein = rs.getString(11);
                        String vermerk = rs.getString(12);
                        int werkstattregeln = rs.getInt(13);
                        int serverzugang = rs.getInt(14);
                        String staatsangehoerigkeit = rs.getString(15);
                        int foto_vorhanden = rs.getInt(16);
                        String Position = rs.getString(17);
                        Mitglied m = new Mitglied(mitglID, vorname, name, telefonnr, email, strasse_Hsnr, plz, ort, angemeldet, email_eRacing, fuehrerschein, vermerk, werkstattregeln, serverzugang, staatsangehoerigkeit, foto_vorhanden, Position);
                        mitglieder.add(m);
                    }
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
            System.err.println("Fehler: " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Geburtsdaten> selectGeburtsdaten(String... args) throws RemoteException {
        try {
            CRM_Server_DB_Connection c = new CRM_Server_DB_Connection();
            Connection con = c.getConnection();
            ArrayList<Geburtsdaten> geburtsdaten = new ArrayList<>();

            String abfrage = "SELECT * FROM Geburtsdaten WHERE " + args[0] + " = '" + args[1] + "';";
            PreparedStatement pstmt = con.prepareStatement(abfrage);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int geburtsID = rs.getInt(1);
                String geburtsdatum = rs.getString(2);
                String geburtsort = rs.getString(3);
                Geburtsdaten g = new Geburtsdaten(geburtsID, geburtsdatum, geburtsort);
                geburtsdaten.add(g);
            }

            System.out.println("Abfrage ausgeführt");
            return geburtsdaten;
        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Kontodaten> selectKontodaten(String... args) throws RemoteException {
        try {
            CRM_Server_DB_Connection c = new CRM_Server_DB_Connection();
            Connection con = c.getConnection();
            ArrayList<Kontodaten> kontodaten = new ArrayList<>();

            String abfrage = "SELECT * FROM Kontodaten WHERE " + args[0] + " = '" + args[1] + "';";
            PreparedStatement pstmt = con.prepareStatement(abfrage);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int kontoID = rs.getInt(1);
                String kreditinstitut = rs.getString(2);
                int kontonr = rs.getInt(3);
                int blz = rs.getInt(4);
                String iban = rs.getString(5);
                String bic = rs.getString(6);
                Kontodaten k = new Kontodaten(kontoID, kreditinstitut, kontonr, blz, iban, bic);
                kontodaten.add(k);
            }

            System.out.println("Abfrage ausgeführt");
            return kontodaten;

        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Mitgliedsstatus> selectMitgliedsstatus(String... args) throws RemoteException {
        try {
            CRM_Server_DB_Connection c = new CRM_Server_DB_Connection();
            Connection con = c.getConnection();
            ArrayList<Mitgliedsstatus> mitgliedsstatus = new ArrayList<>();

            String abfrage = "SELECT * FROM Mitgliedsstatus WHERE " + args[0] + " = '" + args[1] + "';";
            PreparedStatement pstmt = con.prepareStatement(abfrage);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int statusID = rs.getInt(1);
                String mitglied_seit = rs.getString(2);
                String austrittsdatum = rs.getString(3);
                String mitgliedsStatus = rs.getString(4);

                Mitgliedsstatus st = new Mitgliedsstatus(statusID, mitglied_seit, austrittsdatum, mitgliedsStatus);
                mitgliedsstatus.add(st);
            }

            System.out.println("Abfrage ausgeführt");
            return mitgliedsstatus;

        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Studiuminfo> selectStudiuminfo(String... args) throws RemoteException {
        try {
            CRM_Server_DB_Connection c = new CRM_Server_DB_Connection();
            Connection con = c.getConnection();
            ArrayList<Studiuminfo> studiuminfo = new ArrayList<>();

            String abfrage = "SELECT * FROM Studiuminfo WHERE " + args[0] + " = '" + args[1] + "';";
            PreparedStatement pstmt = con.prepareStatement(abfrage);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int studID = rs.getInt(1);
                int aktSemester = rs.getInt(2);
                String studiengang = rs.getString(3);
                Studiuminfo si = new Studiuminfo(studID, aktSemester, studiengang);
                studiuminfo.add(si);
            }

            System.out.println("Abfrage ausgeführt");
            return studiuminfo;

        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Team> selectTeam(String... args) throws RemoteException {
        return null;
    }

    @Override
    public String updateMitglied(Mitglied m) throws RemoteException {
        try {
            CRM_Server_DB_Connection c = new CRM_Server_DB_Connection();
            Connection con = c.getConnection();

            Statement stmt = con.createStatement();
            stmt.executeUpdate("UPDATE laeracing.mitglied SET vorname= '" + m.getVorname() + "',  name= '" + m.getName() + "',  telefonnr= '" + m.getTelefonnr() + "',  email= '" + m.getEmail() + "',  Strasse_HsNr= '" + m.getStrasse_Hsnr() + "',  plz= '" + m.getPlz() + "',  ort= '" + m.getOrt() + "', angemeldet= '" + m.isAngemeldet() + "',  email_eRacing= '" + m.getEmail_eRacing() + "',  fuehrerschein= '" + m.getFuehrerschein() + "',  vermerk= '" + m.getVermerk() + "',  werkstattregeln= '" + m.isWerkstattregeln() + "',  serverzugang= '" + m.isServerzugang() + "',  staatsangehoerigkeit= '" + m.getStaatsangehoerigkeit() + "',  foto_vorhanden= '" + m.isFoto_vorhanden() + "', Position= '" + m.getPosition() + "' WHERE MitgliederID=" + m.getMitgliederID() + ";");
            return "Mitglied erfolgreich aktualisiert!";

        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getMessage());
        }
        return "Fehler 42";
    }

    @Override
    public String updateGeburtsdaten(Geburtsdaten g) throws RemoteException {
        try {
            CRM_Server_DB_Connection c = new CRM_Server_DB_Connection();
            Connection con = c.getConnection();

            Statement stmt = con.createStatement();
            stmt.executeUpdate("UPDATE laeracing.geburtsdaten SET geburtsdatum = '" + g.getGeburtsdatum() + "',  geburtsort= '" + g.getGeburtsort() + "' WHERE GeburtsID=" + g.getGeburtsID() + ";");   
            return "Geburtsdaten erfolgreich aktualisiert!";

        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getMessage());
        }
        return "Fehler 42.1";
    }
    
    @Override
    public String updateKontodaten(Kontodaten k) throws RemoteException {
        try {
            CRM_Server_DB_Connection c = new CRM_Server_DB_Connection();
            Connection con = c.getConnection();

            Statement stmt = con.createStatement();
            stmt.executeUpdate("UPDATE laeracing.kontodaten SET kreditinstitut= '" + k.getKreditinstitut() + "',  kontonr= '" + k.getKontonr() + "', blz= '" + k.getBlz() + "', iban= '" + k.getIban() + "', bic= '" + k.getBic()+ "' WHERE KontoID=" + k.getKontoID() + ";");   
            return "Kontodaten erfolgreich aktualisiert!";

        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getMessage());
        }
        return "Fehler 42.2";
    }

    @Override
    public String updateMitgliedsstatus(Mitgliedsstatus st) throws RemoteException {
        try {
            CRM_Server_DB_Connection c = new CRM_Server_DB_Connection();
            Connection con = c.getConnection();

            Statement stmt = con.createStatement();
            stmt.executeUpdate("UPDATE laeracing.geburtsdaten SET mitglied_seit = '" + st.getMitglied_seit() + "',  austrittsdatum = '" + st.getAustrittsdatum() + "', mitgliedsstatus = '" + st.getMitgliedsstatus() + "',WHERE StatusID=" + st.getStatusID() + ";");   
            return "Mitgliedsstatus erfolgreich aktualisiert!";

        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getMessage());
        }
        return "Fehler 42.3";
    }

    @Override
    public String updateStudiuminfo(Studiuminfo si) throws RemoteException {
        try {
            CRM_Server_DB_Connection c = new CRM_Server_DB_Connection();
            Connection con = c.getConnection();

            Statement stmt = con.createStatement();
            stmt.executeUpdate("UPDATE laeracing.geburtsdaten SET aktSemester = '" + si.getAktSemester() + "',  studiengang = '" + si.getStudiengang() + "' WHERE StudID=" + si.getStudID() + ";");   
            return "Studiuminfo erfolgreich aktualisiert!";

        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getMessage());
        }
        return "Fehler 42.4";
    }

    @Override
    public String updateTeam(Team t, int ID) throws RemoteException {
        try {
            CRM_Server_DB_Connection c = new CRM_Server_DB_Connection();
            Connection con = c.getConnection();

            Statement stmt = con.createStatement();
            stmt.executeUpdate("UPDATE laeracing.mitgliederteam SET team = '" + t.getTeam() + "' WHERE MitgliederID=" + ID + ";");   
            return "Team erfolgreich aktualisiert!";

        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getMessage());
        }
        return "Fehler 42.5";
    }
    
    @Override
    public String deleteMitglied(int mID) throws RemoteException {
        try {
            CRM_Server_DB_Connection c = new CRM_Server_DB_Connection();
            Connection con = c.getConnection();
            int mitglID = 0;
            String abfrage = "SELECT MAX(MitgliederID) FROM Mitglied;";
            PreparedStatement pstmt = con.prepareStatement(abfrage);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                mitglID = rs.getInt(1);
            }
            if (mID <= mitglID) {
                Statement stmt = con.createStatement();
                stmt.executeUpdate("UPDATE laeracing.mitglied SET angemeldet=null , email_eRacing=null ,  fuehrerschein=null , vermerk=null , werkstattregeln=null , serverzugang=null , staatsangehoerigkeit=null , foto_vorhanden=null , Position='Ausgetreten' WHERE MitgliederID=" + mID + ";");
                System.out.println("Datensatz erfolgreich gelöscht.");
                stmt.executeUpdate("UPDATE Mitgliedsstatus SET austrittsdatum = CURRENT_DATE , mitgliedsstatus = 'ausgetreten' WHERE statusID=" + mID + ";");
                System.out.println("Der Mitgliedsstatus wurde erfolgreich aktualisiert.");
                stmt.executeUpdate("UPDATE Geburtsdaten SET geburtsdatum = null, geburtsort = null WHERE GeburtsID=" + mID + ";");
                System.out.println("Die Geburtsdaten wurden erfolgreich gelöscht.");
                stmt.executeUpdate("UPDATE Kontodaten SET kreditinstitut = null , kontonr = null, blz = null, iban = null, bic = null WHERE KontoID=" + mID + ";");
                System.out.println("Die Kontodaten wurden erfolgreich gelöscht.");
                stmt.executeUpdate("UPDATE Studiuminfo SET aktSemester = null, studiengang = null WHERE StudID=" + mID + ";");
                System.out.println("Studiuminfo wurde erfolgreich gelöscht.");
                stmt.executeUpdate("UPDATE Mitgliederteam SET team = null WHERE MitgliederID=" + mID + ";");
                System.out.println("Das Mitgliederteam wurde erfolgreich gelöscht.");
                return "Mitglied erfolgreich gelöscht und Mitgliedsstatus aktualisiert!";
            } else {
                return "Das Mitglied mit der ID " + mID + " existiert nicht!";
            }

        } catch (SQLException e) {
            System.err.println("Fehler: " + e.getMessage());
        }
        return "Fehler 43.0";
    }

    @Override
    public void updateSemester() throws RemoteException {
        try {
            CRM_Server_DB_Connection c = new CRM_Server_DB_Connection();
            Connection con = c.getConnection();
            String updateSem = "UPDATE LAeRacing.studiuminfo SET aktSemester=aktSemester+1;";
            Date d = new Date(System.currentTimeMillis());
            if ("15.3".equals(d.getDate() + "." + (d.getMonth() + 1)) || "1.10".equals(d.getDate() + "." + (d.getMonth() + 1))) {
                Statement stmt = con.createStatement();
                stmt.executeUpdate(updateSem);
            }
        } catch (SQLException e) {
            System.err.println("Etwas hat nicht funktioniert. Fehler: " + e);
        }
    }
}

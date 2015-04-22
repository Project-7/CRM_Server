
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
/**
 *
 * @author MarkusH und RobertK
 * @version 1.0
 */
public class CRM_Server_Main {

    /**
     * @param args the command line arguments
     */
    
    
    public static void main (String args[]){
        try {
            Registry reg = LocateRegistry.createRegistry(1099);
            reg.rebind("server", new CRM_Server_Methoden());
            System.out.println("Server started");
        } catch (Exception e) {
            System.err.println("Fehler"+e.getMessage());
        }
        //eigenen Thread öffnen für updateSemester Methode
    }
    
}

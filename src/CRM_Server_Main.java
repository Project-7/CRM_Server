
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author MarkusH
 */
public class CRM_Server_Main {

    /**
     * @param args the command line arguments
     */
    public static void main (String args[]){
        try {
            Registry reg = LocateRegistry.createRegistry(1099);
            reg.rebind("server", new CRM_Server());
            System.out.println("Server started");
        } catch (Exception e) {
            System.err.println("Fehler"+e.getMessage());
        }
    }
    
}

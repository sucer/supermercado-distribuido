/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ppal;

import java.io.File;
import java.io.FileInputStream;
import java.rmi.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.util.Properties;
/**
 *
 * @author andres
 */
public class Servidor {
     public static void main(String args[]){
        String registryURL;
        String host;
        int RMIPortNum;
        try{
           Properties props = new Properties();
           props.load(new FileInputStream(new File("src\\ppal\\configuracion.properties")));
           host = props.getProperty("host","localhost");
           RMIPortNum=Integer.parseInt(props.getProperty("puerto","733"));
           startRegistry(RMIPortNum);
           IntrOficinaCentral exportedObj = new OficinaCentral();
           registryURL = "rmi://" + host + ":" + RMIPortNum + "/oficinaCentral";
           Naming.rebind(registryURL, exportedObj);
           System.out.println("Servidor iniciado en "+host+":"+RMIPortNum +" con URL: "+registryURL);
        }// end try
        catch (Exception re) {
          System.out.println("Excepcion en Servidor Oficina Central: " + re);
          re.printStackTrace();
        } // end catch
    }
    //This method starts a RMI registry on the local host, if
    //it does not already exists at the specified port number.
    private static void startRegistry(int RMIPortNum)
        throws RemoteException{
        try {
            Registry registry =LocateRegistry.getRegistry(RMIPortNum);
            registry.list( );
            // This call will throw an exception
            // if the registry does not already exist
    }
    catch (RemoteException e) {
      // No valid registry at that port.
        Registry registry =LocateRegistry.createRegistry(RMIPortNum);
    }
  } // end startRegistry

}

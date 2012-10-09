/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ppal;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Usuario UTP
 */
public interface IntrBodega extends Remote {
    
    public void recibirCatalogo(List<Producto> catalogoProducto) throws RemoteException;
    
    public int saldo(Producto producto) throws RemoteException;
    
    public void despachar(Producto producto,int cantidad,IntrSupermercado supermercado) throws RemoteException;

}

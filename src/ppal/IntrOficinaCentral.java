/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ppal;

import java.rmi.Remote;

/**
 *
 * @author Usuario UTP
 */
public interface IntrOficinaCentral extends Remote {
    
    public boolean registrarSupermercado(IntrSupermercado intrSupermercado) throws java.rmi.RemoteException;
    
    public boolean registrarBodega(IntrBodega intrBodega) throws java.rmi.RemoteException;
    
    public void desconectarSupermercado(IntrSupermercado intrSupermercado) throws java.rmi.RemoteException;
    
    public void desconectarBodega(IntrBodega intrBodega) throws java.rmi.RemoteException;
    
    public void solicitud(Producto producto, int cantidad, IntrSupermercado intrSupermercado) throws java.rmi.RemoteException;
    
    public void cambiarPrecio(Producto producto, long precio) throws java.rmi.RemoteException;
    
}

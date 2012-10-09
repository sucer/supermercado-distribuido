/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ppal;

import java.util.List;

/**
 *
 * @author Usuario UTP
 */
public interface IntrSupermercado extends java.rmi.Remote{

    public boolean ingresarDescargar(Producto producto, int cantidad) throws java.rmi.RemoteException;

    public void solicitud(Producto producto, int cantidad) throws java.rmi.RemoteException;

    public void cambiarPrecio(Producto producto, long nuevoPrecio)throws java.rmi.RemoteException;

    public int saldo(Producto producto) throws java.rmi.RemoteException;

    public void recibirCatalogo(List<Producto> catalogoProducto) throws java.rmi.RemoteException;

}

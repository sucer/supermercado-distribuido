/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ppal;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario UTP
 */
public class MonitorInventario implements Runnable{
    private Supermercado supermercado;
    private boolean prendido=true;

    public void run() {
        while (prendido){
            for ( ItemInventario itemInventario : supermercado.getItemsInventario()){
                if (itemInventario.getCantidad()<itemInventario.getReOrden() && !itemInventario.isPedido() ){
                    itemInventario.setPedido(true);
                    try {
                        supermercado.solicitud(itemInventario.getProducto(), itemInventario.getReCompra());
                    } catch (RemoteException ex) {
                        Logger.getLogger(MonitorInventario.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    /**
     * @return the supermercado
     */
    public Supermercado getSupermercado() {
        return supermercado;
    }

    /**
     * @param supermercado the supermercado to set
     */
    public void setSupermercado(Supermercado supermercado) {
        this.supermercado = supermercado;
    }

    /**
     * @return the prendido
     */
    public boolean isPrendido() {
        return prendido;
    }

    /**
     * @param prendido the prendido to set
     */
    public void setPrendido(boolean prendido) {
        this.prendido = prendido;
    }
}

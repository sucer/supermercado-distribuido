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
public class MonitorCajas implements Runnable{
    private Supermercado supermercado;
    private List<Caja> cajas;
    private boolean prendido=true;

    /**
     * @return the cajas
     */
    public List<Caja> getCajas() {
        return cajas;
    }

    /**
     * @param cajas the cajas to set
     */
    public void setCajas(List<Caja> cajas) {
        this.cajas = cajas;
    }

    public boolean existenOtrasCajasColaMenorMaximo(Caja caja){
        for (Caja cajaAux:cajas){
            if ( !cajaAux.equals(caja) && cajaAux.isActiva() && cajaAux.getCola().size()<Supermercado.NUMMAXIMOMERCADOS)
                return true;
        }
        return false;
    }

    public void run() {
        while (prendido){
            for (Caja caja:cajas){
                
                if (caja.isActiva() && caja.getCola().size()>=Supermercado.NUMMAXIMOMERCADOS && !existenOtrasCajasColaMenorMaximo(caja) )
                    supermercado.iniciarCaja();
                if ( caja.isActiva() && caja.getCola().size()<Supermercado.NUMMINIMOMERCADOS && existenOtrasCajasColaMenorMaximo(caja))
                    supermercado.cerrarCaja(caja);
            }
        }
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

}

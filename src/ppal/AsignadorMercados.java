/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ppal;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario UTP
 */
public class AsignadorMercados implements Runnable{

    private List<Mercado> mercados=new ArrayList<Mercado>();
    private Supermercado supermercado;
    private boolean prendido=true;

    public void putMercado(Mercado mercado){
        mercados.add(mercado);
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

    private Caja getCajaActivaMenorMaximoNumMercados(){
        for (Caja caja:supermercado.getCajas()){
            if (caja.isActiva() && caja.getCola().size()<Supermercado.NUMMAXIMOMERCADOS){
                return caja;
            }
        }
        return null;
    }

    private Caja getCajaActiva(){
        for (Caja caja:supermercado.getCajas()){
            if (caja.isActiva())
                return caja;
        }
        return null;
    }

    private Caja getCajaAsignarMercado(){
        Caja caja=getCajaActivaMenorMaximoNumMercados();
        if (caja==null)
            caja=getCajaActiva();
        return caja;
    }

    private Mercado getMercadoAsignar(){
        if (mercados.size()>0){
            return mercados.get(0);
        }
        return null;
    }

    public void run() {
        while (prendido || mercados.size()>0){
            Mercado mercado=getMercadoAsignar();
            if (mercado!=null){
                Caja caja=getCajaAsignarMercado();
                supermercado.setMensajeAsignador("Asignando "+mercado+" a "+caja);
                caja.putMercado(mercado);
                caja.actualizarCola();                   
                mercados.remove(mercado);
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
}

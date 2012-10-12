/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ppal;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author John Bayron
 */
public class Caja  extends Thread{
    private String nombre;
    private List<Mercado> cola=new ArrayList<Mercado>();
    private List<ItemMovCaja> itemsMovCaja=new ArrayList<ItemMovCaja>();
    private Supermercado supermercado;
    private PnlCaja pnlCaja;
    private boolean prendida=true;
    private boolean activa=false;

    public Caja(String nombre) {
        super();
        this.nombre = nombre;
        pnlCaja=new PnlCaja(nombre,itemsMovCaja,cola);
    }
        
    @Override
    public String toString(){
        return nombre;
    }
    
    public void putMercado(Mercado mercado){
        cola.add(mercado);
    }

    /**
     * @return the cola
     */
    public List<Mercado> getCola() {
        return cola;
    }

    /**
     * @param cola the cola to set
     */
    public void setCola(List<Mercado> cola) {
        this.cola = cola;
    }

    /**
     * @return the prendida
     */
    public boolean isPrendida() {
        return prendida;
    }

    /**
     * @param prendida the prendida to set
     */
    public void setPrendida(boolean prendida) {
        this.prendida = prendida;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void run() {
        while (prendida){
            if (cola.size()>0){
                try {
                    Mercado mercado;
                    mercado=cola.get(0);
                    if (mercado!=null){
                        pnlCaja.setMensaje("Registrando "+mercado);
                        float totalMercado=facturar(mercado);
                        pnlCaja.setMensaje("Finalizando registro "+mercado);
                        cola.remove(mercado);
                        pnlCaja.actualizarCola(cola.size());
                        itemsMovCaja.clear();
                        pnlCaja.refrescarProductos(nombre);
                        Thread.sleep(500);
                        pnlCaja.setMensaje("");
                        pnlCaja.limpiar();
                    }                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public float facturar(Mercado mercado){
        long totalMercado=0;
        for (ItemMercado itemMercado: mercado.getItemsMercado()){
            try {
                ItemInventario itemInventario=supermercado.buscarItemInventario(itemMercado.getProducto());
                ItemMovCaja itemMovCaja=new ItemMovCaja(itemMercado.getProducto(), itemMercado.getCantidad(), itemInventario.getPrecioVenta());
                Thread.sleep(600);
                supermercado.ingresarDescargar(itemMercado.getProducto(), itemMercado.getCantidad() * (-1));
                itemsMovCaja.add(itemMovCaja);
                totalMercado += itemInventario.getPrecioVenta() * itemMercado.getCantidad();
                pnlCaja.mostrarItemActual (itemMovCaja.getProducto(), itemMovCaja.getCantidad(), itemMovCaja.getValorUnitario(), itemMovCaja.getSubtotal(), totalMercado, itemsMovCaja.size()-1);
                Thread.sleep(600);      
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return totalMercado;
    }
    
    public void actualizarCola(){
        pnlCaja.actualizarCola(cola.size());
    }
    
    

    /**
     * @return the activa
     */
    public boolean isActiva() {
        return activa;
    }

    /**
     * @param inactiva the inactiva to set
     */
    public void setActiva(boolean activa) {
        this.activa = activa;
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
     * @return the pnlCaja
     */
    public PnlCaja getPnlCaja() {
        return pnlCaja;
    }

    /**
     * @param pnlCaja the pnlCaja to set
     */
    public void setPnlCaja(PnlCaja pnlCaja) {
        this.pnlCaja = pnlCaja;
    }
}

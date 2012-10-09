/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ppal;

/**
 *
 * @author Usuario UTP
 */
public class ItemMovCaja {
    private Producto producto;
    private int cantidad;
    private long valorUnitario;

    public ItemMovCaja(Producto producto, int cantidad, long valorUnitario) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.valorUnitario = valorUnitario;
    }


    /**
     * @return the producto
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /**
     * @return the cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the valorUnitario
     */
    public long getValorUnitario() {
        return valorUnitario;
    }

    /**
     * @param valorUnitario the valorUnitario to set
     */
    public void setValorUnitario(long valorUnitario) {
        this.valorUnitario = valorUnitario;
    }
    
    public long getSubtotal(){
        return valorUnitario*cantidad;
    }


}

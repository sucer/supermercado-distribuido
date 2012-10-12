/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ppal;

/**
 *
 * @author Usuario UTP
 */
public class ItemMercado {
    private Producto producto;
    private int cantidad;

    public ItemMercado(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
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
}

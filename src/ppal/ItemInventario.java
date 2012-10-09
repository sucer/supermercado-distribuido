/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ppal;

/**
 *
 * @author Usuario UTP
 */
public class ItemInventario {
    private Producto producto;
    private int cantidad;
    private int cantidadVirtual;
    private long precioVenta;
    private int reOrden;
    private int reCompra;
    private boolean pedido=false;

    public ItemInventario(Producto producto, int cantidad, int cantidadVirtual, long precioVenta, int reOrden, int reCompra) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.cantidadVirtual = cantidadVirtual;
        this.precioVenta = precioVenta;
        this.reOrden = reOrden;
        this.reCompra = reCompra;
    }

    @Override
    public boolean equals(Object o){
        if  ( (o !=null) && (o instanceof ItemInventario) ){
            if ( ((ItemInventario)o).getProducto().getReferencia().equals(this.getProducto().getReferencia()) )
                    return true;
        }
        return false;
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
     * @return the precioVenta
     */
    public long getPrecioVenta() {
        return precioVenta;
    }

    /**
     * @param precioVenta the precioVenta to set
     */
    public void setPrecioVenta(long precioVenta) {
        this.precioVenta = precioVenta;
    }

    /**
     * @return the reOrden
     */
    public int getReOrden() {
        return reOrden;
    }

    /**
     * @param reOrden the reOrden to set
     */
    public void setReOrden(int reOrden) {
        this.reOrden = reOrden;
    }

    /**
     * @return the reCompra
     */
    public int getReCompra() {
        return reCompra;
    }

    /**
     * @param reCompra the reCompra to set
     */
    public void setReCompra(int reCompra) {
        this.reCompra = reCompra;
    }

    /**
     * @return the cantidadVirtual
     */
    public int getCantidadVirtual() {
        return cantidadVirtual;
    }

    /**
     * @param cantidadVirtual the cantidadVirtual to set
     */
    public void setCantidadVirtual(int cantidadVirtual) {
        this.cantidadVirtual = cantidadVirtual;
    }

    /**
     * @return the pedido
     */
    public boolean isPedido() {
        return pedido;
    }

    /**
     * @param pedido the pedido to set
     */
    public void setPedido(boolean pedido) {
        this.pedido = pedido;
    }

   

}

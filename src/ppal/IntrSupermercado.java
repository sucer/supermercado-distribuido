/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ppal;

/**
 *
 * @author Usuario UTP
 */
public interface IntrSupermercado {

    public boolean ingresarDescargar(Producto producto, int cantidad);

    public void solicitud(Producto producto, int cantidad);

    public void cambiarPrecio(Producto producto, long nuevoPrecio);

    public int saldo(Producto producto);

    public boolean crearItemInventario(Producto producto,int cantidad, long precioVenta, int reOrden, int reCompra);

}

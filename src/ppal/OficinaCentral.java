/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ppal;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario UTP
 */
public class OficinaCentral extends UnicastRemoteObject implements IntrOficinaCentral {

    private List<Producto> catalogoProductos = new ArrayList<Producto>();
    private List<IntrSupermercado> lstSupermercados = new ArrayList<IntrSupermercado>();
    private List<IntrBodega> lstBodegas = new ArrayList<IntrBodega>();

    public OficinaCentral() throws RemoteException {
        super();
        System.out.println("Inicializando la oficina central");
        cargarProductos();
    }

    public synchronized void registrarSupermercado(IntrSupermercado intrSupermercado) throws RemoteException {
        if (!lstSupermercados.contains(intrSupermercado)) {
            lstSupermercados.add(intrSupermercado);
            System.out.println("Nuevo Supermercado Conectado..");
            enviarCatalogoSupermercado(intrSupermercado);
        }
    }

    public synchronized void registrarBodega(IntrBodega intrBodega) throws RemoteException {
        System.out.println("Registrando bodega "+intrBodega.hashCode());
        if (!lstBodegas.contains(intrBodega)) {
            lstBodegas.add(intrBodega);
            System.out.println("Se registro la bodega "+intrBodega.hashCode());
            enviarCatalogoBodega(intrBodega);
        }
    }

    public void desconectarSupermercado(IntrSupermercado intrSupermercado) throws RemoteException {
        if (lstSupermercados.remove(intrSupermercado)) {
            System.out.println("Supermercado Desconectado: ");
        } else {
            System.out.println("El Supermercado no se pudo Desconectar: ");
        }
    }

    public void desconectarBodega(IntrBodega intrBodega) throws RemoteException {
        if (lstSupermercados.remove(intrBodega)) {
            System.out.println("Bodega Desconectado: ");
        } else {
            System.out.println("La Bodega no se pudo Desconectar: ");
        }
    }
   
    public synchronized void solicitud(Producto producto, int cantidad, IntrSupermercado supermercado) throws RemoteException {
        System.out.println("Solicitud de " + cantidad + " " + producto.getDescripcion() + " del Supermercado " + supermercado.hashCode());
        //Busca en las bodegas si disponibilidad
        boolean despachado = false;
        for (IntrBodega iterBodega : lstBodegas) {
            int saldo = iterBodega.saldo(producto);
            if (saldo >= cantidad) {
                iterBodega.despachar(producto, cantidad, supermercado);
                despachado = true;
                System.out.println("Despachado Producto " + producto.getDescripcion() + " cantidad " + cantidad + " a supermercado " + supermercado.hashCode() +" de la bodega "+iterBodega.hashCode());
                break;
            }
            //consulta la cantidad del producto en la bodega
            System.out.println("Saldo del Producto: " + producto.getDescripcion() + " es:" + saldo + " en la Bodega " + iterBodega.hashCode());
        }
        //Busca en los otros supermercados si hay disponibilidad
        /*for (IntrSupermercado iterSupermercado : lstSupermercados) {
            //consulta la cantidad del producto en la bodega
            if (!iterSupermercado.equals(supermercado)) {
                int saldo = iterSupermercado.saldo(producto);
                System.out.println("Saldo del Producto: " + producto.getDescripcion() + " es:" + saldo + " en el Supermercado " + iterSupermercado.hashCode());
                if (saldo >= cantidad) {
                    (producto, cantidad, supermercado);
                    despachado = true;
                    System.out.println("Despachado Producto " + producto.getDescripcion() + " cantidad " + cantidad + " a supermercado " + supermercado.hashCode());
                    break;
                }
            }
        }*/
        if (!despachado) {
            System.out.println("El producto " + producto.getDescripcion() + " esta agotado en toda la cadena de supermercados debe hacerse la solicitud de compra con el proveedor ");
        }
    }

    public synchronized void cambiarPrecio(Producto producto, long precio) throws RemoteException {
        for (IntrSupermercado iterSupermercado : lstSupermercados) {
            //consulta la cantidad del producto en la bodega
            iterSupermercado.cambiarPrecio(producto, precio);
        }
    }
    /**
     * @return the catalogoProductos
     */
    public List<Producto> getCatalogoProductos() {
        return catalogoProductos;
    }

    /**
     * @param catalogoProductos the catalogoProductos to set
     */
    public void setCatalogoProductos(List<Producto> catalogoProductos) {
        this.catalogoProductos = catalogoProductos;
    }

    /**
     * @return the lstSupermercados
     */
    public List<IntrSupermercado> getLstSupermercados() {
        return lstSupermercados;
    }

    /**
     * @param lstSupermercados the lstSupermercados to set
     */
    public void setLstSupermercados(List<IntrSupermercado> lstSupermercados) {
        this.lstSupermercados = lstSupermercados;
    }

    private void enviarCatalogoSupermercado(IntrSupermercado intrSupermercado) throws java.rmi.RemoteException {
        System.out.println("Enviando catalogo de productos a supermercado "+intrSupermercado.hashCode());
        intrSupermercado.recibirCatalogo(catalogoProductos);
    }

    private void enviarCatalogoBodega(IntrBodega intrBodega) throws java.rmi.RemoteException {
        System.out.println("Enviando catalogo de productos a bodega "+intrBodega.hashCode());
        intrBodega.recibirCatalogo(catalogoProductos);
    }

    private void cargarProductos() {
        System.out.println("Creando catalogo de productos ");
        catalogoProductos.add(new Producto("001", "Arroz Roa x 500g"));
        catalogoProductos.add(new Producto("002", "Jabon Rey x 300g"));
        catalogoProductos.add(new Producto("003", "Azucar Manuelita x 1000g"));
        catalogoProductos.add(new Producto("004", "Papa nevada x 10 Kls"));
        catalogoProductos.add(new Producto("005", "Frijol cargamanto x 500g"));
        catalogoProductos.add(new Producto("006", "Crema dental Colgate x 100cm"));
        catalogoProductos.add(new Producto("007", "Cubeta huevos Avinal x 12unds"));
        catalogoProductos.add(new Producto("008", "Jabon Protex x 150g"));
        catalogoProductos.add(new Producto("009", "Sal Refisal x 500g"));
        catalogoProductos.add(new Producto("010", "Lenteja x 500g"));
        catalogoProductos.add(new Producto("011", "Desodorante Axe"));
        catalogoProductos.add(new Producto("012", "Blanquillo x500g"));
        catalogoProductos.add(new Producto("013", "Garbanzo x500g"));
        catalogoProductos.add(new Producto("014", "Frijol bola roja x 500g"));
        catalogoProductos.add(new Producto("015", "Papel higienico El Rosal x und"));
        catalogoProductos.add(new Producto("016", "Aceite Premier x 1000cm"));
        catalogoProductos.add(new Producto("017", "Cepillo dental Colgate Zig Zag"));
        catalogoProductos.add(new Producto("018", "Galletas Saltinas taco"));
        catalogoProductos.add(new Producto("019", "Arroz Diana x 500g"));
        catalogoProductos.add(new Producto("020", "Jabon Vel rosita x 300g"));
        catalogoProductos.add(new Producto("021", "Zucaritas x 300g"));
        catalogoProductos.add(new Producto("022", "Margarina Premier x 500g"));
        catalogoProductos.add(new Producto("023", "Atun Isabel x 300g"));
        catalogoProductos.add(new Producto("024", "Leche Klim x 400"));
        catalogoProductos.add(new Producto("025", "Pollo completo unidad"));
        catalogoProductos.add(new Producto("026", "Condones Today normal x 3unds"));
        catalogoProductos.add(new Producto("027", "Margarina Rama x500"));
        catalogoProductos.add(new Producto("028", "Leche condensada La lechera x 100cm"));
        catalogoProductos.add(new Producto("029", "Arroz Roa x 3000g"));
        catalogoProductos.add(new Producto("030", "Desodorante Rexona clinical"));
        System.out.println("se crearon "+catalogoProductos.size()+" productos");
    }
}

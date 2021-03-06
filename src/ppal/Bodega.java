/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ppal;

import java.io.File;
import java.io.FileInputStream;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author Usuario UTP
 */
public class Bodega extends UnicastRemoteObject implements IntrBodega{
    
    FrmBodega frmBodega;
    
    private List<Producto> catalogoProductos = new ArrayList<Producto>();
    private List<ItemInventario> inventario = new ArrayList<ItemInventario>();
    private String nombre;
    private String registryURL;
    private String host;
    private int RMIPortNum;
    private final int REORDEN=10;
    private final int RECOMPRA=10;
    private final int PRECIOMINIMO=1000;
    private final int PRECIOMAXIMO=90000;
    private final int CANTIDADMINIMAPRODUCTO=1;
    private final int CANTIDADMAXIMAPRODUCTO=30;
    
    
    
    
    
    public Bodega() throws RemoteException {
        super();
                
        frmBodega=new FrmBodega(this, inventario);
        frmBodega.setLocationRelativeTo(null);
                
    }
    
    public void iniciar(){    
        frmBodega=new FrmBodega(this, inventario);
        frmBodega.setLocationRelativeTo(null);
        frmBodega.setTitle(nombre);
        cargarInventario();
        frmBodega.setVisible(true); 
    }
    
    public boolean conectarOficinaCentral() {                    
        try{
            Properties props = new Properties();
            props.load(new FileInputStream(new File("src\\ppal\\configuracion.properties")));
            host = props.getProperty("host","localhost");
            RMIPortNum=Integer.parseInt(props.getProperty("puerto","733"));
            registryURL = "rmi://" + host + ":" + RMIPortNum + "/oficinaCentral";
        }catch (Exception re) {
            frmBodega.mostrarMensajeError("Problemas con la conexión!\nVerifique que la Oficina central ya inició\nó que el archivo de propiedades este bien configurado");
            System.exit(0);
        }
        return true;
    }
    
    public boolean registrarAnteOficinaCentral(){          
        boolean registrado=true;
        do {
            this.nombre=frmBodega.mostrarDialogoObtenerNombre();        
            try{
                IntrOficinaCentral oficinaCentral=(IntrOficinaCentral)Naming.lookup(registryURL);
                //registrando bodega
                registrado=oficinaCentral.registrarBodega(this);
                //System.out.println("Bodega registrado! "+this.hashCode());
                if (!registrado){
                        frmBodega.mostrarMensajeError("No se pudo registrar, bodega ya esta registrado");
                        registrado=false;
                }           
            }
            catch (Exception re) {
                frmBodega.mostrarMensajeError("Problemas con la conexión!\nVerifique que la Oficina central ya inició\nó que el archivo de propiedades esté bien configurado");
                //re.printStackTrace();
                System.exit(0);
                registrado= false;
            }
        }while(!registrado);  
        
        return registrado;
        
    }
    
    
    public void recibirCatalogo(List<Producto> catalogoProducto) throws RemoteException {
        System.out.println("Recibiendo Catalogo de productos...");
        catalogoProductos=catalogoProducto;
    }

    public int saldo(Producto producto) throws RemoteException {
        ItemInventario itemInventario=buscarItemInventario(producto);
        if (itemInventario!=null){
            return itemInventario.getCantidad();
        }
        return 0;
    }
    
    public void despachar(Producto producto, int cantidad, IntrSupermercado supermercado) throws RemoteException {
        if (cantidad > 0) {
            //System.out.println("Despachando producto " + producto.getDescripcion() + " cantidad " + cantidad + "de la bodega " + this.hashCode() + " al supermercado " + supermercado.hashCode());
            frmBodega.setMensajeRecepcionEnvioPedidos("Despachando " +cantidad+" unds de "+ producto +  " al supermercado " + supermercado.getNombre() );
            frmBodega.setMensajeInventario("Descargando " +cantidad+" unds de "+ producto);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            ingresarDescargar(producto, cantidad * -1);
            frmBodega.refrescarInventario();
            //System.out.println("Saldo producto " + producto.getDescripcion() + ": " + saldo(producto) + " en la bodega : " + this.hashCode());
            supermercado.ingresarDescargar(producto, cantidad);
            //System.out.println("Saldo producto " + producto.getDescripcion() + ": " + supermercado.saldo(producto) + " en supermercado: " + supermercado.hashCode());
        }
    }
    
     public boolean ingresarDescargar(Producto producto, int cantidad) {
        ItemInventario itemInventario=buscarItemInventario(producto);
        if (itemInventario!=null){
            itemInventario.setCantidad(itemInventario.getCantidad()+cantidad);
            return true;
        }
        return false;
    }
    
    private ItemInventario buscarItemInventario(Producto producto){
        for (ItemInventario itemInventario: inventario){
            if ( itemInventario.getProducto().equals(producto)){
                return itemInventario;
            }
        }
        return null;
    }
    
    private boolean productoEstaEnInventario(Producto producto){
        for (ItemInventario itemInventario: inventario){
            if ( itemInventario.getProducto().equals(producto)){
                return true;
            }
        }
        return false;
    }
    
    public boolean crearItemInventario(Producto producto, int cantidad, long precioVenta, int reOrden, int reCompra) {
        if (!productoEstaEnInventario(producto)){
            inventario.add(new  ItemInventario(producto, cantidad, cantidad, precioVenta, reOrden, reCompra));
            return true;
        }
        return false;
    }
    
        
    public void desconectarOficinaCentral() {
        try{
           IntrOficinaCentral oficinaCentral=(IntrOficinaCentral)Naming.lookup(getRegistryURL());
           //registrando supermercado
           oficinaCentral.desconectarBodega(this);
           System.out.println("Bodega registrada!");
        }// end try
        catch (Exception re) {
          System.out.println("Excepcion en Bodega: " + re);
          re.printStackTrace();
        }
    }
    
     private void cargarInventario() {
        System.out.println("Creando inventario de productos en la Bodega "+this.hashCode());
        int cantidadProducto=0;
        long precio=0;
        for(Producto producto : catalogoProductos){
            //genera aleatoriamente la cantidad
            cantidadProducto= (int)Math.floor(Math.random()*CANTIDADMAXIMAPRODUCTO+CANTIDADMINIMAPRODUCTO);
            //genera aleatoriamente el precio del producto
            precio=(long)Math.floor(Math.random()*PRECIOMAXIMO+PRECIOMINIMO);
            //crea el item en el inventario
            crearItemInventario(producto, cantidadProducto, precio, REORDEN, RECOMPRA);
            System.out.println("Creado producto "+producto.getDescripcion()+" cantidad: "+cantidadProducto +" precio "+precio);
        }
    }

    /**
     * @return the registryURL
     */
    public String getRegistryURL() {
        return registryURL;
    }

    /**
     * @param registryURL the registryURL to set
     */
    public void setRegistryURL(String registryURL) {
        this.registryURL = registryURL;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the RMIPortNum
     */
    public int getRMIPortNum() {
        return RMIPortNum;
    }

    /**
     * @param RMIPortNum the RMIPortNum to set
     */
    public void setRMIPortNum(int RMIPortNum) {
        this.RMIPortNum = RMIPortNum;
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
    
    public static void main(String args[]) throws RemoteException{
        Bodega bodega=new Bodega();
        if (bodega.conectarOficinaCentral()){      
            if (bodega.registrarAnteOficinaCentral()){
                bodega.iniciar();
            }
        }

    }
}

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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author John Bayron
 */
public class Supermercado extends UnicastRemoteObject implements IntrSupermercado {
    
    FrmSupermercado frmSupermercado;

    private List<Producto> catalogoProductos = new ArrayList<Producto>();
    private List<ItemInventario> itemsInventario=new ArrayList<ItemInventario>();
    private List<Caja> cajas=new ArrayList<Caja>();
    private MonitorCajas monitorCajas;
    private MonitorInventario monitorInventario;
    private FabricaMercados fabricaMercados;
    private AsignadorMercados asignadorMercados;
    private boolean prendido;
    private String nombre="Supermercado";
    
    private String registryURL;
    private String host;
    private int RMIPortNum;

    public static final int NUMEROCAJAS=3;

    public static final int NUMMAXIMOPRODUCTOS=6;
    public static final int NUMMAXIMOITEMSPRODUCTO=4;

    public static final int NUMMAXIMOMERCADOS=10;
    public static final int NUMMINIMOMERCADOS=2;
    
    private static final int REORDEN=10;
    private static final int RECOMPRA=10;
    private static final int PRECIOMINIMO=1000;
    private static final int PRECIOMAXIMO=90000;
    private static final int CANTIDADMINIMAPRODUCTO=1;
    private static final int CANTIDADMAXIMAPRODUCTO=12;
    
    public Supermercado() throws RemoteException {        
                             
        frmSupermercado=new FrmSupermercado(this, itemsInventario);
        frmSupermercado.setLocationRelativeTo(null);
                        
        crearCajas(NUMEROCAJAS);
        
        monitorCajas=new MonitorCajas();
        monitorCajas.setCajas(cajas);
        monitorCajas.setSupermercado(this);
        
        monitorInventario=new MonitorInventario();
        monitorInventario.setSupermercado(this);
                
        asignadorMercados=new AsignadorMercados();
        asignadorMercados.setSupermercado(this);
        frmSupermercado.setColorFabricaMercados(FrmSupermercado.ROJO);
       
        fabricaMercados=new FabricaMercados();
        fabricaMercados.setAsignadorMercados(asignadorMercados);
        fabricaMercados.setSupermercado(this);        
        
                             
        
    }
    
    public void iniciar(){       
        frmSupermercado.setTitle(nombre);
        cargarInventario();
        frmSupermercado.setVisible(true); 
        iniciarCaja();
        (new Thread(monitorCajas)).start();
        (new Thread(monitorInventario)).start();
        frmSupermercado.setColorFabricaMercados(FrmSupermercado.VERDE);
        (new Thread(asignadorMercados)).start();
        (new Thread(fabricaMercados)).start();
    }
    
    public boolean conectarOficinaCentral() {      
        try{
            Properties props = new Properties();
            props.load(new FileInputStream(new File("src\\ppal\\configuracion.properties")));
            host = props.getProperty("host","localhost");
            RMIPortNum=Integer.parseInt(props.getProperty("puerto","733"));
            registryURL = "rmi://" + host + ":" + RMIPortNum + "/oficinaCentral";
        }catch (Exception re) {
            frmSupermercado.mostrarMensajeError("Problemas con la conexión!\nVerifique que la Oficina central ya inició\nó que el archivo de propiedades este bien configurado");
            System.exit(0);
        }
        return true;
    }
    
    public boolean registrarAnteOficinaCentral(){        
        boolean registrado=true;
        do {
            this.nombre=frmSupermercado.mostrarDialogoObtenerNombre();        
            try{
                IntrOficinaCentral oficinaCentral=(IntrOficinaCentral)Naming.lookup(registryURL);
                //registrando supermercado
                registrado=oficinaCentral.registrarSupermercado(this);
                //System.out.println("Supermercado registrado! "+this.hashCode());
                if (!registrado){
                        frmSupermercado.mostrarMensajeError("No se pudo registrar, supermercado ya esta registrado");
                        registrado=false;
                }           
            }
            catch (Exception re) {
                frmSupermercado.mostrarMensajeError("Problemas con la conexión!\nVerifique que la Oficina central ya inició\nó que el archivo de propiedades esté bien configurado");
                //re.printStackTrace();
                System.exit(0);
                registrado= false;
            }
        }while(!registrado);  
        
        return registrado;
        
    }
    
    public void desconectarOficinaCentral() {
        try{
           IntrOficinaCentral oficinaCentral=(IntrOficinaCentral)Naming.lookup(registryURL);
           //registrando supermercado
           oficinaCentral.desconectarSupermercado(this);
           //System.out.println("Supermercado registrado! "+this.hashCode());
        }// end try
        catch (Exception re) {
          System.out.println("Excepcion en Supermercado: " + re);
          //re.printStackTrace();
        }
    }
    
    private void cargarInventario() {
        //System.out.println("Creando inventario de productos en el supermercado "+this.hashCode());
        int cantidadProducto=0;
        long precio=0;
        for(Producto producto : catalogoProductos){
            //genera aleatoriamente la cantidad
            cantidadProducto= (int)Math.floor(Math.random()*CANTIDADMAXIMAPRODUCTO+CANTIDADMINIMAPRODUCTO);
            //genera aleatoriamente el precio del producto
            precio=(long)Math.floor(Math.random()*PRECIOMAXIMO+PRECIOMINIMO);
            //crea el item en el inventario
            crearItemInventario(producto, cantidadProducto, precio, REORDEN, RECOMPRA);
            //System.out.println("Creado producto "+producto.getDescripcion()+" cantidad: "+cantidadProducto +" precio "+precio);
        }
    }
    
    private void crearCajas(int numCajas){
        for (int i=1;i<=numCajas;i++){
            Caja caja=new Caja("Caja "+i);
            caja.setSupermercado(this);
            caja.getPnlCaja().setColorCaja(FrmSupermercado.ROJO);
            cajas.add(caja);
            frmSupermercado.addCaja(caja.getPnlCaja());
         }                    
    }    
    
    public synchronized boolean ingresarDescargar(Producto producto, int cantidad) {
        ItemInventario itemInventario=buscarItemInventario(producto);
        if (itemInventario!=null){
            itemInventario.setCantidad(itemInventario.getCantidad()+cantidad);
            if (cantidad>0){
                itemInventario.setPedido(false);
                frmSupermercado.setMensajeInventario("Recibiendo "+cantidad+" unds de "+producto.getDescripcion());
 
                itemInventario.setCantidadVirtual(itemInventario.getCantidadVirtual()+cantidad);
            }
            else{
                frmSupermercado.setMensajeInventario("Descargando "+(cantidad*(-1))+" unds de "+producto);
            } 
            frmSupermercado.refrescarInventario();
            return true;
        }
        return false;
    }
        
    public void setMensajeAsignador(String mensaje){
        frmSupermercado.setMensajeAsignador(mensaje);
    }

    public void solicitud(Producto producto, int cantidad) throws RemoteException {
        frmSupermercado.setMensajeMonitorInventario("Pidiendo "+cantidad+" unds de "+producto); 
        try {
            System.out.println("Realizando pedido de " + cantidad + " unidades de " + producto.getDescripcion()+" en el supermercado "+this.hashCode());
            IntrOficinaCentral oficinaCentral = (IntrOficinaCentral) Naming.lookup(registryURL);
            //registrando supermercado
            oficinaCentral.solicitud(producto, cantidad, this);
        } catch (Exception ex) {
            Logger.getLogger(Supermercado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void iniciarCaja() {
        for (Caja caja:cajas){
            if (!caja.isActiva()){
                frmSupermercado.setMensajeMonitorCajas("Iniciando "+caja.getNombre());
                caja.getPnlCaja().setColorCaja(FrmSupermercado.VERDE);
                caja.setActiva(true);
                if (!caja.isAlive())
                    caja.start();                          
                return;
            }
        }
    }

    public void cerrarCaja(Caja caja) {
        frmSupermercado.setMensajeMonitorCajas("Cerrando "+caja.getNombre());
        caja.setActiva(false);
        caja.getPnlCaja().setColorCaja(FrmSupermercado.ROJO);
    }

    public void cambiarPrecio(Producto producto, long nuevoPrecio) {
        ItemInventario itemInventario=buscarItemInventario(producto);
        if (itemInventario!=null){
            itemInventario.setPrecioVenta(nuevoPrecio);
        }
    }

    public int saldo(Producto producto) throws RemoteException {
        ItemInventario itemInventario=buscarItemInventario(producto);
        if (itemInventario!=null){
            return itemInventario.getCantidad();
        }
        return 0;
    }

    public synchronized int descargarVirtual(Producto producto, int cantidad) {
        ItemInventario itemInventario=buscarItemInventario(producto);
        if (itemInventario!=null){
            if (itemInventario.getCantidadVirtual()<cantidad){
                int cantVirtual=itemInventario.getCantidadVirtual();
                itemInventario.setCantidadVirtual(0);
                return cantVirtual;
            }
            else{
                itemInventario.setCantidadVirtual(itemInventario.getCantidadVirtual()-cantidad);
                return cantidad;
            }
        }
        return 0;
    }

    public boolean crearItemInventario(Producto producto, int cantidad, long precioVenta, int reOrden, int reCompra) {
        if (!productoEstaEnInventario(producto)){
            getItemsInventario().add(new  ItemInventario(producto, cantidad, cantidad, precioVenta, reOrden, reCompra));
            return true;
        }
        return false;
    }

    public int getCantItemsInventario(){
        return getItemsInventario().size();
    }

    public Producto getProductoIndInventario(int indInventario){
        ItemInventario itemInventario=getItemsInventario().get(indInventario);
        if (itemInventario!=null){
            return itemInventario.getProducto();
        }
        return null;
    }

    private boolean productoEstaEnInventario(Producto producto){
        for (ItemInventario itemInventario: getItemsInventario()){
            if ( itemInventario.getProducto().equals(producto)){
                return true;
            }
        }
        return false;
    }

    public ItemInventario buscarItemInventario(Producto producto){
        for (ItemInventario itemInventario: getItemsInventario()){
            if ( itemInventario.getProducto().equals(producto)){
                return itemInventario;
            }
        }
        return null;
    } 
  
    public void limpiarFabrica(){
        frmSupermercado.limpiarFabrica();
    }
    
    public void pararFabrica(){
        frmSupermercado.pararFabrica();
    }
    
    public void iniciarGeneracionMercado(String nombre, int numProductos, List<ItemMercado> itemsMercado){
        frmSupermercado.iniciarGeneracionMercado(nombre, numProductos, itemsMercado);
    }
    
    public void finalizarGeneracionMercado(String nombre, int numItemsMercado){
         frmSupermercado.finalizarGeneracionMercado(nombre, numItemsMercado);
    }  
   
    public void actualizarItemMercado(int seleccion,String mensaje){
        frmSupermercado.actualizarItemMercado(seleccion, mensaje);
    }
        
    public void activarInactivarFabrica(){
        fabricaMercados.setActivo(!fabricaMercados.isActivo());
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
     * @return the itemsInventario
     */
    public List<ItemInventario> getItemsInventario() {
        return itemsInventario;
    }

    /**
     * @param itemsInventario the itemsInventario to set
     */
    public void setItemsInventario(List<ItemInventario> itemsInventario) {
        this.itemsInventario = itemsInventario;
    }

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
        Supermercado supermercado=new Supermercado();
        if (supermercado.conectarOficinaCentral()){      
            if (supermercado.registrarAnteOficinaCentral())
                supermercado.iniciar();
        }
    }

    public void recibirCatalogo(List<Producto> catalogo) throws RemoteException {
         catalogoProductos=catalogo;
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
    
}

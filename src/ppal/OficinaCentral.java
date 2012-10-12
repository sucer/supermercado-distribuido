/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ppal;

import java.io.File;
import java.io.FileInputStream;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario UTP
 */
public class OficinaCentral extends UnicastRemoteObject implements IntrOficinaCentral {
    
    FrmOficinaCentral frmOficinaCentral;

    private List<Producto> catalogoProductos = new ArrayList<Producto>();
    private List<IntrSupermercado> lstSupermercados = new ArrayList<IntrSupermercado>();
    private List<IntrBodega> lstBodegas = new ArrayList<IntrBodega>();

    public OficinaCentral() throws RemoteException {
        super();
        System.out.println("Inicializando la oficina central");
        cargarProductos();
        
        frmOficinaCentral=new FrmOficinaCentral(catalogoProductos, lstSupermercados, lstBodegas);
        frmOficinaCentral.setLocationRelativeTo(null);
        
        frmOficinaCentral.setVisible(true); 
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    public boolean findSupermercado(String nombre){
        for(IntrSupermercado supermercado:lstSupermercados){
            try {
                if (supermercado.getNombre().equals(nombre)){
                    return true;
                }
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
            
        }
        return false;
    }
    
    public boolean findBodega(String nombre){
        for(IntrBodega bodega:lstBodegas){
            try {
                if (bodega.getNombre().equals(nombre)){
                    return true;
                }
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
            
        }
        return false;
    }

    public synchronized boolean registrarSupermercado(IntrSupermercado intrSupermercado) throws RemoteException {
        if (!findSupermercado(intrSupermercado.getNombre())) {
            lstSupermercados.add(intrSupermercado);            
            //System.out.println("Nuevo Supermercado Conectado..");
            frmOficinaCentral.setMensajeSupermercados(intrSupermercado.getNombre()+ " se ha conectado");
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            frmOficinaCentral.refrescarSupermercados();
            enviarCatalogoSupermercado(intrSupermercado);
            return true;
        }
        return false;
    }

    public synchronized boolean registrarBodega(IntrBodega intrBodega) throws RemoteException {
        //System.out.println("Registrando bodega "+intrBodega.hashCode());
        if (!findBodega(intrBodega.getNombre())) {
            lstBodegas.add(intrBodega);
            //System.out.println("Se registro la bodega "+intrBodega.hashCode());
            frmOficinaCentral.setMensajeBodegas(intrBodega.getNombre()+ " se ha conectado");
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            frmOficinaCentral.refrescarBodegas();
            enviarCatalogoBodega(intrBodega);
            return true;
        }
        return false;
    }

    public void desconectarSupermercado(IntrSupermercado intrSupermercado) throws RemoteException {
        if (lstSupermercados.remove(intrSupermercado)) {
            System.out.println("Supermercado Desconectado: ");
            frmOficinaCentral.setMensajeSupermercados(intrSupermercado.getNombre()+" se ha desconectado");
            frmOficinaCentral.refrescarSupermercados();
        } else {
            System.out.println("El Supermercado no se pudo Desconectar: ");
        }
        
    }

    public void desconectarBodega(IntrBodega intrBodega) throws RemoteException {
        if (lstBodegas.remove(intrBodega)) {
            System.out.println("Bodega Desconectado: ");
            frmOficinaCentral.setMensajeBodegas(intrBodega.getNombre()+" se ha desconectado");
            frmOficinaCentral.refrescarBodegas();
        } else {
            System.out.println("La Bodega no se pudo Desconectar: ");
        }
    }
   
    public synchronized void solicitud(Producto producto, int cantidad, IntrSupermercado supermercado) throws RemoteException {
        //System.out.println("Solicitud de " + cantidad + " " + producto.getDescripcion() + " del Supermercado " + supermercado.hashCode());
        frmOficinaCentral.setMensajeRecepcionEnvioPedidos("Solicitud de " + cantidad + " unds de " + producto + " del Supermercado " + supermercado.getNombre());
        try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        //Busca en las bodegas si disponibilidad
        boolean despachado = false;
        for (IntrBodega iterBodega : lstBodegas) {
            int saldo = iterBodega.saldo(producto);
            if (saldo >= cantidad) {
                iterBodega.despachar(producto, cantidad, supermercado);
                despachado = true;
                frmOficinaCentral.setMensajeRecepcionEnvioPedidos("Enviando pedido de " + cantidad + "unds de " + producto + " de la bodega "+iterBodega.getNombre() + " para el supermercado " + supermercado.getNombre() );
                //System.out.println("Pedido enviado de " + cantidad + "unds de " + producto + "la bodega "+iterBodega.getNombre() + " para el supermercado " + supermercado.getNombre() );
                break;
            }
            //consulta la cantidad del producto en la bodega
            //System.out.println("Saldo del Producto: " + producto.getDescripcion() + " es:" + saldo + " en la Bodega " + iterBodega.hashCode());
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
            frmOficinaCentral.setMensajeRecepcionEnvioPedidos("El producto " + producto.getDescripcion() + " esta agotado en toda la cadena de supermercados debe hacerse la solicitud de compra con el proveedor ");
            //System.out.println("El producto " + producto.getDescripcion() + " esta agotado en toda la cadena de supermercados debe hacerse la solicitud de compra con el proveedor ");
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
    
    
    public static void main(String args[]){
        String registryURL;
        String host;
        int RMIPortNum;
        try{
           Properties props = new Properties();
           props.load(new FileInputStream(new File("src\\ppal\\configuracion.properties")));
           host = props.getProperty("host","localhost");
           RMIPortNum=Integer.parseInt(props.getProperty("puerto","733"));
           startRegistry(RMIPortNum);
           IntrOficinaCentral exportedObj = new OficinaCentral();
           registryURL = "rmi://" + host + ":" + RMIPortNum + "/oficinaCentral";
           Naming.rebind(registryURL, exportedObj);
           System.out.println("Servidor iniciado en "+host+":"+RMIPortNum +" con URL: "+registryURL);
        }// end try
        catch (Exception re) {
          System.out.println("Excepcion en Servidor Oficina Central: " + re);
          re.printStackTrace();
        } // end catch
    }
    //This method starts a RMI registry on the local host, if
    //it does not already exists at the specified port number.
    private static void startRegistry(int RMIPortNum)
        throws RemoteException{
        try {
            Registry registry =LocateRegistry.getRegistry(RMIPortNum);
            registry.list( );
            // This call will throw an exception
            // if the registry does not already exist
    }
    catch (RemoteException e) {
      // No valid registry at that port.
        Registry registry =LocateRegistry.createRegistry(RMIPortNum);
    }
  } // end startRegistry
}

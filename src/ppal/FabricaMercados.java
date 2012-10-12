/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ppal;

/**
 *
 * @author Usuario UTP
 */
public class FabricaMercados implements Runnable{
    private AsignadorMercados asignadorMercados;
    private Supermercado supermercado;
    private boolean prendido = true;
    private int numero=1;
    private boolean activo=true;

    //private static final int NUMMAXIMOMERCADOSGENERAR=10;
    public static final int NUMMAXIMOPRODUCTOS=5;
    public static final int NUMMAXIMOITEMSPRODUCTO=4;
    
    public void run() {
        while (prendido /*&& numero<=NUMMAXIMOMERCADOSGENERAR*/){
            
            supermercado.limpiarFabrica();
            if (!activo)
                 supermercado.pararFabrica();
            while(!activo){
               
            }
            try {
                //System.out.println("Inicializando Mercado "+(numero));
                                                            
                String nombre="Mercado "+(numero);
                
                Mercado mercado=new Mercado();
            
                //System.out.println((nombre + ": Inicia generación de mercados "));
                mercado.setNombre(nombre);
                Producto producto;
                int cantidadObtenida;
                int numProductos= (int) Math.floor(Math.random()*Supermercado.NUMMAXIMOPRODUCTOS+1);
                
                supermercado.iniciarGeneracionMercado(nombre, numProductos, mercado.getItemsMercado());
                //System.out.println(nombre + ": Número productos " + numProductos);

                for (int i=1; i<=numProductos;i++){
                
                    //do {
                        int indiceInventario = (int) Math.floor(Math.random()*(supermercado.getCantItemsInventario())+1);
                        int numItemsProducto = (int) Math.floor(Math.random()*Supermercado.NUMMAXIMOITEMSPRODUCTO+1);
                        producto = supermercado.getProductoIndInventario(indiceInventario-1);
                        cantidadObtenida = supermercado.descargarVirtual(producto, numItemsProducto);                    
                        if (cantidadObtenida > 0) {
                            //System.out.println(nombre + ": Echando " + cantidadObtenida + " unidades de " + producto.getDescripcion());
                            mercado.getItemsMercado().add(new ItemMercado(producto, cantidadObtenida));
                            supermercado.actualizarItemMercado(mercado.getItemsMercado().size()-1,"Incluyendo " + cantidadObtenida + " unds de " + producto);
                        }
                        else{
                            //System.out.println(nombre + ": Sin unidades de " + producto.getDescripcion());
                            supermercado.actualizarItemMercado(mercado.getItemsMercado().size()-1,"Sin unds de " + producto);
                        }

                        Thread.sleep(300);

                    //} while (cantidadObtenida <= 0);
               
                }
                //System.out.println(nombre+": Finaliza generación de mercados ");
                supermercado.finalizarGeneracionMercado(nombre, mercado.getItemsMercado().size());
                Thread.sleep(600);
                if (mercado.getItemsMercado().size()>0){
                    asignadorMercados.putMercado(mercado);
                    numero++;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

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
     * @return the asignadorMercados
     */
    public AsignadorMercados getAsignadorMercados() {
        return asignadorMercados;
    }

    /**
     * @param asignadorMercados the asignadorMercados to set
     */
    public void setAsignadorMercados(AsignadorMercados asignadorMercados) {
        this.asignadorMercados = asignadorMercados;
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
     * @return the activo
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * @param activo the activo to set
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

  }

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ppal;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario UTP
 */
public class Mercado {
    private String nombre;
    private List<ItemMercado> itemsMercado=new ArrayList<ItemMercado>();

    /**
     * @return the itemsMercado
     */
    public List<ItemMercado> getItemsMercado() {
        return itemsMercado;
    }

    /**
     * @param itemsMercado the itemsMercado to set
     */
    public void setItemsMercado(List<ItemMercado> itemsMercado) {
        this.itemsMercado = itemsMercado;
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

    @Override
    public String toString(){
        return nombre;
    }
}

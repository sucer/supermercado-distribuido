/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ppal;

import java.io.Serializable;

/**
 *
 * @author Usuario UTP
 */
public class Producto implements Serializable {
    private String referencia;
    private String descripcion;

    public Producto(String referencia, String descripcion) {
        this.referencia = referencia;
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o){
        if  ( (o !=null) && (o instanceof Producto) ){
            if ( ((Producto)o).getReferencia().equals(this.getReferencia()) )
                    return true;
        }
        return false;
    }
    
    
    @Override
    public String toString(){
        return referencia+"-"+descripcion;
    }
    

    /**
     * @return the referencia
     */
    public String getReferencia() {
        return referencia;
    }

    /**
     * @param referencia the referencia to set
     */
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

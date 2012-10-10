/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FrmOficinaCentral.java
 *
 * Created on 8/10/2012, 09:50:15 PM
 */
package ppal;

import java.awt.Color;
import java.awt.Component;
import java.util.List;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author John Bayron
 */
public class FrmOficinaCentral extends javax.swing.JFrame {
    
    List<IntrSupermercado> supermercados;
    
    List<IntrBodega> bodegas;
    
    List<Producto> catalogo;
    
    public static final Color VERDE = new Color(0,153,0);    
    public static final Color ROJO = new Color(255,60,60);    
    public static final Color AMARILLO = new Color(255,222,0);
    public static final Color BASEAPP = new Color(102,102,102);
    public static final Color COMPONENTES=new Color(255, 255 , 255);

    /** Creates new form FrmOficinaCentral */
    public FrmOficinaCentral( List<Producto> catalogo,List<IntrSupermercado> supermercados, List<IntrBodega> bodegas) {
        this.catalogo=catalogo;
        this.supermercados=supermercados;
        this.bodegas=bodegas;
        initComponents();        
        tblBodegas.setModel(new GeneralTableModel(bodegas, new String[] {"nombre"}, new String[] {"Bodega"}, new Class[] {String.class}));
        tblSupermercados.setModel(new GeneralTableModel(supermercados, new String[] {"nombre"}, new String[] {"Supermercado"}, new Class[] {String.class}));
        tblCatalogo.setModel(new GeneralTableModel(catalogo, new String[] {"referencia","descripcion"}, new String[] {"Referencia","Descripción"}, new Class[] {String.class,String.class}));
        tblCatalogo.getColumnModel().getColumn(0).setPreferredWidth(55);
        tblCatalogo.getColumnModel().getColumn(0).setMaxWidth(55);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlOficinaCentral = new javax.swing.JPanel();
        PnlRecepcionEnvioPedidos = new javax.swing.JPanel();
        txtMensajeRecepcionEnvioPedidos = new javax.swing.JTextField();
        pnlSupermercados = new javax.swing.JPanel();
        txtMensajeSupermercados = new javax.swing.JTextField();
        scrSupermercados = new javax.swing.JScrollPane();
        tblSupermercados = new javax.swing.JTable(){
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
                Component returnComp = super.prepareRenderer(renderer, row,column);
                returnComp.setFont(new java.awt.Font("Tahoma", 1, 11));

                return returnComp;
            }
        };
        pnlBodegas = new javax.swing.JPanel();
        txtMensajeBodegas = new javax.swing.JTextField();
        scrBodegas = new javax.swing.JScrollPane();
        tblBodegas = new javax.swing.JTable(){
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
                Component returnComp = super.prepareRenderer(renderer, row,column);
                returnComp.setFont(new java.awt.Font("Tahoma", 1, 11));
                return returnComp;
            }
        };
        pnlCatalogo = new javax.swing.JPanel();
        scrCatalogo = new javax.swing.JScrollPane();
        tblCatalogo = new javax.swing.JTable(){
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
                Component returnComp = super.prepareRenderer(renderer, row,column);
                returnComp.setFont(new java.awt.Font("Tahoma", 1, 11));
                return returnComp;
            }
        };

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlOficinaCentral.setBackground(new java.awt.Color(255, 255, 255));

        PnlRecepcionEnvioPedidos.setBackground(FrmOficinaCentral.BASEAPP);
        PnlRecepcionEnvioPedidos.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "RECEPCIÓN Y SOLICITUD DE PEDIDOS A LAS BODEGAS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13), java.awt.Color.white)); // NOI18N

        txtMensajeRecepcionEnvioPedidos.setBackground(FrmOficinaCentral.COMPONENTES);
        txtMensajeRecepcionEnvioPedidos.setEditable(false);
        txtMensajeRecepcionEnvioPedidos.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout PnlRecepcionEnvioPedidosLayout = new javax.swing.GroupLayout(PnlRecepcionEnvioPedidos);
        PnlRecepcionEnvioPedidos.setLayout(PnlRecepcionEnvioPedidosLayout);
        PnlRecepcionEnvioPedidosLayout.setHorizontalGroup(
            PnlRecepcionEnvioPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlRecepcionEnvioPedidosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMensajeRecepcionEnvioPedidos)
                .addContainerGap())
        );
        PnlRecepcionEnvioPedidosLayout.setVerticalGroup(
            PnlRecepcionEnvioPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PnlRecepcionEnvioPedidosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMensajeRecepcionEnvioPedidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlSupermercados.setBackground(FrmOficinaCentral.BASEAPP);
        pnlSupermercados.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "SUPERMERCADOS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13), java.awt.Color.white)); // NOI18N

        txtMensajeSupermercados.setBackground(FrmOficinaCentral.COMPONENTES);
        txtMensajeSupermercados.setEditable(false);
        txtMensajeSupermercados.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        tblSupermercados.setBackground(FrmOficinaCentral.COMPONENTES);
        tblSupermercados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        scrSupermercados.setViewportView(tblSupermercados);

        javax.swing.GroupLayout pnlSupermercadosLayout = new javax.swing.GroupLayout(pnlSupermercados);
        pnlSupermercados.setLayout(pnlSupermercadosLayout);
        pnlSupermercadosLayout.setHorizontalGroup(
            pnlSupermercadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSupermercadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSupermercadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtMensajeSupermercados, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrSupermercados, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlSupermercadosLayout.setVerticalGroup(
            pnlSupermercadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSupermercadosLayout.createSequentialGroup()
                .addComponent(txtMensajeSupermercados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrSupermercados, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlBodegas.setBackground(FrmBodega.BASEAPP);
        pnlBodegas.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "BODEGAS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13), java.awt.Color.white)); // NOI18N

        txtMensajeBodegas.setBackground(FrmOficinaCentral.COMPONENTES);
        txtMensajeBodegas.setEditable(false);
        txtMensajeBodegas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        tblBodegas.setBackground(FrmOficinaCentral.COMPONENTES);
        tblBodegas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        scrBodegas.setViewportView(tblBodegas);

        javax.swing.GroupLayout pnlBodegasLayout = new javax.swing.GroupLayout(pnlBodegas);
        pnlBodegas.setLayout(pnlBodegasLayout);
        pnlBodegasLayout.setHorizontalGroup(
            pnlBodegasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBodegasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBodegasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scrBodegas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(txtMensajeBodegas, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        pnlBodegasLayout.setVerticalGroup(
            pnlBodegasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBodegasLayout.createSequentialGroup()
                .addComponent(txtMensajeBodegas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrBodegas, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlCatalogo.setBackground(FrmOficinaCentral.BASEAPP);
        pnlCatalogo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CATÁLOGO DE PRODUCTOS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13), new java.awt.Color(255, 255, 255))); // NOI18N

        scrCatalogo.setViewportView(tblCatalogo);

        javax.swing.GroupLayout pnlCatalogoLayout = new javax.swing.GroupLayout(pnlCatalogo);
        pnlCatalogo.setLayout(pnlCatalogoLayout);
        pnlCatalogoLayout.setHorizontalGroup(
            pnlCatalogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCatalogoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrCatalogo, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlCatalogoLayout.setVerticalGroup(
            pnlCatalogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCatalogoLayout.createSequentialGroup()
                .addComponent(scrCatalogo, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlOficinaCentralLayout = new javax.swing.GroupLayout(pnlOficinaCentral);
        pnlOficinaCentral.setLayout(pnlOficinaCentralLayout);
        pnlOficinaCentralLayout.setHorizontalGroup(
            pnlOficinaCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOficinaCentralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlOficinaCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PnlRecepcionEnvioPedidos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlOficinaCentralLayout.createSequentialGroup()
                        .addGroup(pnlOficinaCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(pnlSupermercados, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlBodegas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnlCatalogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlOficinaCentralLayout.setVerticalGroup(
            pnlOficinaCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOficinaCentralLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(PnlRecepcionEnvioPedidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlOficinaCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlOficinaCentralLayout.createSequentialGroup()
                        .addComponent(pnlSupermercados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(13, 13, 13)
                        .addComponent(pnlBodegas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(pnlCatalogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlOficinaCentral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlOficinaCentral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    public void refrescarBodegas(){
        tblBodegas.revalidate();
        tblBodegas.repaint(); 
    }
    
    public void refrescarSupermercados(){
        tblSupermercados.revalidate();
        tblSupermercados.repaint(); 
    }
    
    public void refrescarCatalogo(){
        tblBodegas.revalidate();
        tblBodegas.repaint(); 
    }
    
    public void setMensajeRecepcionEnvioPedidos(String mensaje){
        txtMensajeRecepcionEnvioPedidos.setText(mensaje);
    }
    
    public void setMensajeSupermercados(String mensaje){
        txtMensajeSupermercados.setText(mensaje);
    }
    
    public void setMensajeBodegas(String mensaje){
        txtMensajeBodegas.setText(mensaje);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PnlRecepcionEnvioPedidos;
    private javax.swing.JPanel pnlBodegas;
    private javax.swing.JPanel pnlCatalogo;
    private javax.swing.JPanel pnlOficinaCentral;
    private javax.swing.JPanel pnlSupermercados;
    private javax.swing.JScrollPane scrBodegas;
    private javax.swing.JScrollPane scrCatalogo;
    private javax.swing.JScrollPane scrSupermercados;
    private javax.swing.JTable tblBodegas;
    private javax.swing.JTable tblCatalogo;
    private javax.swing.JTable tblSupermercados;
    private javax.swing.JTextField txtMensajeBodegas;
    private javax.swing.JTextField txtMensajeRecepcionEnvioPedidos;
    private javax.swing.JTextField txtMensajeSupermercados;
    // End of variables declaration//GEN-END:variables
}

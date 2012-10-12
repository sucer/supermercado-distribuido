
package ppal;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import org.ognl.el.Expression;

import org.ognl.el.ExpressionSyntaxException;

import org.ognl.el.OgnlException;



public class GeneralTableModel extends AbstractTableModel {
    private List datos;
    private Expression[] ognlExprs;
    private String[] propiedades;
    private String[] titulos;
    private Class[] clases;
    private Boolean[] editable;
    private Map<Integer, Map> columnas = new HashMap<Integer, Map>();
    
    /** Creates a new instance of TableModel */
    public GeneralTableModel(List datos, String[] propiedades, String[] titulos) {
        this(datos, propiedades, titulos, null, null);
    }
    
    public GeneralTableModel(List datos, String[] propiedades, String[] titulos, Class[] clases) {
        this(datos, propiedades, titulos, clases, null);
    }
    
    public GeneralTableModel(List datos, String[] propiedades, String[] titulos, Class[] clases, Boolean[] editable) {
        this.datos = datos;
        this.propiedades = propiedades;
        this.titulos = titulos;
        this.clases = clases;
        this.editable = editable;
        ognlExprs = new Expression[propiedades.length];
        for (int i = 0; i < propiedades.length; i++) {
            try {
                String columnExpr = propiedades[i];
                ognlExprs[i] = Ognl.getInstancia().getExpression(columnExpr);
            } catch (ExpressionSyntaxException e) {
                System.out.println(e.getMessage());
            } catch (OgnlException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Object value = Ognl.getInstancia().findValue(ognlExprs[columnIndex], datos.get(rowIndex));
            if (null != columnas.get(columnIndex) && null != value)
                value = columnas.get(columnIndex).get(value);
            return value;
        } catch (OgnlException e) {
            return e.getMessage();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public int getRowCount() {
        return datos!=null?datos.size():0;
    }

    public int getColumnCount() {
        return titulos.length;
    }
    
    public String getColumnName(int columnIndex) {
        return titulos[columnIndex];
    }
    
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (editable == null)
            return false;
        return editable[columnIndex];
    }
    
    public Class getColumnClass(int columnIndex) {        
        if (clases == null)
            return Class.class;
        return clases[columnIndex];
    }
    
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        try {
            Ognl.getInstancia().setValue(propiedades[columnIndex], datos.get(rowIndex), value);
            fireTableCellUpdated(rowIndex, columnIndex);
        } catch (OgnlException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void setDatos(List datos){
        this.datos=datos;
        super.fireTableDataChanged();
    }
    
    public List getItems() {
        return datos;
    }
    
    public void addRow(Object row, int index){
        datos.add(index,row);
        super.fireTableRowsInserted(index,index);
        super.fireTableDataChanged();
    }
    
    public void removeRow(int index){
        datos.remove(index);
        super.fireTableRowsDeleted(index,index);
        super.fireTableDataChanged();        
    }
    
    public Object getRow(int row){
        return datos.get(row);
    }
    
    public void setColumnRenderer(int columnIndex, Map columnMap) {
        columnas.put(columnIndex, columnMap);
    }
    
    public Object getItemAt(int row){
        return (row < datos.size() ? datos.get(row) : null);
    }
    
}

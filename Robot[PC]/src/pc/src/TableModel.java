/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc.src;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author keke
 */
public class TableModel extends AbstractTableModel{
    private Terrain carte;
    
    public TableModel(Terrain labyrinthe){
        carte = labyrinthe;
    }    

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
    @Override
    public int getRowCount() {
        return Terrain.height;
    }

    @Override
    public int getColumnCount() {
        return Terrain.width;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(columnIndex == 0){
            return rowIndex;
        }
        return carte.getCase(rowIndex, columnIndex-1);
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        super.setValueAt(aValue, rowIndex, columnIndex);
        this.fireTableDataChanged();
    }

    @Override
    public String getColumnName(int column) {
        if(column == 0){
            return "";
        }
        return Integer.toString(column-1);
        //return String.valueOf((char)(column+64));
    }    
    
}

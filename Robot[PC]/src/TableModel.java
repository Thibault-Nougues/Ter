/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import javax.swing.table.AbstractTableModel;
import static src.Constantes.*;

import java.awt.Point;

/**
 *
 * @author keke
 */
public class TableModel extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
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
        return ARENE_HEIGHT;
    }

    @Override
    public int getColumnCount() {
        return ARENE_WIDTH+1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(columnIndex == 0){
            return rowIndex+1;
        }
        return carte.getCase(new Point(rowIndex, columnIndex-1));
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

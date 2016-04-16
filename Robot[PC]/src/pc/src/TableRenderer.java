/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screen;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import screen.Terrain.Case;

/**
 *
 * @author keke
 */
public class TableRenderer extends DefaultTableCellRenderer{
    private Terrain carte;
    
    public TableRenderer(Terrain labyrinthe){
        carte = labyrinthe;
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(column != 0){           
            
            ((JLabel) c).setBackground(Color.LIGHT_GRAY);
            Case caseCourant = carte.getCase(row, column-1);
            ((JLabel) c).setText(Integer.toString(caseCourant.num));
            if(caseCourant.estObstacle()){
                ((JLabel) c).setBackground(Color.DARK_GRAY);
                ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            }
            if(caseCourant.getFinal()){
                ((JLabel) c).setBackground(Color.YELLOW);
            }
            if(caseCourant.estPosCourante()){
                ((JLabel) c).setBackground(Color.GREEN);
            }
            int murs = caseCourant.getMurs();
            int n = (murs & Terrain.NORTH)/Terrain.NORTH;
            int e = (murs & Terrain.EAST)/Terrain.EAST;
            int s = (murs & Terrain.SOUTH)/Terrain.SOUTH;
            int w = (murs & Terrain.WEST)/Terrain.WEST;
            ((JLabel) c).setBorder(BorderFactory.createMatteBorder(n, 
                    w, 
                    s, 
                    e,
                    Color.BLACK));
        }
        else{
            ((JLabel) c).setBackground(Color.WHITE);
            ((JLabel) c).setHorizontalAlignment(RIGHT);
        }
        return c;
        
    }
}

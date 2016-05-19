/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc.src;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import static pc.src.Constantes.*;

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
            Case caseCourante = carte.getCase(row, column-1);
            ((JLabel) c).setText(Integer.toString(caseCourante.getPoids()));
                    
            if(caseCourante.estObstacle()){
                ((JLabel) c).setBackground(Color.DARK_GRAY);
                ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            }
            if(caseCourante.getFinal()){
                ((JLabel) c).setBackground(Color.YELLOW);
            }
            if(caseCourante.getDepart()){
                ((JLabel) c).setBackground(Color.BLUE);
            }
            /*switch(caseCourante.getDirection()){
                case Terrain.NORTH : ((JLabel) c).setIcon(new ImageIcon("images/haut.png"));
                    break;
                case Terrain.SOUTH : ((JLabel) c).setIcon(new ImageIcon("images/bas.png"));
                    break;
                case Terrain.EAST : ((JLabel) c).setIcon(new ImageIcon("images/droite.png"));
                    break;
                case Terrain.WEST : ((JLabel) c).setIcon(new ImageIcon("images/gauche.png"));
                    break;
                default:
                    ((JLabel) c).setForeground(Color.BLACK);
                    ((JLabel) c).setIcon(null);
                    break;
            }*/
            int mursVue = caseCourante.getMursVue();
            int n = (mursVue & HAUT)/HAUT;
            int e = (mursVue & DROITE)/DROITE;
            int s = (mursVue & BAS)/BAS;
            int w = (mursVue & GAUCHE)/GAUCHE;
            ((JLabel) c).setBorder(BorderFactory.createMatteBorder(n, 
                    w, 
                    s, 
                    e,
                    Color.GREEN));
            
            
            
            int murs = caseCourante.getMurs();
            n = (murs & HAUT)/HAUT;
            e = (murs & DROITE)/DROITE;
            s = (murs & BAS)/BAS;
            w = (murs & GAUCHE)/GAUCHE;
            ((JLabel) c).setBorder(BorderFactory.createMatteBorder(n, 
                    w, 
                    s, 
                    e,
                    Color.RED));
            if(caseCourante.getPoids() != 100){
                ((JLabel) c).setBackground(arcEnCiel(caseCourante.getPoids()));
            }
            
            
        }
        else{
            ((JLabel) c).setBackground(Color.WHITE);
            ((JLabel) c).setHorizontalAlignment(RIGHT);
        }
        return c;
        
    }
    
    private Color arcEnCiel(int x){
        int val = x;
        int r = 255;
        int g = 255;
        int b = 255;
        x = 510-x*6;
        if(x>=0 && x<255){
            r = 255;
            g = x;
            b = 0;
        }
        else if(x>=255 && x<510){
            r = 510-x;
            g = 255;
            b = 0;
        }
        return new Color(r, g, b);
    }
}

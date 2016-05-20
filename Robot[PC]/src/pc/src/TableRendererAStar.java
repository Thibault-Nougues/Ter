/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc.src;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import static pc.src.Constantes.*;

/**
 *
 * @author keke
 */
public class TableRendererAStar extends DefaultTableCellRenderer{
	private static final long serialVersionUID = 1L;
	private Terrain carte;
    
    public TableRendererAStar(Terrain labyrinthe){
        carte = labyrinthe;
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(column != 0){           
            
            ((JLabel) c).setBackground(Color.LIGHT_GRAY);
            Case caseCourante = carte.getCase(new Point(row, column-1));
                    
            if(caseCourante.estObstacle()){
                ((JLabel) c).setBackground(Color.DARK_GRAY);
                ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            }
            else if(caseCourante.getFinal()){
                ((JLabel) c).setBackground(Color.YELLOW);
            }
            else if(caseCourante.getDepart()){
                ((JLabel) c).setBackground(Color.BLUE);
            }/*
            switch(caseCourante.getDirection()){
                case HAUT : ((JLabel) c).setIcon(new ImageIcon("src/pc/src/images/haut.png"));
                    break;
                case BAS : ((JLabel) c).setIcon(new ImageIcon("src/pc/src/images/bas.png"));
                    break;
                case DROITE : ((JLabel) c).setIcon(new ImageIcon("src/pc/src/images/droite.png"));
                    break;
                case GAUCHE : ((JLabel) c).setIcon(new ImageIcon("src/pc/src/images/gauche.png"));
                    break;
                default:
                    ((JLabel) c).setForeground(Color.BLACK);
                    ((JLabel) c).setIcon(null);
                    break;
            }*/     
            
            int murs = caseCourante.getMurs();
            int n = (murs & HAUT)/HAUT;
            int e = (murs & DROITE)/DROITE;
            int s = (murs & BAS)/BAS;
            int w = (murs & GAUCHE)/GAUCHE;
            ((JLabel) c).setBorder(BorderFactory.createMatteBorder(n, 
                    w, 
                    s, 
                    e,
                    Color.BLACK));
            if(caseCourante.getPoids() != 100){
                ((JLabel) c).setBackground(arcEnCiel(caseCourante.getPoids()));
                ((JLabel) c).setText(Integer.toString(caseCourante.getPoids()));
            }
            else{
                ((JLabel) c).setText("");
            }

            
        }
        else{
            ((JLabel) c).setBackground(Color.WHITE);
            ((JLabel) c).setHorizontalAlignment(RIGHT);
        }
        return c;
        
    }
    
    private Color arcEnCiel(int x){
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

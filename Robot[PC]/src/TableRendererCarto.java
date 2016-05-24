package src;

import static src.Constantes.BAS;
import static src.Constantes.DROITE;
import static src.Constantes.GAUCHE;
import static src.Constantes.HAUT;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.CompoundBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class TableRendererCarto extends DefaultTableCellRenderer{
	private static final long serialVersionUID = 1L;
	private Terrain carte;
    
    public TableRendererCarto(Terrain labyrinthe){
        carte = labyrinthe;
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if(column != 0){           
            
            ((JLabel) c).setBackground(Color.LIGHT_GRAY);
            Case caseCourante = carte.getCase(new Point(row, column-1));
            ((JLabel) c).setText("");
                    
            if(caseCourante.estObstacle()){
                ((JLabel) c).setBackground(Color.DARK_GRAY);
                ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            }
            else{
            	if(caseCourante.getMursVue()==(HAUT+BAS+GAUCHE+DROITE)){
                	((JLabel) c).setBackground(Color.GREEN);
            	}
            	int murs = caseCourante.getMurs();
                int murHaut = (murs & HAUT)/HAUT;
                int murDroite = (murs & DROITE)/DROITE;
                int murBas = (murs & BAS)/BAS;
                int murGauche = (murs & GAUCHE)/GAUCHE;
                
            	int noMurs = caseCourante.getMursVue();
                int haut = (noMurs & HAUT)/HAUT - murHaut;
                int droite = (noMurs & DROITE)/DROITE - murDroite;
                int bas = (noMurs & BAS)/BAS - murBas;
                int gauche = (noMurs & GAUCHE)/GAUCHE - murGauche;
                
                CompoundBorder customBorder = BorderFactory.createCompoundBorder();
                customBorder = BorderFactory.createCompoundBorder(customBorder, BorderFactory.createMatteBorder(haut,gauche,bas,droite,Color.GREEN));
                customBorder = BorderFactory.createCompoundBorder(customBorder, BorderFactory.createMatteBorder(murHaut,murGauche,murBas,murDroite,Color.RED));
                ((JLabel) c).setBorder(customBorder);
            }
        }
        else{
            ((JLabel) c).setBackground(Color.WHITE);
            ((JLabel) c).setHorizontalAlignment(RIGHT);
        }
        if(isSelected){
            ((JLabel) c).setBorder(BorderFactory.createLineBorder(Color.BLUE));
        }
        table.setRowHeight(row, getPreferredSize().height + row * 10);
        return c;
        
    }
}

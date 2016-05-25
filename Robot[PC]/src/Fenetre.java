package src;

import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.table.TableColumn;
import static src.Constantes.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author keke
 */
public class Fenetre extends JDialog implements  ComponentListener {
    private Terrain carte;
    private Point caseArrivee = new Point(-1, -1);
    ArrayList<Case> solution;
    
    /**
     * Creates new form NewJFrame
     */
    public Fenetre(Terrain arene, JFrame owner, boolean modal) {
    	super(owner, modal);
        carte = arene;
        solution = new ArrayList<Case>();
        initComponents();
        jPanel1.addComponentListener(this);
        addBas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBasActionPerformed(evt);
            }
        });
        addDroite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addDroiteActionPerformed(evt);
            }
        });
        addGauche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addGaucheActionPerformed(evt);
            }
        });
        addHaut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addHautActionPerformed(evt);
            }
        });
        delBas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delBasActionPerformed(evt);
            }
        });
        delDroite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delDroiteActionPerformed(evt);
            }
        });
        delGauche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delGaucheActionPerformed(evt);
            }
        });
        delHaut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delHautActionPerformed(evt);
            }
        });
        addObstacle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addObstacleActionPerformed(evt);
            }
        });
        delObstacle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delObstacleActionPerformed(evt);
            }
        });
        jTable1.setRowHeight(jPanel1.getHeight()/ARENE_HEIGHT-2);
        TableColumn col;
        for(int i=0 ; i<ARENE_WIDTH+1 ; i++){
            col = jTable1.getColumnModel().getColumn(i);
            col.setPreferredWidth(jPanel1.getWidth()/(ARENE_WIDTH+1));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        addHaut = new javax.swing.JButton();
        addBas = new javax.swing.JButton();
        addGauche = new javax.swing.JButton();
        addDroite = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        delHaut = new javax.swing.JButton();
        delBas = new javax.swing.JButton();
        delGauche = new javax.swing.JButton();
        delDroite = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        addObstacle = new javax.swing.JButton();
        delObstacle = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();

        jButton2.setText("jButton2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Exploration");
        setPreferredSize(new java.awt.Dimension(800, 550));

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        jTable1.setModel(new TableModel(carte));
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTable1.setCellSelectionEnabled(true);
        jScrollPane1.setViewportView(jTable1);
        jTable1.setDefaultRenderer(Object.class, new TableRendererCarto(carte));

        jPanel1.add(jScrollPane1);

        jPanel2.setMaximumSize(new java.awt.Dimension(150, 32767));
        jPanel2.setMinimumSize(new java.awt.Dimension(150, 300));
        jPanel2.setPreferredSize(new java.awt.Dimension(150, 500));

        jLabel1.setText("AJOUTER UN MUR");
        jPanel2.add(jLabel1);

        addHaut.setText("HAUT");
        addHaut.setMaximumSize(new java.awt.Dimension(90, 25));
        addHaut.setMinimumSize(new java.awt.Dimension(90, 25));
        addHaut.setPreferredSize(new java.awt.Dimension(90, 25));
        jPanel2.add(addHaut);

        addBas.setText("BAS");
        addBas.setMaximumSize(new java.awt.Dimension(90, 25));
        addBas.setMinimumSize(new java.awt.Dimension(90, 25));
        addBas.setPreferredSize(new java.awt.Dimension(90, 25));
        jPanel2.add(addBas);

        addGauche.setText("GAUCHE");
        addGauche.setMaximumSize(new java.awt.Dimension(90, 25));
        addGauche.setMinimumSize(new java.awt.Dimension(90, 25));
        addGauche.setPreferredSize(new java.awt.Dimension(90, 25));
        jPanel2.add(addGauche);

        addDroite.setText("DROITE");
        addDroite.setMaximumSize(new java.awt.Dimension(90, 25));
        addDroite.setMinimumSize(new java.awt.Dimension(90, 25));
        addDroite.setPreferredSize(new java.awt.Dimension(90, 25));
        jPanel2.add(addDroite);

        jLabel2.setText("SUPRIMMER UN MUR");
        jPanel2.add(jLabel2);

        delHaut.setText("HAUT");
        delHaut.setMaximumSize(new java.awt.Dimension(90, 25));
        delHaut.setMinimumSize(new java.awt.Dimension(90, 25));
        delHaut.setPreferredSize(new java.awt.Dimension(90, 25));
        jPanel2.add(delHaut);

        delBas.setText("BAS");
        delBas.setMaximumSize(new java.awt.Dimension(90, 25));
        delBas.setMinimumSize(new java.awt.Dimension(90, 25));
        delBas.setPreferredSize(new java.awt.Dimension(90, 25));
        jPanel2.add(delBas);

        delGauche.setText("GAUCHE");
        delGauche.setMaximumSize(new java.awt.Dimension(90, 25));
        delGauche.setMinimumSize(new java.awt.Dimension(90, 25));
        delGauche.setPreferredSize(new java.awt.Dimension(90, 25));
        jPanel2.add(delGauche);

        delDroite.setText("DROITE");
        delDroite.setMaximumSize(new java.awt.Dimension(90, 25));
        delDroite.setMinimumSize(new java.awt.Dimension(90, 25));
        delDroite.setPreferredSize(new java.awt.Dimension(90, 25));
        jPanel2.add(delDroite);

        jLabel3.setText("OBSTACLES");
        jPanel2.add(jLabel3);

        addObstacle.setText("AJOUTER OBSTACLE");
        jPanel2.add(addObstacle);

        delObstacle.setText("SUPPRIMER OBSTACLE");
        jPanel2.add(delObstacle);

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setPreferredSize(new java.awt.Dimension(150, 100));

        jLabel4.setText("Coordonnées d'arrivée");

        jLabel5.setText("X");

        jLabel6.setText("Y");

        jButton1.setText("LANCER A*");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(9, 9, 9)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel3);

        jPanel1.add(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        setTitle("Exploitation");
        jTable1.setDefaultRenderer(Object.class, new TableRendererAStar(carte));
        jTable1.repaint();
        try {
            AStar algo = new AStar(carte, new Point(Integer.parseInt(jTextField1.getText()), Integer.parseInt(jTextField2.getText())), this);
            solution = algo.getSolution();
        } catch (InterruptedException ex) {
            Logger.getLogger(Fenetre.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    
    private void addDroiteActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        int x = jTable1.getSelectedRow();
        int y = jTable1.getSelectedColumn()-1;
        carte.addMur(new Point(x, y), DROITE);
        Case caseTmp = carte.getCase(new Point(x, y));
        jTable1.setValueAt(caseTmp, caseTmp.getX(), caseTmp.getY());
        jTable1.changeSelection(x, y+1, false, false);
        jTable1.requestFocus();
    }                                         

    private void delDroiteActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        int x = jTable1.getSelectedRow();
        int y = jTable1.getSelectedColumn()-1;
        carte.addNoMurs(new Point(jTable1.getSelectedRow(), jTable1.getSelectedColumn()-1), DROITE);
        Case caseTmp = carte.getCase(new Point(jTable1.getSelectedRow(), jTable1.getSelectedColumn()-1));
        jTable1.setValueAt(caseTmp, caseTmp.getX(), caseTmp.getY());
        jTable1.changeSelection(x, y+1, false, false);
        jTable1.requestFocus();
    }                                         

    private void delHautActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // TODO add your handling code here:
        int x = jTable1.getSelectedRow();
        int y = jTable1.getSelectedColumn()-1;
        carte.addNoMurs(new Point(jTable1.getSelectedRow(), jTable1.getSelectedColumn()-1), HAUT);
        Case caseTmp = carte.getCase(new Point(jTable1.getSelectedRow(), jTable1.getSelectedColumn()-1));
        jTable1.setValueAt(caseTmp, caseTmp.getX(), caseTmp.getY());
        jTable1.changeSelection(x, y+1, false, false);
        jTable1.requestFocus();
    }                                       

    private void addHautActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // TODO add your handling code here:
        int x = jTable1.getSelectedRow();
        int y = jTable1.getSelectedColumn()-1;
        carte.addMur(new Point(jTable1.getSelectedRow(), jTable1.getSelectedColumn()-1), HAUT);
        Case caseTmp = carte.getCase(new Point(jTable1.getSelectedRow(), jTable1.getSelectedColumn()-1));
        jTable1.setValueAt(caseTmp, caseTmp.getX(), caseTmp.getY());
        jTable1.changeSelection(x, y+1, false, false);
        jTable1.requestFocus();
    }                                       

    private void addBasActionPerformed(java.awt.event.ActionEvent evt) {                                       
        // TODO add your handling code here:
        int x = jTable1.getSelectedRow();
        int y = jTable1.getSelectedColumn()-1;
        carte.addMur(new Point(jTable1.getSelectedRow(), jTable1.getSelectedColumn()-1), BAS);
        Case caseTmp = carte.getCase(new Point(jTable1.getSelectedRow(), jTable1.getSelectedColumn()-1));
        jTable1.setValueAt(caseTmp, caseTmp.getX(), caseTmp.getY());
        jTable1.changeSelection(x, y+1, false, false);
        jTable1.requestFocus();
    }                                      

    private void addGaucheActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        int x = jTable1.getSelectedRow();
        int y = jTable1.getSelectedColumn()-1;
        carte.addMur(new Point(jTable1.getSelectedRow(), jTable1.getSelectedColumn()-1), GAUCHE);
        Case caseTmp = carte.getCase(new Point(jTable1.getSelectedRow(), jTable1.getSelectedColumn()-1));
        jTable1.setValueAt(caseTmp, caseTmp.getX(), caseTmp.getY());
        jTable1.changeSelection(x, y+1, false, false);
        jTable1.requestFocus();
    }                                         

    private void delBasActionPerformed(java.awt.event.ActionEvent evt) {                                       
        // TODO add your handling code here:
        int x = jTable1.getSelectedRow();
        int y = jTable1.getSelectedColumn()-1;
        carte.addNoMurs(new Point(jTable1.getSelectedRow(), jTable1.getSelectedColumn()-1), BAS);
        Case caseTmp = carte.getCase(new Point(jTable1.getSelectedRow(), jTable1.getSelectedColumn()-1));
        jTable1.setValueAt(caseTmp, caseTmp.getX(), caseTmp.getY());
        jTable1.changeSelection(x, y+1, false, false);
        jTable1.requestFocus();
    }                                      

    private void delGaucheActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        int x = jTable1.getSelectedRow();
        int y = jTable1.getSelectedColumn()-1;
        carte.addNoMurs(new Point(jTable1.getSelectedRow(), jTable1.getSelectedColumn()-1), GAUCHE);
        Case caseTmp = carte.getCase(new Point(jTable1.getSelectedRow(), jTable1.getSelectedColumn()-1));
        jTable1.setValueAt(caseTmp, caseTmp.getX(), caseTmp.getY());
        jTable1.changeSelection(x, y+1, false, false);
        jTable1.requestFocus();
    }                                         

    private void addObstacleActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
        int x = jTable1.getSelectedRow();
        int y = jTable1.getSelectedColumn()-1;
        carte.addObstacle(jTable1.getSelectedRow(), jTable1.getSelectedColumn()-1);
        Case caseTmp = carte.getCase(new Point(jTable1.getSelectedRow(), jTable1.getSelectedColumn()-1));
        jTable1.setValueAt(caseTmp, caseTmp.getX(), caseTmp.getY());
        jTable1.changeSelection(x, y+1, false, false);
        jTable1.requestFocus();
    }   
    
    private void delObstacleActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
        int x = jTable1.getSelectedRow();
        int y = jTable1.getSelectedColumn()-1;
        carte.addNoMurs(new Point(jTable1.getSelectedRow(), jTable1.getSelectedColumn()-1), HAUT);
        carte.addNoMurs(new Point(jTable1.getSelectedRow(), jTable1.getSelectedColumn()-1), BAS);
        carte.addNoMurs(new Point(jTable1.getSelectedRow(), jTable1.getSelectedColumn()-1), GAUCHE);
        carte.addNoMurs(new Point(jTable1.getSelectedRow(), jTable1.getSelectedColumn()-1), DROITE);
        Case caseTmp = carte.getCase(new Point(jTable1.getSelectedRow(), jTable1.getSelectedColumn()-1));
        jTable1.setValueAt(caseTmp, caseTmp.getX(), caseTmp.getY());
        jTable1.changeSelection(x, y+1, false, false);
        jTable1.requestFocus();
    }   
    
    public ArrayList<Case> lancerAStar(){
        new Fenetre(new Terrain(), new JFrame(), true).setVisible(true);
        return solution;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Fenetre.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Fenetre.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Fenetre.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Fenetre.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Fenetre(new Terrain(), new JFrame(), true).setVisible(true);
            }
        });
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBas;
    private javax.swing.JButton addDroite;
    private javax.swing.JButton addGauche;
    private javax.swing.JButton addHaut;
    private javax.swing.JButton addObstacle;
    private javax.swing.JButton delBas;
    private javax.swing.JButton delDroite;
    private javax.swing.JButton delGauche;
    private javax.swing.JButton delHaut;
    private javax.swing.JButton delObstacle;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables

    @Override
    public void componentResized(ComponentEvent e) {
        jTable1.setRowHeight(jPanel1.getHeight()/ARENE_HEIGHT-2);
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void componentShown(ComponentEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

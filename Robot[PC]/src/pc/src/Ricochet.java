/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc.src;


/**
 *
 * @author keke
 */
public class Ricochet {
    public static final int height = 11;
    public static final int width = 23;
    
    private Terrain labyrinthe;
    private int xFinal, yFinal;             //coordonnées de l'objectif
   /* 
    public Ricochet(Terrain lab, int finalX, int finalY){
        xFinal = finalX;
        yFinal = finalY;
        labyrinthe = lab;
        algorithme(labyrinthe.getCase(finalX, finalY).setFinal(), -1, 1);
    }
    
    public Terrain algorithme(Case caseCourante, int directionCourante, int profondeur){
        caseCourante.setPoids(1, directionCourante);
        ArrayList<Integer> directions = caseCourante.directionsPossible(directionCourante);
        for(int i=0 ; i< directions.size() ; i++){         
            do{
                // on avance
                caseCourante = labyrinthe.avancer(caseCourante.getX(), caseCourante.getY(), directions.get(i));
                caseCourante.setPoids(1, directions.get(i));
                if(caseCourante.getMurs() != 0 && profondeur > 0){
                    labyrinthe = algorithme(caseCourante, directions.get(i), profondeur--);
                }
            }while(!caseCourante.finTrajectoire(directionCourante));
            
        }
        return labyrinthe;
    }
    
    public Terrain getTerrain(){
        return labyrinthe;
    }*/
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc.src;

import java.awt.Point;
import java.util.ArrayList;
import static pc.src.Constantes.*;

/**
 *
 * @author keke
 */
public class Terrain {

    private Case[][] terrain;
    
    public Terrain(){
        terrain = new Case[ARENE_HEIGHT][ARENE_WIDTH];
        for(int i=0 ; i<terrain.length ; i++){
            for(int j=0 ; j<terrain[i].length ; j++){
                terrain[i][j] = new Case(new Point(i, j));
                if(i==0){
                    terrain[i][j].addMur(HAUT);
                }
                else if(i==ARENE_HEIGHT-1){
                    terrain[i][j].addMur(BAS);
                }
                if(j==0){
                    terrain[i][j].addMur(GAUCHE);
                }
                else if(j==ARENE_WIDTH-1){
                    terrain[i][j].addMur(DROITE);
                }
            }
        }
        
    	/* TESTS */
        /*
        //premiere ligne
        addMur(0, 3, DROITE);
        addMur(0, 15, DROITE);
        addMur(1, 5, DROITE);
        
        //croix
        addMur(1, 5, BAS);
        addMur(1, 10, DROITE);
        addMur(1, 10, BAS);
        addMur(1, 11, BAS);
        
        // le reste ligne par ligne
        addMur(2, 1, DROITE);
        addMur(2, 1, BAS);
        addMur(2, 10, DROITE);
        addMur(2, 18, BAS);
        
        //ligne4
        addMur(3, 0, DROITE);
        addMur(3, 18, HAUT);
        addMur(3, 18, DROITE);
        
        //ligne5
        addMur(4, 0, DROITE);
        addMur(4, 1, BAS);
        addMur(4, 9, BAS);
        addMur(4, 10, DROITE);
        addMur(4, 10, BAS);
        
        //ligne6
        addMur(5, 10, DROITE);
        addMur(5, 22, BAS);
        
        //ligne7
        addMur(6, 2, BAS);
        
        //ligne8
        addMur(7, 2, DROITE);
        addMur(7, 7, DROITE);
        addMur(7, 7, BAS);
        addMur(7, 14, DROITE);
        addMur(7, 21, BAS);
        addMur(7, 21, GAUCHE);
        
        //ligne9
        addMur(8, 2, DROITE);
        addMur(8, 7, DROITE);
        addMur(8, 15, GAUCHE);
        addMur(8, 15, BAS);
        addMur(8, 16, BAS);
        
        //ligne10
        addMur(9, 5, GAUCHE);
        addMur(9, 5, BAS);
        addMur(9, 14, DROITE);
        
        //ligne11
        addMur(10, 11, DROITE);
        addMur(10, 19, DROITE);
        
        //ajout d'un mur pour avoir unz solution
        addMur(8, 19, BAS);
        
        
        addObstacle(0, 18);
        addObstacle(2, 14);
        addObstacle(3, 8);
        addObstacle(6, 3);
        */
    }
    
    /* GETTERS */
    
    public Case getCase(Point p){
        return terrain[p.x][p.y];
    }
    
    
    public void addMur(Point p, int dir){
        terrain[p.x][p.y].addMur(dir);
        switch(dir){
            case HAUT: if(p.x>0)
                    terrain[p.x-1][p.y].addMur(BAS);
                break;
            case GAUCHE: if(p.y>0)
                    terrain[p.x][p.y-1].addMur(DROITE);
                break;
            case BAS: if(p.x<ARENE_HEIGHT-1)
                    terrain[p.x+1][p.y].addMur(HAUT);
                break;
            case DROITE: if(p.y<ARENE_WIDTH-1)
                    terrain[p.x][p.y+1].addMur(GAUCHE);
                break;
        }
    }
    
    public void addNoMurs(Point p, int dir){
    	terrain[p.x][p.y].addNoMurs(dir);
        switch(dir){
            case HAUT: if(p.x>0)
                    terrain[p.x-1][p.y].addNoMurs(BAS);
                break;
            case GAUCHE: if(p.y>0)
                    terrain[p.x][p.y-1].addNoMurs(DROITE);
                break;
            case BAS: if(p.x<ARENE_HEIGHT-1)
                    terrain[p.x+1][p.y].addNoMurs(HAUT);
                break;
            case DROITE: if(p.y<ARENE_WIDTH-1)
                    terrain[p.x][p.y+1].addNoMurs(GAUCHE);
                break;
        }
    }
    
    public void addObstacle(int x, int y){
        terrain[x][y].setObstacle();
            
        switch(x){
            case 0 : terrain[x+1][y].addMur(HAUT);
                break;
            case ARENE_HEIGHT : terrain[x-1][y].addMur(BAS);
                break;
            default : terrain[x+1][y].addMur(HAUT);
                    terrain[x-1][y].addMur(BAS);
                break;
        }
        switch(y){
            case 0 : terrain[x][y+1].addMur(GAUCHE);
                break;
            case ARENE_WIDTH :terrain[x][y-1].addMur(DROITE);
                break;
            default : terrain[x][y-1].addMur(DROITE);
                terrain[x][y+1].addMur(GAUCHE);
                break;
        }
    }

    /* SETTERS */
    
    /* METHODS */
    
    public void setPoids(Case caseCourante, int poids, int direction){
        terrain[caseCourante.getX()][caseCourante.getY()].setPoids(poids, direction);
    }
    
    public boolean dejaVisite(Case caseCourante){
        return terrain[caseCourante.getX()][caseCourante.getY()].getVisite();
    }
    
    public void setVisite(Case caseCourante, boolean b){
        terrain[caseCourante.getX()][caseCourante.getY()].setVisite(b);
    }
    
    public Case avancer(int x, int y, int direction){
        Case caseTmp = null;
        
        switch(direction){
            case HAUT : caseTmp = new Case(terrain[x-1][y]);
                break;
            case BAS : caseTmp = new Case(terrain[x+1][y]);
                break;
            case DROITE : caseTmp = new Case(terrain[x][y+1]);
                break;
            case GAUCHE : caseTmp = new Case(terrain[x][y-1]);
                break;
            default: ;
        }
        return caseTmp;
    }
    
    public Case avancer(Case caseCourante, int direction){
        int x = caseCourante.getX();
        int y = caseCourante.getY();
        switch(direction){
            case HAUT : return terrain[x-1][y];
            case BAS : return terrain[x+1][y];
            case DROITE : return terrain[x][y+1];
            case GAUCHE : return terrain[x][y-1];
            default: return null;
        }
    }
    
    public ArrayList<Integer> getDirections(Case caseCourante, int direction){
        ArrayList<Integer> directionPossible = caseCourante.directionsPossible(direction);
        return directionPossible;
    }
    
    public void avancer(Point position, int direction){
    	switch(direction){
    	case HAUT: position.x -= 1;
    		break;
    	case BAS: position.x += 1;
    		break;
    	case GAUCHE: position.y -= 1;
    		break;
    	case DROITE: position.y += 1;
    		break;
    		default:
    			break;
    	}
    }
}

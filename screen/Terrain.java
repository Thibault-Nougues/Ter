/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screen;

import java.util.ArrayList;

/**
 *
 * @author keke
 */
public class Terrain {
    public static final int height = 11;
    public static final int width = 24;
    public static final int NORTH = 1;
    public static final int WEST = 2;
    public static final int SOUTH = 4;
    public static final int EAST = 8;
    
    private Case[][] terrain;
    
    public Terrain(){
        terrain = new Case[height][width];
        for(int i=0 ; i<terrain.length ; i++){
            for(int j=0 ; j<terrain[i].length ; j++){
                terrain[i][j] = new Case(i, j);
            }
        }
    }
    
    public Terrain(int x, int y){
        terrain = new Case[11][23];
        for(int i=0 ; i<terrain.length ; i++){
            for(int j=0 ; j<terrain[i].length ; j++){
                terrain[i][j] = new Case(i, j);
                if(i==0){
                    terrain[i][j].addMur(NORTH);
                }
                if(j==0){
                    terrain[i][j].addMur(WEST);
                }
                if(i==10){
                    terrain[i][j].addMur(SOUTH);
                }
                if(j==22){
                    terrain[i][j].addMur(EAST);
                }
            }
        }
        
        /* TESTS */
        
        //premiere ligne
        addMur(0, 3, EAST);
        addMur(0, 15, EAST);
        addMur(1, 5, EAST);
        
        //croix
        addMur(1, 5, SOUTH);
        addMur(1, 10, EAST);
        addMur(1, 10, SOUTH);
        addMur(1, 11, SOUTH);
        
        // le reste ligne par ligne
        addMur(2, 1, EAST);
        addMur(2, 1, SOUTH);
        addMur(2, 10, EAST);
        addMur(2, 18, SOUTH);
        
        //ligne4
        addMur(3, 0, EAST);
        addMur(3, 18, NORTH);
        addMur(3, 18, EAST);
        
        //ligne5
        addMur(4, 0, EAST);
        addMur(4, 1, SOUTH);
        addMur(4, 9, SOUTH);
        addMur(4, 10, EAST);
        addMur(4, 10, SOUTH);
        
        //ligne6
        addMur(5, 10, EAST);
        addMur(5, 22, SOUTH);
        
        //ligne7
        addMur(6, 2, SOUTH);
        
        //ligne8
        addMur(7, 2, EAST);
        addMur(7, 7, EAST);
        addMur(7, 7, SOUTH);
        addMur(7, 14, EAST);
        addMur(7, 21, SOUTH);
        addMur(7, 21, WEST);
        
        //ligne9
        addMur(8, 2, EAST);
        addMur(8, 7, EAST);
        addMur(8, 15, WEST);
        addMur(8, 15, SOUTH);
        addMur(8, 16, SOUTH);
        
        //ligne10
        addMur(9, 5, WEST);
        addMur(9, 5, SOUTH);
        addMur(9, 14, EAST);
        
        //ligne11
        addMur(10, 11, EAST);
        addMur(10, 19, EAST);
        
        //ajout d'un mur pour avoir unz solution
        addMur(8, 19, SOUTH);
        
        
        addObstacle(0, 18);
        addObstacle(2, 14);
        addObstacle(3, 8);
        addObstacle(6, 3);
    }
    
    /* GETTERS */
    
    public Case getCase(int x, int y){
        return terrain[x][y];
    }
    
    
    public void addMur(int x, int y, int dir){
        terrain[x][y].addMur(dir);
        switch(dir){
            case NORTH: if(x>0)
                    terrain[x-1][y].addMur(SOUTH);
                break;
            case WEST: if(y>0)
                    terrain[x][y-1].addMur(EAST);
                break;
            case SOUTH: if(x<height)
                    terrain[x+1][y].addMur(NORTH);
                break;
            case EAST: if(y<width)
                    terrain[x][y+1].addMur(WEST);
                break;
        }
        
    }
    
    public void addObstacle(int x, int y){
        terrain[x][y].setObstacle();
            
        switch(x){
            case 0 : terrain[x+1][y].addMur(NORTH);
                break;
            case 10 : terrain[x-1][y].addMur(SOUTH);
                break;
            default : terrain[x+1][y].addMur(NORTH);
                    terrain[x-1][y].addMur(SOUTH);
                break;
        }
        switch(y){
            case 0 : terrain[x][y+1].addMur(WEST);
                break;
            case 22 :terrain[x][y-1].addMur(EAST);
                break;
            default : terrain[x][y-1].addMur(EAST);
                terrain[x][y+1].addMur(WEST);
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
            case NORTH : caseTmp = new Case(terrain[x-1][y]);
                break;
            case SOUTH : caseTmp = new Case(terrain[x+1][y]);
                break;
            case EAST : caseTmp = new Case(terrain[x][y+1]);
                break;
            case WEST : caseTmp = new Case(terrain[x][y-1]);
                break;
            default: ;
        }
        return caseTmp;
    }
    
    public Case avancer(Case caseCourante, int direction){
        int x = caseCourante.getX();
        int y = caseCourante.getY();
        switch(direction){
            case NORTH : return terrain[x-1][y];
            case SOUTH : return terrain[x+1][y];
            case EAST : return terrain[x][y+1];
            case WEST : return terrain[x][y-1];
            default: return null;
        }
    }
    
    public ArrayList<Integer> getDirections(Case caseCourante, int direction){
        ArrayList<Integer> directionPossible = caseCourante.directionsPossible(direction);
        return directionPossible;
    }
}

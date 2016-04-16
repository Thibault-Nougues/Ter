/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screen;

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
                terrain[i][j] = new Case(i*10+j);
            }
        }
    }
    
    public Terrain(int x, int y){
        terrain = new Case[11][24];
        for(int i=0 ; i<terrain.length ; i++){
            for(int j=0 ; j<terrain[i].length ; j++){
                terrain[i][j] = new Case(i*10+j);
            }
        }
        
        terrain[0][0].setPosCourante(true);
        
        terrain[0][18].setObstacle();
        terrain[2][14].setObstacle();
        terrain[3][8].setObstacle();
        terrain[7][3].setObstacle();
        
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
        
        // Et ainsi de suite ...
        
        terrain[9][22].setFinal();
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

    /* SETTERS */
    
    /* METHODS */
    
    
    /*
        La classe Case représente une cellule pouvant avoir des murs sur chacuns
        de se côtés.
    
    */
    public class Case {
        private boolean isPosCourante, isFinal;
        private int murs;
        public int num;

        public Case(int numero){
            isPosCourante = isFinal = false;
            num = numero;
            int murs = 0;
        }

        /* GETTERS */
        public boolean getMurNorth(){
            return (murs & NORTH) == NORTH;
        }
        
        public boolean getMurWest(){
            return (murs & WEST) == WEST;
        }
        
        public boolean getMurSouth(){
            return (murs & SOUTH) == SOUTH;
        }
        
        public boolean getMurEast(){
            return (murs & EAST) == EAST;
        }
        
        public int getMurs(){
            return murs;
        }
        
        
        public boolean getFinal(){
            return isFinal;
        }
        
        public boolean estObstacle(){
            return (murs & NORTH) == NORTH &&
                    (murs & EAST) == EAST &&
                    (murs & SOUTH) == SOUTH &&
                    (murs & WEST) == WEST;
        }
        
        public boolean estPosCourante(){
            return isPosCourante;
        }

        
        /* SETTER */
        public void addMur(int direction){
            if((murs & direction) == 0)
                murs += direction;
        }
        
        
        public void setPosCourante(boolean isCourant){
            isPosCourante = isCourant;
        }
        
        public void setObstacle(){
            murs = NORTH+WEST+SOUTH+EAST;
        }
        
        public void setFinal(){
            isFinal = true;
        }

    }
}

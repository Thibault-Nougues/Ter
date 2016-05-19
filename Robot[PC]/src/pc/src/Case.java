/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pc.src;
import java.util.ArrayList;
import static pc.src.Constantes.*;


/**
 *
 * @author keke
 */
public class Case implements Comparable<Case>{
    private boolean dejaVisite, isFinal, isDepart;
    private int murs;
    private int mursVue;
    private int x, y;
    private int poids;
    private int direction;

    public Case(int posX, int posY){
        dejaVisite = isFinal = isDepart = false;
        murs = mursVue = direction = 0;
        poids = 100;
        x = posX;
        y = posY;
    }
    //Constructeur par copie
    public Case(Case c){
        dejaVisite = c.dejaVisite;
        isDepart = c.isDepart;
        isFinal = c.isFinal;
        murs = c.murs;
        direction = c.direction;
        poids = c.poids;
        x = c.x;
        y = c.y;
    }

    /* GETTERS */
    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public boolean getVisite(){
        return dejaVisite;
    }
    @Override
    public String toString() {
        return x + "/" + y + " : p=" + poids + " | dir=" + direction + " | visite=" + dejaVisite;
    }
    public int getDirection(){
        return direction;
    }
    
    public boolean getDepart(){
        return isDepart;
    }
    
    public boolean getFinal(){
        return isFinal;
    }

    public boolean getMur(int direction){
        switch(direction){
            case HAUT : return (murs & HAUT) == HAUT;
            case BAS : return (murs & BAS) == BAS;
            case DROITE : return (murs & DROITE) == DROITE;
            case GAUCHE : return (murs & GAUCHE) == GAUCHE;
            default: return false;
        }
    }

    public int getMurs(){
        return murs;
    }
    
    public int getMursVue(){
        return mursVue;
    }
    
    public int getPoids(){
        return poids;
    }

    public boolean estObstacle(){
        return (murs & HAUT) == HAUT &&
                (murs & DROITE) == DROITE &&
                (murs & BAS) == BAS &&
                (murs & GAUCHE) == GAUCHE;
    }
    
    /* SETTER */
    public int setX(int val){
        return x=val;
    }

    public int setY(int val){
        return y=val;
    }
    
    public void setVisite(boolean b){
        dejaVisite = b;
    }    
    
    public void addMur(int direction){
        murs = murs | direction;
    }

    public void setObstacle(){
        murs = HAUT+GAUCHE+BAS+DROITE;
    }
    
    public void setDepart(){
        isDepart = true;
    }
    
    public void setFinal(){
        isFinal = true;
        poids = 0;
    }

    public void setPoids(int newPoids, int newDirection){
        poids = newPoids;
        direction = newDirection;
    }
    
    public void setDirection(int newDirection){
        direction = newDirection;
    }

    /*Méthodes pour l'algorithme A* */

    public int directionOppose(int direction){
        // si trajectoires perpendiculaires
        if(direction == HAUT || direction == GAUCHE)
			return direction*=4;
		else
			return direction/=4;
    }
    
    public boolean directionOppose(Case caseCourante){
        // si trajectoires perpendiculaires
        switch(direction){
            case HAUT : return caseCourante.getDirection() == BAS;
            case BAS : return caseCourante.getDirection() == HAUT;
            case DROITE : return caseCourante.getDirection() == GAUCHE;
            case GAUCHE : return caseCourante.getDirection() == DROITE;
            default: return true;
        }
    }

    public boolean memeTrajectoire(int direction){
        return this.direction == direction || 
                this.direction == directionOppose(direction);
    }

    public ArrayList<Integer> directionsPossible(int direction){
        ArrayList<Integer> directions = new ArrayList();
        if((murs & direction) != direction && direction != 0){
            directions.add(direction);
        }
        //cas du tunnel
        if(((murs & HAUT) == HAUT && (murs & BAS) == BAS ) ||
                ((murs & DROITE) == DROITE && (murs & GAUCHE) == GAUCHE)){
            return directions;
        }
        else{
            // test de toutes les directions
            if((murs & HAUT) == HAUT && direction != HAUT){
                directions.add(BAS);
            }
            if((murs & DROITE) == DROITE && direction != DROITE){
                directions.add(GAUCHE);
            }
            if((murs & BAS) == BAS && direction != BAS){
                directions.add(HAUT);
            }
            if((murs & GAUCHE) == GAUCHE && direction != GAUCHE){
                directions.add(DROITE);
            }
        }

        // retour des directions opposÃ©es aux obstacles
        return directions;
    }

    public boolean finTrajectoire(){
        return (murs & direction) == direction;
    }
    
    @Override
    public boolean equals(Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Case))return false;
        Case caseTmp = (Case)other;
        return x == caseTmp.x && y == caseTmp.y && this.memeTrajectoire(caseTmp.direction);
    }
    
    @Override
    public int compareTo(Case o) {
        int retour = 1;
        
        if(o.direction == 0 || direction == 0){
            if(direction == 0) retour = -1;
            if(x==o.x && y==o.y)retour = 0;
        }        
        else if((o.dejaVisite || dejaVisite)){
            if(dejaVisite) retour = -1;
            if(x==o.x && y==o.y && direction == o.direction)retour = 0;
        }
        else{
            retour = (x==o.x && y==o.y && direction == o.direction && poids - o.poids != 0) ? poids - o.poids : 1;
        }
        return retour;
    }
    
    /* Méthodes pour la cartographie */
    
    public void addMursVue(int murs){
    	//vérif murs si erreur
    	/*
    	 * 
    	 * A FAIRE !!!
    	 * 
    	 * 
    	 */
		mursVue &= murs;
    }
    
}

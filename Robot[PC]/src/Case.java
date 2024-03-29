/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.awt.Point;
import java.util.ArrayList;
import static src.Constantes.*;


/**
 *
 * @author keke
 */
public class Case implements Comparable<Case>{
    private boolean dejaVisite, isFinal, isDepart;
    private int murs;
    private int mursVue;
    private Point position;
    private int poids;
    private int direction;

    public Case(Point p){
        dejaVisite = isFinal = isDepart = false;
        murs = mursVue = direction = 0;
        poids = 100;
        position = p;
    }
    
    public Case(){
        dejaVisite = isFinal = isDepart = false;
        murs = mursVue = direction = 0;
        poids = 100;
        position = new Point(0,0);
    }
    
    //Constructeur par copie
    public Case(Case c){
        dejaVisite = c.dejaVisite;
        isDepart = c.isDepart;
        isFinal = c.isFinal;
        murs = c.murs;
        direction = c.direction;
        poids = c.poids;
        position = c.position;
    }

    /* GETTERS */
    public Point getPosition(){
        return position;
    }
    
    public int getX(){
        return position.x;
    }

    public int getY(){
        return position.y;
    }

    public boolean getVisite(){
        return dejaVisite;
    }
    @Override
    public String toString() {
        return position.x + "/" + position.y + " : p=" + poids + " | dir=" + direction + " | visite=" + dejaVisite;
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
    public void setPosition(Point p){
        position = p;
    }
    
    public void setX(int val){
        position.x=val;
    }

    public void setY(int val){
        position.y=val;
    }
    
    public void setVisite(boolean b){
        dejaVisite = b;
    }    
    
    public void addMur(int direction){
        murs |= direction;
        mursVue |= direction;
    }
    
    public void addNoMurs(int murs){
        mursVue |= murs;
        this.murs &= (0xF^murs);
    }
    
    public void setObstacle(){
    	mursVue = murs = HAUT+GAUCHE+BAS+DROITE;
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

    /*M�thodes pour l'algorithme A* */

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
        ArrayList<Integer> directions = new ArrayList<Integer>();
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

        // retour des directions opposées aux obstacles
        return directions;
    }

    public boolean finTrajectoire(){
        return (murs & direction) == direction;
    }
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Case other = (Case) obj;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}
    
    @Override
    public int compareTo(Case o) {
        int retour = 1;
        
        if(o.direction == 0 || direction == 0){
            if(direction == 0) retour = -1;
            if(position==o.position)retour = 0;
        }        
        else if((o.dejaVisite || dejaVisite)){
            if(dejaVisite) retour = -1;
            if(position==o.position && direction == o.direction)retour = 0;
        }
        else{
            retour = (position==o.position && direction == o.direction && poids - o.poids != 0) ? poids - o.poids : 1;
        }
        return retour;
    }
    
}

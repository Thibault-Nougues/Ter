package nxt.src;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.Sound;
import lejos.robotics.navigation.DifferentialPilot;
import static nxt.src.Constantes.*;
import java.lang.Math;

public class Deplacement {
	
	public Deplacement(){
		pilote=new DifferentialPilot(WHEEL_SIZE, TRACKWIDTH, Motor.A, Motor.C);
		pilote.setTravelSpeed(SPEED);
	}
	
	public void avancer(){
		pilote.forward();
	}
	
	public void avancer(double distance){
		pilote.travel(distance);
	}
	
	public void arreter(){
		pilote.stop();
	}
	
	public void tourner(int cote, boolean sansArret ){
		//pilote.setTravelSpeed(ROTATE_SPEED);
		switch(cote){
			case DROITE :
				if(sansArret){
					int test = MOTEUR_GAUCHE.getTachoCount();
					while(MOTEUR_GAUCHE.getTachoCount()<test+350 && !Button.ESCAPE.isDown()){
						MOTEUR_GAUCHE.forward();
						MOTEUR_DROITE.setSpeed(0);
					}
				}else{
					this.arreter();
					pilote.arc(0,-90);
				}
				//MOTEUR_DROITE.setSpeed(SPEED);
			break;
			case GAUCHE : 
				if(sansArret){
					int test = MOTEUR_DROITE.getTachoCount();
					while(MOTEUR_DROITE.getTachoCount()<test+350 && !Button.ESCAPE.isDown()){
						MOTEUR_DROITE.forward();
						MOTEUR_GAUCHE.setSpeed(0);
					}
				}else{
					this.arreter();
					pilote.arc(0,90);
				}
			break;
		}
		pilote.setTravelSpeed(SPEED);
		this.avancer();
	}
	
	public void demiTour(){
		pilote.setTravelSpeed(ROTATE_SPEED);
		pilote.arc(0, 180);
		pilote.setTravelSpeed(SPEED);
	}
	
	public void redresser(int ligne){
		int debut=0,fin=0;
		int couleur_gauche=COULEUR_GAUCHE.readValue(),couleur_droite=COULEUR_DROITE.readValue();
		if(couleur_droite>=ligne-TOLERENCE && couleur_droite<=ligne+TOLERENCE 
			&& (couleur_gauche<ligne-TOLERENCE || couleur_gauche>ligne+TOLERENCE)){
			couleur_gauche=COULEUR_GAUCHE.readValue();
			fin=debut=MOTEUR_DROITE.getTachoCount();
			while((couleur_gauche<ligne-TOLERENCE || couleur_gauche>ligne+TOLERENCE)
					&& !Button.ESCAPE.isDown()){
				couleur_gauche=COULEUR_GAUCHE.readValue();
			}
			fin=MOTEUR_DROITE.getTachoCount();
			int angle=fin-debut;
			this.avancer();
			MOTEUR_DROITE.rotate(angle-20);
			this.avancer();
			Sound.beep();
			//this.arreter();
		}
		if(COULEUR_DROITE.readValue()!=ligne && COULEUR_GAUCHE.readValue()==ligne){
			/*fin=debut=MOTEUR_GAUCHE.getTachoCount();
			while(COULEUR_DROITE.readValue()!=ligne){
				fin=MOTEUR_GAUCHE.getTachoCount();
			}
			MOTEUR_DROITE.rotate(fin-debut);
			this.avancer();*/
			this.arreter();
		}
	}
	
	public void test(){
		pilote.arc(TRACKWIDTH/2, 90);
	}

}

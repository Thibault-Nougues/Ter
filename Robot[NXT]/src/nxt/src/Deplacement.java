package nxt.src;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.robotics.navigation.DifferentialPilot;
import static nxt.src.Constantes.*;
import java.io.IOException;

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
	
	public void patienter(int angle, NXTRegulatedMotor moteur){
		int angle_depart = moteur.getTachoCount();
		while(moteur.getTachoCount()<angle_depart+angle){}
	}
	
	public void eviterMur(int cote){
		int tacho = 0 ;
		switch (cote) {
		case GAUCHE:
			tacho=MOTEUR_GAUCHE.getTachoCount();
			while(MOTEUR_GAUCHE.getTachoCount()<tacho+EVITERMUR_ANGLE){
				MOTEUR_DROITE.setSpeed(0);
			}
			break;

		case DROITE :
			tacho=MOTEUR_DROITE.getTachoCount();
			while(MOTEUR_DROITE.getTachoCount()<tacho+EVITERMUR_ANGLE){
				MOTEUR_GAUCHE.setSpeed(0);
			}
			break;
		}
		pilote.setTravelSpeed(SPEED);
		this.avancer();
	}
	
	public boolean redresser() throws IOException{
		int angle_redresser = (int) (SPEED*0.073);
		if(COULEUR_GAUCHE.getNormalizedLightValue()<SEUIL 
				&& COULEUR_DROITE.getNormalizedLightValue()<SEUIL){
			this.patienter(180, MOTEUR_GAUCHE);
			return true;
		}else if(COULEUR_GAUCHE.getNormalizedLightValue()<SEUIL 
				&& COULEUR_DROITE.getNormalizedLightValue()>SEUIL){
			
			while(COULEUR_DROITE.getNormalizedLightValue()>SEUIL)
				MOTEUR_GAUCHE.setSpeed(0);
			pilote.setTravelSpeed(SPEED);
			this.patienter(angle_redresser, MOTEUR_DROITE);
			this.avancer();
			this.patienter(180, MOTEUR_DROITE);
			return true;
		}else if(COULEUR_DROITE.getNormalizedLightValue()<SEUIL 
				&& COULEUR_GAUCHE.getNormalizedLightValue()>SEUIL){
			
			while(COULEUR_GAUCHE.getNormalizedLightValue()>SEUIL)
				MOTEUR_DROITE.setSpeed(0);
			pilote.setTravelSpeed(SPEED);
			this.patienter(angle_redresser, MOTEUR_GAUCHE);
			this.avancer();
			this.patienter(180, MOTEUR_GAUCHE);
			return true;
		}
		return false;
	}
	
	public void ralentir(){
		SPEED=175;
		pilote.setTravelSpeed(SPEED);
	}
	
	public void accelerer(){
		SPEED=300;
		pilote.setTravelSpeed(SPEED);
	}
	
	public void test(){
		System.out.print("ok");
	}

}

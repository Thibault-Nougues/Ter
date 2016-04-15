package nxt.src;

import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;
import static nxt.src.Constantes.*;

public class Deplacement {
	
	public Deplacement(){
		p=new DifferentialPilot(WHEEL_SIZE, TRACKWIDTH, Motor.A, Motor.C);
		p.setTravelSpeed(SPEED);
	}
	
	public void avancer(){
		p.forward();
	}
	
	public void avancer(double distance){
		p.travel(distance);
	}
	
	public void arreter(){
		p.stop();
	}
	
	public void tourner(int cote){
		p.setTravelSpeed(ROTATE_SPEED);
		switch(cote){
			case DROITE : p.arc(0, -90);
			break;
			case GAUCHE : p.arc(0, 90);
			break;
		}
		p.setTravelSpeed(SPEED);
	}
	
	public void demiTour(){
		p.setTravelSpeed(ROTATE_SPEED);
		p.arc(0, 180);
		p.setTravelSpeed(SPEED);
	}

}

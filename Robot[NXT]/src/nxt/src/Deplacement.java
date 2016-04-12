package nxt.src;

import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;

public class Deplacement {
	
	public static final double WHEEL_SIZE = 5.6;
	public static final double TRACKWIDTH = 11.4;
	DifferentialPilot p;
	
	public Deplacement(){
		this.p=new DifferentialPilot(WHEEL_SIZE, TRACKWIDTH, Motor.A, Motor.C);
	}
	
	public void avancer(){
		p.backward();
	}
	
	public void arreter(){
		p.stop();
	}
	
	public void tourner(String cote){
		switch(cote){
			case "droite" : p.arc(0, 90);
			break;
			case "gauche" : p.arc(0, -90);
			default : 
		}
	}

}

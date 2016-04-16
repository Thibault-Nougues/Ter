package nxt.src;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;

public class Constantes {
	
	public static final int AVANT = 0;
	public static final int ARRIERE = 1;
	public static final int DROITE = 2;
	public static final int GAUCHE = 3;
	
	public static final byte DISTANCE_GAUCHE = 0b01111111;
	public static final byte DISTANCE_DROITE = 0b00000000;
	
	public static final double WHEEL_SIZE = 56;
	public static final double TRACKWIDTH = 114;
	public static final double SPEED = 250;
	public static final double ROTATE_SPEED = 100;
	public static DifferentialPilot p;
	
	public static final NXTRegulatedMotor MOTEUR_GAUCHE = Motor.A;
	public static final NXTRegulatedMotor MOTEUR_TETE = Motor.B;
	public static final NXTRegulatedMotor MOTEUR_DROITE = Motor.C;
	
	public static final UltrasonicSensor TETE = new UltrasonicSensor(SensorPort.S1);
	public static final LightSensor COULEUR = new LightSensor(SensorPort.S2);

}

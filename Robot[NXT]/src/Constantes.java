package src;

import java.util.ArrayList;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;

public class Constantes {
	
	public static final int AVANT = 0;
	public static final int ARRIERE = 1;
	public static final int DROITE = 8;
	public static final int GAUCHE = 2;
	public static final int FIN = -1;
	public static final int REDRESSER_DROITE = 5;
	public static final int REDRESSER_GAUCHE = 6;
	
	public static int ACTION;
	public static ArrayList<Integer> CHEMIN;
	public static final int SEUIL = 500;
	public static final int EVITERMUR_ANGLE = 60;
	
	public static final byte DISTANCE_GAUCHE = 0b01111111;
	public static final byte DISTANCE_DROITE = 0b00000000;
	
	public static final double WHEEL_SIZE = 56;
	public static final double TRACKWIDTH = 114;
	public static double SPEED = 320;
	public static final double ROTATE_SPEED = 100;
	public static DifferentialPilot pilote;
	
	public static final NXTRegulatedMotor MOTEUR_GAUCHE = Motor.A;
	public static final NXTRegulatedMotor MOTEUR_TETE = Motor.B;
	public static final NXTRegulatedMotor MOTEUR_DROITE = Motor.C;

	public static final UltrasonicSensor TETE_AVANT = new UltrasonicSensor(SensorPort.S2);
	public static final UltrasonicSensor TETE_ARRIERE = new UltrasonicSensor(SensorPort.S3);
	public static final LightSensor COULEUR_DROITE = new LightSensor(SensorPort.S1);
	public static final LightSensor COULEUR_GAUCHE = new LightSensor(SensorPort.S4);
	
}

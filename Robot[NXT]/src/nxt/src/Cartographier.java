package nxt.src;

import lejos.nxt.Battery;
import lejos.nxt.Button;
import lejos.nxt.Sound;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

import static nxt.src.Constantes.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Cartographier extends Thread {
	
	private static Deplacement deplacement;
	private DataOutputStream outputData;
	
	public Cartographier(DataOutputStream outputData){
		MOTEUR_TETE.setSpeed(100*Battery.getVoltage());
		deplacement = new Deplacement();
		this.outputData = outputData;
		this.start();
	}
	
	public void depart(){
		//int av=TETE.getDistance();
		MOTEUR_TETE.rotate(90);
		//int g=TETE.getDistance();
		MOTEUR_TETE.rotate(-180);
		//int d=TETE.getDistance();
		MOTEUR_TETE.rotate(90);
		//if(g>d && g>av)
		//	deplacement.tourner(GAUCHE,false);
		//if(d>av && d>g)
		//	deplacement.tourner(DROITE,false);
	}
	
	public void scanner() throws IOException{Sound.beep();
		outputData.write((byte)TETE_AVANT.getDistance());
		outputData.flush();
		MOTEUR_TETE.rotate(90);
		outputData.write(DISTANCE_GAUCHE);
		outputData.flush();
		outputData.write((byte)TETE_ARRIERE.getDistance());
		outputData.flush();
		outputData.write(DISTANCE_DROITE);
		outputData.flush();
		outputData.write((byte)TETE_AVANT.getDistance());
		outputData.flush();
		MOTEUR_TETE.rotate(-90);
	}
	
	public void run(){
		boolean obstacle = false;
		
		
		try {
			scanner();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		while(!Button.ESCAPE.isDown() && !obstacle ){
			deplacement.avancer();
			System.out.println(COULEUR_DROITE.getNormalizedLightValue());
			try {
				if(deplacement.redresser()){
					try {
						if(TETE_AVANT.getDistance()<75 && TETE_AVANT.getDistance()>40){
							deplacement.ralentir();
						}
						this.scanner();
						switch (ACTION) {
						case ARRIERE:
							deplacement.demiTour();
							break;
						case DROITE:
						case GAUCHE:
							deplacement.tourner(ACTION, false);
							break;
						}
						ACTION=AVANT;
						while(TETE_AVANT.getDistance()<35){
							
							deplacement.tourner(DROITE, false);
						}
						if(TETE_AVANT.getDistance()<75 && TETE_AVANT.getDistance()>40){
							deplacement.ralentir();
						}else if(TETE_AVANT.getDistance()>80){
							deplacement.accelerer();
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else{
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		
		deplacement.arreter();

	}


}

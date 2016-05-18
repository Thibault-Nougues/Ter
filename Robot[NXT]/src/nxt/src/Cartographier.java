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
	private static BTConnection pcConnect;
	private static DataInputStream inputData;
	private static DataOutputStream outputData;
	
	public Cartographier(){
		MOTEUR_TETE.setSpeed(100*Battery.getVoltage());
		deplacement = new Deplacement();
		this.start();
	}
	
	public void connexion(){
		pcConnect = Bluetooth.waitForConnection();
		inputData = pcConnect.openDataInputStream();
		outputData = pcConnect.openDataOutputStream();
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
	
	public void scanner() throws IOException{
		//for(int i = 0 ; i<250;i++){System.out.println();}
		Sound.beep();
		int g,d;
		MOTEUR_TETE.rotate(90);
		outputData.write(DISTANCE_GAUCHE);
		outputData.flush();
		//g=TETE.getDistance();
		//outputData.write((byte)g);
		outputData.flush();
		MOTEUR_TETE.rotate(-180);
		outputData.write(DISTANCE_DROITE);
		outputData.flush();
		//d=TETE.getDistance();
		//outputData.write((byte)d);
		outputData.flush();
		MOTEUR_TETE.rotate(90);
	}
	
	public void run(){
		boolean obstacle = false;
		int ligne = (COULEUR_DROITE.getNormalizedLightValue()+COULEUR_GAUCHE.getNormalizedLightValue())/2;
		deplacement.avancer(50);
		System.out.println(ligne);
		System.out.println(COULEUR_DROITE.getNormalizedLightValue());
		System.out.println(COULEUR_GAUCHE.getNormalizedLightValue());
		connexion();
		
		while(!Button.ESCAPE.isDown() && TETE_AVANT.getDistance()>20 && !obstacle ){
			deplacement.avancer();
			/*if((COULEUR_DROITE.readValue()<ligne+15 && COULEUR_DROITE.readValue()>ligne-15)){
				//deplacement.arreter();
				Sound.beep();
				//obstacle=true;
			}*/
			try {
				deplacement.redresser(ligne,outputData);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		deplacement.arreter();
		//System.out.println(TETE.getDistance());}
		//int i = 0;
		/*deplacement.avancer();
		for(int j = 0 ; j<200 ; j++){
			System.out.println("depart");COULEUR_DROITE.readValue();
		}
		Sound.beep();
		MOTEUR_DROITE.rotate(360);
		deplacement.avancer();
		for(int j = 0 ; j<200 ; j++){
			System.out.println("depart");COULEUR_DROITE.readValue();
		}
		Sound.buzz();
		deplacement.arreter();*/
		//int ligne=COULEUR.readValue();
		/*deplacement.avancer(50);
		while(!Button.ESCAPE.isDown()){
			//if(!obstacle)
			deplacement.avancer();
			System.out.println(MOTEUR_DROITE.getTachoCount());
			i++;*/
			/*if(COULEUR.readValue()<= ligne+5 && COULEUR.readValue()>= ligne-5){
				deplacement.arreter();
			}*/
			/*if (i==500){
				i++;
				//obstacle=true;
				deplacement.tourner(DROITE,true);
			}
		}*/
		/*int ligne=COULEUR.readValue();
		deplacement.avancer(50);
		depart();
		deplacement.avancer();
		while(!obstacle && !Button.ESCAPE.isDown()){
			if(COULEUR.readValue()<= ligne+5 && COULEUR.readValue()>= ligne-5){
				
				try {
					scanner();
					deplacement.avancer();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}//else{
			//	deplacement.avancer();
			//}
			if (TETE.getDistance()<40){
				obstacle=true;
				deplacement.arreter();
			}*//*else{
				deplacement.arreter();
			}*/
		/*}
		deplacement.demiTour();
		obstacle=false;
		deplacement.avancer();
		while(!obstacle && !Button.ESCAPE.isDown()){
			if (TETE.getDistance()<30){
				obstacle=true;
				deplacement.arreter();
			}
		}*/
		//Button.waitForAnyPress();
	}


}

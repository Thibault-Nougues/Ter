package nxt.src;

import lejos.nxt.Battery;
import lejos.nxt.Button;
import lejos.nxt.Sound;
import static nxt.src.Constantes.*;
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
		switch (ACTION) {
		case ARRIERE:
			ACTION=AVANT;
			deplacement.demiTour();
			deplacement.avancer();
			break;
		case DROITE:
		case GAUCHE:
			if(ACTION==GAUCHE)
				Sound.buzz();
			deplacement.tourner(ACTION, false);
			break;
			
		case REDRESSER_DROITE :
			deplacement.eviterMur(DROITE);
			break;
		
		case REDRESSER_GAUCHE : 
			deplacement.eviterMur(GAUCHE);
			break;
		}
	}
	
	public void run(){
		boolean obstacle = false;
		/*while(!Button.ESCAPE.isDown()){
			System.out.println(COULEUR_DROITE.getNormalizedLightValue()+" "+COULEUR_GAUCHE.getNormalizedLightValue());
		}*/
		try {
			this.scanner();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		while(!Button.ESCAPE.isDown() && !obstacle ){
			deplacement.avancer();
			if(TETE_AVANT.getDistance()<95 && TETE_AVANT.getDistance()>40){
				deplacement.ralentir();
			}else if(TETE_AVANT.getDistance()>95){
				deplacement.accelerer();
			}
			try {
				if(deplacement.redresser()){
					try {
						
						this.scanner();
						
						//ACTION=AVANT;
						while(TETE_AVANT.getDistance()<30){
							deplacement.arreter();
						}
						/*if(TETE_AVANT.getDistance()<95 && TETE_AVANT.getDistance()>40){
							deplacement.ralentir();
						}else */
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
		}
		
		deplacement.arreter();

	}


}

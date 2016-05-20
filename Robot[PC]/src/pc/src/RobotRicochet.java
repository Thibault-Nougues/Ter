package pc.src;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import lejos.pc.comm.NXTConnector;
import static pc.src.Constantes.*;

public class RobotRicochet {

    private static Terrain carte;
	private static Fenetre fen;
    private static Point positionCourante = depart1;
    private static int directionCourante = BAS;
    private static int[] distances = new int[3];
    
	private static NXTConnector nxtConnect;
	private static DataOutputStream outputData;
	private static DataInputStream inputData;
	private static boolean connecte = false;
	
	public static void cartographier() throws IOException{
		
        int action=0;

		do{
			byte data=inputData.readByte();
			switch(data){
				case DISTANCE_GAUCHE :	distances[1] = (int)(inputData.readByte()& (0xff));
										break;
				case DISTANCE_DROITE :	distances[2] = (int)(inputData.readByte()& (0xff));
										action = strategie();
										outputData.write(action);
										outputData.flush();
										break;
				default: 				distances[0] = (int)(data& (0xff));
										break;	
			}
		}while(action != FIN);
	}

	public static int tourner(int dir){
		int direction = directionCourante;
    	switch(dir){
    	case GAUCHE: if(direction!=DROITE)
    		direction*=2;
			else
				direction=HAUT;
    		break;
    	case DROITE: if(direction!=HAUT)
    		direction/=2;
			else
				direction=DROITE;
    		break;
    	case ARRIERE: if(direction == HAUT || direction == GAUCHE)
    		direction*=4;
			else
				direction/=4;
    		break;
    	}
		return direction;
    }
    
	private static void ajouterMurs(int direction, int distance){
		int nbMur = 0;
		switch(direction){
    	case HAUT: nbMur = positionCourante.x-distance/40;
    		break;
    	case BAS: nbMur = distance/40-positionCourante.x;
    		break;
    	case GAUCHE: nbMur = positionCourante.y-distance/40;
    		break;
    	case DROITE: nbMur = distance/40-positionCourante.y;
    		break;
		}
		int i=0;
		Case caseCourante = carte.getCase(positionCourante);
		while(nbMur >i && i<5){
			System.out.println(caseCourante + " : noMur � " + direction);
			caseCourante.addNoMurs(direction);
			fen.jTable1.setValueAt(caseCourante, caseCourante.getX(), caseCourante.getY());
			caseCourante = carte.avancer(caseCourante, direction);
			i++;
		}
		if(nbMur >5){
			System.out.println(caseCourante + " : FIN noMur � " + direction);
			caseCourante.addNoMurs(direction);
		}
		else{
			System.out.println(caseCourante + " : FIN Mur � " + direction);
			caseCourante.addMur(direction);
		}
		fen.jTable1.setValueAt(caseCourante, caseCourante.getX(), caseCourante.getY());
			
	}
	
    /**
     * Methode qui ajoute des murs ou noMurs selon la distance mesure.
     * Attention si on sors de l'arene sinon EXCEPTION !!!
     * @param distance
     */
    private static void ajouterMursVue(){
		ajouterMurs(directionCourante, distances[0]+20);
		ajouterMurs(tourner(GAUCHE), distances[1]+20);
		ajouterMurs(tourner(DROITE), distances[2]+20);
    }
    
    public int calculerRedressement(){
    	switch (directionCourante) {
		case HAUT :
			if(distances[1]/40<positionCourante.getY() && distances[1]<255){
				if(distances[1]%40<10){
					return REDRESSER_DROITE;
				}else if(distances[1]%40>20){
					return REDRESSER_GAUCHE;
				}else {
					return AVANT;
				}
			}else if(distances[2]/40<ARENE_WIDTH-positionCourante.getY() && distances[1]<255){
				if(distances[2]%40<10){
					return REDRESSER_GAUCHE;
				}else if(distances[2]%40>20){
					return REDRESSER_DROITE;
				}else {
					return AVANT;
				}
			}
		break;
		
		case BAS :
			if(distances[2]/40<positionCourante.getY() && distances[2]<255){
				if(distances[2]%40<10){
					return REDRESSER_DROITE;
				}else if(distances[2]%40>20){
					return REDRESSER_GAUCHE;
				}else {
					return AVANT;
				}
			}else if(distances[1]/40<ARENE_WIDTH-positionCourante.getY() && distances[1]<255){
				if(distances[1]%40<10){
					return REDRESSER_GAUCHE;
				}else if(distances[1]%40>20){
					return REDRESSER_DROITE;
				}else {
					return AVANT;
				}
			}
		break;
		
		case DROITE :
			if(distances[1]/40<positionCourante.getX() && distances[1]<255){
				if(distances[1]%40<10){
					return REDRESSER_DROITE;
				}else if(distances[1]%40>20){
					return REDRESSER_GAUCHE;
				}else {
					return AVANT;
				}
			}else if(distances[2]/40<ARENE_WIDTH-positionCourante.getX() && distances[1]<255){
				if(distances[2]%40<10){
					return REDRESSER_GAUCHE;
				}else if(distances[2]%40>20){
					return REDRESSER_DROITE;
				}else {
					return AVANT;
				}
			}
		break;
		case GAUCHE :
			if(distances[2]/40<positionCourante.getX() && distances[2]<255){
				if(distances[2]%40<10){
					return REDRESSER_DROITE;
				}else if(distances[2]%40>20){
					return REDRESSER_GAUCHE;
				}else {
					return AVANT;
				}
			}else if(distances[1]/40<ARENE_WIDTH-positionCourante.getX() && distances[1]<255){
				if(distances[1]%40<10){
					return REDRESSER_GAUCHE;
				}else if(distances[1]%40>20){
					return REDRESSER_DROITE;
				}else {
					return AVANT;
				}
			}
		break;
		}
    	return -1;
    }
    

    public static int strategie(){
    	ajouterMursVue();
    	int action = AVANT;
    	/* cas des faux murs */
    	System.out.println(distances[0]);
    	/* contourner les murs */
    	if(distances[0]<40){
    		action=ARRIERE;
    	}else{
    		action=AVANT;
    	}
    	/* cases inaccessibles */
    	
    	/* aller chercher les dernieres cases */
    	
    	switch(action){
    	case AVANT: positionCourante = avancer(positionCourante, directionCourante);
    		break;
    	case DROITE:
    	case GAUCHE:
    		break;
    	case ARRIERE:
    		break;
    	case REDRESSER_DROITE:
    		break;
    	case REDRESSER_GAUCHE:
    		break;
    	}
    	
    	/* fin de strategie */
    	return action;
    }
    
    public static Point avancer(Point p, int direction){
        Point pointTmp = p;
        
        switch(direction){
            case HAUT : pointTmp.x-=1;
                break;
            case BAS : pointTmp.x+=1;
                break;
            case DROITE : pointTmp.y+=1;
                break;
            case GAUCHE : pointTmp.y-=1;
                break;
            default: ;
        }
        return pointTmp;
    }
    
    
	public static void main(String[] args) throws IOException, InterruptedException {
		carte = new Terrain();
		fen = new Fenetre(carte);
    	fen.jTable1.setDefaultRenderer(Object.class, new TableRendererCarto(carte));
    	fen.setVisible(true);
		
		// TODO Auto-generated method stub
		nxtConnect = new NXTConnector();
		connecte = nxtConnect.connectTo("btspp://001653162E5B");
		outputData = new DataOutputStream(nxtConnect.getOutputStream());
		inputData = new DataInputStream(nxtConnect.getInputStream());
		if(connecte){
			System.out.println("connecte");
			cartographier();
			
			Scanner sc = new Scanner(System.in);
			String arriveeX = sc.nextLine();
			String arriveeY = sc.nextLine();
			
			AStar algo = new AStar(carte, new Point(Integer.parseInt(arriveeX), Integer.parseInt(arriveeY)));
			ArrayList<Case> solution = algo.getSolution();
			/* envoyer la solution au robot */
			
		}else{
			System.out.println("non connecte");
		}
	}
}

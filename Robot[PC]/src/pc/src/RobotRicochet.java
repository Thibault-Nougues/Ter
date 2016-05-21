package pc.src;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import lejos.nxt.Sound;
import lejos.pc.comm.NXTConnector;
import static pc.src.Constantes.*;

public class RobotRicochet {

    private static Terrain carte;
	private static Fenetre fen;
    private static Point positionCourante = depart2;
    private static int directionCourante = DROITE;
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
		int nbMur = distance/40;
		switch(direction){
    	case HAUT: if(positionCourante.x-nbMur<0)
    			nbMur = positionCourante.x-nbMur;
    		break;
    	case BAS: if(positionCourante.x+nbMur+1>ARENE_HEIGHT)
    			nbMur = ARENE_HEIGHT-positionCourante.x-1;
    		break;
    	case GAUCHE: if(positionCourante.y-nbMur<0)
    			nbMur = positionCourante.y-nbMur;
    		break;
    	case DROITE: if(positionCourante.y-distance/40>ARENE_WIDTH)
    		 	nbMur = ARENE_WIDTH-positionCourante.y;
    		break;
		}
		int i=0;
		Case caseCourante = carte.getCase(positionCourante);

		while(nbMur>i && i<4){
			caseCourante.addNoMurs(direction);
			fen.jTable1.setValueAt(caseCourante, caseCourante.getX(), caseCourante.getY());
			caseCourante = carte.avancer(caseCourante, direction);
			i++;
		}

		if(nbMur>4){
			caseCourante.addNoMurs(direction);
		}
		else{
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
		ajouterMurs(directionCourante, distances[0]);
		ajouterMurs(tourner(GAUCHE), distances[1]+20);
		ajouterMurs(tourner(DROITE), distances[2]+20);
    }
    
    public static int redressement (int distance, int cote){
    	switch(cote){
    	case DROITE :
    		System.out.println("droite");
    		if(distance%40<10){
    			return REDRESSER_DROITE;
    		}else if(distance%40>20){
    			return REDRESSER_GAUCHE;
    		}else {
    			return AVANT;
    		}
    	case GAUCHE :
    		System.out.println("gauche");
    		if(distance%40<10){
    			return REDRESSER_GAUCHE;
    		}else if(distance%40>20){
    			return REDRESSER_DROITE;
    		}else {
    			return AVANT;
    		}
    	default :
    		return AVANT;
    	}
    	
    }
    
    public static int calculerRedressement(double position, boolean inverse, int longeur){
    	if (inverse){
    		if(distances[1]/40<position && distances[1]<255){
    			System.out.println("a");
    			return redressement(distances[1],GAUCHE);
			}else if(distances[2]/40<longeur-position && distances[2]<255){
				System.out.println("b");
				return redressement(distances[2],DROITE);
			}
    	}else{
    		if(distances[2]/40<position && distances[2]<255){
    			return redressement(distances[1],DROITE);
			}else if(distances[1]/40<longeur-position && distances[1]<255){
				return redressement(distances[1],GAUCHE);
			}
    	}
    	return AVANT;
    }
    
    public static int calculerRedressementOriente(){

    	switch (directionCourante) {
		case HAUT :
			return(calculerRedressement(positionCourante.getY(), true, ARENE_WIDTH));
		
		case BAS :
			return(calculerRedressement(positionCourante.getY(), false, ARENE_WIDTH));
		
		case DROITE :
			System.out.println("droite   2 ");
			return(calculerRedressement(positionCourante.getX(), true, ARENE_HEIGHT));

		case GAUCHE :
			return(calculerRedressement(positionCourante.getX(), false, ARENE_HEIGHT));
			
		default :
	    	return AVANT;
		}
    }
    

    public static int strategie(){
    	ajouterMursVue();
    	int action = AVANT;
    	/* cas des faux murs */
    	/* contourner les murs */
    	if(distances[0]<40){
    		action=ARRIERE;
    	}else{
    		System.out.println(distances[1]+" "+calculerRedressementOriente());
    		action=calculerRedressementOriente();
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

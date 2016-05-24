package src;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import lejos.pc.comm.NXTConnector;
import static src.Constantes.*;

public class RobotRicochet {

    private static Terrain carte;
	private static Fenetre fen;
    private static int niveau = 0;
    private static boolean contourner = false;
    private static Case caseContournerArrivee = null;
    private static Point positionCourante = new Point(depart1);
    private static int directionCourante = BAS;
    private static HashMap<Integer, Integer> distances = new HashMap<>(3);
    
	private static NXTConnector nxtConnect;
	private static DataOutputStream outputData;
	private static DataInputStream inputData;
	private static boolean connecte = false;
	
	public static void cartographier() throws IOException{
		
        int action=0;

		do{
			byte data=inputData.readByte();
			//System.out.println((int)(data& (0xff)));
			switch(data){
				case DISTANCE_GAUCHE :	distances.put(GAUCHE,(int)(inputData.readByte()& (0xff)));
										break;
				case DISTANCE_DROITE :	distances.put(DROITE,(int)(inputData.readByte()& (0xff)));
										//System.out.println(distances.get(AVANT)+" "+distances.get(DROITE)+" "+distances.get(GAUCHE)+" ");
										action = strategie();
										//System.out.println("scanner "+action);
										outputData.writeInt(action);
										outputData.flush();
										break;
				default: 				distances.put(AVANT,(int)(data& (0xff)) );
										break;	
			}
		}while(action != FIN);
		System.out.println("artographie fini !");
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
	
	public static void demiTour(){
		switch(directionCourante){
        	case HAUT : directionCourante=BAS;
            break;
            
        	case BAS : directionCourante=HAUT;
            break;
            
        	case DROITE : directionCourante=GAUCHE;
            break;
            
        	case GAUCHE : directionCourante=DROITE;
            break;
		}
	}
    
	private static void ajouterMurs(int direction, int distance){
		int nbMur = distance/40;
		//System.out.println("AjouterMurs " + distance + " /direction " + direction);
		switch(direction){
    	case HAUT: if(positionCourante.x-nbMur<0)
    			nbMur = positionCourante.x;
    		break;
    	case BAS: if(positionCourante.x+nbMur+1>ARENE_HEIGHT)
    			nbMur = ARENE_HEIGHT-positionCourante.x-1;
    		break;
    	case GAUCHE: if(positionCourante.y-nbMur<0)
    			nbMur = positionCourante.y;
    		break;
    	case DROITE: if(positionCourante.y+nbMur+1>ARENE_WIDTH)
    		 	nbMur = ARENE_WIDTH-positionCourante.y-1;
    		break;
		}
		int i=0;
		Case caseCourante = carte.getCase(positionCourante);

		while(nbMur>i && i<4){
			carte.addNoMurs(caseCourante.getPosition(), direction);
			fen.jTable1.setValueAt(caseCourante, caseCourante.getX(), caseCourante.getY());
			caseCourante = carte.avancer(caseCourante, direction);
			i++;
		}

		if(nbMur>4){
			carte.addNoMurs(caseCourante.getPosition(), direction);
		}
		else{
			carte.addMur(caseCourante.getPosition(), direction);
		}
		fen.jTable1.setValueAt(caseCourante, caseCourante.getX(), caseCourante.getY());
			
	}
	
    /**
     * Methode qui ajoute des murs ou noMurs selon la distance mesure.
     * Attention si on sors de l'arene sinon EXCEPTION !!!
     * @param distance
     */
    private static void ajouterMursVue(){
		ajouterMurs(directionCourante, distances.get(AVANT));
		ajouterMurs(tourner(GAUCHE), distances.get(GAUCHE)+15);
		ajouterMurs(tourner(DROITE), distances.get(DROITE)+15);
    }
    
    public static int redressement (int distance, int cote){
    	switch(cote){
    	case DROITE :
    		if(distance%40<10){
    			return REDRESSER_DROITE;
    		}else if(distance%40>20){
    			return REDRESSER_GAUCHE;
    		}else {
    			return AVANT;
    		}
    	case GAUCHE :
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
    		if(distances.get(GAUCHE)/40<position && distances.get(GAUCHE)<255){
    			return redressement(distances.get(GAUCHE),GAUCHE);
			}else if(distances.get(DROITE)/40<longeur-position && distances.get(DROITE)<255){
				return redressement(distances.get(DROITE),DROITE);
			}
    	}else{
    		if(distances.get(DROITE)/40<position && distances.get(DROITE)<255){
    			return redressement(distances.get(GAUCHE),DROITE);
			}else if(distances.get(GAUCHE)/40<longeur-position && distances.get(GAUCHE)<255){
				return redressement(distances.get(GAUCHE),GAUCHE);
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
			return(calculerRedressement(positionCourante.getX(), true, ARENE_HEIGHT));

		case GAUCHE :
			return(calculerRedressement(positionCourante.getX(), false, ARENE_HEIGHT));
			
		default :
	    	return AVANT;
		}
    }
    
    public static int distanceMax(){
    	switch (directionCourante) {
		case HAUT :
			return (int) positionCourante.getX();
			
		case BAS :
			return ARENE_HEIGHT-(int)positionCourante.getX()-1;
			
		case GAUCHE :
			return (int) positionCourante.getY();
			
		case DROITE :
			return ARENE_WIDTH-(int)positionCourante.getY()-1;

		default:
			return -1;
		}
    }
    

    public static int strategie(){
    	System.out.println("position courante : " + positionCourante.x + ", " + positionCourante.y);

		System.out.println("niveau " + niveau);
    	ajouterMursVue();
    	//System.out.println("strategie "+positionCourante.getX()+" "+positionCourante.getY());
    	int action = AVANT;
    	Point coin;
    	switch (directionCourante) {
			case HAUT:
				System.out.println("haut");
				coin = new Point((int)positionCourante.getX()-niveau, (int)positionCourante.getY()+niveau);
				if(coin.equals(depart3) && niveau<2)
					action=GAUCHE;
				else if(coin.y == depart3.y && niveau == 2){
					System.out.println("FINFINFIN");
					action=FIN;
				}
			break;
			case BAS :
				System.out.println("bas");
				coin = new Point((int)positionCourante.getX()+niveau, (int)positionCourante.getY()-niveau);
				if(coin.equals(depart2) && niveau<2){
					action=GAUCHE;
				}else if(coin.y == depart2.y && niveau == 2){
					System.out.println("FINFINFIN");
					action=FIN;
				}
			break;
			case GAUCHE :
				coin = new Point(positionCourante.x-niveau, positionCourante.y-niveau-1);
				System.out.println("gauche"+coin.getX()+" "+coin.getY()+" d1.x"+depart1.getX()+" d1.y"+depart1.getY());
				if(depart1.equals(coin) && niveau<2){
					System.out.println("coin");
					action=GAUCHE;
					niveau++;
				}
				else if(coin.y == depart1.y && niveau == 2){
					System.out.println("FINFINFIN");
					action=FIN;
				}
			break;
			case DROITE :
				coin = new Point((int)positionCourante.getX()+niveau, (int)positionCourante.getY()+niveau);
				System.out.println("droite"+coin.getX()+" "+coin.getY());
				if(coin.equals(depart4) && niveau<2)
					action=GAUCHE;
				else if(coin.y == depart4.y && niveau == 2){
						System.out.println("FINFINFIN");
						action=FIN;
				}
				System.out.println("action avant contourner" + action);
			break;
		}
    	if(action != FIN){
    		/* contourner les murs */   	
        	if(contourner){
        		action= contournerMur();
				System.out.println("action après contourner" + action);
        		//System.out.println(action);
        		//return action;
        	}else{
        		//on commence � contourner
        		if(distances.get(AVANT)<35 && distances.get(AVANT)/40 < distanceMax()){
        			contourner = true;
        			System.out.println("etape 0"+contourner);
        			caseContournerArrivee = carte.avancer(carte.getCase(positionCourante), directionCourante);
        			caseContournerArrivee.setDirection(directionCourante);
        			if(distances.get(GAUCHE) > 40){
        				System.out.println("tourner gauche");
        				action = GAUCHE;
        			}
        			else{
        				action = ARRIERE;
        			}
            		System.out.println(action);
            		//return action;
        		}else if(action == AVANT){
        			//action=calculerRedressementOriente();
        		}
        	}
    	}
    	
    	switch(action){
    	case DROITE: 
    	case GAUCHE: directionCourante = tourner(action);
    		break;
    	case ARRIERE: demiTour();
    		break;
    	}
    	avancer();
    	
    	/* fin de strategie */
		System.out.println("action de retour " + action);
    	return action;
    }
    
    public static void avancer(){  
    	//System.out.println("avancer");
        switch(directionCourante){
            case HAUT : positionCourante.x--;
                break;
            case BAS : positionCourante.x++;
                break;
            case DROITE : positionCourante.y++;
                break;
            case GAUCHE : positionCourante.y--;
                break;
            default: ;
        }
    }
    
    public static int contournerMur(){
    	int action = AVANT;
    	
    	//Teste si fin de contournement
		if(positionCourante.equals(caseContournerArrivee.getPosition())){
			System.out.println("fini");
    		contourner=false;
			if(distances.get(GAUCHE) < 40){
				action = ARRIERE;
        		contourner=true;
			}
			else if(tourner(GAUCHE) != caseContournerArrivee.getDirection()){
    			action = ARRIERE;
    		}
    		else{
    			action = GAUCHE;
    		}
    	}
		else{
			if(distances.get(DROITE) > 40){
				action = DROITE;
			}
			else if(distances.get(AVANT) > 40){
				action = AVANT;
			}
			else if(distances.get(GAUCHE) > 40){
				action = GAUCHE;
			}
			else{
				action =ARRIERE;
			}    	
		}
		action = finContourner(action);
    	return action;
    }
    
    private static int finContourner(int old_action){
    	int action = old_action;
    	
    	switch (directionCourante) {
		case HAUT :
			if(positionCourante.x == niveau){
				if(distances.get(DROITE) > 40){
					action = DROITE;
				}
				else if(distances.get(GAUCHE) < 40){
					caseContournerArrivee = carte.avancer(carte.getCase(positionCourante), tourner(GAUCHE));
					caseContournerArrivee.setDirection(tourner(GAUCHE));
					contourner = true;
					action = ARRIERE;
				}
				else if(positionCourante.equals(caseContournerArrivee.getPosition())){
					action = ARRIERE;
					contourner = false;
				}
				else{
					action = GAUCHE;
					contourner = false;
				}
			}
			break;
		case BAS : 
			System.out.println(caseContournerArrivee);
			if(positionCourante.x == ARENE_HEIGHT-1-niveau){
				if(distances.get(DROITE) > 40){
					action = DROITE;
				}
				else if(distances.get(GAUCHE) < 40){
					caseContournerArrivee = carte.avancer(carte.getCase(positionCourante), tourner(GAUCHE));
					caseContournerArrivee.setDirection(tourner(GAUCHE));
					contourner = true;
					action = ARRIERE;
				}
				else if(positionCourante.equals(caseContournerArrivee.getPosition())){
					action = ARRIERE;
					contourner = false;
				}
				else{
					action = GAUCHE;
					contourner = false;
				}
			}
			break;
		case DROITE : 
			if(positionCourante.y == ARENE_WIDTH-1-niveau){
				if(distances.get(DROITE) > 40){
					action = DROITE;
				}
				else if(distances.get(GAUCHE) < 40){
					caseContournerArrivee = carte.avancer(carte.getCase(positionCourante), tourner(GAUCHE));
					caseContournerArrivee.setDirection(tourner(GAUCHE));
					contourner = true;
					action = ARRIERE;
				}
				else if(positionCourante.equals(caseContournerArrivee.getPosition())){
					action = ARRIERE;
					contourner = false;
				}
				else{
					action = GAUCHE;
					contourner = false;	    					
				}
			}
			break;
		case GAUCHE : 
			if(positionCourante.y == niveau){
				if(distances.get(DROITE) > 40){
					action = DROITE;
				}
				else if(distances.get(GAUCHE) < 40){
					caseContournerArrivee = carte.avancer(carte.getCase(positionCourante), tourner(GAUCHE));
					caseContournerArrivee.setDirection(tourner(GAUCHE));
					contourner = true;
					action = ARRIERE;
				}
				else if(positionCourante.equals(caseContournerArrivee.getPosition())){
					action = ARRIERE;
					contourner = false;
				}
				else{
					action = GAUCHE;
					contourner = false;		    					
				}
			}
    	}
    	return action;
	}
    
	public static void main(String[] args) throws IOException, InterruptedException {
		carte = new Terrain();
		fen = new Fenetre(carte);
    	fen.setTitle("Exploration");
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
			ArrayList<Case> solution = algo.algorithme(0, 0);
			
			for(Case caseTmp : solution){
				outputData.write(caseTmp.getPoids());
				outputData.flush();
				outputData.write(caseTmp.getDirection());
				outputData.flush();
			}
			
			//attente du d�part
			sc.nextLine();
			
			outputData.write(FIN);
			outputData.flush();
			
		}else{
			System.out.println("non connecte");
		}
	}
}

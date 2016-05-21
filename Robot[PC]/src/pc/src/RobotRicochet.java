package pc.src;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import lejos.nxt.Sound;
import lejos.pc.comm.NXTConnector;
import static pc.src.Constantes.*;

public class RobotRicochet {

    private static Terrain carte;
	private static Fenetre fen;
    private static int niveau = 0;
    private static boolean contourner = false;
    private static boolean presDuMur = false;
    private static int etapeContournement = 0;
    private static Case caseContourner = null;
    private static Point positionCourante = depart1;
    private static int directionCourante = BAS;
    private static HashMap<Integer, Integer> distances = new HashMap<>(3);
    
	private static NXTConnector nxtConnect;
	private static DataOutputStream outputData;
	private static DataInputStream inputData;
	private static boolean connecte = false;
	
	public static void cartographier() throws IOException{
		
        int action=0;
        caseContourner = carte.getCase(positionCourante);

		do{
			byte data=inputData.readByte();
			//System.out.println((int)(data& (0xff)));
			switch(data){
				case DISTANCE_GAUCHE :	distances.put(GAUCHE,(int)(inputData.readByte()& (0xff)));
										break;
				case DISTANCE_DROITE :	distances.put(DROITE,(int)(inputData.readByte()& (0xff)));
										//System.out.println("scanner");
										action = strategie();
										System.out.println("scanner "+action);
										outputData.writeInt(action);
										outputData.flush();
										break;
				default: 				distances.put(AVANT,(int)(data& (0xff)) );
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

    	case DROITE: if(positionCourante.y-nbMur>ARENE_WIDTH)
    		 	nbMur = ARENE_WIDTH-positionCourante.y-1;
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
		ajouterMurs(directionCourante, distances.get(AVANT));
		ajouterMurs(tourner(GAUCHE), distances.get(GAUCHE)+20);
		ajouterMurs(tourner(DROITE), distances.get(DROITE)+20);
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
    

    public static int strategie(){
    	ajouterMursVue();
    	System.out.println("strategie");
    	int action = AVANT;
    	/* contourner les murs */
    	if(contourner){
    		action= contournerMur();
    		System.out.println(action);
    		return action;
    	}else{
    		if(distances.get(AVANT)<90){
    			action= contournerMur();
        		System.out.println(action);
        		return action;
    		}else{
    			action=calculerRedressementOriente();
    		}
    	}
    	/* cases inaccessibles */
    	
    	/* aller chercher les dernieres cases */
    	
    	/*switch(action){
    	case AVANT: avancer();
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
    	}*/
    	
    	/* fin de strategie */
    	return action;
    }
    
    public static void avancer(){        
        switch(directionCourante){
            case HAUT : positionCourante.x-=1;
                break;
            case BAS : positionCourante.x+=1;
                break;
            case DROITE : positionCourante.y+=1;
                break;
            case GAUCHE : positionCourante.y-=1;
                break;
            default: ;
        }
    }
    
    public static int contournerMur(){
    	int action = AVANT;
    	etapeContournement++;
    	System.out.println("contourner");
    	//Debut du contournement d'obstacle
    	if(!contourner){
        	etapeContournement = 0;
			contourner = true;
	    	caseContourner = carte.getCase(positionCourante);
	    	System.out.println("etape 0"+contourner);
	    	if(distances.get(AVANT)<40){
	    		presDuMur=true;
	    	}else{
	    		presDuMur=false;
	    	}
			if(distances.get(GAUCHE) > 40){
				System.out.println("tourner");
				return GAUCHE;
			}else{
				
			}
    	}else{
    		switch(etapeContournement){
    		case 0:if(distances.get(GAUCHE) > 40){
				action = GAUCHE;
			}else{
				
			}
    			break;
    			
    		case 1 : if(distances.get(DROITE) > 40){
					action = DROITE;
					System.out.println("etape 1");
				}
	    		else{
	    			
	    		}
    			break;
    		case 2 : if(distances.get(AVANT) > 40){
    			System.out.println("etape 2");
				
			}
    		else{
    			action = GAUCHE;
    		}
    			break;
    		case 3: if(presDuMur){
    			if(distances.get(DROITE) > 40){
    				action = DROITE;
    				System.out.println("etape 3");
    			}
        		else{
        			System.out.println("etape 32");
        		}
    		}
    		else{
    			if(distances.get(AVANT) > 40){
    				System.out.println("etape 33");
    				action = AVANT;
    				presDuMur = true;
    				etapeContournement--;
    			}
        		else{
        			System.out.println("etape 34");
        		}
    		}
    		break;
    			
    		case 4:
    			if(distances.get(GAUCHE) > 40){
				action = GAUCHE;
				contourner=false;
				System.out.println("etape 4");
			}
    		else{
    			
    		}
    			
    			break;
			default:
				break;
    		}
    	}
    	
    	switch (directionCourante) {
		case HAUT :
			break;
		case BAS :
			break;
		case DROITE :
			break;

		case GAUCHE :
			break;
			
		default :
		}
    	
    	return action;
    	
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

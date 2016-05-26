package src;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

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
    private static int LEVEL_MAX = ARENE_HEIGHT/2+ARENE_HEIGHT%2;
    private static HashMap<Integer, Integer> distances = new HashMap<>(3);
    private static int tourneEnRond = 0;
    public static ArrayList<Case> solution;
    public static Point objectif;
    
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
		System.out.println("cartographie fini !");
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
    	boolean tournerCoin = false;
    	//System.out.println("strategie "+positionCourante.getX()+" "+positionCourante.getY());
    	int action = AVANT;
    	Point coin;
    	switch (directionCourante) {
			case HAUT:
				System.out.println("haut");
				if(contourner){
					if(positionCourante.y == niveau ||
							(positionCourante.x == niveau && positionCourante.y<caseContournerArrivee.getY())){
						action = finContourner();
					}
					else
						action = contourner();
					
					if(action == DROITE){
						tourneEnRond++;
					}
					else{
						tourneEnRond = 0;
					}
					tournerCoin = true;
				}
				else{
					coin = new Point((int)positionCourante.getX()-niveau, (int)positionCourante.getY()+niveau);
					if(coin.equals(depart3) && niveau<3){
						tournerCoin = true;
						if(distances.get(GAUCHE)>40)
							action=GAUCHE;
						else{
							action=ARRIERE;
			    			caseContournerArrivee = carte.avancer(carte.getCase(positionCourante), tourner(GAUCHE));
			    			caseContournerArrivee.setDirection(tourner(GAUCHE));
							contourner=true;
						}
	    			}else if(coin.y == depart3.y && niveau == 3){
						System.out.println("FINFINFIN");
						action=FIN;
					}
				}				
			break;
			case BAS :
				System.out.println("bas");
				if(contourner){
					if(positionCourante.y == ARENE_WIDTH-1-niveau ||
							(positionCourante.x == ARENE_HEIGHT-1-niveau && positionCourante.y>caseContournerArrivee.getY())){
						System.out.println("YES");
						action = finContourner();
					}
					else
						action = contourner();
					tournerCoin = true;
					if(action == DROITE){
						tourneEnRond++;
					}
					else{
						tourneEnRond = 0;
					}
				}
				else{
					coin = new Point((int)positionCourante.getX()+niveau, (int)positionCourante.getY()-niveau);
					if(coin.equals(depart2) && niveau<3){
						tournerCoin = true;
						if(distances.get(GAUCHE)>40)
							action=GAUCHE;
						else{
							action=ARRIERE;
			    			caseContournerArrivee = carte.avancer(carte.getCase(positionCourante), tourner(GAUCHE));
			    			caseContournerArrivee.setDirection(tourner(GAUCHE));
							contourner=true;
						}
					}else if(coin.y == depart2.y && niveau == 3){
						System.out.println("FINFINFIN");
						action=FIN;
					}
				}
			break;
			case GAUCHE :
				if(contourner){
					if(positionCourante.x == ARENE_HEIGHT-niveau-1 ||
							(positionCourante.y == niveau && positionCourante.x>caseContournerArrivee.getX())){
						System.out.println("GAUCHE : finContourner fin de ligne");
						action = finContourner();
					}//passage au niveau superieur
					else if(positionCourante.y == niveau+1 && caseContournerArrivee.getDirection() == GAUCHE){
						System.out.println("GAUCHE : finContourner retour normal");
						action = finContourner();
						niveau++;
					}
					else{
						action = contourner();
						System.out.println("GAUCHE : contourner");
					}
					if(action == DROITE){
						tourneEnRond++;
					}
					else{
						tourneEnRond = 0;
					}
					tournerCoin = true;
				}
				else{
					coin = new Point(positionCourante.x-niveau, positionCourante.y-niveau-1);
					System.out.println("gauche"+coin.getX()+" "+coin.getY()+" d1.x"+depart1.getX()+" d1.y"+depart1.getY());
					if(depart1.equals(coin) && niveau<3){
						tournerCoin = true;
						System.out.println("coin");
						if(distances.get(GAUCHE)>40)
							action=GAUCHE;
						else{
							action=ARRIERE;
			    			caseContournerArrivee = carte.avancer(carte.getCase(positionCourante), tourner(GAUCHE));
			    			caseContournerArrivee.setDirection(tourner(GAUCHE));
							contourner=true;
						}
						niveau++;
					}
					else if(coin.y == depart1.y && niveau == 3){
						System.out.println("FINFINFIN");
						action=FIN;
					}
				}
			break;
			case DROITE :
				if(contourner){
					if(positionCourante.x == niveau ||
							(positionCourante.y == ARENE_WIDTH-1-niveau && positionCourante.x<caseContournerArrivee.getX())){
						action = finContourner();
					}
					else
						action = contourner();
					
					if(action == DROITE){
						tourneEnRond++;
					}
					else{
						tourneEnRond = 0;
					}
					tournerCoin = true;
				}
				else{
					coin = new Point((int)positionCourante.getX()+niveau, (int)positionCourante.getY()+niveau);
					System.out.println("droite"+coin.getX()+" "+coin.getY());
					if(coin.equals(depart4) && niveau<3){
						if(distances.get(GAUCHE)>40)
							action=GAUCHE;
						else{
							action=ARRIERE;
			    			caseContournerArrivee = carte.avancer(carte.getCase(positionCourante), tourner(GAUCHE));
			    			caseContournerArrivee.setDirection(tourner(GAUCHE));
							contourner=true;
						}
						tournerCoin = true;
					}
					else if(coin.y == depart4.y && niveau == 3){
							System.out.println("FINFINFIN");
							action=FIN;
					}
					System.out.println("action avant contourner" + action);
				}
			break;
		}
				
    	if(action != FIN && !contourner && !tournerCoin){
    		//on commence a contourner un mur
    		if(distances.get(AVANT)<40 && distances.get(AVANT)/40 <= distanceMax()){
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
    		}
    	}else if(contourner && tourneEnRond == 4){
    		action = ARRIERE;
    		tourneEnRond = 0;
    	}
    	
    	directionCourante = tourner(action);
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
    
    public static int contourner(){
    	int action = AVANT;
    	if(positionCourante.equals(caseContournerArrivee.getPosition())){
			System.out.println("fini");
    		contourner=false;
			if(tourner(GAUCHE) != caseContournerArrivee.getDirection()){
    			action = ARRIERE;
    			System.out.println("arriere3");
    		}
			else if(distances.get(GAUCHE) < 40){
				action = ARRIERE;
				caseContournerArrivee = carte.avancer(carte.getCase(positionCourante), tourner(GAUCHE));
				caseContournerArrivee.setDirection(tourner(GAUCHE));
				System.out.println("arriere2");
        		contourner=true;
			}
    		else{
    			action = GAUCHE;
    		}
    	}
    	//Teste si fin de contournement
    	else if(distances.get(DROITE) > 40){
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
			System.out.println("arriere4");
		}    	
    	return action;
    }
    
    private static int finContourner(){
    	int action = AVANT;
    	
    	if(positionCourante.equals(caseContournerArrivee.getPosition())){
			System.out.println("fini");
    		contourner=false;
    		action = ARRIERE;
    	}
    	else if(distances.get(DROITE) > 40){
			action = DROITE;
		}
		else if(distances.get(GAUCHE) < 40){
			action = ARRIERE;
			caseContournerArrivee = carte.avancer(carte.getCase(positionCourante), tourner(GAUCHE));
			caseContournerArrivee.setDirection(tourner(GAUCHE));
		}else{
			action = GAUCHE;
			contourner = false;
		}
    	return action;
	}
    
	public static void main(String[] args) throws IOException, InterruptedException {
		carte = new Terrain();
		solution = new ArrayList<Case>();
		fen = new Fenetre(carte, new JFrame(), false);
    	fen.setVisible(true);
    	fen.dispose();
		fen = new Fenetre(carte, new JFrame(), true);
    	fen.setVisible(true);
    	
        AStar algo = new AStar(carte, objectif);
        solution = algo.getSolution();
		// TODO Auto-generated method stub
		nxtConnect = new NXTConnector();
		connecte = nxtConnect.connectTo("btspp://001653162E5B");
		outputData = new DataOutputStream(nxtConnect.getOutputStream());
		inputData = new DataInputStream(nxtConnect.getInputStream());
		if(connecte){
			System.out.println("connecte");
			cartographier();

	    	fen.dispose();
			fen = new Fenetre(carte, new JFrame(), true);
	    	fen.setVisible(true);
	    	
	        algo = new AStar(carte, objectif);
	        solution = algo.getSolution();
			
			for(Case caseTmp : solution){
				outputData.writeInt(caseTmp.getPoids());
				outputData.flush();
				outputData.writeInt(caseTmp.getDirection());
				outputData.flush();
			}
			
			//attente du depart
			Scanner sc = new Scanner(System.in);
			sc.nextLine();
			
			System.out.println("Lancer robot");
			outputData.writeInt(FIN);
			outputData.flush();
			//Quitter le programme
			sc.nextLine();
			sc.close();
		}else{
			System.out.println("non connecte");
	    	fen.dispose();
		}
		System.exit(0);
	}
}

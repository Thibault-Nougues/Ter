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
    
	private static NXTConnector nxtConnect;
	private static DataOutputStream outputData;
	private static DataInputStream inputData;
	private static boolean connecte = false;
	
	public static void cartographier() throws IOException{
		
        int action=0;

		do{
			byte data=inputData.readByte();
			switch(data){
				case DISTANCE_GAUCHE :	action = scan((int)inputData.readByte()& (0xff), GAUCHE);
										break;
				case DISTANCE_DROITE :	action = scan((int)inputData.readByte()& (0xff), DROITE);
										break;
				default: 				action = scan((int)inputData.readByte()& (0xff), AVANT);
										break;	
			}
			outputData.write(action);
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
    		direction*=2;
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
    
    public static int scan(int distance, int direction){
    	int action = AVANT;
    	// récupération de la direction de la tête qui à pris la mesure
    	if(direction == AVANT){
    		direction = directionCourante;
    	}
    	else{
    		direction = tourner(direction);
    	}
    	
    	// regarder si direction sors du terrain
    	
        switch(direction){
    	case HAUT: if(positionCourante.x*40-distance>0){
	    	//intérieur
    		ajouterMursVue(distance, direction);
	    	}
    		break;
    	case BAS: positionCourante.x += 1;
    		break;
    	case GAUCHE: positionCourante.y -= 1;
    		break;
    	case DROITE: if((ARENE_WIDTH-positionCourante.y)*40-distance>0){
	    		ajouterMursVue(distance, direction);
	    	}
    		break;
		default:
			break;
    	}
    	
        //Sinon placer les murs
        
        
    	return action;
    }
    
    
    /**
     * Méthode qui ajoute des murs ou noMurs selon la distance mesuré.
     * Attention si on sors de l'arène sinon EXCEPTION !!!
     * @param distance
     */
    private static void ajouterMursVue(int distance, int direction){
    	Case caseCourante = carte.getCase(positionCourante);
    	if(distance>200){
    		for(int i=0 ; i<5 ; i++){
    			caseCourante.addNoMurs(direction);
    			caseCourante = carte.avancer(caseCourante, direction);
    		}
    	}
    	else{
    		
    	}
    	
    }
    

    public void stratégie(){
    	/* cas des faux murs */
    	
    	/* contourner les murs */
    	
    	/* cases inaccessibles */
    	
    	/* aller chercher les dernières cases */
    	
    	/* fin de stratégie */
    }
    
	public static void main(String[] args) throws IOException, InterruptedException {
		carte = new Terrain();
		//carte.addNoMurs(depart1, DROITE);
		carte.addNoMurs(depart1, BAS);
		fen = new Fenetre(carte);
    	fen.jTable1.setDefaultRenderer(Object.class, new TableRendererCarto(carte));
    	fen.setVisible(true);
		
		// TODO Auto-generated method stub
		nxtConnect = new NXTConnector();
		connecte = nxtConnect.connectTo("btspp://001653162E5B");
		outputData = new DataOutputStream(nxtConnect.getOutputStream());
		inputData = new DataInputStream(nxtConnect.getInputStream());
		if(connecte){
			System.out.println("connectï¿½");
			cartographier();
			
			Scanner sc = new Scanner(System.in);
			String arriveeX = sc.nextLine();
			String arriveeY = sc.nextLine();
			
			AStar algo = new AStar(carte, new Point(Integer.parseInt(arriveeX), Integer.parseInt(arriveeY)));
			ArrayList<Case> solution = algo.getSolution();
			/* envoyer la solution au robot */
			
		}else{
			System.out.println("non connectï¿½");
		}
		
			
	}

}

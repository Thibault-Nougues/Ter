package pc.src;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import lejos.pc.comm.NXTConnector;
import static pc.src.Constantes.*;

public class RobotRicochet {

    private Point positionCourante = depart1;
    private static int directionCourante = BAS;
    private static Terrain carte;
    
	private static NXTConnector nxtConnect;
	private static DataOutputStream outputData;
	private static DataInputStream inputData;
	private static boolean connecte = false;
	private static Fenetre fen;
	public static void cartographier() throws IOException, InterruptedException{
		/*
        int action=0;

		while(true){
			byte data=inputData.readByte();
			switch(data){
				case DISTANCE_GAUCHE :	// a remplacer par l'ajout des valeurs dans ta partie
										System.out.println("gauche");
										System.out.println((int)inputData.readByte()& (0xff));
										break;
				case DISTANCE_DROITE :	// a remplacer par l'ajout des valeurs dans ta partie
										System.out.println("droite");
										System.out.println((int)inputData.readByte()& (0xff));
										break;
										
				default: action = fen.getCarte().scan((int)inputData.readByte()& (0xff));
					break;	
			}
			fen.maj();
			outputData.write(action);
			
		}*/
		outputData.write(FIN);
		//fen.executeAStar(directionCourante);
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		carte = new Terrain();
		fen = new Fenetre(carte);
        fen.setVisible(true);
        AStar algo = new AStar(carte, new Point(9, 22));
		// TODO Auto-generated method stub
		nxtConnect = new NXTConnector();
		connecte = nxtConnect.connectTo("btspp://001653162E5B");
		outputData = new DataOutputStream(nxtConnect.getOutputStream());
		inputData = new DataInputStream(nxtConnect.getInputStream());
		if(connecte){
			System.out.println("connect�");
			cartographier();
		}else{
			System.out.println("non connect�");
		}
		
			
	}

}

package pc.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import lejos.pc.comm.NXTConnector;
import static pc.src.Constantes.*;

public class RobotRicochet {
	
	private static NXTConnector nxtConnect;
	private static DataOutputStream outputData;
	private static DataInputStream inputData;
	private static boolean connecte = false;
	
	public static void cartographier() throws IOException{
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
			}
		}
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		nxtConnect = new NXTConnector();
		connecte = nxtConnect.connectTo("btspp://001653162E5B");
		outputData = new DataOutputStream(nxtConnect.getOutputStream());
		inputData = new DataInputStream(nxtConnect.getInputStream());
		if(connecte){
			System.out.println("connecté");
			cartographier();
		}else{
			System.out.println("non connecté");
		}
		
			
	}

}

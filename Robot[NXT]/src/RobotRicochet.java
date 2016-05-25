package src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import lejos.nxt.Battery;
import lejos.nxt.Button;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import static src.Constantes.*;

public class RobotRicochet {
	
	private static BTConnection pcConnect;
	private static DataInputStream inputData;
	private static DataOutputStream output;
	
	public static void connexion(){
		pcConnect = Bluetooth.waitForConnection();
		inputData = pcConnect.openDataInputStream();
		output = pcConnect.openDataOutputStream();
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
		connexion();
		new Cartographier(output);
		do{
			ACTION=inputData.readInt();
			//System.out.println(ACTION);
		}while(ACTION!=FIN && !Button.ESCAPE.isDown());
		CHEMIN = new ArrayList<Integer>();
		/*do{
			ACTION=inputData.readInt();
			System.out.println("lecture : " + ACTION);
			if(ACTION!=FIN)
				CHEMIN.add(ACTION);
		}while(ACTION!=FIN && !Button.ESCAPE.isDown());
		System.out.println("AVANT exploitation");
		new Exploitation();*/
	}

}

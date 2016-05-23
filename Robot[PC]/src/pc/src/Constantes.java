package pc.src;

import java.awt.Point;

public class Constantes {

	public static final byte DISTANCE_GAUCHE = 0b01111111;
	public static final byte DISTANCE_DROITE = 0b00000000;

    public static final int ARENE_HEIGHT = 7;
    public static final int ARENE_WIDTH = 19;
    
    public static final int HAUT = 1;
    public static final int GAUCHE = 2;
    public static final int BAS = 4;
    public static final int DROITE = 8;
    
	public static final int AVANT = 0;
	public static final int ARRIERE = 1;
	
	public static final int REDRESSER_DROITE = 5;
	public static final int REDRESSER_GAUCHE = 6;
	
	public static final int FIN = 3;

    public static final Point depart1 = new Point(0, 0);
    public static final Point depart2 = new Point(ARENE_HEIGHT-1, 0);
    public static final Point depart3 = new Point(0, ARENE_WIDTH-1);
    public static final Point depart4 = new Point(ARENE_HEIGHT-1, ARENE_WIDTH-1);    
}

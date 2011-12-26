package simulator;

import tools.ATPLogger;

/**
 * 
 * Definitions class
 *
 */
public class Defs {
	public static final double TSWITCH = 0;
	public static final double NESTING_LEVEL = 20;
	public static final double F1_LEVEL = 1;
	public static final double F100_LEVEL = 100;
	public static final double F10000_LEVEL = 10000;
	public static final int HUMAN = 1;
	public static final int GTS = 2;
	public static final int HORIZON = 100000;
	public static final int CUTOFF = 100000;
	public static final int F_UNITS = 100;
	public static final int MAX_ROOT_ID = 7;
	public static int GDN_ID = 100;
	public static final int COMP = 0;
	public static final boolean DEBUG = false;
	

	public static void print(String str){
		if(DEBUG) ATPLogger.log(str);
	}
	
}

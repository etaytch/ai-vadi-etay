package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import simulator.Simulator;

import fileParsing.EnvLineAnalyzer;
import fileParsing.FileParser;
import fileParsing.LineAnalyzerInterface;


//TODO
//PART II
//1. Calc equation !    Agent Score = f*S+T where T-Number of expansiones (nesting level), S-agents score, and f= 1 / 10 / 10000 (already in Defs.java)
//2. Bonus (SpeedNut prediction)

public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		

		System.out.println("\n	*********************************");
		System.out.println("	******** Welcome To The *********");
		System.out.println("	*** American Traveler Problem ***");
		System.out.println("	***** Simulation Environment ****");
		System.out.println("	*********************************\n\n\n");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = ""; 
		boolean finish = true;
		while (finish)
		{
			LineAnalyzerInterface la = new EnvLineAnalyzer();
			FileParser.parseEnv("env_input.txt", la);
			Simulator sim = (Simulator)la.getParsedObject();
			System.out.println(sim);
			sim.startSimulation();
			
			System.out.println("to run another Simulation press enter for exit press Q ");		
			str  = br.readLine();
			if (str.equals("Q"))
			{
				finish = false;
			}
		}

		System.out.println("\n\n	*********************************");
		System.out.println("	*********** Good Bye ! **********");
		System.out.println("	*********************************\n");		
		System.in.read();
	}	
	

}//class

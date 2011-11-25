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
		
		System.out.println();
		System.out.println("	*********************************");
		System.out.println("	******** Welcome To The *********");
		System.out.println("	*** American Traveler Problem ***");
		System.out.println("	***** Simulation Environment ****");
		System.out.println("	*********************************");
		System.out.println();
		System.out.println();
		System.out.println();
		
		
		LineAnalyzerInterface la = new EnvLineAnalyzer();
		FileParser.parseEnv("env_input.txt", la);
		Simulator sim = (Simulator)la.getParsedObject();
		System.out.println(sim);
		userInput(sim);
		sim.startSimulation();
		
		System.out.println();
		System.out.println();
		System.out.println("	*********************************");
		System.out.println("	*********** Good Bye ! **********");
		System.out.println("	*********************************");
		System.out.println();
		System.in.read();
	}

	/**
	 * this method is UI to add more user defined agents
	 * 
	 * @param sim
	 * @throws IOException
	 */
	private static void userInput(Simulator sim) throws IOException {
		System.out.println("How many more agents to add? (Enter - read from file");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String num="";		 
		num = br.readLine();
		if (num.equals(null) || num.equals("") || num.equals("\n")){
			return;
		}
	    String tStr="";	    	 
	    try{
	    	
		    if(Integer.parseInt(num)!=0){	    	
	    		for (int i=1;i<=Integer.parseInt(num);i++){
	    			int typeAsNum;
	    	    	
						System.out.println("Agent #"+i+":");
						System.out.println("-------------\n");
						System.out.println("Please enter Agent's type:");
						System.out.println("1) Human Agent");
						System.out.println("2) SpeedNutAutomation Agent");
						System.out.println("3) Simple Greedy Agent");
						System.out.println("4) Greedy Agent");
						System.out.println("5) A* Agent");
						System.out.println("6) RT A* Agent");
						tStr = br.readLine();
						typeAsNum = Integer.parseInt(tStr);
						while (typeAsNum<1 || typeAsNum>6){
							System.out.println("Please enter valid type number:");
							tStr = br.readLine();
							typeAsNum = Integer.parseInt(tStr);
						}
						System.out.println("Enter agent name:");
						String agentName = br.readLine();
											
						System.out.println("Enter source vertex number:");
						tStr = br.readLine();
						int fromVertex = Integer.parseInt(tStr);
						System.out.println("Enter target vertex number:");
						tStr = br.readLine();
						int toVertex = Integer.parseInt(tStr);				
						sim.get_env().addAgent(typeAsNum,agentName,fromVertex,toVertex);					
	    	    	
				} //for	    
		    }//if
	    }//try
	    catch (java.lang.NumberFormatException ex) {
			System.out.println("You antered illeagal input! reading from file... ");
		}
	}//method
}//class

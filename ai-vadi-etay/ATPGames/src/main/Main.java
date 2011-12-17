package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import simulator.Simulator;

import fileParsing.EnvLineAnalyzer;
import fileParsing.FileParser;
import fileParsing.LineAnalyzerInterface;

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
		System.out.println("	********* Assignment 2  *********");
		System.out.println("	*********************************\n\n\n");
		
		
		LineAnalyzerInterface la = new EnvLineAnalyzer();
		FileParser.parseEnv("env_input.txt", la);
		Simulator sim = (Simulator)la.getParsedObject();
		System.out.println(sim);
		userInput(sim);
		sim.startSimulation();
		

		System.out.println("\n\n	*********************************");
		System.out.println("	*********** Good Bye ! **********");
		System.out.println("	*********************************\n");		
		//System.in.read();
	}	
	
	/**
	 * this method is UI to add more user defined agents
	 * 
	 * @param sim
	 * @throws IOException
	 */
	private static void userInput(Simulator sim) throws IOException {
		System.out.println("Please provide agent's properties.\nIf you wish to read from file please press ENTER. Press any other key otherwise.");		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String num="";		 
		num = br.readLine();
		if (num.equals(null) || num.equals("") || num.equals("\n")){
			return;
		}
		num="2";
	    String tStr="";	
	    System.out.println("Please enter Game's type:");
	    System.out.println("(1) A Zero Sum Game");
	    System.out.println("(2) A Non Zero-Sum Game");
	    System.out.println("(3) A Fully Cooperative Game");
	    tStr = br.readLine();
	    int typeGameAsNum = Integer.parseInt(tStr);
	    try{	    	
		    if(Integer.parseInt(num)!=0){	    	
	    		for (int i=1;i<=Integer.parseInt(num);i++){
	    			int typeAgentAsNum;
	    	   	
						System.out.println("Agent #"+i+":");
						System.out.println("-------------\n");
						System.out.println("Please enter Agent's type:");
						System.out.println("(1) Human Agent");
						System.out.println("(2) Game Tree Search Agent");
						tStr = br.readLine();
						typeAgentAsNum = Integer.parseInt(tStr);
						while (typeAgentAsNum<1 || typeAgentAsNum>6){
							System.out.println("Please enter valid type number:");
							tStr = br.readLine();
							typeAgentAsNum = Integer.parseInt(tStr);
						}
						System.out.println("Enter agent name:");
						String agentName = br.readLine();
											
						System.out.println("Enter source vertex number:");
						tStr = br.readLine();
						int fromVertex = Integer.parseInt(tStr);
						System.out.println("Enter target vertex number:");
						tStr = br.readLine();
						int toVertex = Integer.parseInt(tStr);				
						sim.get_env().addAgent(typeGameAsNum,typeAgentAsNum,agentName,fromVertex,toVertex);					
	    	    	
				} //for	    
		    }//if
	    }//try
	    catch (java.lang.NumberFormatException ex) {
			System.out.println("You've entered an illegal input! Reading from file... ");
		}
	}//method
}//class

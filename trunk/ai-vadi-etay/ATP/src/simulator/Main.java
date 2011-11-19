package simulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import fileParsing.EnvLineAnalyzer;
import fileParsing.FileParser;
import fileParsing.LineAnalyzerInterface;


//TODO
//PART I
//1. add user interface for pre run agents configuration - and loading pre run agents configuration from file
//2.add human agent
//3.add speed nut agent
//4.add greedy agent

//PART II
//5.add greedy2 agent
//6.add A* agent
//7.add A*RT agent

public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
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
		//sim.setGreedyGraph(true);		
		System.out.println(sim);
		
		System.out.println("How many more agents to add? (0 - read from file");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String num="";		 
		num = br.readLine();	         
	    String tStr="";	    	    
	    if(Integer.parseInt(num)!=0){	    	
    		for (int i=1;i<=Integer.parseInt(num);i++){
    			int typeAsNum;
    	    	
					System.out.println("Agent #"+i+":");
					System.out.println("-------------\n");
					System.out.println("Please enter Agent's type:");
					System.out.println("1) Human Agent");
					System.out.println("2) SpeedNutAutomation Agent");
					System.out.println("3) Greedy Agent");
					tStr = br.readLine();
					typeAsNum = Integer.parseInt(tStr);
					while (typeAsNum<1 || typeAsNum>3){
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
														
					sim.addAgent(typeAsNum,agentName,fromVertex,toVertex);					
    	    	
			}	    
	    	
	    }
		sim.startSimulation();
		
	}

}

package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import Algorithms.Algorithms;
import DO.Node;
import base.BaseNet;
import base.BaseNetImpl;

import fileParsing.BaseLineAnalyzer;
import fileParsing.FileParser;
import fileParsing.LineAnalyzerInterface;

public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		System.out.println("\n	********************************");
		System.out.println("	********** Welcome To **********");
		System.out.println("	******** AI 2012 Course ********");		
		System.out.println("	********* Assignment 4 *********");
		System.out.println("	********************************\n\n\n");
		System.out.println("Loading Bayes network from file..\n");
		BaseNet bn = new BaseNetImpl();
		
		LineAnalyzerInterface la = new BaseLineAnalyzer(bn);
		FileParser.parseEnv("conf.txt", la);
		bn = (BaseNet)la.getParsedObject();

		Environment env = new Environment(bn);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input=".";
		int num;
		//!input.equals("") && !input.equals("\n")
		while (!input.equals(null) && !input.equals("7")){
			System.out.println("Please choose one of the following:");
			
			System.out.println("1 - Print out the network");
			System.out.println("2 - Add evidence");
			System.out.println("3 - Reset the evidence to null.");
			System.out.println("4 - Set a query node, upon which the program computes and prints out the posterior distribution of the query node given the current evidence.");
			System.out.println("5 - Run algorithm.");
			System.out.println("6 - Query All Diseases");			
			System.out.println("7 - Quit.");
				input = br.readLine();
				String inputArgs[] = input.split("\\s+"); 
				try{
					num = Integer.valueOf(inputArgs[0]);
					switch(num){
					case 1: env.getBn().print();
							break;
					case 2: env.addEvidence(inputArgs[1], inputArgs[2]);
							break;
					case 3: env.cleanEvidence();
							break;
					case 4: env.setQuery(inputArgs[1]);
							break;
					case 5: runAlgorithm(env);
							break;
					case 6: queryAllDiseases(env);
							break;
					case 7: {
						System.out.println("Bye Bye...");
						System.exit(0);
					}
					default: System.out.println("Illegal input.. Try againg"); 
					}
				}
				
				catch(NumberFormatException e){
					System.out.println("Illegal input.. Try againg");
				}
			}
		
	}

	private static void queryAllDiseases(Environment env) {
		Map<String,Node> nodes = env.getBn().getNodes();
				
		System.out.println("Runnig Queries for all Diseases:");
		System.out.println("Evidence: "+env.getEvidence());
		for(String node : nodes.keySet()){
			if(node.startsWith("D")){
				env.setQuery(node);
				Map<String,Double> ans = Algorithms.EnumerationAsk(env.getQuery(), env.getEvidence(), env.getBn());
				System.out.println("results for: "+node+" are: "+ans);
			}
			
		}
		
	}

	private static void runAlgorithm(Environment env) {
		Map<String,Double> ans = Algorithms.EnumerationAsk(env.getQuery(), env.getEvidence(), env.getBn());
		System.out.println(ans);
	}
}

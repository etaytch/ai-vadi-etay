package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import Algorithms.Algorithms;
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
		while (!input.equals(null) && !input.equals("5")){
			System.out.println("Please choose one of the following:");
			
			System.out.println("1 - Print out the network");
			System.out.println("2 - Add evidence");
			System.out.println("3 - Reset the evidence to null.");
			System.out.println("4 - Set a query node, upon which the program computes and prints out the posterior distribution of the query node given the current evidence.");
			System.out.println("5 - Run algorithm.");
			System.out.println("6 - Quit.");
			
			while(true){
				input = br.readLine();
				try{
					num = Integer.valueOf(input);
					switch(num){
					case 1: env.getBn().print();
							break;
					case 2: addEvidence();
							break;
					case 3: addEvidence();
							break;
					case 4: setQueryNode();
							break;
					case 5: runAlgorithm(env);
							break;
					case 6: {
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
	}

	private static void runAlgorithm(Environment env) {
		Map<String,Double> ans = Algorithms.EnumerationAsk(env.getQuery(), env.getEvidence(), env.getBn());
		System.out.println(ans);
	}

	private static void loadBayes() {
		// TODO Auto-generated method stub
		
	}

	private static void setQueryNode() {
		// TODO Auto-generated method stub
		
	}

	private static void addEvidence() {
		// TODO Auto-generated method stub
		
	}

	private static void printNetwork() {
		// TODO Auto-generated method stub
		
	}
}

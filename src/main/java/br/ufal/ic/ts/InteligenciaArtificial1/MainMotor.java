package br.ufal.ic.ts.InteligenciaArtificial1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class MainMotor {
	
	static final String filepath = "test.txt";
	static final String auxFilepath = "testAux.txt";
	static boolean concDisplay = false; 
	static List<Sentence> sentences = new ArrayList<>();
	static List<String> atoms = new ArrayList<>();
	static Set<String> conclusions = new HashSet<>();
	static Motor motor;
	
	public static void main(String[] args) {
		int answer = 1;
		do {
			try {
				motor = new Motor(args[0]);
				Scanner scan = new Scanner(System.in);
				System.out.println("---------------");
		        System.out.println("Menu:");
		        System.out.println("0 - Exit");
		        System.out.println("1 - List Rules");
		        System.out.println("2 - Add Rule");
		        System.out.println("3 - Remove Rule");
		        System.out.println("4 - Test Case");
		        System.out.println("5 - Toggle step-by-step conclusions");
		        System.out.print("Input: ");
		        answer = scan.nextInt();
		        scan.nextLine();
		        if(answer == 1)
		        	motor.listRules();
		        else if(answer == 2)
		        	motor.addRule(scan);
		        else if(answer == 3)
		        	motor.deleteRule(scan);
		        else if(answer == 4) {
		        	motor.readDatabase();
		        	motor.askQuestions(scan);
		        }
		        else if(answer == 5) {
		        	if(concDisplay) System.out.println("Step-by-step conclusions turned off");
		        	else System.out.println("Step-by-step conclusions turned on");
		        	concDisplay = !concDisplay;	
		        }
	        } catch (Exception e) {
	        	System.err.println("Error - Please confirm the data entered is valid and that the file \"test.txt\" exists");
			}
		}while(answer != 0);
	}

}

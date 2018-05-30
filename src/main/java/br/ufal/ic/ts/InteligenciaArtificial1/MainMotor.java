package br.ufal.ic.ts.InteligenciaArtificial1;

import java.io.IOException;
import java.util.Scanner;

public class MainMotor {
	
	static boolean concDisplay = false;
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
	        } catch(IOException e){
	        	System.err.println("Error - File "+ args[0] +" not found");
	        	System.exit(1);
	        }
			catch (Exception e) {
	        	System.err.println("Error - Please confirm the data entered is valid and that the file "+args[0]+" exists");
			}
		}while(answer != 0);
	}

}

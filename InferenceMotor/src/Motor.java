import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Motor {
	static final String filepath = "test.txt";
	static final String auxFilepath = "testAux.txt";
	static Scanner scan = new Scanner(System.in);
	static int answer;
	static List<Sentence> sentences = new ArrayList<>();
	static List<String> atoms = new ArrayList<>();
	static Set<String> conclusions = new HashSet<>();
	
	public static void main(String[] args) {
		do {
			try {
				sentences.clear();
				atoms.clear();
				conclusions.clear();
				System.out.println("---------------");
		        System.out.println("Menu:");
		        System.out.println("0 - Exit");
		        System.out.println("1 - List Rules");
		        System.out.println("2 - Add Rule");
		        System.out.println("3 - Remove Rule");
		        System.out.println("4 - Test Case");
		        System.out.print("Input: ");
		        answer = scan.nextInt();
		        scan.nextLine();
		        if(answer == 1)
		        	listRules();
		        else if(answer == 2)
		        	addRule();
		        else if(answer == 3)
		        	deleteRule();
		        else if(answer == 4) {
		        	readDatabase();
		        	askQuestions();
		        }
	        } catch (Exception e) {
	        	System.err.println("Error - Please confirm the data entered is valid and that the file \"test.txt\" exists");
			}
		}while(answer != 0);
	}
	public static void listRules() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filepath));
		String line = reader.readLine();
		int i = 1;
		while(line != null) {
			System.out.println(i+" - "+line);
			line = reader.readLine();
			i++;
		}
		reader.close();
	}
	public static void readDatabase() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filepath));
		String line = reader.readLine();
		Set<String> auxAtoms = new HashSet<>();
		while(line != null) {
			if(!line.isEmpty()) {
				Sentence sentence = new Sentence(line);
				sentences.add(sentence);
				auxAtoms.addAll(sentence.getConditions());
			}
			line = reader.readLine();
		}
		atoms.addAll(auxAtoms);
		reader.close();
	}
	
	public static void addRule() throws IOException {
		BufferedWriter output = new BufferedWriter(new FileWriter(filepath, true));
		
		System.out.print("New Rule: ");
		String line = scan.nextLine();
		while(!line.matches("SE .+ ENTAO .+")) {
			System.out.println("Unnacceptable Rule, try again!\nNew Rule:");
			line = scan.nextLine();
		}
		output.write(line+System.lineSeparator());
		output.close();
	}
	public static void deleteRule() throws IOException {
		listRules();
		System.out.print("Select rule to be removed: ");
		int lineToRemove = scan.nextInt();
		scan.nextLine();
		
		File input = new File(filepath);
		File tempFile = new File(auxFilepath);
		BufferedReader reader = new BufferedReader(new FileReader(input));
		PrintWriter writer = new PrintWriter(new FileWriter(tempFile));

		String currentLine;

		for(int i = 1; (currentLine = reader.readLine()) != null; i++)
			if(i == lineToRemove) continue;
			else writer.write(currentLine.trim() + System.getProperty("line.separator"));
		writer.close();
		reader.close();
		input.delete();
		if(tempFile.renameTo(input)) System.out.println("Rule successfully removed");
	}
	
	public static void askQuestions() {
		for(int i = 0; i < atoms.size(); i++) {
			String cond = atoms.get(i);
			System.out.println(cond+"?(Y/N)");
			char answer = scan.nextLine().toUpperCase().charAt(0);
			if(answer == 'Y') {
				checkTrue(cond);
			}
		}
		Object[] aux = conclusions.toArray();
		for(int i = 0; i < conclusions.size(); i++) {
			if(i != 0) System.out.print(" E ");
			System.out.print(aux[i]);
		}
		System.out.println();
	}
	
	public static void checkTrue(String cond) {
		for(int i = 0; i < sentences.size(); i++) {
			Sentence sent = sentences.get(i);
			List<String> concl = sent.validateCondition(cond);
			if(concl != null) {
				conclusions.addAll(concl);
				sentences.remove(sent);
				atoms.removeAll(conclusions);
				concl.forEach(j -> checkTrue(j));
			}
		}
	}
	
}

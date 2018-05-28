package br.ufal.ic.ts.InteligenciaArtificial1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Motor {
	private String filepath;
	private String auxFilepath;
	private boolean concDisplay = false; 
	private List<Sentence> sentences = new ArrayList<>();
	private List<String> atoms = new ArrayList<>();
	private Set<String> conclusions = new HashSet<>();
	private BufferedReader reader;
	
	public Motor(String filepath) throws IOException {
		this.filepath = filepath;
		String[] stringArray = filepath.split("\\.");
		if(stringArray.length == 1) {
			auxFilepath = stringArray[0] + "Aux";
		} else {
			auxFilepath = stringArray[0] + "Aux." + stringArray[1];
		}
		reader = new BufferedReader(new FileReader(filepath));
	}
	
	public Motor(BufferedReader reader) {
		auxFilepath = System.getProperty("user.dir") + "/testAux.txt";
		filepath = System.getProperty("user.dir") + "/test.txt";
		this.reader = reader;
	}
	
	public void listRules() throws IOException {
		String line = reader.readLine();
		int i = 1;
		System.out.println("Rules: ");
		while(line != null) {
			System.out.println(i+" - "+line);
			line = reader.readLine();
			i++;
		}
		if(i == 1) System.out.println("No rules found!"); 
	}
	public void readDatabase() throws IOException {
		String line = reader.readLine();
		Set<String> auxAtoms = new HashSet<>();
		while(line != null) {
			if(!line.isEmpty()) {
				String sent[] = line.replace("SE", "").split("ENTAO");
				String conds[] = sent[0].split("OU");
				for(int i = 0; i < conds.length; i++) {
					Sentence sentence = new Sentence(conds[i], sent[1]);
					sentences.add(sentence);
					auxAtoms.addAll(sentence.getConditions());
				}
			}
			line = reader.readLine();
		}
		atoms.addAll(auxAtoms);
	}
	
	public void addRule(Scanner scan) throws IOException {
		BufferedWriter output = new BufferedWriter(new FileWriter(filepath, true));
		System.out.println("Rule Format: SE ... ENTAO ...");
		System.out.print("New Rule: ");
		String line = scan.nextLine();
		while(!line.matches("SE .+ ENTAO [^(OU)]+")) {
			System.out.println("Unacceptable Rule, try again!\nNew Rule:");
			line = scan.nextLine();
		}
		output.write(line+System.lineSeparator());
		output.close();
	}
	public void deleteRule(Scanner scan) throws IOException {
		listRules();
		System.out.print("Select rule to be removed: ");
		int lineToRemove = scan.nextInt();
		scan.nextLine();
		FileInputStream fis = new FileInputStream(filepath);
		if(fis.read() != -1) {
			File input = new File(filepath);
			File tempFile = new File(auxFilepath);
			//reader = new BufferedReader(new FileReader(input));
			PrintWriter writer = new PrintWriter(new FileWriter(tempFile));
	
			String currentLine;
			
			for(int i = 1; (currentLine = reader.readLine()) != null; i++)
				if(i == lineToRemove) continue;
				else writer.write(currentLine.trim() + System.getProperty("line.separator"));
			writer.close();
			input.delete();
			if(tempFile.renameTo(input)) System.out.println("Rule successfully removed");
		} else if(!sentences.isEmpty()) sentences.remove(lineToRemove-1);
		fis.close();
	}
	
	public void askQuestions(Scanner scan) {
		
		for(int i = 0; i < atoms.size(); i++) {
			String cond = atoms.get(i);
			System.out.println(cond+"?(Y/N)");
			char answer =  scan.nextLine().toUpperCase().charAt(0);
			if(answer == 'Y') {
				checkTrue(cond);
			}
		}
		System.out.println("Conclusions:");
		if(conclusions.isEmpty()) System.out.println("No conclusions could be taken");
		else printAux(conclusions);
	}
	
	public void removeQuestions(String cond) {
		for(int i = 0; i < sentences.size(); i++) {
			//if you say A is true, then there's no reason to ask if C is true if C only implies A
			if(sentences.get(i).checkConclusion(cond)) {
				sentences.remove(i);
				atoms.remove(cond);
			}
		}
	}
	
	public void checkTrue(String cond) {
		//removes C => A from sentences, if A was already said to be true
		removeQuestions(cond);
		for(int i = 0; i < sentences.size(); i++) {
			Sentence sent = sentences.get(i);
			List<String> concl = sent.validateCondition(cond);
			if(concl != null) {
				List<String> aux = new ArrayList<>();
				concl.forEach(f -> {if(conclusions.add(f)) aux.add(f);});					
				if(concDisplay) {
					System.out.print(sent.getWholeCondition()+" => ");
					printAux(aux);
				}
				conclusions.addAll(concl);
				sentences.remove(sent);
				atoms.removeAll(conclusions);
				concl.forEach(j -> checkTrue(j));
			}
		}
	}
	
	private void printAux(Collection<String> aux) {
		String sep = "";
		for(String c : aux) {
			System.out.print(sep + c);
			sep = " E ";
		}
		System.out.println();
	}
	
	public void close() throws IOException {
		reader.close();
	}
	
}
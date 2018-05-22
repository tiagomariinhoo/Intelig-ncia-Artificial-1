package br.ufal.ic.ts.InteligenciaArtificial1;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Sentence {
	private String wholeCondition;
	private List<String> conditions;
	private List<String> conclusions;
	
	public Sentence(String cond, String concl) {

		wholeCondition = (cond.trim());
		conditions = Arrays.asList(cond.split("E"));
		conditions = conditions.stream().map(String::trim).collect(Collectors.toList());

		conclusions = Arrays.asList(concl.split("E"));
		conclusions = conclusions.stream().map(String::trim).collect(Collectors.toList());
	}
	
	public List<String> validateCondition(String cond) {
		conditions.remove(cond);
		if(conditions.isEmpty()) {
			return conclusions;
		}
		else return null;
	}
	
	public boolean checkConclusion(String concl) {
		List<String> aux = Arrays.asList(concl.split("E"));
		aux = aux.stream().map(String::trim).collect(Collectors.toList());
		if(conclusions.equals(aux)) return true;
		else return false;
	}
	
	public List<String> getConditions() {
		return conditions;
	}

	public String getWholeCondition() {
		return wholeCondition;
	}
	
}
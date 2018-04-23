import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Sentence {
	List<String> conditions;
	List<String> conclusions;
	
	public Sentence(String line) {
		line = line.replace("SE", "");
		String sentence[] = line.split("ENTAO");
		
		conditions = Arrays.asList(sentence[0].split("E"));
		conditions = conditions.stream().map(String::trim).collect(Collectors.toList());

		conclusions = Arrays.asList(sentence[1].split("E"));
		conclusions = conclusions.stream().map(String::trim).collect(Collectors.toList());
	}
	
	public List<String> validateCondition(String cond) {
		conditions.remove(cond);
		if(conditions.isEmpty()) {
			return conclusions;
		}
		else return null;
	}
	
	public List<String> getConditions() {
		return conditions;
	}	
	
}

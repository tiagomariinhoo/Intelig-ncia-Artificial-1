package br.ufal.ic.ts.InteligenciaArtificial1;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

public class TestInferenceMotor {
	
   @SuppressWarnings("unchecked")
   @Test
   public void testReadDatabase	() throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
	   InputStream is = new ByteArrayInputStream("SE a ENTAO b".getBytes());
	   
	   Motor motor = new Motor(new BufferedReader(new InputStreamReader(is)));
	   motor.readDatabase();
	   motor.askQuestions(new Scanner("y")); 
	   Field f = motor.getClass().getDeclaredField("conclusions");
       f.setAccessible(true);    
       HashSet<String> word = ((HashSet<String>) f.get(motor));

       HashSet<String> oracle = new HashSet<String>();
       oracle.add("b");
       
       assertEquals(oracle, word);
       motor.close();
   }
   
   @SuppressWarnings("unchecked")
   @Test
   public void testQuantRules() throws Exception {
	   InputStream is = new ByteArrayInputStream("SE a ENTAO b\nSE b ENTAO c\nSE c ENTAO d".getBytes());

	   Motor motor = new Motor(new BufferedReader(new InputStreamReader(is)));
	   motor.readDatabase();

	   Field f = motor.getClass().getDeclaredField("sentences");
       f.setAccessible(true);  
       List<Sentence> sentences = (List<Sentence>) f.get(motor);

       assertEquals(3,sentences.size());
       motor.close();
	   
   }
   
   @SuppressWarnings("unchecked")
   @Test
   public void testDeleteRule() throws Exception {
	   InputStream is = new ByteArrayInputStream("SE a ENTAO b\nSE b ENTAO c\nSE c ENTAO d".getBytes());

	   Motor motor = new Motor(new BufferedReader(new InputStreamReader(is)));
	   motor.readDatabase();
	   motor.deleteRule(new Scanner("1\n"));
	   
	   Field f = motor.getClass().getDeclaredField("sentences");
       f.setAccessible(true);  
       List<Sentence> sentences = (ArrayList<Sentence>) f.get(motor);
       List<Sentence> oracle = new ArrayList<>();
       oracle.add(new Sentence("b", "c"));
       oracle.add(new Sentence("c", "d"));
       
       assertAll(
	       () -> assertEquals(2, sentences.size()),
	       () -> assertEquals(oracle.get(0), sentences.get(0)),
	       () -> assertEquals(oracle.get(1), sentences.get(1))
       );
       motor.close();
   }
}
 
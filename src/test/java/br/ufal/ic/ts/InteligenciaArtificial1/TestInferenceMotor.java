package br.ufal.ic.ts.InteligenciaArtificial1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

public class TestInferenceMotor
{
   @SuppressWarnings("unchecked")
@Test
   public void testReadDatabase	() throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
	   InputStream is = new ByteArrayInputStream("SE a ENTAO b".getBytes());
	   //Motor.readDatabase(new BufferedReader(new InputStreamReader(is)));
	   Motor motor = new Motor(new BufferedReader(new InputStreamReader(is)), new BufferedReader(new InputStreamReader(is)));
	   motor.readDatabase();
	   motor.askQuestions(new Scanner("y")); 
	   Field f = motor.getClass().getDeclaredField("conclusions");
       f.setAccessible(true);    
       HashSet<String> word = ((HashSet<String>) f.get(motor));
       
       word.equals(new HashSet<String>().add("b"));

       //word.forEach(w -> System.out.println(w));
       HashSet<String> oracle = new HashSet<String>();
       oracle.add("b");
       
       assertEquals(word,(oracle));
       
   }
   
   @Test
   public void testQuantSentences() throws Exception {
	   InputStream is = new ByteArrayInputStream("SE a ENTAO b\nSE b ENTAO c\nSE c ENTAO d".getBytes());
	   //Motor.readDatabase(new BufferedReader(new InputStreamReader(is)));
	   Motor motor = new Motor(new BufferedReader(new InputStreamReader(is)), new BufferedReader(new InputStreamReader(is)));
	   motor.readDatabase();

	   Field f = motor.getClass().getDeclaredField("sentences");
       f.setAccessible(true);    
       List<Sentence> sentences =(List<Sentence>) f.get(motor);

       assertEquals(3,sentences.size());
      
	   
   }
}
 
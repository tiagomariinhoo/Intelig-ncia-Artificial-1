package br.ufal.ic.ts.InteligenciaArtificial1;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestEightDigits {
	@Test
	public void testAllCoordinates() {
		SolveEightDigits solve = new SolveEightDigits();
		List<Integer> input = new ArrayList<Integer>();
		
		input.add(4); input.add(1); input.add(2);
		input.add(7); input.add(9); input.add(3);
		input.add(8); input.add(5); input.add(6);
		
		List<Integer> result = solve.solveToTest(input);
		Integer bfsTest = result.get(0);
		Integer iterativeDfsTest = result.get(1);
		Integer esperado = 8;
		
		assertAll(() -> {
			assertEquals(esperado, bfsTest);
			assertEquals(esperado, iterativeDfsTest);
		});
	}
	
}

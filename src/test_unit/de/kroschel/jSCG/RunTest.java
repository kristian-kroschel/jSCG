package de.kroschel.jSCG;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RunTest {
	
	@Before
	public void setup(){
		
	}
	
	@After
	public void tearDown(){
		
	}

	@Test
	public void testPrintUsage() throws IOException {
		PrintStream def = System.out; 
		PrintStream ps = new PrintStream("usage.out");
		System.setOut(ps);
		Run.main(null);
		System.setOut(def);
		System.out.println("Test");
		
		FileReader istF = new FileReader("usage.out");
		FileReader sollF = new FileReader("usage.txt");
		
		char[] istC = new char[400];
		istF.read(istC);
		istF.close();
		
		char[] sollC = new char[400];
		sollF.read(sollC);
		sollF.close();

		boolean res=true;
		for (int i=0; i<sollC.length;i++){
			res &= (sollC[i] == istC[i]); 
		}
		assertTrue(res);
		
		
		
	}
	
	private boolean compareFiles(){
		return false;
	}
	
	@Test
	public void testGenerator(){
		
	}

}

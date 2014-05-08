package de.kroschel.jSCG.template;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LiteralScannerTest {
	
	String input;
	ArrayList<LiteralPosition> result;
	
	public void setup(){
		LiteralScanner s = new LiteralScanner();
		for (int i=0;i<input.length();i++){
			System.out.print(i+":"+input.charAt(i)+" ");
			s.scan(input.charAt(i));
		}
		result = s.getLiteralPositions();
	}
	
	@After
	public void teardown(){
		this.input = null;
		this.result = null;
	}

	@Test
	public void testBeginLineComment() {
		input = "$'comment\ntext";
		setup();
		
		assertTrue(result.size() >= 1);
		LiteralPosition lp = result.get(0);
		assertTrue(lp.getPosition().getLine()==1);
		assertTrue(lp.getPosition().getColumn()==0);
		assertTrue(lp.getPosition().getScanPosition()==0);
		assertTrue(lp.getLiteral() == Literal.beginLineComment);
		
	}
	
	@Test
	public void testEndLineComment() {
		input = "$'comment\ntext";
		setup();
		
		assertTrue(result.size() >= 2);
		LiteralPosition lp = result.get(1);
		assertTrue(lp.getPosition().getLine()==1);
		assertTrue(lp.getPosition().getColumn()==9);
		assertTrue(lp.getPosition().getScanPosition()==9);
		assertTrue(lp.getLiteral() == Literal.endLineComment);
		
	}
	
	@Test
	public void testBeginEndScript(){
		//                 1 11  -> Position 
		//       01234567890 12
		input = "$>a script\n<$";
		setup();
		
		assertTrue(result.size() == 2);
		LiteralPosition lp0 = result.get(0);
		LiteralPosition lp1 = result.get(1);
		assertTrue(lp0.getLiteral() == Literal.beginScriptTag);

		assertTrue(lp1.getLiteral() == Literal.endScriptTag);
		assertTrue(lp1.getPosition().getLine()==2);
		assertTrue(lp1.getPosition().getColumn()==0);
		assertEquals("Scanposition-endScriptTag",11,lp1.getPosition().getScanPosition());
	}
	
	
	@Test
	public void testEscape(){
		//                  11111111112
		//       0 12345678901234567890
		input = "\\$> escaped begin $>real begin script<$";
		setup();
		
		assertEquals("size literals",2,result.size());
		LiteralPosition lp0 = result.get(0);
		assertEquals("Literal",Literal.beginScriptTag, lp0.getLiteral());
		assertEquals("Scanposition-beginScriptTag",18,lp0.getPosition().getScanPosition());
	}

}

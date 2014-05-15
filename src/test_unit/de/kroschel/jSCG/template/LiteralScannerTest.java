package de.kroschel.jSCG.template;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LiteralScannerTest {
	
	String input;
	ArrayList<LiteralPosition> result;
	TemplatePosition endPosition;
	
	public void setup(){
		LiteralScanner s = new LiteralScanner();
		for (int i=0;i<input.length();i++){
			System.out.print(i+":"+input.charAt(i)+" ");
			s.scan(input.charAt(i));
		}
		result = s.getLiteralPositions();
		endPosition = s.getLastTemplatePosition();
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
		assertTrue(lp.getPosition().getColumn()==1);
		assertTrue(lp.getPosition().getScanPosition()==0);
		assertTrue(lp.getLiteral() == Literal.beginLineComment);
		
	}
	
	@Test
	public void testEndLineComment() {
		//       0123456789 01234
		input = "$'comment\ntext";
		setup();
		
		assertEquals(2,result.size());
		LiteralPosition lp = result.get(1);
		assertEquals(1,lp.getPosition().getLine());
		assertEquals(10,lp.getPosition().getColumn());
		assertEquals(9,lp.getPosition().getScanPosition());
		assertEquals(Literal.endLineComment,lp.getLiteral());
		
	}
	
	@Test
	public void testBeginEndScript(){
		//                 1 11  -> Position 
		//       01234567890 12
		input = "$>a script\n<$";
		setup();
		
		assertEquals(2,result.size());
		LiteralPosition lp0 = result.get(0);
		LiteralPosition lp1 = result.get(1);
		assertEquals(Literal.beginScript,lp0.getLiteral());

		assertEquals(Literal.endScript,lp1.getLiteral());
		assertEquals("Line-endScriptTag",2,lp1.getPosition().getLine());
		assertEquals("column-endScriptTag",1,lp1.getPosition().getColumn());
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
		assertEquals("Literal",Literal.beginScript, lp0.getLiteral());
		assertEquals("Scanposition-beginScriptTag",18,lp0.getPosition().getScanPosition());
	}
	
	@Test
	public void testCommentAndScript(){
		//   0000000001111111111
		//   1234567890123456789
		// 1 $' a comment
		// 2 $>script<$
		// 3 text
        //                 111 11111112
        //       0123456789012 34567890
		input = "$' a comment\n$>script<$\ntext";
		setup();
		
		assertEquals("size literals",4,result.size());
		assertEquals("Literal 1",Literal.beginLineComment, result.get(0).getLiteral());
		assertEquals("Literal 2",Literal.endLineComment, result.get(1).getLiteral());
		assertEquals("Literal 3",Literal.beginScript, result.get(2).getLiteral());
		assertEquals("Literal 4",Literal.endScript, result.get(3).getLiteral());
		
		assertEquals("Scanposition - Literal 1",0,result.get(0).getPosition().getScanPosition());
		assertEquals("Scanposition - Literal 2",12,result.get(1).getPosition().getScanPosition());
		assertEquals("Scanposition - Literal 3",13,result.get(2).getPosition().getScanPosition());
		assertEquals("Scanposition - Literal 4",21,result.get(3).getPosition().getScanPosition());
		
		assertEquals("Line - Literal 1",1,result.get(0).getPosition().getLine());
		assertEquals("Line - Literal 2",1,result.get(1).getPosition().getLine());
		assertEquals("Line - Literal 3",2,result.get(2).getPosition().getLine());
		assertEquals("Line - Literal 4",2,result.get(3).getPosition().getLine());
		
		assertEquals("Column - Literal 1",1,result.get(0).getPosition().getColumn());
		assertEquals("Column - Literal 2",13,result.get(1).getPosition().getColumn());
		assertEquals("Column - Literal 3",1,result.get(2).getPosition().getColumn());
		assertEquals("Column - Literal 4",9,result.get(3).getPosition().getColumn());
	}

	@Test
	public void testGetLastEndposition(){
		input = "text";
		setup();
		assertEquals(1,endPosition.getLine());
		assertEquals(4,endPosition.getColumn());
		assertEquals(3, endPosition.getScanPosition());
	}
	
	@Test
	public void testDataInstructions(){
		/* $! data(root,node);
		      data(root,child,son);
		      data(root,attribute,r1,r1value);
		      data(son,node);
		   !$
		*/
		//       00000000000111111111 2222222222333333333344444 444445555555555666666666677777777778 8888888889999999999 00
		//       01234567890123456789 0123456789012345678901234 567890123456789012345678901234567890 1234567890123456789 01
		input = "$! data(root,node);\n   data(root,child,son);\n   data(root,attribute,r1,r1value);\n   data(son,node);\n!$";
		setup();
		
		assertEquals("size literals",2,result.size());
		assertEquals("Literal 1",Literal.beginInstruction, result.get(0).getLiteral());
		assertEquals("Literal 2",Literal.endInstruction, result.get(1).getLiteral());
		assertEquals("Scanposition - Literal 1",0,result.get(0).getPosition().getScanPosition());
		assertEquals("Scanposition - Literal 2",100,result.get(1).getPosition().getScanPosition());
	}
}

package de.kroschel.jSCG.template;

import static org.junit.Assert.*;

import org.junit.Test;

public class LiteralPositionTest {
	
	// to understand the tests, I have used the following positions:
	// line   : 1111111111 22222 333333333 444 5
	// column : 0000000001 00000 000000000 000 0
	// column : 1234567890 12345 123456789 123 1
	// scanpos: 0000000000 11111 111112222 222 2
	// scanpos: 0123456789 01234 567890123 456 7
	// Text:  : $'comment\ntext\n$>script\n<$\n
	   
	//   1234567890
	// 1 $'comment\n
	// 2 text\n
	// 3 $>script\n
	// 4 <$\n
	// 5
	
	

	@Test
	public void testCalculateOffsetLineComments() {
		LiteralPosition endPosition = new LiteralPosition();
		endPosition.setLiteral(Literal.endLineComment);
		endPosition.setPosition(TemplatePosition.createTemplatePosition(1, 10, 9));
		TemplatePosition nextBeginPos = endPosition.calculateNextBeginPosition();
		
		assertEquals("line",2,nextBeginPos.getLine());
		assertEquals("column",1,nextBeginPos.getColumn());
		assertEquals("scanPosition",10,nextBeginPos.getScanPosition());
	}
	
	
	@Test
	public void testCalculateOffsetBeginScript() {
		LiteralPosition endPosition = new LiteralPosition();
		endPosition.setLiteral(Literal.beginScript);
		endPosition.setPosition(TemplatePosition.createTemplatePosition(3, 1, 15));
		TemplatePosition nextBeginPos = endPosition.calculateNextBeginPosition();
		
		assertEquals("line",3,nextBeginPos.getLine());
		assertEquals("column",3,nextBeginPos.getColumn());
		assertEquals("scanPosition",17,nextBeginPos.getScanPosition());
		
	}
	
	

}

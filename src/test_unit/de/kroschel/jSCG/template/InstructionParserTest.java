package de.kroschel.jSCG.template;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import de.kroschel.jSCG.template.InstructionParser.InstructionToken;

public class InstructionParserTest {
	
	String input;
	ArrayList<TemplateInstruction> instructionList;
	
	public void run(){
		InstructionParser ip = new InstructionParser();
		instructionList = ip.parse(input);
	}

	@Test
	public void testUsage() {
		input = " data(root,node);\n   data(root,child,son);\n   data(root,attribute,r1,r1value);\n   data(son,node);\n";
		run();
		
		assertEquals("list size", 4, instructionList.size());
		assertEquals("token 0",InstructionToken.data, instructionList.get(0).token);
		assertEquals("value 0","root,node", instructionList.get(0).value);
		assertEquals("token 1",InstructionToken.data, instructionList.get(1).token);
		assertEquals("value 1","root,child,son", instructionList.get(1).value);
	}
	
	@Test(expected=InstructionParserException.class)
	public void testUnexpectedEndOfInstructionMissingSemicolon(){
		//       000000000011111111112
		//       012345678901234567890
		input = " data(root,node)\n   data(root,child,son);\n   data(root,attribute,r1,r1value);\n   data(son,node);\n";;
        //                           ^--- here: a semicolon expected
		try {
			run();
		} catch (InstructionParserException e) {
			e.printStackTrace();
			assertEquals("scanposition",20,e.scanposition);			
			throw e;
		}
	}
	
	@Test(expected=InstructionParserException.class)
	public void testUnexpectedEndOfInstructionMissingClosingParantesis(){
		//       000000000011111111112
		//       012345678901234567890
		input = " data(root,node;";
	    //                      ^--- here: a parantesis is missed
		try {
			run();
		} catch (InstructionParserException e) {
			e.printStackTrace();
			assertEquals("scanposition",17,e.scanposition);			
			throw e;
		}
	}
	
	@Test
	public void testDataInstruction(){
		input = "data(root,node);";
		run();
		
		assertEquals("list size", 1, instructionList.size());
		assertEquals("token 0",InstructionToken.data, instructionList.get(0).token);
		assertEquals("value 0","root,node", instructionList.get(0).value);
	}
	
	@Test
	public void testPragmaInstruction(){
		input = "pragma(file:overwrite);";
		run();
		
		assertEquals("list size", 1, instructionList.size());
		assertEquals("token 0",InstructionToken.pragma, instructionList.get(0).token);
		assertEquals("value 0","file:overwrite", instructionList.get(0).value);
	}
	
	@Test
	public void testIncludeInstruction(){
		input = "include(include_template.tpl);";
		run();
		
		assertEquals("list size", 1, instructionList.size());
		assertEquals("token 0",InstructionToken.include, instructionList.get(0).token);
		assertEquals("value 0","include_template.tpl", instructionList.get(0).value);
	}

}

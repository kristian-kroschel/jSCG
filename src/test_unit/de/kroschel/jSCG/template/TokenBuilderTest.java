package de.kroschel.jSCG.template;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TokenBuilderTest {

	private ArrayList<LiteralPosition> literalPositions;
	private String templateinput;
	private ArrayList<TemplateFragment> fragments;
	
	@Before
	public void setUp(){
		this.templateinput = "$' a comment\n$> \nscript\n<$\ntext";
		LiteralScanner s = new LiteralScanner();
		for (int i=0;i<this.templateinput.length();i++){
			s.scan(this.templateinput.charAt(i));
		}
		this.literalPositions = s.getLiteralPositions();
		TokenBuilder tb = new TokenBuilder(this.templateinput,this.literalPositions);
		fragments = tb.buildFragments(TemplatePosition.createInitialPosition(),TemplatePosition.createTemplatePosition(5, 5, 30));
	}
	
	@Test
	public void testFragmentsListSize() {
		assertEquals("list size", 3, fragments.size());
	}
	
	@Test
	public void testFragmentsListExists() {
		assertNotNull("fragments list does not exist", fragments);	
	}
	
	@Test
	public void testScriptFragment() {
		assertTrue("first is not a script", fragments.get(1) instanceof ScriptTemplateFragment);
	}
	
	@Test
	public void testTextFragment() {
		assertTrue("second is not a text", fragments.get(2) instanceof TextTemplateFragment);
	}

}

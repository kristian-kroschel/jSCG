package de.kroschel.jSCG.template;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.StringReader;

import org.junit.Test;

import de.kroschel.jSCG.core.ScaffolderNotInitializedException;
import de.kroschel.jSCG.data.DataItem;

public class TemplateParserTest {

	@Test
	public void testUsage() {
		TemplateParser tr = new TemplateParser("../../src/test_integration/basics.tpl");
		tr.parseTemplate();
		Template t = tr.getTemplate();
		DataItem i = tr.getInlineData();
		
		assertNotNull(t);
		assertNotNull(i);
	}
	
	@Test(expected=TemplateParserException.class)
	public void testInvalidPath(){
		TemplateParser tr = new TemplateParser("noExistingFile");
	}
	
	@Test
	public void testOnlyTextInput() {
		TemplateParser tr = new TemplateParser();
		String input = "line1\nline2";
		tr.setInputReader(new StringReader(input));
		tr.parseTemplate();
		
		Template t = tr.getTemplate();
		String result = t.getScript();
		assertEquals("print('line1\nline2');",result);
	}
	
	
	@Test
	public void testTextWithComments(){
		TemplateParser tr = new TemplateParser();
		String input = "$' comment\nline2\ntext line3";
		tr.setInputReader(new StringReader(input));
		tr.parseTemplate();
		
		Template t = tr.getTemplate();
		String result = t.getScript();
		assertEquals("print('line2\ntext line3');",result);
	}

}

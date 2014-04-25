package de.kroschel.jSCG.template;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.kroschel.jSCG.template.Template.TemplateFragmentType;

public class TemplateTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testPrepareTextTemplateFragment() {
		Template t = new Template();
		t.addTemplateFragment(TemplateFragmentType.TEXT, "some Text\nbla");
		t.prepareScript(null);
		String script = t.getScript();
		assertEquals("print('some Text\nbla');",script);
	}
	
	@Test
	public void testPrepareScriptTemplateFragment() {
		Template t = new Template();
		t.addTemplateFragment(TemplateFragmentType.SCRIPT, "println('A Text');");
		t.prepareScript(null);
		String script = t.getScript();
		assertEquals("println('A Text');",script);
	}
	
	@Test(expected=TemplateNotReadyException.class)
	public void testTemplateNotReadyForPreparation(){
		Template t = new Template();
		t.prepareScript(null);
	}
	
	@Test(expected=TemplateNotReadyException.class)
	public void testTemplateNotReadyForScriptOutput(){
		Template t = new Template();
		t.getScript();
	}

	@Test
	public void testIsNotEmpty() {
		Template t = new Template();
		t.addTemplateFragment(TemplateFragmentType.TEXT, "some Text\nbla");
		assertTrue(t.isFilled());
	}

}

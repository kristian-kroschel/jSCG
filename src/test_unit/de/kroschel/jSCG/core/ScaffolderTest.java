package de.kroschel.jSCG.core;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.kroschel.jSCG.core.LogMessage.MessageCategory;
import de.kroschel.jSCG.data.DataItem;
import de.kroschel.jSCG.data.DataNode;
import de.kroschel.jSCG.output.OutputItem;
import de.kroschel.jSCG.template.Template;

public class ScaffolderTest {
	
	private Scaffolder s;
	private DataItem i;
	private Template t;
	private OutputItem o;
	private LogMessage m;
	private Exception processException;
	
	@Before
	public void setUp() {
		this.s = new Scaffolder();
		this.i = new DataItem();
		this.t = new Template();
	}
	
	private void run(){
		s.setDataItem(i);
		s.setTemplate(t);
		try {
			s.process();
		} catch (Exception e){
			processException = e;
		}
		this.m = s.getLog();
		this.o = s.getOutput();
	}

	
	@Test
	public void testProcessNotInitializedScaffolder() {
		try{
			s.process();
		} catch (Exception e){
			// handled by separate test case
		}
		
		assertNull("no output produced?", s.getOutput());
		
		LogMessage m = s.getLog();
		assertNotNull("log produced?", m);
		assertEquals("Not initialized Message reported?","Scaffolder Not Initialized", m.getMessage());
		assertTrue("log category is error?",m.getCategory() == MessageCategory.error);
		
	}
	
	@Test(expected=ScaffolderNotInitializedException.class)
	public void testProcessNotInitializedScaffolderExeptionThrown(){
		s.process();
	}
	
	
	@Test
	public void testSetDataItem(){
		DataItem i = new DataItem();
		s.setDataItem(i);
		
		assertSame(i, s.getDataItem());
	}
	
	@Test
	public void testSetTemplate(){
		Template t = new Template();
		s.setTemplate(t);
		
		assertSame(t, s.getTemplate());
	}
	
	@Test
	public void testInitialzizeEmptyAndRunLogProducedButNoOutput(){
		run();
		
		assertNull("no output produced?", o);
		
		assertNotNull("log produced?", m);
		assertEquals("Not initialized Message reported?","Scaffolder Not Initialized", m.getMessage());
		assertTrue("log category is error?",m.getCategory() == MessageCategory.error);
		
	}
	
	@Test
	public void testInitialzizeEmptyAndRunExceptionThrown(){
		run();
		assertTrue(processException instanceof ScaffolderNotInitializedException );
	}
	
	@Test
	public void testStaticTextTemplate(){
		t.addTemplateFragment(Template.TemplateFragmentType.TEXT,"Hello World");
		t.setOutputName("HelloWorldStatic.tpl");
		run();
		
		assertNotNull("output produced?", o);
		
		assertEquals("produced what expected?","Hello World",o.getText());
		assertNotNull("log produced?", m);
		assertTrue("log category is success?",m.getCategory() == MessageCategory.success);
	}
	
	@Test
	public void testStaticTextWithScript(){
		t.addTemplateFragment(Template.TemplateFragmentType.TEXT,"Hello World");
		t.addTemplateFragment(Template.TemplateFragmentType.SCRIPT,"for(i=0;i<3;i++) { println('Hello'); }");
		t.setOutputName("HelloWorldScript.tpl");
		run();
		assertNotNull("output produced?", o);
		assertEquals("produced what expected?","Hello WorldHello\nHello\nHello\n",o.getText());
		assertNotNull("log produced?", m);
		assertTrue("log category is success?",m.getCategory() == MessageCategory.success);
	}
	
	@Test
	public void testExposedObjectsAccessFromScript(){
		DataNode rootNode = new DataNode("RootNode");
		i.setRoot(rootNode);
		i.setOutputName("EmptyNode");
		t.addTemplateFragment(Template.TemplateFragmentType.SCRIPT,"print(JSCG_ROOTNODE.getName());print(JSCG_TEMPLATE_NAME);print(JSCG_DATAITEM_NAME);");
		t.setOutputName("RootNodeScript.tpl");
		run();
		assertNotNull("output produced?", o);
		assertEquals("produced what expected?","RootNodeRootNodeScript.tplEmptyNode",o.getText());
	}

}

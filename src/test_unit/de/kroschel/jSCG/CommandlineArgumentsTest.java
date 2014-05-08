package de.kroschel.jSCG;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CommandlineArgumentsTest {
	
	private String[] args;
	CommandlineArguments cla;
	
	@Before
	public void setup(){
		args = new String[2];
		args[0] = "-SomeName";
		args[1] = "someValue";
		cla = new CommandlineArguments();
		cla.init(args);
	}
	
	@After
	public void teardown(){
		args = null;
	}

	@Test
	public void testGetArgumentNames() {		
		assertNotNull(cla.getArgumentNames());
		assertTrue(cla.getArgumentNames().size()==1);
		assertEquals("somename",cla.getArgumentNames().get(0));
	}

	@Test
	public void testGetArgumentValue() {
		assertEquals("someValue",cla.getArgumentValue("SomeName"));
		assertEquals("someValue",cla.getArgumentValue("someName"));
	}

	@Test
	public void testIsArgumentSet() {
		assertTrue(cla.isArgumentSet("SomeName"));
		assertFalse(cla.isArgumentSet("noOption"));
	}
	
	@Test
	public void testReInitWithANullValue(){
		String[] largs = new String[1];
		largs[0] = "-AnotherName";
		cla.init(largs);
		
		assertNotNull(cla.getArgumentNames());
		assertTrue(cla.getArgumentNames().size()==1);
		assertEquals("anothername",cla.getArgumentNames().get(0));
		
		assertNull(cla.getArgumentValue("anothername"));
		
		assertTrue(cla.isArgumentSet("anothername"));
	}

}

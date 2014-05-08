package de.kroschel.jSCG;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CommandlineParserTest {

	private String[] args;
	CommandlineArguments cla;
	private final String[] argumentVariants = {"somename","sn"};
	CommandlineParser clp;
	
	@Before
	public void setUp() throws Exception {
		args = new String[2];
		args[0] = "-SomeName";
		args[1] = "someValue";
		cla = new CommandlineArguments();
		cla.init(args);
		clp = new CommandlineParser(cla);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testUsage() {
		
		String someNameValue = clp.extractArgument(argumentVariants);
		
		assertNotNull(someNameValue);
		assertEquals("someValue",someNameValue);
	}
	
	@Test
	public void testArgumentNotSet(){
		String someNotSetValue = clp.extractArgument(new String[]{"NotSET"});
		assertNull(someNotSetValue);
	}
	

}

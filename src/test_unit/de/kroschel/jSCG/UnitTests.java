package de.kroschel.jSCG;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	de.kroschel.jSCG.core.ScaffolderTest.class,
	de.kroschel.jSCG.data.DataNodeTest.class,
	de.kroschel.jSCG.template.TemplateTest.class,
	de.kroschel.jSCG.RunTest.class
})

public class UnitTests {
	// the class remains empty,
	// used only as a holder for the above annotations
}


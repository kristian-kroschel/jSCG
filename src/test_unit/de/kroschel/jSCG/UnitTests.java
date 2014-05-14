package de.kroschel.jSCG;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	de.kroschel.jSCG.CommandlineArgumentsTest.class,
	de.kroschel.jSCG.CommandlineParserTest.class,
	de.kroschel.jSCG.RunTest.class,
	de.kroschel.jSCG.core.ScaffolderTest.class,
	de.kroschel.jSCG.data.DataNodeTest.class,
	de.kroschel.jSCG.output.OutputItemTest.class,
	de.kroschel.jSCG.template.LiteralScannerTest.class,
	de.kroschel.jSCG.template.LiteralPositionTest.class,
	de.kroschel.jSCG.template.TemplateParserTest.class,
	de.kroschel.jSCG.template.TemplatePositionTest.class,
	de.kroschel.jSCG.template.TemplateTest.class,
	de.kroschel.jSCG.template.TokenBuilderTest.class
})

public class UnitTests {
	// the class remains empty,
	// used only as a holder for the above annotations
}


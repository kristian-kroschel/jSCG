package de.kroschel.jSCG.template;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TemplatePositionTest {

	private TemplatePosition pos;
	
	@Before
	public void setup(){
		this.pos = new TemplatePosition();
		this.pos.setLine(1);
		this.pos.setColumn(0);
		this.pos.setScanPosition(0);
	}
	
	@Test
	public void testIncScanPosition() {
		this.pos.incScanPosition();
		assertTrue(this.pos.getScanPosition() == 1);
	}

	@Test
	public void testIncLine() {
		this.pos.incLine();
		assertTrue(this.pos.getLine() == 2);
	}

	@Test
	public void testResetColumn() {
		this.pos.incColumn();
		this.pos.resetColumn();
		assertTrue(this.pos.getColumn() == 0);
	}

	@Test
	public void testIncColumn() {
		this.pos.incColumn();
		assertTrue(this.pos.getColumn() == 1);
	}

}

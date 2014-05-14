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
	
	@Test
	public void testEqualsSymmetryInitialPosition(){
		TemplatePosition posX = TemplatePosition.createInitialPosition();
		TemplatePosition posY = TemplatePosition.createInitialPosition();
		assertTrue(posX.equals(posY));
		assertTrue(posY.equals(posX));
	}
	
	@Test
	public void testEqualsReflexivtiyInitialPosition(){
		TemplatePosition pos1 = TemplatePosition.createInitialPosition();
		assertTrue(pos1.equals(pos1));
	}
	
	@Test
	public void testEqualsReflexivtiySomePosition(){
		TemplatePosition pos1 = TemplatePosition.createTemplatePosition(2, 4, 25);
		assertTrue(pos1.equals(pos1));
	}
	
	@Test
	public void testEqualsForNullsSomePosition(){
		TemplatePosition pos1 = TemplatePosition.createTemplatePosition(2, 4, 25);
		assertFalse(pos1.equals(null));
	}
	
	@Test
	public void testEqualsSymmetrySomePosition(){
		TemplatePosition posX = TemplatePosition.createTemplatePosition(2, 4, 25);
		TemplatePosition posY = TemplatePosition.createTemplatePosition(2, 4, 25);
		assertTrue(posX.equals(posY));
		assertTrue(posY.equals(posX));
	}
	
	@Test
	public void testEqualsTransitivitySomePosition(){
		TemplatePosition posX = TemplatePosition.createTemplatePosition(2, 4, 25);
		TemplatePosition posY = TemplatePosition.createTemplatePosition(2, 4, 25);
		TemplatePosition posZ = TemplatePosition.createTemplatePosition(2, 4, 25);
		assertTrue(posX.equals(posY));
		assertTrue(posY.equals(posZ));
		assertTrue(posX.equals(posZ));
	}
	
	@Test
	public void testEqualsDiffPositionsColumn(){
		TemplatePosition posX = TemplatePosition.createTemplatePosition(2, 5, 25);
		TemplatePosition posY = TemplatePosition.createTemplatePosition(2, 4, 25);
		assertFalse(posX.equals(posY));
		assertFalse(posY.equals(posX));
	}
	
	@Test
	public void testEqualsDiffPositionsLine(){
		TemplatePosition posX = TemplatePosition.createTemplatePosition(3, 4, 25);
		TemplatePosition posY = TemplatePosition.createTemplatePosition(2, 4, 25);
		assertFalse(posX.equals(posY));
		assertFalse(posY.equals(posX));
	}
	
	@Test
	public void testEqualsDiffPositionsScanPosition(){
		TemplatePosition posX = TemplatePosition.createTemplatePosition(2, 4, 26);
		TemplatePosition posY = TemplatePosition.createTemplatePosition(2, 4, 25);
		assertFalse(posX.equals(posY));
		assertFalse(posY.equals(posX));
	}
	
	

}

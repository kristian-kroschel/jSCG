package de.kroschel.jSCG.data;

import org.junit.Test;

import junit.framework.TestCase;

public class DataNodeTest extends TestCase {
	
	@Test
	public void testCreation(){
		DataNode dn = new DataNode("TestNode");
		assertEquals("dataNode Name",dn.getName(),"TestNode");
		
		assertTrue("number of childs",dn.childCount() == 0);
		assertTrue("number of attribute",dn.attributeCount() == 0);
	}
	
	@Test
	public void testAddChild(){
		DataNode dnParent = new DataNode("TestNodeWithChildsParent");
		DataNode dnChild1 = new DataNode("TestNodeWithChildsChild1");
		DataNode dnChild2 = new DataNode("TestNodeWithChildsChild2");
		
		dnParent.addChild(dnChild1);
		dnParent.addChild(dnChild2);
		
		assertTrue("number of childs",dnParent.childCount() == 2);
		
		assertSame("parent Set for child1?",dnChild1.getParent(),dnParent);
		assertSame("parent Set for child2?",dnChild2.getParent(),dnParent);
		assertNull("no parent for parent",dnParent.getParent());
		
		assertSame("child1 accessible by name?",dnParent.getChildByName("TestNodeWithChildsChild1"),dnChild1);
		assertSame("child2 accessible by name?",dnParent.getChildByName("TestNodeWithChildsChild2"),dnChild2);
		
		assertSame("child1 by index",dnParent.getChild(0),dnChild1);
		assertSame("child2 by index",dnParent.getChild(1),dnChild2);
	}
	
	@Test
	public void testIllegalChildAccess(){
		DataNode dn = new DataNode("TestNodeWithNoChilds");
		
		try {
			dn.getChildByName("SomeChildName");
	        fail("Expected an ChildNotPresentException to be thrown");
	    } catch (ChildNotPresentException e) {
	          assertTrue(e instanceof ChildNotPresentException);
	    }
	}
	
	@Test
	public void testSetAttribute(){
		DataNode dn = new DataNode("TestNodeWithAttributes");
		dn.addAttribute("AttributeName1","AttValue1");
		dn.addAttribute("AttributeName2","AttValue2");
		
		String value = dn.getAttribute("AttributeName1");
		assertEquals("attributeValueCorrect",value,"AttValue1");
		
		value = dn.getAttribute("AttributeName2");
		assertEquals("attributeValueCorrect",value,"AttValue2");
		
		assertTrue("number of attribute",dn.attributeCount() == 2);
		
		
	}
	
	@Test
	public void testIllegalAttributeAccess(){
		DataNode dn = new DataNode("TestNodeWithNoAttributes");
		try {
			  dn.getAttribute("SomeAttributeName");
	          fail("Expected an AttributeNotPresentException to be thrown");
	    } catch (AttributeNotPresentException e) {
	          assertTrue(e instanceof AttributeNotPresentException);
	    }
	}
	
	
	
	

}

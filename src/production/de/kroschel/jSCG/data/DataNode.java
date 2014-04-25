package de.kroschel.jSCG.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * represents one single node of data. 
 * A node has a set of attributes,  consisting of name-value pairs of strings
 * which can be accessed by their attribute name.
 * The node it self has also a name.
 * It may have child-nodes and/or a parent. 
 * @author Kristian Kroschel
 */
public class DataNode {
	
	public DataNode(String aNodeName) {
		this.nodeName = aNodeName;
	}
	
	private String nodeName;
	
	private DataNode parentNode;
	private HashMap<String,String> attributes;
	
	private List<DataNode> childNodes;
	private HashMap<String,DataNode> childsByName = null;
	
	/**
	 * 
	 * @return the Name of this node
	 */
	public String getName() {
		return this.nodeName;
	}

	/**
	 * 
	 * @return the number of childs of this node
	 */
	public int childCount() {
		if (this.childNodes == null){
			return 0;
		} else {
			return this.childNodes.size();
		}
	}


	/**
	 * 
	 * @return number of attributes of this node
	 */
	public int attributeCount() {
		if (this.attributes == null){
			return 0;
		} else {
			return this.attributes.size();
		}
	}

	/**
	 *  adds a node as a child node, maintaining the parent attribute of the child
	 *  (this implies that a child may belong only to one parent)
	 */
	public void addChild(DataNode aChildNode) {
		if (aChildNode != null){
			initChildStorage();
			this.childNodes.add(aChildNode);
			this.childsByName.put(aChildNode.getName(), aChildNode);
			aChildNode.parentNode = this;			
		}
		
	}

	private void initChildStorage(){
		if (this.childNodes == null){
			this.childNodes = new ArrayList<DataNode>();
		}	
		if (childsByName == null){
			this.childsByName = new HashMap<String, DataNode>();
		}
	};

	public DataNode getParent() {
		return parentNode;
	}


	/**
	 * returns a child by its name, if multiple childs with equal names exists in the node,
	 * the last added child is returned.
	 * @param aNodeName
	 * @return 
	 * @throws ChildNotPresentException if no child with the name is there
	 */
	public DataNode getChildByName(String aNodeName) {
		if (this.childsByName != null && this.childsByName.containsKey(aNodeName)){
			return this.childsByName.get(aNodeName);
		} else {
			throw new ChildNotPresentException(); // TODO: exception-Message !!
		}
	}


	public DataNode getChild(int index) {
		if (this.childNodes != null && this.childNodes.size() > index){
			return this.childNodes.get(index);
		} else {
			throw new ChildNotPresentException(); // TODO: exception-Message !!
		}
	}


	/**
	 * adds or sets an node attribute
	 * @param attributeName the name of the attribute
	 * @param attributeValue the value of the attribute
	 */
	public void addAttribute(String attributeName, String attributeValue) {
		if (this.attributes == null){
			this.attributes = new HashMap<String, String>();
		}
		this.attributes.put(attributeName, attributeValue);
	}


	/**
	 * returns the value of an attribute
	 * @param attributeName
	 * @return
	 * @throws AttributeNotPresentException when no attribute with the given name is present 
	 */
	public String getAttribute(String attributeName) {
		if (this.attributes != null && this.attributes.containsKey(attributeName)){
			return this.attributes.get(attributeName);
		} else {
			throw new AttributeNotPresentException(); // TODO: exception-Message !!
		}
	}
		
}

/**
 * 
 */
package de.kroschel.jSCG.data;

/**
 * @author kristian
 *
 */
public class DataItem {
	
	private DataNode root;
	private String outputName;

	public void setRoot(DataNode rootNode) {this.root = rootNode;}
	public void setOutputName(String aName) {this.outputName = aName;}
	public DataNode getRoot() {return this.root;}
	public String getOutputName() {return this.outputName;}

}

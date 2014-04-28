/**
 * 
 */
package de.kroschel.jSCG.template;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Iterator;

import de.kroschel.jSCG.data.DataItem;

/**
 * @author kristian
 *
 */
public class Template {
	
	public enum TemplateFragmentType{
		TEXT, SCRIPT;
	}

	private ArrayList<String> fragments;
	private String preparedScript;
	private String outputName;

	public void addTemplateFragment(TemplateFragmentType aFragmentType, String aFragment) {
		if(this.fragments == null){
			this.fragments = new ArrayList<String>();
		}

		if (aFragmentType == TemplateFragmentType.SCRIPT){
			this.fragments.add(aFragment);
		}
		
		if (aFragmentType == TemplateFragmentType.TEXT){
			this.fragments.add("print('" + escape(aFragment) + "');");
		}
	}

	private String escape(String aFragment) {
		StringBuffer result = new StringBuffer();
		CharacterIterator i = new StringCharacterIterator(aFragment);
		for(char c = i.first(); c != CharacterIterator.DONE; c = i.next()) {
	         processChar(c,result);
	     }
		return result.toString();
	}

	private void processChar(char c, StringBuffer result) {
		if (Character.getType(c) == Character.LINE_SEPARATOR){
			result.append("\\");
			result.append("n");		
		} else {
			result.append(c);
		}
		
	}

	public void setOutputName(String string) {
		this.outputName = string;
	}

	public boolean isFilled() {
		return this.fragments != null && !this.fragments.isEmpty();
	}

	public void prepareScript(DataItem currentDataItem) {
		if (!isFilled()){
			throw new TemplateNotReadyException();
		}
		StringBuffer result = new StringBuffer();
		for (String aFragment : this.fragments){
			result.append(aFragment);
		}
		preparedScript = result.toString();
	}

	public String getScript() {
		if (preparedScript == null){
			throw new TemplateNotReadyException();
		}
		return preparedScript;
	}

	public String getOutputName() {
		return this.outputName;
	}

}
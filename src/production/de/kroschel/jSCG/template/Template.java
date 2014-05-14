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
 * this class represents the executable template:
 * it consists of both text and script fragments which will form together with the data-item an output-item
 * with the addFragment method either a plain text  or an already executable JS statement may be added.
 * with the prepareScript method these fragments are composed together to form the script.
 *  
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

	/**
	 * depending on the fragment type, the fragment is surrounded by an executionable 
	 * @param aFragmentType 
	 * @param aFragment
	 */
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

	public boolean isFragmentListPresent() {
		return this.fragments != null && !this.fragments.isEmpty();
	}

	
	public String prepareScript(DataItem currentDataItem) {
		if (!isFragmentListPresent()){
			throw new TemplateNotReadyException("No Fragments");
		}
		StringBuffer result = new StringBuffer();
		for (String aFragment : this.fragments){
			result.append(aFragment);
		}
		preparedScript = result.toString();
		return preparedScript;
	}

	public String getScript() {
		if (preparedScript == null){
			throw new TemplateNotReadyException("No Script");
		}
		return preparedScript;
	}

	public String getOutputName() {
		return this.outputName;
	}

}

package de.kroschel.jSCG.template;

import de.kroschel.jSCG.template.InstructionParser.InstructionToken;

public class TemplateInstruction {
	
	InstructionToken token;
	String value; 

	public TemplateInstruction(InstructionToken foundToken) {
		this.token = foundToken;
	}

	public void setRawValue(String substring) {
		this.value = substring;
	}
	
	

}

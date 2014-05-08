package de.kroschel.jSCG.template;

public enum Literal {
	
	beginBlockComment("$\""),
	endBlockComment("\"$"),
	beginLineComment("$'"),
	endLineComment("\n"),
	beginScriptTag("$>"),
	endScriptTag("<$"),
	newLine("\n"),
	singleQuote("'"),
	escapeCharacter("\\"),
	beginInstructionTag("$!"),
	endInstructionTag("!$");
	
	String characterSequence;
	
	Literal(String aSequence){
		this.characterSequence = aSequence;
	}
	
	int length(){
		return this.characterSequence.length();
	}

}

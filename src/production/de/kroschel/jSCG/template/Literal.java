package de.kroschel.jSCG.template;

public enum Literal {
	
	beginBlockComment("$\""),
	endBlockComment("\"$"),
	beginLineComment("$'"),
	endLineComment("\n"),
	beginScript("$>"),
	endScript("<$"),
	newLine("\n"),
	singleQuote("'"),
	escapeCharacter("\\"),
	beginInstruction("$!"),
	endInstruction("!$");
	
	String characterSequence;
	
	Literal(String aSequence){
		this.characterSequence = aSequence;
	}
	
	int length(){
		return this.characterSequence.length();
	}

}

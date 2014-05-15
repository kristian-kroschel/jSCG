package de.kroschel.jSCG.template;

import java.util.ArrayList;

public class InstructionParser {
	
	private int scanposition;
	private String input;
	
	private ArrayList<TemplateInstruction> instructions;
	
	public enum InstructionToken {
		data("data"),
		pragma("pragma"),
		include("include"),
		openingParanthesis("("),
		closingParanthesis(")"),
		semicolon(";");
		
		String literal;
		
		InstructionToken(String aLiteral){
			this.literal = aLiteral;
		}
		
		public String toString(){
			return this.name();
		}
	}

	public ArrayList<TemplateInstruction> parse(String input) throws InstructionParserException {
		initializeParser(input);
		while (scanposition <= this.input.length()){
			scan();
		}
		checkInstructionCompleted();
		return instructions;
	}

	private void checkInstructionCompleted() {
		if (!this.instructionCompleted){
			checkExpectedToken();
		}
	}

	private void initializeParser(String input) {
		this.scanposition = 0;
		this.input = input;
		this.instructions = new ArrayList<TemplateInstruction>();
		setExpectedToken(InstructionToken.data, InstructionToken.pragma, InstructionToken.include);
	}

	private void scan() throws InstructionParserException {
		if (isATokenAtScanPosition()){
			checkExpectedToken();
			consumeToken();
		} else {
			scanposition++;
		}
	}
	
	

	private void checkExpectedToken() throws InstructionParserException {
		boolean found = false;
		StringBuffer expectedTokensMessage = new StringBuffer();
		boolean first = true;
		for (InstructionToken aToken : this.expectedTokens){
			found |= (aToken == foundToken);
			expectedTokensMessage.append(first ? "[" : ",");
			expectedTokensMessage.append(aToken);
			first = false;
		}
		if (!found){
			expectedTokensMessage.append("]");
			throw new InstructionParserException("Unexpected End of Instruction: " + expectedTokensMessage.toString() + " expected",this.scanposition);
		}
		
	}

	private InstructionToken foundToken;
	private InstructionToken[] expectedTokens;
	private TemplateInstruction currentInstruction;
	private int currentValueBegin;
	private int currentValueEnd;
	private boolean watchForExpectedTokens = false;
	private boolean instructionCompleted = false;
	
	private boolean isATokenAtScanPosition(){
		InstructionToken[] searchBase;
		if (watchForExpectedTokens){
			searchBase = expectedTokens;
		} else {
			searchBase = InstructionToken.values();
		}
		for(InstructionToken token : searchBase){
			if (isTokenAtScanPosition(token)){
				foundToken = token;
				return true;
			}
		}
		return false;
	}
	
	private boolean isTokenAtScanPosition(InstructionToken token){
		String observedInputPart;
		boolean result;
		try{
			observedInputPart = this.input.substring(this.scanposition, this.scanposition+token.literal.length());
			result = token.literal.equals(observedInputPart);
		} catch (IndexOutOfBoundsException e){
			result = false;
		}
		return result;
	}
	
	private void consumeToken() {
		switch (this.foundToken){
		case data:
		case pragma:
		case include:
			createNewInstruction();
			setExpectedToken(InstructionToken.openingParanthesis);
			break;
		case openingParanthesis:
			beginValueRecording();
			setExpectedToken(InstructionToken.closingParanthesis);
			break;	
		case closingParanthesis:
			endValueRecording();
			setExpectedToken(InstructionToken.semicolon);
			break;
		case semicolon:
			finalizeInstruction();
			setExpectedToken(InstructionToken.data, InstructionToken.pragma, InstructionToken.include);
		}
		// move the position further:
		this.scanposition = this.scanposition + this.foundToken.literal.length();
	}

	private void createNewInstruction() {
		this.currentInstruction = new TemplateInstruction(this.foundToken);
		this.instructionCompleted  = false;
	}

	private void beginValueRecording() {
		this.currentValueBegin = this.scanposition+1;
		this.watchForExpectedTokens = true;
	}

	private void endValueRecording() {
		this.currentValueEnd = this.scanposition;
		this.watchForExpectedTokens = false;
	}

	private void finalizeInstruction() {
		this.currentInstruction.setRawValue(this.input.substring(this.currentValueBegin, this.currentValueEnd));
		this.instructions.add(currentInstruction);
		this.instructionCompleted = true;
	}

	private void setExpectedToken(InstructionToken... tokens) {
		this.expectedTokens = tokens;
	}

	
	
	
	

}

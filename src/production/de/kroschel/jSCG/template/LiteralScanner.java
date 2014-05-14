package de.kroschel.jSCG.template;

import java.util.ArrayList;

public class LiteralScanner {
	
	ArrayList<LiteralPosition> resultPositions = new ArrayList<LiteralPosition>();
	StringBuilder buffer = new StringBuilder();
	
	Literal lastLiteral;
	Literal literalfound;
	boolean escapeWasRead = false;
	boolean bufferInput = false;
	TemplatePosition literalPosition;
	int linecount = 1, column = 1, scanPosition = 0;
	
	public void scan(char currentChar) {
		
		
		if (isEscapeLiteral(currentChar)){
			// ignore the following char:
			escapeWasRead = true;
		} else {
			if (literalStarts(currentChar) && !escapeWasRead){
				bufferInput = true;
				literalPosition = TemplatePosition.createTemplatePosition(linecount, column, scanPosition);
			}
			if (bufferInput) {
				buffer.append(currentChar);
			}
			if (literalFound()){
				resultPositions.add(createLiteralPosition());
				buffer = new StringBuilder();
				bufferInput = false;
				lastLiteral = literalfound;
				literalfound = null;
			}
			if (escapeWasRead){
				escapeWasRead = false;
			}
		}
		
		column++;
		if (isNewlineLiteral(currentChar)){
			linecount++;
			column = 1;
		}
		scanPosition++;
	}

	private boolean isNewlineLiteral(char currentChar) {
		int newLinePos = Literal.newLine.characterSequence.length()-1;
		return (Literal.newLine.characterSequence.charAt(newLinePos) == currentChar);
	}

	private boolean isEscapeLiteral(char currentChar) {
		return (Literal.escapeCharacter.characterSequence.charAt(0) == currentChar);
	}

	private LiteralPosition createLiteralPosition() {
		LiteralPosition result = new LiteralPosition();
		result.setLiteral(literalfound);
		result.setPosition(literalPosition);
		return result;
	}

	private boolean literalFound() {
		String bufferContent = buffer.toString();
		if (bufferContent.equals(Literal.beginLineComment.characterSequence)){
			literalfound = Literal.beginLineComment;
		}
		if (bufferContent.equals(Literal.endLineComment.characterSequence)){
			literalfound = Literal.endLineComment;
		}
		if (bufferContent.equals(Literal.beginScript.characterSequence)){
			literalfound = Literal.beginScript;
		}
		if (bufferContent.equals(Literal.endScript.characterSequence)){
			literalfound = Literal.endScript;
		}
		return literalfound != null;
	}

	private boolean literalStarts(char currentChar) {
		boolean result = false;
		
		// usually we are looking for beginning tags:
		result =
				(Literal.beginLineComment.characterSequence.charAt(0) == currentChar) ||
				(Literal.beginScript.characterSequence.charAt(0) == currentChar)
		;
		
		// in case the last literal was already the beginning of a line comment or a script we search for their end tags:
		if (lastLiteral != null){
			switch (lastLiteral){
				case beginLineComment:
					result = (Literal.endLineComment.characterSequence.charAt(0) == currentChar);
					break;
				case beginScript:
					result = (Literal.endScript.characterSequence.charAt(0) == currentChar);
					break;
				default:
					// search for begin tags (see above)
			}	
		}
		return result;
	}

	public ArrayList<LiteralPosition> getLiteralPositions() {
		return resultPositions;
	}

}

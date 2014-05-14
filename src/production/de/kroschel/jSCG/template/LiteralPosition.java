package de.kroschel.jSCG.template;

public class LiteralPosition {

    private TemplatePosition position;
    private Literal literal;

	public TemplatePosition getPosition() {
		return position;
	}

	public Literal getLiteral() {
		return literal;
	}

	public void setLiteral(Literal aLiteral) {
		literal = aLiteral;
	}

	public void setPosition(TemplatePosition literalPosition) {
		this.position = literalPosition;
		
	}
	
	public int getLiteralLength(){
		return this.literal.length();
	}

	public TemplatePosition calculateNextBeginPosition() {
		//LiteralPosition retval = new LiteralPosition();
		TemplatePosition beginPosition = new TemplatePosition();
		//retval.setLiteral(this.literal);
		//retval.setPosition(beginPosition);
		// the scanposition is just the offset of the length:
		beginPosition.setScanPosition(this.position.getScanPosition()+this.getLiteralLength());
		
		// if the Literal is the ending of a LineComment, then the line is increased and the column reset to 1.
		// otherwise the column is increased by the literal offset.
		if (this.literal == Literal.endLineComment){
			beginPosition.setLine(this.position.getLine()+1);
			beginPosition.setColumn(1);
		} else {
			beginPosition.setColumn(this.position.getColumn()+this.getLiteralLength());
			beginPosition.setLine(this.position.getLine());
		}
		
		return beginPosition;
	}
	
	public String toString(){
		return this.literal + "-" + this.position.toString();
	}
	

}

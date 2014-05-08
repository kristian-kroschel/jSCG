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

}

package de.kroschel.jSCG.template;

public class InstructionParserException extends RuntimeException {

	private static final long serialVersionUID = -6344562942110481319L;
	public int scanposition;
	
	public InstructionParserException(String message,
			IndexOutOfBoundsException e, int aScanposition) {
		super(message,e);
		this.scanposition = aScanposition; 
	}

	public InstructionParserException(String message, int aScanposition) {
		super(message);
		this.scanposition = aScanposition;
	}

}

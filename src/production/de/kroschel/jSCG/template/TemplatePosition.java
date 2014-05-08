package de.kroschel.jSCG.template;

/**
 * represents a position within a template file
 * @author kristian
 *
 */
public class TemplatePosition {
	
	private int line;
	private int column;
	
	private int scanPosition;

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getScanPosition() {
		return scanPosition;
	}

	public void setScanPosition(int scanPosition) {
		this.scanPosition = scanPosition;
	}

	public void incScanPosition() {
		this.scanPosition++;
	}

	public void incLine() {
		this.line++;
	}

	public void resetColumn() {
		this.column = 0;
	}

	public void incColumn() {
		this.column++;
	}
	
	public String toString(){
		return this.line + "/" + this.column + "(" + this.scanPosition + ")";
	}
	

}

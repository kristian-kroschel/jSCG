package de.kroschel.jSCG.template;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

import de.kroschel.jSCG.data.DataItem;

public class TemplateParser {
	
	private File templateFile;
	private Reader templateReader;
	
	// final result:
	private Template template;
	
	private TemplatePosition currentParsePosition;
	private String currentCharacter;
		
	private String lineseparator;

	// temp. result
	private ArrayList<TemplateFragment> fragments;
	
	public TemplateParser() {
		initLineSeparator();
	}
	
	public TemplateParser(String fileName) {
		this();
		initTemplateFile(fileName);
	}

	private void initTemplateFile(String fileName) {
		this.templateFile = new File(fileName);
		try {
			setInputReader(new FileReader(this.templateFile));
		} catch (FileNotFoundException e) {
			TemplateParserException exeptionThrown = new TemplateParserException(e);
			throw exeptionThrown;
		}
	}

	

	private void initLineSeparator() {
		String lineSep = System.lineSeparator();
		int lineSepLength = lineSep.length();
		this.lineseparator = lineSep.substring(lineSepLength-1);
	}

	public void parseTemplate() {
		this.template = new Template();
		
		try {
			
			initCurrentPosition();
			
			char[] currentChar = new char[1];
			while ( templateReader.read(currentChar,0,1) != -1){
				this.currentCharacter = new String(Character.toString(currentChar[0]));
				System.out.print(currentChar);
				determineParsePosition();
				//System.out.print(":");
				//System.out.print(new Character((char) charRead[0]));
				System.out.print(this.currentParsePosition.toString() + " ");
			}
			
			System.out.println("---");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (templateReader != null) {
				try {
					templateReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
			
		}
		
	}

	private void initCurrentPosition() {
		currentParsePosition = new TemplatePosition();
		currentParsePosition.setColumn(0);
		currentParsePosition.setLine(1);
		currentParsePosition.setScanPosition(0);
	}

	private void determineParsePosition() {
		this.currentParsePosition.incScanPosition();
		this.currentParsePosition.incColumn();
		if (isCurrentCharacterLineSeparator()){
			this.currentParsePosition.incLine();
			this.currentParsePosition.resetColumn();
		}
		
	}
	
	private boolean isCurrentCharacterLineSeparator(){
		boolean res = this.lineseparator.equals(this.currentCharacter);
		return res;
	}

	public Template getTemplate() {
		return this.template;
	}

	public DataItem getInlineData() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setInputReader(Reader aReader) {
		this.templateReader = aReader;
	}

}

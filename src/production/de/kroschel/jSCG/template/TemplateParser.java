package de.kroschel.jSCG.template;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

import de.kroschel.jSCG.data.DataItem;
import de.kroschel.jSCG.template.Template.TemplateFragmentType;

public class TemplateParser {
	
	private File templateFile;
	private Reader templateReader;
	
	// final result:
	private Template template;
	
	private TemplatePosition currentParsePosition;
	
	private char currentCharacter;	
	private char lineseparator;

	// temp. result:
	private StringBuilder templateinput;
	private ArrayList<LiteralPosition> literalpositions;
	private ArrayList<TemplateFragment> fragments;
	// not used:
	//private TemplateFragment currentFragment;
	
	
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
		this.lineseparator = lineSep.charAt(lineSepLength-1);
	}

	public void parseTemplate() {
		
		
		try {
			initialiseParser();
			
			LiteralScanner scanner = scanInput();
			this.literalpositions = scanner.getLiteralPositions();
			
			TokenBuilder tb = new TokenBuilder(this.templateinput.toString(), this.literalpositions);
			//this.fragments = tb.buildFragments(TemplatePosition.createInitialPosition(), this.currentParsePosition);
			this.fragments = tb.buildFragments(TemplatePosition.createInitialPosition(),scanner.getLastTemplatePosition());
						
			buildTemplate();
			
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

	private void buildTemplate() {
		for(TemplateFragment aFragment : this.fragments){
			TemplateFragmentType aFragmentType = null;
			
			if (aFragment instanceof ScriptTemplateFragment){
				aFragmentType = TemplateFragmentType.SCRIPT;
			}
			if (aFragment instanceof TextTemplateFragment){
				aFragmentType = TemplateFragmentType.TEXT;
			}
			if (aFragmentType != null){
				this.template.addTemplateFragment(aFragmentType, aFragment.getContent());
			}
		}
		
		this.template.prepareScript(null);
	}

	private LiteralScanner scanInput() throws IOException {
		LiteralScanner scanner = new LiteralScanner();
		int currentChar;
		while ( (currentChar = templateReader.read()) != -1){
			this.currentCharacter = (char)currentChar;
			determineParsePosition();
			scanner.scan(this.currentCharacter);
			this.templateinput.append(this.currentCharacter);
			// TODO: Logging?
			//System.out.print(this.currentParsePosition.toString() + " " + (char)currentChar);
		}
		return scanner;
	}

	

	private void initialiseParser() {
		this.template = new Template();
		this.templateinput = new StringBuilder();
		this.currentParsePosition = TemplatePosition.createInitialPosition();
	}
	
	
    // TODO: this logic is doubled- see literal scanner
	private void determineParsePosition() {
		this.currentParsePosition.incScanPosition();
		this.currentParsePosition.incColumn();
		if (isCurrentCharacterLineSeparator()){
			this.currentParsePosition.incLine();
			this.currentParsePosition.resetColumn();
		}
		
	}
	
	// doubled in scanner:
	private boolean isCurrentCharacterLineSeparator(){
		boolean res = (this.lineseparator == this.currentCharacter);
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

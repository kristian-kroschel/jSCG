package de.kroschel.jSCG.template;

/**
 * represents a portion of a template, e.g. comments, scripting part, text part
 * 
 * @author kristian
 *
 */
public abstract class TemplateFragment {
	
	private LiteralPosition begin;
	private LiteralPosition end;
	
	private StringBuilder content = new StringBuilder();
	
	public LiteralPosition getBegin() {
		return begin;
	}
	public void setBegin(LiteralPosition begin) {
		this.begin = begin;
	}
	public LiteralPosition getEnd() {
		return end;
	}
	public void setEnd(LiteralPosition end) {
		this.end = end;
	}
	
	public abstract String getStartSequence();
	
	public abstract String getEndSequence();
	
	public abstract boolean isEndReached(String currentCharacter);
	
	public void addCharacter(String currentCharacter) {
		this.content.append(currentCharacter);
	}
	
	public String getContent() {
		return this.content.toString();
	}
	
	public void addContent(String aContent){
		this.content.append(aContent);
	}
	
		
	

}

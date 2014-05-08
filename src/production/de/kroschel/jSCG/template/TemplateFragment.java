package de.kroschel.jSCG.template;

/**
 * represents a portion of a template, e.g. comments, scripting part, text part
 * 
 * @author kristian
 *
 */
public abstract class TemplateFragment {
	
	private TemplatePosition begin;
	private TemplatePosition end;
	
	private StringBuilder content = new StringBuilder();
	
	public TemplatePosition getBegin() {
		return begin;
	}
	public void setBegin(TemplatePosition begin) {
		this.begin = begin;
	}
	public TemplatePosition getEnd() {
		return end;
	}
	public void setEnd(TemplatePosition end) {
		this.end = end;
	}
	
	public abstract String getStartSequence();
	
	public abstract String getEndSequence();
	
	public abstract boolean isEndReached(String currentCharacter);
	
	public void addCharacter(String currentCharacter) {
		this.content.append(currentCharacter);
	}
		
	

}

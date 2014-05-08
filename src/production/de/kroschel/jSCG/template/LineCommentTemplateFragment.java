package de.kroschel.jSCG.template;

public class LineCommentTemplateFragment extends TemplateFragment {

	public String getStartSequence() {
		return "$'";
	}

	public String getEndSequence() {
		return "\n";
	}

	public boolean isEndReached(String currentCharacter) {
		return getEndSequence().equals(currentCharacter);
	}

}

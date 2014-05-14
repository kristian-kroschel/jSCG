package de.kroschel.jSCG.template;

import java.util.ArrayList;

public class TokenBuilder {
	
	private ArrayList<LiteralPosition> literalpositions;
	private ArrayList<TemplateFragment> fragments;
	private TemplateFragment currentFragment;
	private String templateinput;

	public TokenBuilder(String input,
			ArrayList<LiteralPosition> literalPositionsList) {
		this.literalpositions = literalPositionsList;
		this.templateinput = input;
		
		this.fragments = new ArrayList<TemplateFragment>();
		this.currentFragment = new TextTemplateFragment();
	}

	public ArrayList<TemplateFragment> buildFragments(TemplatePosition beginTemplatePosition, TemplatePosition endTemplatePosition) {
		LiteralPosition begin = new LiteralPosition();
		begin.setLiteral(null);
		begin.setPosition(beginTemplatePosition);
		this.currentFragment.setBegin(begin);
		
		for(LiteralPosition lPos : this.literalpositions){
			switch (lPos.getLiteral()){
				case beginScript:
					handleFragmentSwitch(lPos, new ScriptTemplateFragment());
					break;
//				case endScript:
//					handleFragmentSwitch(lPos, new TextTemplateFragment());
//					break;
				case beginLineComment:
					handleFragmentSwitch(lPos,  new LineCommentTemplateFragment());
					break;
				case endLineComment:
					finalizeCurrentFragment(lPos);
					LiteralPosition newPos = beginTextLiteral(lPos);
					initialiseNewFragment(newPos, new TextTemplateFragment());
					break;
				default:
					handleFragmentSwitch(lPos,  new TextTemplateFragment());
					break;
			}
		}
		
		LiteralPosition end = new LiteralPosition();
		end.setLiteral(null);
		end.setPosition(endTemplatePosition );
		
		finalizeCurrentFragment(end);
		
		return this.fragments;
	}
	
	private void handleFragmentSwitch(LiteralPosition lPos, TemplateFragment nextFragment) {
		// handle the end of the last  fragment and save it:
		if (this.currentFragment != null && !(this.currentFragment.getBegin().getPosition().equals(lPos.getPosition()))){
			this.currentFragment.setEnd(lPos);
			this.currentFragment.addContent(this.templateinput.substring(this.currentFragment.getBegin().getPosition().getScanPosition(),this.currentFragment.getEnd().getPosition().getScanPosition()));
			this.fragments.add(currentFragment);
		}
		// init the new fragment:
		this.currentFragment = nextFragment;
		this.currentFragment.setBegin(lPos);
	}
	
	private void finalizeCurrentFragment(LiteralPosition lPos){
			this.currentFragment.setEnd(lPos);
			this.currentFragment.addContent(this.templateinput.substring(this.currentFragment.getBegin().getPosition().getScanPosition(),this.currentFragment.getEnd().getPosition().getScanPosition()));
			this.fragments.add(currentFragment);
	}
	
	private void initialiseNewFragment(LiteralPosition lPos, TemplateFragment nextFragment){
		this.currentFragment = nextFragment;
		this.currentFragment.setBegin(lPos);
	}
	
	private LiteralPosition beginTextLiteral(LiteralPosition endPosition){
		LiteralPosition retval = new LiteralPosition();
		retval.setLiteral(null);
		retval.setPosition(endPosition.calculateNextBeginPosition());
		return retval;
	}


}

/**
 * 
 */
package de.kroschel.jSCG.core;

import java.io.StringWriter;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import de.kroschel.jSCG.core.LogMessage.MessageCategory;
import de.kroschel.jSCG.data.DataItem;
import de.kroschel.jSCG.output.OutputItem;
import de.kroschel.jSCG.template.Template;

/**
 * takes a template together with a data item and produces 
 * the output together with a log-message.
 * 
 *
 * @author kristian
 *
 */
public class Scaffolder {
	
	private DataItem currentDataItem;
	private Template currentTemplate;
	private OutputItem output;
	private ScriptEngine scriptEngine;
	private LogMessage lastLogMessage;

	/**
	 * sets the input data to be processed along with the template
	 * @param aItem
	 */
	public void setDataItem(DataItem aItem) {
		this.currentDataItem = aItem;
	}

	/**
	 * @return the input data
	 */
	public DataItem getDataItem() {
		return this.currentDataItem;
	}
	
	/**
	 * sets the template to be processed
	 * @param aTemplate
	 */

	public void setTemplate(Template aTemplate) {
		this.currentTemplate = aTemplate;
	}

	/**
	 * @return the current template
	 */
	public Template getTemplate() {
		return this.currentTemplate;
	}

	/**
	 * processes the current DataItem and Template
	 * into an output and log-Object
	 */
	public void process() {
		resetOutput();
		if (processingPreConditionsFullfilled()){
			setupScriptEngine(this.currentTemplate);
			this.currentTemplate.prepareScript(this.currentDataItem);
			runScript();
		} else {
			throw new ScaffolderNotInitializedException();
		}
	}

	private void resetOutput() {
		this.output = null;
	}

	private void runScript() {
		StringWriter resultWriter = new StringWriter();
		this.scriptEngine.getContext().setWriter(resultWriter);
		if (dataNodesPresent() ){
			this.scriptEngine.put("JSCG_ROOTNODE", this.currentDataItem.getRoot());
			this.scriptEngine.put("JSCG_TEMPLATE_NAME", this.currentTemplate.getOutputName());
			this.scriptEngine.put("JSCG_DATAITEM_NAME", this.currentDataItem.getOutputName());		
		}
		try{		
			this.scriptEngine.eval(this.currentTemplate.getScript());
		} catch (ScriptException e){
			e.printStackTrace(); //todo: logging
			//throw new TemplateExecutionException("template failure",e);
			System.out.println("Message:      " + e.getMessage());
			System.out.println("FileName:     " + e.getFileName());
			System.out.println("LineNumber:   " + e.getLineNumber());
			System.out.println("ColumnNumber: " + e.getColumnNumber());
		}
		
		createSuccessLogMessage().setReason("RunScriptSuccess");
		
		this.output = new OutputItem();
    	this.output.setText(resultWriter.toString());
	}

	public OutputItem getOutput() {
		return this.output;
	}

	public LogMessage getLog() {
		return this.lastLogMessage; 
	}
	
	private boolean dataNodesPresent(){
		return this.currentDataItem != null;
	}
	
	/**
	 * 
	 * @return true, if preconditions are fullfilled
	 */
	private boolean processingPreConditionsFullfilled(){
		return checkTemplate();

	}
	
	/**
	 * 
	 * @return true, if everything is ok - false otherwise
	 */
	private boolean checkTemplate(){
		if (
			conductCheckFailed((this.currentTemplate == null),"Template Not Set")
		) return false;
		if (
			conductCheckFailed((!this.currentTemplate.isFilled()),"Template Not Filled")
		) return false;
		return true;
		
//		if (this.currentTemplate == null){
//			createNotInitializedLogMessage();
//			this.lastLogMessage.setReason("Template Not Set");
//			return false;
//		}
//		if (!this.currentTemplate.isFilled()){
//			createNotInitializedLogMessage();
//			this.lastLogMessage.setReason("Template Not Filled");
//			return false;
//		}
//		return true;
	}
	
	private boolean conductCheckFailed(boolean conditionFailed, String reason){
		if (conditionFailed){
			createNotInitializedLogMessage();
			this.lastLogMessage.setReason(reason);
		}
		return conditionFailed;
	}
	
	private LogMessage createNotInitializedLogMessage(){
		LogMessage logM = new LogMessage();
		logM.setMessage("Scaffolder Not Initialized");
		logM.setCategory(MessageCategory.error);
		this.lastLogMessage = logM;
		return logM;
	}
	
	private LogMessage createSuccessLogMessage(){
		LogMessage logM = new LogMessage();
		logM.setMessage("Scaffolding processed");
		logM.setCategory(MessageCategory.success);
		this.lastLogMessage = logM;
		return logM;
	} 
	
	private void setupScriptEngine(Template t){
		if (this.scriptEngine == null){
			ScriptEngineManager m = new ScriptEngineManager();
			this.scriptEngine = m.getEngineByName("JavaScript"); // TODO [future Extension] allow other languages than javascript in template
		}
	}

}

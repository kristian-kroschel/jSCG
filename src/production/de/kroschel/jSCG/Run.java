/**
 * 
 */
package de.kroschel.jSCG;

import de.kroschel.jSCG.core.LogMessage;
import de.kroschel.jSCG.core.Scaffolder;
import de.kroschel.jSCG.data.DataItem;
import de.kroschel.jSCG.output.OutputItem;
import de.kroschel.jSCG.template.Template;

/**
 * @author kristian
 *
 */
public class Run {

	private enum Job{
		printUsage, generateFiles
	}
	
	private Job jobToDo;
	
	private final String[] templateFileArgumentName = {"templatefiles","tf"};
	private final String[] dataFileArgumentName = {"datafile","df"};
	
	String templateFiles;
	String datafile;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Run aRun = new Run();
		aRun.init(args);
		aRun.run();
	}
	
	private void init(String[] args){
		this.jobToDo = Job.printUsage;
		parseCommandLine(args);
	}
	
	private void run(){
		
		switch (this.jobToDo){
			case generateFiles:
				generateFiles();
				break;
			case printUsage:
			default:
				printUsage();
				break;
		}
		
	}
	
	private void parseCommandLine(String[] args) {
		if (args!= null){
			CommandlineArguments cla = new CommandlineArguments();
			cla.init(args);
			CommandlineParser clp = new CommandlineParser(cla);
			
			this.templateFiles = clp.extractArgument(templateFileArgumentName);
			this.datafile = clp.extractArgument(dataFileArgumentName);
			
			if ((this.templateFiles != null)){
				this.jobToDo = Job.generateFiles;
			}
		}
	}
	
	private void printUsage(){
		System.out.println("Usage: ...");
	}
	
	private void generateFiles() {
		System.out.println("Generating:");
		System.out.println("Template(s): " + this.templateFiles);
		System.out.println("Datafile:" + this.datafile);
		
		Scaffolder s = new Scaffolder();
		DataItem i = new DataItem();
		Template t = new Template();
		OutputItem o;
		LogMessage m;
		Exception processException = null;
		
		s.setDataItem(i);
		s.setTemplate(t);
		try {
			s.process();
		} catch (Exception e){
			processException = e;
		}
		m = s.getLog();
		o = s.getOutput();
		
		if (processException != null){
			System.out.println("Exception occured!");
			processException.printStackTrace();
		}
		System.out.println("Log-Result:" + m.getMessage());
		System.out.println("Output:" + o.getText());
		
	}

}

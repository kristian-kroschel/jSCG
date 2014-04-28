/**
 * 
 */
package de.kroschel.jSCG;

/**
 * @author kristian
 *
 */
public class Run {

	private enum Job{
		printUsage, generateFiles
	}
	
	private Job jobToDo;
	
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
	}
	
	private void run(){
		switch (this.jobToDo){
			case generateFiles:
				// .. con't here
				break;
			case printUsage:
			default:
				
				break;
		}
		
	}

}

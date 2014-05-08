package de.kroschel.jSCG;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * reads and stores a map of commandline arguments of the form
 * '-name value' or just '-name' (the value is null then)
 * 
 * 
 * @author Kroschel
 */
public class CommandlineArguments {

	/**
	 * holds the arguments
	 * name is stored lowercase without the minus
	 */
	private HashMap<String,String> arguments;
	
	/**
	 * initialises the arguments, giving an array of commandline parameters
	 * @param args
	 */
	public void init(String[] args){
		int arglen = args.length;
		int index = 0;
		if (this.arguments != null){
			this.arguments.clear();			// remove all stored arguments
		} else {
			this.arguments = new HashMap<String, String>();
		}
		while (index < arglen){
			String token = args[index];
			String name = null;
			String value = null; 
			if (token.startsWith("-")){		// we expect an argument name
				name = token.substring(1).toLowerCase(); 	// set the name without the minus: all Lowercase
				try {
					token = args[index+1]; 		// Preview the next token
					if (token.startsWith("-")){
						value = null; 			// again a argumentname: treat it in the next round; the value is null
					} else {
						index++; 				// a value, so update the index
						value = token; 			// and set the value
					}
				} catch (IndexOutOfBoundsException e){
					// no preview possible => value remains null
				}
			}
			this.arguments.put(name, value);// store it
			index++; 						// go ahead
		}
	}
	
	public ArrayList<String> getArgumentNames(){
		ArrayList<String> retval = new ArrayList<String>();
		retval.addAll(this.arguments.keySet());
		return retval;
	}
	
	
	/**
	 * returns the value of a argument
	 * @param name
	 * @return null, if the option is not contained in the list or has the value null
	 */
	public String getArgumentValue(String name){
		String retval = null;
		if (name != null){
			String lcname = name.toLowerCase();
			if (this.arguments.containsKey(lcname)){
				retval = this.arguments.get(lcname);
			}
		}
		return retval;
	}
	
	/**
	 * 
	 * @param name
	 * @return true , if the option is set
	 */
	public boolean isArgumentSet(String name){
		boolean retval = false;
		if (name != null){
			String lcname = name.toLowerCase();
			retval = this.arguments.containsKey(lcname);
		}
		return retval;
	}
}

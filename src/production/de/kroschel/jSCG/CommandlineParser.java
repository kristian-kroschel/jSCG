package de.kroschel.jSCG;

public class CommandlineParser {

	CommandlineArguments commandlineArgs;
	
	
	public CommandlineParser(CommandlineArguments cla) {
		this.commandlineArgs = cla;
	}

	public String extractArgument(String... variants) {
		String retval = null;
		
		for (int i = 0; i<variants.length; i++){
			String value = commandlineArgs.getArgumentValue(variants[i]);
			if (value != null){
				retval = value;
			}
		}
		return retval;
	}

}

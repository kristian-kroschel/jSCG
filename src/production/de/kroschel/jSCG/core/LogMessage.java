package de.kroschel.jSCG.core;

/**
 * contains a message
 * @author kristian
 *
 */
public class LogMessage {
	
	public enum MessageCategory{
		info,warning,success,error
	}
	
	private String message;
	private String reason;
	private MessageCategory category;
	

	public String getMessage() {
		// TODO 
		return message;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MessageCategory getCategory() {
		return category;
	}

	public void setCategory(MessageCategory category) {
		this.category = category;
	}
	
	
	

}

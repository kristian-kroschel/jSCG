/**
 * 
 */
package de.kroschel.jSCG.template;

/**
 * @author kristian
 *
 */
public class TemplateNotReadyException extends RuntimeException {

	public TemplateNotReadyException(String reason) {
		super(reason);
	}

	private static final long serialVersionUID = -8541662240521402434L;

}

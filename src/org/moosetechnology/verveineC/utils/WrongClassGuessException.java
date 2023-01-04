package org.moosetechnology.verveineC.utils;

import org.moosetechnology.famix.famixtraits.TNamedEntity;
import org.moosetechnology.famix.famixtraits.TSourceEntity;

/**
 * An exception to flag entities wrongly typed during parsing (e.g. a Namespace that was expected as a Class.
 * It often happens because the entity was first created without really knowing what it is.
 * 
 * Class also defines a static method to log such errors 
 */
public class WrongClassGuessException extends Exception {
	private static final long serialVersionUID = 1L;

	public WrongClassGuessException(String message) {
		super(message);
	}

	static public <T extends TNamedEntity> void reportWrongClassGuess(Class<T> clazz, TNamedEntity found) {
		System.err.print("Exception: wrong guessed type '"
				+ found.getClass().getSimpleName()
				+ "' instead of expected type '"
				+ clazz.getSimpleName()
				+ "' for entity `"
				+ found.getName()
				+ "'");
		if ( (found instanceof TSourceEntity) && (((TSourceEntity)found).getSourceAnchor() != null) ) {
			System.err.println(" @" + ((TSourceEntity)found).getSourceAnchor().toString());
		}
		else {
			System.err.println();
		}
		
		new Throwable().printStackTrace();
	}
 
}

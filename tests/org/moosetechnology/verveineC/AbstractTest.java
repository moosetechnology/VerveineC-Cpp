package org.moosetechnology.verveineC;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import eu.synectique.verveine.core.gen.famix.Entity;
import eu.synectique.verveine.core.gen.famix.NamedEntity;
import fr.verveine.plugin.VerveineCParser;
import ch.akuhn.fame.Repository;

/**
 * Essentially implements utility methods used in all tests
 */
public class AbstractTest {

	protected static VerveineCParser parser;
	protected static Repository repo;

	/** All Entity of class <code>clazz</code> in the repository of the parser
	 */
    protected <T extends Entity> Collection<T> entitiesOfType(Class<T> clazz) {
        return repo.all(clazz);
    }

	/** All NamedEntity with the right name
	 */
	protected Collection<NamedEntity> entitiesNamed( String name) {
        return entitiesNamed( NamedEntity.class, name);
    }

	/** All NamedEntity of class <code>clazz</code> with the right name
	 */
    protected <T extends NamedEntity> Collection<T> entitiesNamed( Class<T> clazz, String name) {
        List<T> ret = new LinkedList<>();

        for (T fmx : entitiesOfType(clazz)) {
            if (fmx.getName().equals(name)) {
                ret.add(fmx);
            }
        }

        return ret;
    }

	/** First NamedEntity of class <code>clazz</code> with the right name
	 */
    protected <T extends NamedEntity> T entityNamed(Class<T> clazz, String name) {
        for (T fmx : entitiesOfType(clazz)) {
            if (fmx.getName().equals(name)) {
                return fmx;
            }
        }
    	
    	return null;
    }

	/** First Object in given collection or <code>null</code> if the collection is empty
	 */
   protected <T> T first(Collection<T> collection) {
    	Iterator<T> iter = collection.iterator();
    	if (iter.hasNext()) {
    		return iter.next();
    	}
    	else {
    		return null;
    	}
    }
    
	public static void newParser() {
		parser = new VerveineCParser();
		repo = parser.getFamixRepo();
	}

}

// Automagically generated code, please do not change
package org.moosetechnology.famix.moosequery;

import ch.akuhn.fame.MetaRepository;

public class MooseQueryModel {

    public static MetaRepository metamodel() {
        MetaRepository metamodel = new MetaRepository();
        importInto(metamodel);
        return metamodel;
    }
    
    public static void importInto(MetaRepository metamodel) {
		metamodel.with(org.moosetechnology.famix.moosequery.TAssociationMetaLevelDependency.class);
		metamodel.with(org.moosetechnology.famix.moosequery.TEntityMetaLevelDependency.class);

    }

}


// Automagically generated code, please do not change
package eu.synectique.verveine.core.gen.moose;

import ch.akuhn.fame.MetaRepository;

public class MooseModel {

    public static MetaRepository metamodel() {
        MetaRepository metamodel = new MetaRepository();
        importInto(metamodel);
        return metamodel;
    }
    
    public static void importInto(MetaRepository metamodel) {
		metamodel.with(eu.synectique.verveine.core.gen.moose.PropertyGroup.class);
		metamodel.with(eu.synectique.verveine.core.gen.moose.Entity.class);
		metamodel.with(eu.synectique.verveine.core.gen.moose.Model.class);
		metamodel.with(eu.synectique.verveine.core.gen.moose.AbstractGroup.class);
		metamodel.with(eu.synectique.verveine.core.gen.moose.Group.class);

    }

}


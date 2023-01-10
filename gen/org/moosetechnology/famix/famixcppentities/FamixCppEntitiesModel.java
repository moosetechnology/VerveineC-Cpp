// Automagically generated code, please do not change
package org.moosetechnology.famix.famixcppentities;

import ch.akuhn.fame.MetaRepository;

public class FamixCppEntitiesModel {

    public static MetaRepository metamodel() {
        MetaRepository metamodel = new MetaRepository();
        importInto(metamodel);
        return metamodel;
    }
    
    public static void importInto(MetaRepository metamodel) {
		metamodel.with(org.moosetechnology.famix.famixcppentities.Class.class);
		metamodel.with(org.moosetechnology.famix.famixcppentities.Comment.class);
		metamodel.with(org.moosetechnology.famix.famixcppentities.Entity.class);
		metamodel.with(org.moosetechnology.famix.famixcppentities.FamixCppModel.class);
		metamodel.with(org.moosetechnology.famix.famixcppentities.ImplicitVariable.class);
		metamodel.with(org.moosetechnology.famix.famixcppentities.Inheritance.class);
		metamodel.with(org.moosetechnology.famix.famixcppentities.Method.class);
		metamodel.with(org.moosetechnology.famix.famixcppentities.NamedEntity.class);
		metamodel.with(org.moosetechnology.famix.famixcppentities.OOInvocation.class);
		metamodel.with(org.moosetechnology.famix.famixcppentities.ParameterType.class);
		metamodel.with(org.moosetechnology.famix.famixcppentities.ParameterizableClass.class);
		metamodel.with(org.moosetechnology.famix.famixcppentities.ParameterizedType.class);
		metamodel.with(org.moosetechnology.famix.famixcppentities.SourceAnchor.class);
		metamodel.with(org.moosetechnology.famix.famixcppentities.SourceLanguage.class);
		metamodel.with(org.moosetechnology.famix.famixcppentities.SourceTextAnchor.class);
		metamodel.with(org.moosetechnology.famix.famixcppentities.SourcedEntity.class);
		metamodel.with(org.moosetechnology.famix.famixcppentities.UnknownSourceLanguage.class);

    }

}


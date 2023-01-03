// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixcppentities;

import ch.akuhn.fame.MetaRepository;

public class FamixCppEntitiesModel {

    public static MetaRepository metamodel() {
        MetaRepository metamodel = new MetaRepository();
        importInto(metamodel);
        return metamodel;
    }
    
    public static void importInto(MetaRepository metamodel) {
		metamodel.with(org.moosetechnology.model.famix.famixcppentities.Class.class);
		metamodel.with(org.moosetechnology.model.famix.famixcppentities.Comment.class);
		metamodel.with(org.moosetechnology.model.famix.famixcppentities.Entity.class);
		metamodel.with(org.moosetechnology.model.famix.famixcppentities.FamixCppModel.class);
		metamodel.with(org.moosetechnology.model.famix.famixcppentities.ImplicitVar.class);
		metamodel.with(org.moosetechnology.model.famix.famixcppentities.Inheritance.class);
		metamodel.with(org.moosetechnology.model.famix.famixcppentities.Method.class);
		metamodel.with(org.moosetechnology.model.famix.famixcppentities.NamedEntity.class);
		metamodel.with(org.moosetechnology.model.famix.famixcppentities.Namespace.class);
		metamodel.with(org.moosetechnology.model.famix.famixcppentities.OOInvocation.class);
		metamodel.with(org.moosetechnology.model.famix.famixcppentities.SourceAnchor.class);
		metamodel.with(org.moosetechnology.model.famix.famixcppentities.SourceLanguage.class);
		metamodel.with(org.moosetechnology.model.famix.famixcppentities.SourceTextAnchor.class);
		metamodel.with(org.moosetechnology.model.famix.famixcppentities.SourcedEntity.class);
		metamodel.with(org.moosetechnology.model.famix.famixcppentities.UnknownSourceLanguage.class);

    }

}


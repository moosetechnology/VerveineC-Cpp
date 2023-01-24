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
		metamodel.with(org.moosetechnology.famix.famixcppentities.Entity.class);
		metamodel.with(org.moosetechnology.famix.famixcppentities.FamixCppModel.class);
		metamodel.with(org.moosetechnology.famix.famixcppentities.ImplicitVariable.class);
		metamodel.with(org.moosetechnology.famix.famixcppentities.Inheritance.class);
		metamodel.with(org.moosetechnology.famix.famixcppentities.Method.class);
		metamodel.with(org.moosetechnology.famix.famixcppentities.Namespace.class);
		metamodel.with(org.moosetechnology.famix.famixcppentities.OOInvocation.class);
		metamodel.with(org.moosetechnology.famix.famixcppentities.TemplateParameterType.class);
		metamodel.with(org.moosetechnology.famix.famixcppentities.TemplateClass.class);
		metamodel.with(org.moosetechnology.famix.famixcppentities.TemplateInstanciationType.class);
		metamodel.with(org.moosetechnology.famix.famixcppentities.SourceLanguage.class);

    }

}


// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixcpreprocentities;

import ch.akuhn.fame.MetaRepository;

public class FamixCPreprocEntitiesModel {

    public static MetaRepository metamodel() {
        MetaRepository metamodel = new MetaRepository();
        importInto(metamodel);
        return metamodel;
    }
    
    public static void importInto(MetaRepository metamodel) {
		metamodel.with(org.moosetechnology.model.famix.famixcpreprocentities.CFile.class);
		metamodel.with(org.moosetechnology.model.famix.famixcpreprocentities.CompilUnit.class);
		metamodel.with(org.moosetechnology.model.famix.famixcpreprocentities.Entity.class);
		metamodel.with(org.moosetechnology.model.famix.famixcpreprocentities.FamixCPreprocModel.class);
		metamodel.with(org.moosetechnology.model.famix.famixcpreprocentities.Folder.class);
		metamodel.with(org.moosetechnology.model.famix.famixcpreprocentities.Header.class);
		metamodel.with(org.moosetechnology.model.famix.famixcpreprocentities.Include.class);
		metamodel.with(org.moosetechnology.model.famix.famixcpreprocentities.PreprocDefine.class);
		metamodel.with(org.moosetechnology.model.famix.famixcpreprocentities.PreprocIfdef.class);
		metamodel.with(org.moosetechnology.model.famix.famixcpreprocentities.PreprocStatement.class);

    }

}


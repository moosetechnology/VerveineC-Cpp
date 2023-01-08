// Automagically generated code, please do not change
package org.moosetechnology.famix.famixcpreprocentities;

import ch.akuhn.fame.MetaRepository;

public class FamixCPreprocEntitiesModel {

    public static MetaRepository metamodel() {
        MetaRepository metamodel = new MetaRepository();
        importInto(metamodel);
        return metamodel;
    }
    
    public static void importInto(MetaRepository metamodel) {
		metamodel.with(org.moosetechnology.famix.famixcpreprocentities.CFile.class);
		metamodel.with(org.moosetechnology.famix.famixcpreprocentities.CompilationUnit.class);
		metamodel.with(org.moosetechnology.famix.famixcpreprocentities.DiskEntity.class);
		metamodel.with(org.moosetechnology.famix.famixcpreprocentities.Entity.class);
		metamodel.with(org.moosetechnology.famix.famixcpreprocentities.FamixCPreprocModel.class);
		metamodel.with(org.moosetechnology.famix.famixcpreprocentities.Folder.class);
		metamodel.with(org.moosetechnology.famix.famixcpreprocentities.HeaderFile.class);
		metamodel.with(org.moosetechnology.famix.famixcpreprocentities.Include.class);
		metamodel.with(org.moosetechnology.famix.famixcpreprocentities.PreprocessorDefine.class);
		metamodel.with(org.moosetechnology.famix.famixcpreprocentities.PreprocessorIfdef.class);
		metamodel.with(org.moosetechnology.famix.famixcpreprocentities.PreprocessorStatement.class);

    }

}


// Automagically generated code, please do not change
package org.moosetechnology.famix.file;

import ch.akuhn.fame.MetaRepository;

public class FILEModel {

    public static MetaRepository metamodel() {
        MetaRepository metamodel = new MetaRepository();
        importInto(metamodel);
        return metamodel;
    }
    
    public static void importInto(MetaRepository metamodel) {
		metamodel.with(org.moosetechnology.famix.file.FileGroup.class);
		metamodel.with(org.moosetechnology.famix.file.AbstractFile.class);
		metamodel.with(org.moosetechnology.famix.file.Folder.class);
		metamodel.with(org.moosetechnology.famix.file.File.class);
		metamodel.with(org.moosetechnology.famix.file.FolderGroup.class);

    }

}


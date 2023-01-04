// Automagically generated code, please do not change
package eu.synectique.verveine.core.gen.file;

import ch.akuhn.fame.MetaRepository;

public class FILEModel {

    public static MetaRepository metamodel() {
        MetaRepository metamodel = new MetaRepository();
        importInto(metamodel);
        return metamodel;
    }
    
    public static void importInto(MetaRepository metamodel) {
		metamodel.with(eu.synectique.verveine.core.gen.file.FileGroup.class);
		metamodel.with(eu.synectique.verveine.core.gen.file.AbstractFile.class);
		metamodel.with(eu.synectique.verveine.core.gen.file.Folder.class);
		metamodel.with(eu.synectique.verveine.core.gen.file.File.class);
		metamodel.with(eu.synectique.verveine.core.gen.file.FolderGroup.class);

    }

}


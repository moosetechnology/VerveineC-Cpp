// Automagically generated code, please do not change
package org.moosetechnology.famix.cpp;

import ch.akuhn.fame.FameProperty;

import org.moosetechnology.famix.file.File;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;


@FamePackage("FAMIX")
@FameDescription("AbstractFileAnchor")
public class AbstractFileAnchor extends SourceAnchor {



    private String fileName;
    
    @FameProperty(name = "fileName")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    private File correspondingFile;
    
    @FameProperty(name = "correspondingFile")
    public File getCorrespondingFile() {
        return correspondingFile;
    }

    public void setCorrespondingFile(File correspondingFile) {
        this.correspondingFile = correspondingFile;
    }
    


}


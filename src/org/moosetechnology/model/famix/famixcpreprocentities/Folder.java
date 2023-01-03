// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixcpreprocentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;


@FamePackage("Famix-CPreproc-Entities")
@FameDescription("Folder")
public class Folder extends CFile  {

    private Collection<CFile> files; 



    @FameProperty(name = "files", opposite = "parentFolder", derived = true)
    public Collection<CFile> getFiles() {
        if (files == null) {
            files = new MultivalueSet<CFile>() {
                @Override
                protected void clearOpposite(CFile e) {
                    e.setParentFolder(null);
                }
                @Override
                protected void setOpposite(CFile e) {
                    e.setParentFolder(Folder.this);
                }
            };
        }
        return files;
    }
    
    public void setFiles(Collection<? extends CFile> files) {
        this.getFiles().clear();
        this.getFiles().addAll(files);
    }                    
    
        
    public void addFiles(CFile one) {
        this.getFiles().add(one);
    }   
    
    public void addFiles(CFile one, CFile... many) {
        this.getFiles().add(one);
        for (CFile each : many)
            this.getFiles().add(each);
    }   
    
    public void addFiles(Iterable<? extends CFile> many) {
        for (CFile each : many)
            this.getFiles().add(each);
    }   
                
    public void addFiles(CFile[] many) {
        for (CFile each : many)
            this.getFiles().add(each);
    }
    
    public int numberOfFiles() {
        return getFiles().size();
    }

    public boolean hasFiles() {
        return !getFiles().isEmpty();
    }



}


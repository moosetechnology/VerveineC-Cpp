// Automagically generated code, please do not change
package org.moosetechnology.famix.famixcpreprocentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.famix.famixtraits.TNamedEntity;


@FamePackage("Famix-CPreproc-Entities")
@FameDescription("DiskEntity")
public class DiskEntity extends Entity implements TNamedEntity {

    private Folder parentFolder;
    
    private String name;
    


    @FameProperty(name = "parentFolder", opposite = "children", container = true)
    public Folder getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(Folder parentFolder) {
        if (this.parentFolder != null) {
            if (this.parentFolder.equals(parentFolder)) return;
            this.parentFolder.getChildren().remove(this);
        }
        this.parentFolder = parentFolder;
        if (parentFolder == null) return;
        parentFolder.getChildren().add(this);
    }
    
    @FameProperty(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    


}


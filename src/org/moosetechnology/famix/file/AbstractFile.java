// Automagically generated code, please do not change
package org.moosetechnology.famix.file;

import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;

import java.util.Collection;

import org.moosetechnology.famix.cpp.Entity;
import org.moosetechnology.famix.cpp.SourcedEntity;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;


@FamePackage("FILE")
@FameDescription("AbstractFile")
public class AbstractFile extends Entity {



    private Folder parentFolder;
    
    @FameProperty(name = "parentFolder", opposite = "childrenFileSystemEntities", container = true)
    public Folder getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(Folder parentFolder) {
        if (this.parentFolder != null) {
            if (this.parentFolder.equals(parentFolder)) return;
            this.parentFolder.getChildrenFileSystemEntities().remove(this);
        }
        this.parentFolder = parentFolder;
        if (parentFolder == null) return;
        parentFolder.getChildrenFileSystemEntities().add(this);
    }
    
    @FameProperty(name = "numberOfLinesOfText", derived = true)
    public Number getNumberOfLinesOfText() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    private String name;
    
    @FameProperty(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Collection<SourcedEntity> entities;

    @FameProperty(name = "entities", opposite = "containerFiles", derived = true)
	public Collection<SourcedEntity> getEntities() {
        if (entities == null) {
        	entities = new MultivalueSet<SourcedEntity>() {
                @Override
                protected void clearOpposite(SourcedEntity e) {
                    e.setContainerFiles(null);
                }
                @Override
                protected void setOpposite(SourcedEntity e) {
                    e.addContainerFiles(AbstractFile.this);
                }
            };
        }
        return entities;
	}
    


}


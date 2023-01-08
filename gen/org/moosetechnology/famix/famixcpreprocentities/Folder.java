// Automagically generated code, please do not change
package org.moosetechnology.famix.famixcpreprocentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;


@FamePackage("Famix-CPreproc-Entities")
@FameDescription("Folder")
public class Folder extends DiskEntity  {

    private Collection<DiskEntity> children; 



    @FameProperty(name = "children", opposite = "parentFolder", derived = true)
    public Collection<DiskEntity> getChildren() {
        if (children == null) {
            children = new MultivalueSet<DiskEntity>() {
                @Override
                protected void clearOpposite(DiskEntity e) {
                    e.setParentFolder(null);
                }
                @Override
                protected void setOpposite(DiskEntity e) {
                    e.setParentFolder(Folder.this);
                }
            };
        }
        return children;
    }
    
    public void setChildren(Collection<? extends DiskEntity> children) {
        this.getChildren().clear();
        this.getChildren().addAll(children);
    }                    
    
        
    public void addChildren(DiskEntity one) {
        this.getChildren().add(one);
    }   
    
    public void addChildren(DiskEntity one, DiskEntity... many) {
        this.getChildren().add(one);
        for (DiskEntity each : many)
            this.getChildren().add(each);
    }   
    
    public void addChildren(Iterable<? extends DiskEntity> many) {
        for (DiskEntity each : many)
            this.getChildren().add(each);
    }   
                
    public void addChildren(DiskEntity[] many) {
        for (DiskEntity each : many)
            this.getChildren().add(each);
    }
    
    public int numberOfChildren() {
        return getChildren().size();
    }

    public boolean hasChildren() {
        return !getChildren().isEmpty();
    }



}


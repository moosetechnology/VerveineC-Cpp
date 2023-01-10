// Automagically generated code, please do not change
package org.moosetechnology.famix.famixcppentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;
import org.moosetechnology.famix.famixcentities.ContainerEntity;


@FamePackage("Famix-Cpp-Entities")
@FameDescription("Namespace")
public class Namespace extends ContainerEntity  {

    private Collection<Namespace> childrenNamespaces; 

    private Namespace parentNamespace;
    


    @FameProperty(name = "childrenNamespaces", opposite = "parentNamespace", derived = true)
    public Collection<Namespace> getChildrenNamespaces() {
        if (childrenNamespaces == null) {
            childrenNamespaces = new MultivalueSet<Namespace>() {
                @Override
                protected void clearOpposite(Namespace e) {
                    e.setParentNamespace(null);
                }
                @Override
                protected void setOpposite(Namespace e) {
                    e.setParentNamespace(Namespace.this);
                }
            };
        }
        return childrenNamespaces;
    }
    
    public void setChildrenNamespaces(Collection<? extends Namespace> childrenNamespaces) {
        this.getChildrenNamespaces().clear();
        this.getChildrenNamespaces().addAll(childrenNamespaces);
    }                    
    
        
    public void addChildrenNamespaces(Namespace one) {
        this.getChildrenNamespaces().add(one);
    }   
    
    public void addChildrenNamespaces(Namespace one, Namespace... many) {
        this.getChildrenNamespaces().add(one);
        for (Namespace each : many)
            this.getChildrenNamespaces().add(each);
    }   
    
    public void addChildrenNamespaces(Iterable<? extends Namespace> many) {
        for (Namespace each : many)
            this.getChildrenNamespaces().add(each);
    }   
                
    public void addChildrenNamespaces(Namespace[] many) {
        for (Namespace each : many)
            this.getChildrenNamespaces().add(each);
    }
    
    public int numberOfChildrenNamespaces() {
        return getChildrenNamespaces().size();
    }

    public boolean hasChildrenNamespaces() {
        return !getChildrenNamespaces().isEmpty();
    }

    @FameProperty(name = "parentNamespace", opposite = "childrenNamespaces", container = true)
    public Namespace getParentNamespace() {
        return parentNamespace;
    }

    public void setParentNamespace(Namespace parentNamespace) {
        if (this.parentNamespace != null) {
            if (this.parentNamespace.equals(parentNamespace)) return;
            this.parentNamespace.getChildrenNamespaces().remove(this);
        }
        this.parentNamespace = parentNamespace;
        if (parentNamespace == null) return;
        parentNamespace.getChildrenNamespaces().add(this);
    }
    


}


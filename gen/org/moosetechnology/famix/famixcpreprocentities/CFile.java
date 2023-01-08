// Automagically generated code, please do not change
package org.moosetechnology.famix.famixcpreprocentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;
import org.moosetechnology.famix.moosequery.TEntityMetaLevelDependency;


@FamePackage("Famix-CPreproc-Entities")
@FameDescription("CFile")
public class CFile extends DiskEntity implements TEntityMetaLevelDependency {

    private Collection<Include> includes; 

    private Collection<Include> inclusion; 



    @FameProperty(name = "includes", opposite = "includedBy", derived = true)
    public Collection<Include> getIncludes() {
        if (includes == null) {
            includes = new MultivalueSet<Include>() {
                @Override
                protected void clearOpposite(Include e) {
                    e.setIncludedBy(null);
                }
                @Override
                protected void setOpposite(Include e) {
                    e.setIncludedBy(CFile.this);
                }
            };
        }
        return includes;
    }
    
    public void setIncludes(Collection<? extends Include> includes) {
        this.getIncludes().clear();
        this.getIncludes().addAll(includes);
    }                    
    
        
    public void addIncludes(Include one) {
        this.getIncludes().add(one);
    }   
    
    public void addIncludes(Include one, Include... many) {
        this.getIncludes().add(one);
        for (Include each : many)
            this.getIncludes().add(each);
    }   
    
    public void addIncludes(Iterable<? extends Include> many) {
        for (Include each : many)
            this.getIncludes().add(each);
    }   
                
    public void addIncludes(Include[] many) {
        for (Include each : many)
            this.getIncludes().add(each);
    }
    
    public int numberOfIncludes() {
        return getIncludes().size();
    }

    public boolean hasIncludes() {
        return !getIncludes().isEmpty();
    }

    @FameProperty(name = "inclusion", opposite = "included", derived = true)
    public Collection<Include> getInclusion() {
        if (inclusion == null) {
            inclusion = new MultivalueSet<Include>() {
                @Override
                protected void clearOpposite(Include e) {
                    e.setIncluded(null);
                }
                @Override
                protected void setOpposite(Include e) {
                    e.setIncluded(CFile.this);
                }
            };
        }
        return inclusion;
    }
    
    public void setInclusion(Collection<? extends Include> inclusion) {
        this.getInclusion().clear();
        this.getInclusion().addAll(inclusion);
    }                    
    
        
    public void addInclusion(Include one) {
        this.getInclusion().add(one);
    }   
    
    public void addInclusion(Include one, Include... many) {
        this.getInclusion().add(one);
        for (Include each : many)
            this.getInclusion().add(each);
    }   
    
    public void addInclusion(Iterable<? extends Include> many) {
        for (Include each : many)
            this.getInclusion().add(each);
    }   
                
    public void addInclusion(Include[] many) {
        for (Include each : many)
            this.getInclusion().add(each);
    }
    
    public int numberOfInclusion() {
        return getInclusion().size();
    }

    public boolean hasInclusion() {
        return !getInclusion().isEmpty();
    }

    @FameProperty(name = "fanIn", derived = true)
    public Number getFanIn() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "fanOut", derived = true)
    public Number getFanOut() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "isDead", derived = true)
    public Boolean getIsDead() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfChildren", derived = true)
    public Number getNumberOfChildren() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfDeadChildren", derived = true)
    public Number getNumberOfDeadChildren() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfExternalClients", derived = true)
    public Number getNumberOfExternalClients() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfExternalProviders", derived = true)
    public Number getNumberOfExternalProviders() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfInternalClients", derived = true)
    public Number getNumberOfInternalClients() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfInternalProviders", derived = true)
    public Number getNumberOfInternalProviders() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    


}


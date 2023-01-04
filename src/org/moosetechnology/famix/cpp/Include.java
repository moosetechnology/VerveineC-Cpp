// Automagically generated code, please do not change
package org.moosetechnology.famix.cpp;

import ch.akuhn.fame.FameProperty;

import org.moosetechnology.famix.cpp.Association;
import org.moosetechnology.famix.cpp.CFile;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;


@FamePackage("FAMIX")
@FameDescription("Include")
public class Include extends Association {



    private CFile source;
    
    @FameProperty(name = "source", opposite = "outgoingIncludeRelations")
    public CFile getSource() {
        return source;
    }

    public void setSource(CFile source) {
        if (this.source != null) {
            if (this.source.equals(source)) return;
            this.source.getOutgoingIncludeRelations().remove(this);
        }
        this.source = source;
        if (source == null) return;
        source.getOutgoingIncludeRelations().add(this);
    }
    
    private CFile target;
    
    @FameProperty(name = "target", opposite = "incomingIncludeRelations")
    public CFile getTarget() {
        return target;
    }

    public void setTarget(CFile target) {
        if (this.target != null) {
            if (this.target.equals(target)) return;
            this.target.getIncomingIncludeRelations().remove(this);
        }
        this.target = target;
        if (target == null) return;
        target.getIncomingIncludeRelations().add(this);
    }
    


}


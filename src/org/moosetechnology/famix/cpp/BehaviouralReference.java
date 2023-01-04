// Automagically generated code, please do not change
package org.moosetechnology.famix.cpp;

import ch.akuhn.fame.FameProperty;

import org.moosetechnology.famix.cpp.Association;
import org.moosetechnology.famix.cpp.BehaviouralEntity;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;


@FamePackage("FAMIX")
@FameDescription("BehaviouralReference")
public class BehaviouralReference extends Association {



    private BehaviouralEntity pointed;
    
    @FameProperty(name = "pointed", opposite = "references")
    public BehaviouralEntity getPointed() {
        return pointed;
    }

    public void setPointed(BehaviouralEntity pointed) {
        if (this.pointed != null) {
            if (this.pointed.equals(pointed)) return;
            this.pointed.getReferences().remove(this);
        }
        this.pointed = pointed;
        if (pointed == null) return;
        pointed.getReferences().add(this);
    }
    
    private BehaviouralEntity referer;
    
    @FameProperty(name = "referer", opposite = "behaviouralPointers")
    public BehaviouralEntity getReferer() {
        return referer;
    }

    public void setReferer(BehaviouralEntity referer) {
        if (this.referer != null) {
            if (this.referer.equals(referer)) return;
            this.referer.getBehaviouralPointers().remove(this);
        }
        this.referer = referer;
        if (referer == null) return;
        referer.getBehaviouralPointers().add(this);
    }
    


}


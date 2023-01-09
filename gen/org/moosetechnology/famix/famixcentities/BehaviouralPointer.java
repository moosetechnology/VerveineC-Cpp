// Automagically generated code, please do not change
package org.moosetechnology.famix.famixcentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-C-Entities")
@FameDescription("BehaviouralPointer")
public class BehaviouralPointer extends Association  {

    private BehaviouralEntity behaviouralPointed;
    
    private BehaviouralEntity referer;
    


    @FameProperty(name = "behaviouralPointed", opposite = "behaviouralAddressers")
    public BehaviouralEntity getBehaviouralPointed() {
        return behaviouralPointed;
    }

    public void setBehaviouralPointed(BehaviouralEntity behaviouralPointed) {
        if (this.behaviouralPointed != null) {
            if (this.behaviouralPointed.equals(behaviouralPointed)) return;
            this.behaviouralPointed.getBehaviouralAddressers().remove(this);
        }
        this.behaviouralPointed = behaviouralPointed;
        if (behaviouralPointed == null) return;
        behaviouralPointed.getBehaviouralAddressers().add(this);
    }
    
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


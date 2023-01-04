// Automagically generated code, please do not change
package org.moosetechnology.famix.cpp;

import ch.akuhn.fame.FameProperty;

import org.moosetechnology.famix.cpp.Entity;
import org.moosetechnology.famix.cpp.SourcedEntity;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;


@FamePackage("FAMIX")
@FameDescription("SourceAnchor")
public class SourceAnchor extends Entity {



    @FameProperty(name = "lineCount", derived = true)
    public Number getLineCount() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    private SourcedEntity element;
    
    @FameProperty(name = "element", opposite = "sourceAnchor", container = true)
    public SourcedEntity getElement() {
        return element;
    }

    public void setElement(SourcedEntity element) {
        if (this.element == null ? element != null : !this.element.equals(element)) {
            SourcedEntity old_element = this.element;
            this.element = element;
            if (old_element != null) old_element.setSourceAnchor(null);
            if (element != null) element.setSourceAnchor(this);
        }
    }
    


}


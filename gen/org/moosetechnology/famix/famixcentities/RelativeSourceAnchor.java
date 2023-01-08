// Automagically generated code, please do not change
package org.moosetechnology.famix.famixcentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.famix.famixtraits.TRelativeSourceAnchor;
import org.moosetechnology.famix.famixtraits.TSourceAnchor;
import org.moosetechnology.famix.famixtraits.TSourceEntity;


@FamePackage("Famix-C-Entities")
@FameDescription("RelativeSourceAnchor")
public class RelativeSourceAnchor extends Entity implements TRelativeSourceAnchor, TSourceAnchor {

    private TSourceEntity element;
    
    private Number endPos;
    
    private TSourceAnchor relatedAnchor;
    
    private Number startPos;
    


    @FameProperty(name = "element", opposite = "sourceAnchor")
    public TSourceEntity getElement() {
        return element;
    }

    public void setElement(TSourceEntity element) {
        if (this.element == null ? element != null : !this.element.equals(element)) {
            TSourceEntity old_element = this.element;
            this.element = element;
            if (old_element != null) old_element.setSourceAnchor(null);
            if (element != null) element.setSourceAnchor(this);
        }
    }
    
    @FameProperty(name = "endPos")
    public Number getEndPos() {
        return endPos;
    }

    public void setEndPos(Number endPos) {
        this.endPos = endPos;
    }
    
    @FameProperty(name = "lineCount", derived = true)
    public Number getLineCount() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "relatedAnchor")
    public TSourceAnchor getRelatedAnchor() {
        return relatedAnchor;
    }

    public void setRelatedAnchor(TSourceAnchor relatedAnchor) {
        this.relatedAnchor = relatedAnchor;
    }
    
    @FameProperty(name = "startPos")
    public Number getStartPos() {
        return startPos;
    }

    public void setStartPos(Number startPos) {
        this.startPos = startPos;
    }
    


}


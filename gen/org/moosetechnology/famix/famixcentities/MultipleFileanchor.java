// Automagically generated code, please do not change
package org.moosetechnology.famix.famixcentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import java.util.*;
import org.moosetechnology.famix.famixtraits.TFileAnchor;
import org.moosetechnology.famix.famixtraits.TMultipleFileAnchor;
import org.moosetechnology.famix.famixtraits.TSourceAnchor;
import org.moosetechnology.famix.famixtraits.TSourceEntity;


@FamePackage("Famix-C-Entities")
@FameDescription("MultipleFileanchor")
public class MultipleFileanchor extends SourceAnchor implements TMultipleFileAnchor, TSourceAnchor {

    private TSourceEntity element;
    
    private Collection<TFileAnchor> fileAnchors; 



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
    
    @FameProperty(name = "fileAnchors")
    public Collection<TFileAnchor> getFileAnchors() {
        if (fileAnchors == null) fileAnchors = new HashSet<TFileAnchor>();
        return fileAnchors;
    }
    
    public void setFileAnchors(Collection<? extends TFileAnchor> fileAnchors) {
        this.getFileAnchors().clear();
        this.getFileAnchors().addAll(fileAnchors);
    }                    

    public void addFileAnchors(TFileAnchor one) {
        this.getFileAnchors().add(one);
    }   
    
    public void addFileAnchors(TFileAnchor one, TFileAnchor... many) {
        this.getFileAnchors().add(one);
        for (TFileAnchor each : many)
            this.getFileAnchors().add(each);
    }   
    
    public void addFileAnchors(Iterable<? extends TFileAnchor> many) {
        for (TFileAnchor each : many)
            this.getFileAnchors().add(each);
    }   
                
    public void addFileAnchors(TFileAnchor[] many) {
        for (TFileAnchor each : many)
            this.getFileAnchors().add(each);
    }
    
    public int numberOfFileAnchors() {
        return getFileAnchors().size();
    }

    public boolean hasFileAnchors() {
        return !getFileAnchors().isEmpty();
    }

    @FameProperty(name = "lineCount", derived = true)
    public Number getLineCount() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    


}


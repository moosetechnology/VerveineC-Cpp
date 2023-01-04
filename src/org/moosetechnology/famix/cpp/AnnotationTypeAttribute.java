// Automagically generated code, please do not change
package org.moosetechnology.famix.cpp;

import ch.akuhn.fame.internal.MultivalueSet;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.FameDescription;
import java.util.*;

import org.moosetechnology.famix.cpp.AnnotationInstanceAttribute;
import org.moosetechnology.famix.cpp.AnnotationType;
import org.moosetechnology.famix.cpp.AnnotationTypeAttribute;
import org.moosetechnology.famix.cpp.Attribute;

import ch.akuhn.fame.FamePackage;


@FamePackage("FAMIX")
@FameDescription("AnnotationTypeAttribute")
public class AnnotationTypeAttribute extends Attribute {



    @FameProperty(name = "parentAnnotationType", derived = true)
    public AnnotationType getParentAnnotationType() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    private Collection<AnnotationInstanceAttribute> annotationAttributeInstances; 

    @FameProperty(name = "annotationAttributeInstances", opposite = "annotationTypeAttribute", derived = true)
    public Collection<AnnotationInstanceAttribute> getAnnotationAttributeInstances() {
        if (annotationAttributeInstances == null) {
            annotationAttributeInstances = new MultivalueSet<AnnotationInstanceAttribute>() {
                @Override
                protected void clearOpposite(AnnotationInstanceAttribute e) {
                    e.setAnnotationTypeAttribute(null);
                }
                @Override
                protected void setOpposite(AnnotationInstanceAttribute e) {
                    e.setAnnotationTypeAttribute(AnnotationTypeAttribute.this);
                }
            };
        }
        return annotationAttributeInstances;
    }
    
    public void setAnnotationAttributeInstances(Collection<? extends AnnotationInstanceAttribute> annotationAttributeInstances) {
        this.getAnnotationAttributeInstances().clear();
        this.getAnnotationAttributeInstances().addAll(annotationAttributeInstances);
    }                    
    
        
    public void addAnnotationAttributeInstances(AnnotationInstanceAttribute one) {
        this.getAnnotationAttributeInstances().add(one);
    }   
    
    public void addAnnotationAttributeInstances(AnnotationInstanceAttribute one, AnnotationInstanceAttribute... many) {
        this.getAnnotationAttributeInstances().add(one);
        for (AnnotationInstanceAttribute each : many)
            this.getAnnotationAttributeInstances().add(each);
    }   
    
    public void addAnnotationAttributeInstances(Iterable<? extends AnnotationInstanceAttribute> many) {
        for (AnnotationInstanceAttribute each : many)
            this.getAnnotationAttributeInstances().add(each);
    }   
                
    public void addAnnotationAttributeInstances(AnnotationInstanceAttribute[] many) {
        for (AnnotationInstanceAttribute each : many)
            this.getAnnotationAttributeInstances().add(each);
    }
    
    public int numberOfAnnotationAttributeInstances() {
        return getAnnotationAttributeInstances().size();
    }

    public boolean hasAnnotationAttributeInstances() {
        return !getAnnotationAttributeInstances().isEmpty();
    }
    
                


}


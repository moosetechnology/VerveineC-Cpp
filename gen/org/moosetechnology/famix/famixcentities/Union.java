// Automagically generated code, please do not change
package org.moosetechnology.famix.famixcentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;
import org.moosetechnology.famix.famixtraits.TAttribute;
import org.moosetechnology.famix.famixtraits.TComment;
import org.moosetechnology.famix.famixtraits.TWithAttributes;
import org.moosetechnology.famix.famixtraits.TWithComments;


@FamePackage("Famix-C-Entities")
@FameDescription("Union")
public class Union extends Type implements TWithAttributes, TWithComments {

    private Collection<TAttribute> attributes; 

    private Collection<TComment> comments; 



    @FameProperty(name = "attributes", opposite = "parentType", derived = true)
    public Collection<TAttribute> getAttributes() {
        if (attributes == null) {
            attributes = new MultivalueSet<TAttribute>() {
                @Override
                protected void clearOpposite(TAttribute e) {
                    e.setParentType(null);
                }
                @Override
                protected void setOpposite(TAttribute e) {
                    e.setParentType(Union.this);
                }
            };
        }
        return attributes;
    }
    
    public void setAttributes(Collection<? extends TAttribute> attributes) {
        this.getAttributes().clear();
        this.getAttributes().addAll(attributes);
    }                    
    
        
    public void addAttributes(TAttribute one) {
        this.getAttributes().add(one);
    }   
    
    public void addAttributes(TAttribute one, TAttribute... many) {
        this.getAttributes().add(one);
        for (TAttribute each : many)
            this.getAttributes().add(each);
    }   
    
    public void addAttributes(Iterable<? extends TAttribute> many) {
        for (TAttribute each : many)
            this.getAttributes().add(each);
    }   
                
    public void addAttributes(TAttribute[] many) {
        for (TAttribute each : many)
            this.getAttributes().add(each);
    }
    
    public int numberOfAttributes() {
        return getAttributes().size();
    }

    public boolean hasAttributes() {
        return !getAttributes().isEmpty();
    }

    @FameProperty(name = "comments", opposite = "container", derived = true)
    public Collection<TComment> getComments() {
        if (comments == null) {
            comments = new MultivalueSet<TComment>() {
                @Override
                protected void clearOpposite(TComment e) {
                    e.setContainer(null);
                }
                @Override
                protected void setOpposite(TComment e) {
                    e.setContainer(Union.this);
                }
            };
        }
        return comments;
    }
    
    public void setComments(Collection<? extends TComment> comments) {
        this.getComments().clear();
        this.getComments().addAll(comments);
    }                    
    
        
    public void addComments(TComment one) {
        this.getComments().add(one);
    }   
    
    public void addComments(TComment one, TComment... many) {
        this.getComments().add(one);
        for (TComment each : many)
            this.getComments().add(each);
    }   
    
    public void addComments(Iterable<? extends TComment> many) {
        for (TComment each : many)
            this.getComments().add(each);
    }   
                
    public void addComments(TComment[] many) {
        for (TComment each : many)
            this.getComments().add(each);
    }
    
    public int numberOfComments() {
        return getComments().size();
    }

    public boolean hasComments() {
        return !getComments().isEmpty();
    }

    @FameProperty(name = "hasComments", derived = true)
    public Boolean getHasComments() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfAttributes", derived = true)
    public Number getNumberOfAttributes() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfComments", derived = true)
    public Number getNumberOfComments() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    


}


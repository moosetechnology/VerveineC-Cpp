// Automagically generated code, please do not change
package eu.synectique.verveine.core.gen.famix;

import ch.akuhn.fame.FameProperty;
import eu.synectique.verveine.core.gen.famix.Association;
import eu.synectique.verveine.core.gen.famix.NamedEntity;
import eu.synectique.verveine.core.gen.famix.SourcedEntity;
import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;


@FamePackage("FAMIX")
@FameDescription("Association")
public class Association extends SourcedEntity {



    private Association next;
    
    @FameProperty(name = "next", opposite = "previous", derived = true)
    public Association getNext() {
        return next;
    }

    public void setNext(Association next) {
        if (this.next == null ? next != null : !this.next.equals(next)) {
            Association old_next = this.next;
            this.next = next;
            if (old_next != null) old_next.setPrevious(null);
            if (next != null) next.setPrevious(this);
        }
    }
    
    private Association previous;
    
    @FameProperty(name = "previous", opposite = "next")
    public Association getPrevious() {
        return previous;
    }

    public void setPrevious(Association previous) {
        if (this.previous == null ? previous != null : !this.previous.equals(previous)) {
            Association old_previous = this.previous;
            this.previous = previous;
            if (old_previous != null) old_previous.setNext(null);
            if (previous != null) previous.setNext(this);
        }
    }
    
    @FameProperty(name = "from", derived = true)
    public NamedEntity getFrom() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "to", derived = true)
    public NamedEntity getTo() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    


}


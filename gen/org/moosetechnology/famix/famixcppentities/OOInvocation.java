// Automagically generated code, please do not change
package org.moosetechnology.famix.famixcppentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;
import org.moosetechnology.famix.famixcentities.Invocation;
import org.moosetechnology.famix.famixtraits.TAssociation;
import org.moosetechnology.famix.famixtraits.TInvocable;
import org.moosetechnology.famix.famixtraits.TInvocation;
import org.moosetechnology.famix.famixtraits.TInvocationsReceiver;
import org.moosetechnology.famix.famixtraits.TSourceAnchor;
import org.moosetechnology.famix.famixtraits.TSourceEntity;
import org.moosetechnology.famix.famixtraits.TWithInvocations;
import org.moosetechnology.famix.moosequery.TAssociationMetaLevelDependency;


@FamePackage("Famix-Cpp-Entities")
@FameDescription("OOInvocation")
public class OOInvocation extends Invocation implements TAssociation, TAssociationMetaLevelDependency, TInvocation, TSourceEntity {

    private Collection<TInvocable> candidates; 

    private Boolean isStub;
    
    private TAssociation next;
    
    private Number numberOfLinesOfCode;
    
    private TAssociation previous;
    
    private TInvocationsReceiver receiver;
    
    private TWithInvocations sender;
    
    private TSourceAnchor sourceAnchor;
    


    @FameProperty(name = "candidates", opposite = "incomingInvocations")
    public Collection<TInvocable> getCandidates() {
        if (candidates == null) {
            candidates = new MultivalueSet<TInvocable>() {
                @Override
                protected void clearOpposite(TInvocable e) {
                    e.getIncomingInvocations().remove(OOInvocation.this);
                }
                @Override
                protected void setOpposite(TInvocable e) {
                    e.getIncomingInvocations().add(OOInvocation.this);
                }
            };
        }
        return candidates;
    }
    
    public void setCandidates(Collection<? extends TInvocable> candidates) {
        this.getCandidates().clear();
        this.getCandidates().addAll(candidates);
    }
    
    public void addCandidates(TInvocable one) {
        this.getCandidates().add(one);
    }   
    
    public void addCandidates(TInvocable one, TInvocable... many) {
        this.getCandidates().add(one);
        for (TInvocable each : many)
            this.getCandidates().add(each);
    }   
    
    public void addCandidates(Iterable<? extends TInvocable> many) {
        for (TInvocable each : many)
            this.getCandidates().add(each);
    }   
                
    public void addCandidates(TInvocable[] many) {
        for (TInvocable each : many)
            this.getCandidates().add(each);
    }
    
    public int numberOfCandidates() {
        return getCandidates().size();
    }

    public boolean hasCandidates() {
        return !getCandidates().isEmpty();
    }

    @FameProperty(name = "isStub")
    public Boolean getIsStub() {
        return isStub;
    }

    public void setIsStub(Boolean isStub) {
        this.isStub = isStub;
    }
    
    @FameProperty(name = "next", opposite = "previous", derived = true)
    public TAssociation getNext() {
        return next;
    }

    public void setNext(TAssociation next) {
        if (this.next == null ? next != null : !this.next.equals(next)) {
            TAssociation old_next = this.next;
            this.next = next;
            if (old_next != null) old_next.setPrevious(null);
            if (next != null) next.setPrevious(this);
        }
    }
    
    @FameProperty(name = "numberOfLinesOfCode")
    public Number getNumberOfLinesOfCode() {
        return numberOfLinesOfCode;
    }

    public void setNumberOfLinesOfCode(Number numberOfLinesOfCode) {
        this.numberOfLinesOfCode = numberOfLinesOfCode;
    }
    
    @FameProperty(name = "numberOfLinesOfCodeWithMoreThanOneCharacter", derived = true)
    public Number getNumberOfLinesOfCodeWithMoreThanOneCharacter() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "previous", opposite = "next")
    public TAssociation getPrevious() {
        return previous;
    }

    public void setPrevious(TAssociation previous) {
        if (this.previous == null ? previous != null : !this.previous.equals(previous)) {
            TAssociation old_previous = this.previous;
            this.previous = previous;
            if (old_previous != null) old_previous.setNext(null);
            if (previous != null) previous.setNext(this);
        }
    }
    
    @FameProperty(name = "receiver", opposite = "receivingInvocations")
    public TInvocationsReceiver getReceiver() {
        return receiver;
    }

    public void setReceiver(TInvocationsReceiver receiver) {
        if (this.receiver != null) {
            if (this.receiver.equals(receiver)) return;
            this.receiver.getReceivingInvocations().remove(this);
        }
        this.receiver = receiver;
        if (receiver == null) return;
        receiver.getReceivingInvocations().add(this);
    }
    
    @FameProperty(name = "sender", opposite = "outgoingInvocations")
    public TWithInvocations getSender() {
        return sender;
    }

    public void setSender(TWithInvocations sender) {
        if (this.sender != null) {
            if (this.sender.equals(sender)) return;
            this.sender.getOutgoingInvocations().remove(this);
        }
        this.sender = sender;
        if (sender == null) return;
        sender.getOutgoingInvocations().add(this);
    }
    
    @FameProperty(name = "sourceAnchor", opposite = "element", derived = true)
    public TSourceAnchor getSourceAnchor() {
        return sourceAnchor;
    }

    public void setSourceAnchor(TSourceAnchor sourceAnchor) {
        if (this.sourceAnchor == null ? sourceAnchor != null : !this.sourceAnchor.equals(sourceAnchor)) {
            TSourceAnchor old_sourceAnchor = this.sourceAnchor;
            this.sourceAnchor = sourceAnchor;
            if (old_sourceAnchor != null) old_sourceAnchor.setElement(null);
            if (sourceAnchor != null) sourceAnchor.setElement(this);
        }
    }
    
    @FameProperty(name = "sourceText", derived = true)
    public String getSourceText() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    


}

// Automagically generated code, please do not change
package org.moosetechnology.famix.famixcentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;
import org.moosetechnology.famix.famixtraits.TAccess;
import org.moosetechnology.famix.famixtraits.THasSignature;
import org.moosetechnology.famix.famixtraits.TInvocable;
import org.moosetechnology.famix.famixtraits.TInvocation;
import org.moosetechnology.famix.famixtraits.TReference;
import org.moosetechnology.famix.famixtraits.TSourceAnchor;
import org.moosetechnology.famix.famixtraits.TSourceEntity;
import org.moosetechnology.famix.famixtraits.TWithAccesses;
import org.moosetechnology.famix.famixtraits.TWithInvocations;
import org.moosetechnology.famix.famixtraits.TWithReferences;
import org.moosetechnology.famix.famixtraits.TWithStatements;


@FamePackage("Famix-C-Entities")
@FameDescription("BehaviouralEntity")
public class BehaviouralEntity extends ContainerEntity implements THasSignature, TInvocable, TSourceEntity, TWithAccesses, TWithInvocations, TWithReferences, TWithStatements {

    private Collection<BehaviouralPointer> behaviouralAddressers; 

    private Collection<BehaviouralPointer> behaviouralPointers; 

    private Number cyclomaticComplexity;
    
    private Number numberOfStatements;
    
    private Collection<TAccess> accesses; 

    private Collection<TInvocation> incomingInvocations; 

    private Boolean isStub;
    
    private Number numberOfLinesOfCode;
    
    private Collection<TInvocation> outgoingInvocations; 

    private Collection<TReference> outgoingReferences; 

    private String signature;
    
    private TSourceAnchor sourceAnchor;
    


    @FameProperty(name = "behaviouralAddressers", opposite = "behaviouralPointed", derived = true)
    public Collection<BehaviouralPointer> getBehaviouralAddressers() {
        if (behaviouralAddressers == null) {
            behaviouralAddressers = new MultivalueSet<BehaviouralPointer>() {
                @Override
                protected void clearOpposite(BehaviouralPointer e) {
                    e.setBehaviouralPointed(null);
                }
                @Override
                protected void setOpposite(BehaviouralPointer e) {
                    e.setBehaviouralPointed(BehaviouralEntity.this);
                }
            };
        }
        return behaviouralAddressers;
    }
    
    public void setBehaviouralAddressers(Collection<? extends BehaviouralPointer> behaviouralAddressers) {
        this.getBehaviouralAddressers().clear();
        this.getBehaviouralAddressers().addAll(behaviouralAddressers);
    }                    
    
        
    public void addBehaviouralAddressers(BehaviouralPointer one) {
        this.getBehaviouralAddressers().add(one);
    }   
    
    public void addBehaviouralAddressers(BehaviouralPointer one, BehaviouralPointer... many) {
        this.getBehaviouralAddressers().add(one);
        for (BehaviouralPointer each : many)
            this.getBehaviouralAddressers().add(each);
    }   
    
    public void addBehaviouralAddressers(Iterable<? extends BehaviouralPointer> many) {
        for (BehaviouralPointer each : many)
            this.getBehaviouralAddressers().add(each);
    }   
                
    public void addBehaviouralAddressers(BehaviouralPointer[] many) {
        for (BehaviouralPointer each : many)
            this.getBehaviouralAddressers().add(each);
    }
    
    public int numberOfBehaviouralAddressers() {
        return getBehaviouralAddressers().size();
    }

    public boolean hasBehaviouralAddressers() {
        return !getBehaviouralAddressers().isEmpty();
    }

    @FameProperty(name = "behaviouralPointers", opposite = "referer", derived = true)
    public Collection<BehaviouralPointer> getBehaviouralPointers() {
        if (behaviouralPointers == null) {
            behaviouralPointers = new MultivalueSet<BehaviouralPointer>() {
                @Override
                protected void clearOpposite(BehaviouralPointer e) {
                    e.setReferer(null);
                }
                @Override
                protected void setOpposite(BehaviouralPointer e) {
                    e.setReferer(BehaviouralEntity.this);
                }
            };
        }
        return behaviouralPointers;
    }
    
    public void setBehaviouralPointers(Collection<? extends BehaviouralPointer> behaviouralPointers) {
        this.getBehaviouralPointers().clear();
        this.getBehaviouralPointers().addAll(behaviouralPointers);
    }                    
    
        
    public void addBehaviouralPointers(BehaviouralPointer one) {
        this.getBehaviouralPointers().add(one);
    }   
    
    public void addBehaviouralPointers(BehaviouralPointer one, BehaviouralPointer... many) {
        this.getBehaviouralPointers().add(one);
        for (BehaviouralPointer each : many)
            this.getBehaviouralPointers().add(each);
    }   
    
    public void addBehaviouralPointers(Iterable<? extends BehaviouralPointer> many) {
        for (BehaviouralPointer each : many)
            this.getBehaviouralPointers().add(each);
    }   
                
    public void addBehaviouralPointers(BehaviouralPointer[] many) {
        for (BehaviouralPointer each : many)
            this.getBehaviouralPointers().add(each);
    }
    
    public int numberOfBehaviouralPointers() {
        return getBehaviouralPointers().size();
    }

    public boolean hasBehaviouralPointers() {
        return !getBehaviouralPointers().isEmpty();
    }

    @FameProperty(name = "cyclomaticComplexity")
    public Number getCyclomaticComplexity() {
        return cyclomaticComplexity;
    }

    public void setCyclomaticComplexity(Number cyclomaticComplexity) {
        this.cyclomaticComplexity = cyclomaticComplexity;
    }
    
    @FameProperty(name = "numberOfStatements")
    public Number getNumberOfStatements() {
        return numberOfStatements;
    }

    public void setNumberOfStatements(Number numberOfStatements) {
        this.numberOfStatements = numberOfStatements;
    }
    
    @FameProperty(name = "accesses", opposite = "accessor", derived = true)
    public Collection<TAccess> getAccesses() {
        if (accesses == null) {
            accesses = new MultivalueSet<TAccess>() {
                @Override
                protected void clearOpposite(TAccess e) {
                    e.setAccessor(null);
                }
                @Override
                protected void setOpposite(TAccess e) {
                    e.setAccessor(BehaviouralEntity.this);
                }
            };
        }
        return accesses;
    }
    
    public void setAccesses(Collection<? extends TAccess> accesses) {
        this.getAccesses().clear();
        this.getAccesses().addAll(accesses);
    }                    
    
        
    public void addAccesses(TAccess one) {
        this.getAccesses().add(one);
    }   
    
    public void addAccesses(TAccess one, TAccess... many) {
        this.getAccesses().add(one);
        for (TAccess each : many)
            this.getAccesses().add(each);
    }   
    
    public void addAccesses(Iterable<? extends TAccess> many) {
        for (TAccess each : many)
            this.getAccesses().add(each);
    }   
                
    public void addAccesses(TAccess[] many) {
        for (TAccess each : many)
            this.getAccesses().add(each);
    }
    
    public int numberOfAccesses() {
        return getAccesses().size();
    }

    public boolean hasAccesses() {
        return !getAccesses().isEmpty();
    }

    @FameProperty(name = "incomingInvocations", opposite = "candidates", derived = true)
    public Collection<TInvocation> getIncomingInvocations() {
        if (incomingInvocations == null) {
            incomingInvocations = new MultivalueSet<TInvocation>() {
                @Override
                protected void clearOpposite(TInvocation e) {
                    e.getCandidates().remove(BehaviouralEntity.this);
                }
                @Override
                protected void setOpposite(TInvocation e) {
                    e.getCandidates().add(BehaviouralEntity.this);
                }
            };
        }
        return incomingInvocations;
    }
    
    public void setIncomingInvocations(Collection<? extends TInvocation> incomingInvocations) {
        this.getIncomingInvocations().clear();
        this.getIncomingInvocations().addAll(incomingInvocations);
    }
    
    public void addIncomingInvocations(TInvocation one) {
        this.getIncomingInvocations().add(one);
    }   
    
    public void addIncomingInvocations(TInvocation one, TInvocation... many) {
        this.getIncomingInvocations().add(one);
        for (TInvocation each : many)
            this.getIncomingInvocations().add(each);
    }   
    
    public void addIncomingInvocations(Iterable<? extends TInvocation> many) {
        for (TInvocation each : many)
            this.getIncomingInvocations().add(each);
    }   
                
    public void addIncomingInvocations(TInvocation[] many) {
        for (TInvocation each : many)
            this.getIncomingInvocations().add(each);
    }
    
    public int numberOfIncomingInvocations() {
        return getIncomingInvocations().size();
    }

    public boolean hasIncomingInvocations() {
        return !getIncomingInvocations().isEmpty();
    }

    @FameProperty(name = "isStub")
    public Boolean getIsStub() {
        return isStub;
    }

    public void setIsStub(Boolean isStub) {
        this.isStub = isStub;
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
    
    @FameProperty(name = "numberOfOutgoingInvocations", derived = true)
    public Number getNumberOfOutgoingInvocations() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "outgoingInvocations", opposite = "sender", derived = true)
    public Collection<TInvocation> getOutgoingInvocations() {
        if (outgoingInvocations == null) {
            outgoingInvocations = new MultivalueSet<TInvocation>() {
                @Override
                protected void clearOpposite(TInvocation e) {
                    e.setSender(null);
                }
                @Override
                protected void setOpposite(TInvocation e) {
                    e.setSender(BehaviouralEntity.this);
                }
            };
        }
        return outgoingInvocations;
    }
    
    public void setOutgoingInvocations(Collection<? extends TInvocation> outgoingInvocations) {
        this.getOutgoingInvocations().clear();
        this.getOutgoingInvocations().addAll(outgoingInvocations);
    }                    
    
        
    public void addOutgoingInvocations(TInvocation one) {
        this.getOutgoingInvocations().add(one);
    }   
    
    public void addOutgoingInvocations(TInvocation one, TInvocation... many) {
        this.getOutgoingInvocations().add(one);
        for (TInvocation each : many)
            this.getOutgoingInvocations().add(each);
    }   
    
    public void addOutgoingInvocations(Iterable<? extends TInvocation> many) {
        for (TInvocation each : many)
            this.getOutgoingInvocations().add(each);
    }   
                
    public void addOutgoingInvocations(TInvocation[] many) {
        for (TInvocation each : many)
            this.getOutgoingInvocations().add(each);
    }
    
    public int numberOfOutgoingInvocations() {
        return getOutgoingInvocations().size();
    }

    public boolean hasOutgoingInvocations() {
        return !getOutgoingInvocations().isEmpty();
    }

    @FameProperty(name = "outgoingReferences", opposite = "referencer", derived = true)
    public Collection<TReference> getOutgoingReferences() {
        if (outgoingReferences == null) {
            outgoingReferences = new MultivalueSet<TReference>() {
                @Override
                protected void clearOpposite(TReference e) {
                    e.setReferencer(null);
                }
                @Override
                protected void setOpposite(TReference e) {
                    e.setReferencer(BehaviouralEntity.this);
                }
            };
        }
        return outgoingReferences;
    }
    
    public void setOutgoingReferences(Collection<? extends TReference> outgoingReferences) {
        this.getOutgoingReferences().clear();
        this.getOutgoingReferences().addAll(outgoingReferences);
    }                    
    
        
    public void addOutgoingReferences(TReference one) {
        this.getOutgoingReferences().add(one);
    }   
    
    public void addOutgoingReferences(TReference one, TReference... many) {
        this.getOutgoingReferences().add(one);
        for (TReference each : many)
            this.getOutgoingReferences().add(each);
    }   
    
    public void addOutgoingReferences(Iterable<? extends TReference> many) {
        for (TReference each : many)
            this.getOutgoingReferences().add(each);
    }   
                
    public void addOutgoingReferences(TReference[] many) {
        for (TReference each : many)
            this.getOutgoingReferences().add(each);
    }
    
    public int numberOfOutgoingReferences() {
        return getOutgoingReferences().size();
    }

    public boolean hasOutgoingReferences() {
        return !getOutgoingReferences().isEmpty();
    }

    @FameProperty(name = "signature")
    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
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


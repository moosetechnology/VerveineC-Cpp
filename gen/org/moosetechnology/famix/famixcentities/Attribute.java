// Automagically generated code, please do not change
package org.moosetechnology.famix.famixcentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;
import org.moosetechnology.famix.famixtraits.TAccess;
import org.moosetechnology.famix.famixtraits.TAccessible;
import org.moosetechnology.famix.famixtraits.TAttribute;
import org.moosetechnology.famix.famixtraits.TInvocation;
import org.moosetechnology.famix.famixtraits.TInvocationsReceiver;
import org.moosetechnology.famix.famixtraits.TNamedEntity;
import org.moosetechnology.famix.famixtraits.TSourceAnchor;
import org.moosetechnology.famix.famixtraits.TSourceEntity;
import org.moosetechnology.famix.famixtraits.TStructuralEntity;
import org.moosetechnology.famix.famixtraits.TType;
import org.moosetechnology.famix.famixtraits.TTypedEntity;
import org.moosetechnology.famix.famixtraits.TWithAccesses;
import org.moosetechnology.famix.famixtraits.TWithAttributes;
import org.moosetechnology.famix.moosequery.TEntityMetaLevelDependency;


@FamePackage("Famix-C-Entities")
@FameDescription("Attribute")
public class Attribute extends NamedEntity implements TAccessible, TAttribute, TEntityMetaLevelDependency, TInvocationsReceiver, TNamedEntity, TSourceEntity, TStructuralEntity, TTypedEntity {

    private TType declaredType;
    
    private Collection<TAccess> incomingAccesses; 

    private Boolean isStub;
    
    private String name;
    
    private Number numberOfLinesOfCode;
    
    private TWithAttributes parentType;
    
    private Collection<TInvocation> receivingInvocations; 

    private TSourceAnchor sourceAnchor;
    


    @FameProperty(name = "accessors", derived = true)
    public Collection<TWithAccesses> getAccessors() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
        
    @FameProperty(name = "declaredType", opposite = "typedEntities")
    public TType getDeclaredType() {
        return declaredType;
    }

    public void setDeclaredType(TType declaredType) {
        if (this.declaredType != null) {
            if (this.declaredType.equals(declaredType)) return;
            this.declaredType.getTypedEntities().remove(this);
        }
        this.declaredType = declaredType;
        if (declaredType == null) return;
        declaredType.getTypedEntities().add(this);
    }
    
    @FameProperty(name = "fanIn", derived = true)
    public Number getFanIn() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "fanOut", derived = true)
    public Number getFanOut() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "hasClassScope", derived = true)
    public Boolean getHasClassScope() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "hierarchyNestingLevel", derived = true)
    public Number getHierarchyNestingLevel() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "incomingAccesses", opposite = "variable", derived = true)
    public Collection<TAccess> getIncomingAccesses() {
        if (incomingAccesses == null) {
            incomingAccesses = new MultivalueSet<TAccess>() {
                @Override
                protected void clearOpposite(TAccess e) {
                    e.setVariable(null);
                }
                @Override
                protected void setOpposite(TAccess e) {
                    e.setVariable(Attribute.this);
                }
            };
        }
        return incomingAccesses;
    }
    
    public void setIncomingAccesses(Collection<? extends TAccess> incomingAccesses) {
        this.getIncomingAccesses().clear();
        this.getIncomingAccesses().addAll(incomingAccesses);
    }                    
    
        
    public void addIncomingAccesses(TAccess one) {
        this.getIncomingAccesses().add(one);
    }   
    
    public void addIncomingAccesses(TAccess one, TAccess... many) {
        this.getIncomingAccesses().add(one);
        for (TAccess each : many)
            this.getIncomingAccesses().add(each);
    }   
    
    public void addIncomingAccesses(Iterable<? extends TAccess> many) {
        for (TAccess each : many)
            this.getIncomingAccesses().add(each);
    }   
                
    public void addIncomingAccesses(TAccess[] many) {
        for (TAccess each : many)
            this.getIncomingAccesses().add(each);
    }
    
    public int numberOfIncomingAccesses() {
        return getIncomingAccesses().size();
    }

    public boolean hasIncomingAccesses() {
        return !getIncomingAccesses().isEmpty();
    }

    @FameProperty(name = "isDead", derived = true)
    public Boolean getIsDead() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "isStub")
    public Boolean getIsStub() {
        return isStub;
    }

    public void setIsStub(Boolean isStub) {
        this.isStub = isStub;
    }
    
    @FameProperty(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @FameProperty(name = "numberOfAccesses", derived = true)
    public Number getNumberOfAccesses() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfAccessingClasses", derived = true)
    public Number getNumberOfAccessingClasses() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfAccessingMethods", derived = true)
    public Number getNumberOfAccessingMethods() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfChildren", derived = true)
    public Number getNumberOfChildren() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfDeadChildren", derived = true)
    public Number getNumberOfDeadChildren() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfExternalClients", derived = true)
    public Number getNumberOfExternalClients() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfExternalProviders", derived = true)
    public Number getNumberOfExternalProviders() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfGlobalAccesses", derived = true)
    public Number getNumberOfGlobalAccesses() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfInternalClients", derived = true)
    public Number getNumberOfInternalClients() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfInternalProviders", derived = true)
    public Number getNumberOfInternalProviders() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
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
    
    @FameProperty(name = "numberOfLocalAccesses", derived = true)
    public Number getNumberOfLocalAccesses() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "parentType", opposite = "attributes", container = true)
    public TWithAttributes getParentType() {
        return parentType;
    }

    public void setParentType(TWithAttributes parentType) {
        if (this.parentType != null) {
            if (this.parentType.equals(parentType)) return;
            this.parentType.getAttributes().remove(this);
        }
        this.parentType = parentType;
        if (parentType == null) return;
        parentType.getAttributes().add(this);
    }
    
    @FameProperty(name = "receivingInvocations", opposite = "receiver", derived = true)
    public Collection<TInvocation> getReceivingInvocations() {
        if (receivingInvocations == null) {
            receivingInvocations = new MultivalueSet<TInvocation>() {
                @Override
                protected void clearOpposite(TInvocation e) {
                    e.setReceiver(null);
                }
                @Override
                protected void setOpposite(TInvocation e) {
                    e.setReceiver(Attribute.this);
                }
            };
        }
        return receivingInvocations;
    }
    
    public void setReceivingInvocations(Collection<? extends TInvocation> receivingInvocations) {
        this.getReceivingInvocations().clear();
        this.getReceivingInvocations().addAll(receivingInvocations);
    }                    
    
        
    public void addReceivingInvocations(TInvocation one) {
        this.getReceivingInvocations().add(one);
    }   
    
    public void addReceivingInvocations(TInvocation one, TInvocation... many) {
        this.getReceivingInvocations().add(one);
        for (TInvocation each : many)
            this.getReceivingInvocations().add(each);
    }   
    
    public void addReceivingInvocations(Iterable<? extends TInvocation> many) {
        for (TInvocation each : many)
            this.getReceivingInvocations().add(each);
    }   
                
    public void addReceivingInvocations(TInvocation[] many) {
        for (TInvocation each : many)
            this.getReceivingInvocations().add(each);
    }
    
    public int numberOfReceivingInvocations() {
        return getReceivingInvocations().size();
    }

    public boolean hasReceivingInvocations() {
        return !getReceivingInvocations().isEmpty();
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


// Automagically generated code, please do not change
package org.moosetechnology.famix.famixcentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;
import org.moosetechnology.famix.famixtraits.TNamedEntity;
import org.moosetechnology.famix.famixtraits.TPackage;
import org.moosetechnology.famix.famixtraits.TPackageable;
import org.moosetechnology.famix.famixtraits.TReference;
import org.moosetechnology.famix.famixtraits.TReferenceable;
import org.moosetechnology.famix.famixtraits.TSourceAnchor;
import org.moosetechnology.famix.famixtraits.TSourceEntity;
import org.moosetechnology.famix.famixtraits.TType;
import org.moosetechnology.famix.famixtraits.TTypeAlias;
import org.moosetechnology.famix.famixtraits.TTypedEntity;
import org.moosetechnology.famix.famixtraits.TWithTypeAliases;
import org.moosetechnology.famix.famixtraits.TWithTypes;
import org.moosetechnology.famix.moosequery.TEntityMetaLevelDependency;


@FamePackage("Famix-C-Entities")
@FameDescription("Type")
public class Type extends ContainerEntity implements TEntityMetaLevelDependency, TNamedEntity, TPackageable, TReferenceable, TSourceEntity, TType, TWithTypeAliases {

    private Collection<TReference> incomingReferences; 

    private Boolean isStub;
    
    private String name;
    
    private Number numberOfLinesOfCode;
    
    private TPackage parentPackage;
    
    private TSourceAnchor sourceAnchor;
    
    private Collection<TTypeAlias> typeAliases; 

    private TWithTypes typeContainer;
    
    private Collection<TTypedEntity> typedEntities; 



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
    
    @FameProperty(name = "incomingReferences", opposite = "referredType", derived = true)
    public Collection<TReference> getIncomingReferences() {
        if (incomingReferences == null) {
            incomingReferences = new MultivalueSet<TReference>() {
                @Override
                protected void clearOpposite(TReference e) {
                    e.setReferredType(null);
                }
                @Override
                protected void setOpposite(TReference e) {
                    e.setReferredType(Type.this);
                }
            };
        }
        return incomingReferences;
    }
    
    public void setIncomingReferences(Collection<? extends TReference> incomingReferences) {
        this.getIncomingReferences().clear();
        this.getIncomingReferences().addAll(incomingReferences);
    }                    
    
        
    public void addIncomingReferences(TReference one) {
        this.getIncomingReferences().add(one);
    }   
    
    public void addIncomingReferences(TReference one, TReference... many) {
        this.getIncomingReferences().add(one);
        for (TReference each : many)
            this.getIncomingReferences().add(each);
    }   
    
    public void addIncomingReferences(Iterable<? extends TReference> many) {
        for (TReference each : many)
            this.getIncomingReferences().add(each);
    }   
                
    public void addIncomingReferences(TReference[] many) {
        for (TReference each : many)
            this.getIncomingReferences().add(each);
    }
    
    public int numberOfIncomingReferences() {
        return getIncomingReferences().size();
    }

    public boolean hasIncomingReferences() {
        return !getIncomingReferences().isEmpty();
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
    
    @FameProperty(name = "parentPackage", opposite = "childEntities", container = true)
    public TPackage getParentPackage() {
        return parentPackage;
    }

    public void setParentPackage(TPackage parentPackage) {
        if (this.parentPackage != null) {
            if (this.parentPackage.equals(parentPackage)) return;
            this.parentPackage.getChildEntities().remove(this);
        }
        this.parentPackage = parentPackage;
        if (parentPackage == null) return;
        parentPackage.getChildEntities().add(this);
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
    
    @FameProperty(name = "typeAliases", opposite = "aliasedType", derived = true)
    public Collection<TTypeAlias> getTypeAliases() {
        if (typeAliases == null) {
            typeAliases = new MultivalueSet<TTypeAlias>() {
                @Override
                protected void clearOpposite(TTypeAlias e) {
                    e.setAliasedType(null);
                }
                @Override
                protected void setOpposite(TTypeAlias e) {
                    e.setAliasedType(Type.this);
                }
            };
        }
        return typeAliases;
    }
    
    public void setTypeAliases(Collection<? extends TTypeAlias> typeAliases) {
        this.getTypeAliases().clear();
        this.getTypeAliases().addAll(typeAliases);
    }                    
    
        
    public void addTypeAliases(TTypeAlias one) {
        this.getTypeAliases().add(one);
    }   
    
    public void addTypeAliases(TTypeAlias one, TTypeAlias... many) {
        this.getTypeAliases().add(one);
        for (TTypeAlias each : many)
            this.getTypeAliases().add(each);
    }   
    
    public void addTypeAliases(Iterable<? extends TTypeAlias> many) {
        for (TTypeAlias each : many)
            this.getTypeAliases().add(each);
    }   
                
    public void addTypeAliases(TTypeAlias[] many) {
        for (TTypeAlias each : many)
            this.getTypeAliases().add(each);
    }
    
    public int numberOfTypeAliases() {
        return getTypeAliases().size();
    }

    public boolean hasTypeAliases() {
        return !getTypeAliases().isEmpty();
    }

    @FameProperty(name = "typeContainer", opposite = "types", container = true)
    public TWithTypes getTypeContainer() {
        return typeContainer;
    }

    public void setTypeContainer(TWithTypes typeContainer) {
        if (this.typeContainer != null) {
            if (this.typeContainer.equals(typeContainer)) return;
            this.typeContainer.getTypes().remove(this);
        }
        this.typeContainer = typeContainer;
        if (typeContainer == null) return;
        typeContainer.getTypes().add(this);
    }
    
    @FameProperty(name = "typedEntities", opposite = "declaredType", derived = true)
    public Collection<TTypedEntity> getTypedEntities() {
        if (typedEntities == null) {
            typedEntities = new MultivalueSet<TTypedEntity>() {
                @Override
                protected void clearOpposite(TTypedEntity e) {
                    e.setDeclaredType(null);
                }
                @Override
                protected void setOpposite(TTypedEntity e) {
                    e.setDeclaredType(Type.this);
                }
            };
        }
        return typedEntities;
    }
    
    public void setTypedEntities(Collection<? extends TTypedEntity> typedEntities) {
        this.getTypedEntities().clear();
        this.getTypedEntities().addAll(typedEntities);
    }                    
    
        
    public void addTypedEntities(TTypedEntity one) {
        this.getTypedEntities().add(one);
    }   
    
    public void addTypedEntities(TTypedEntity one, TTypedEntity... many) {
        this.getTypedEntities().add(one);
        for (TTypedEntity each : many)
            this.getTypedEntities().add(each);
    }   
    
    public void addTypedEntities(Iterable<? extends TTypedEntity> many) {
        for (TTypedEntity each : many)
            this.getTypedEntities().add(each);
    }   
                
    public void addTypedEntities(TTypedEntity[] many) {
        for (TTypedEntity each : many)
            this.getTypedEntities().add(each);
    }
    
    public int numberOfTypedEntities() {
        return getTypedEntities().size();
    }

    public boolean hasTypedEntities() {
        return !getTypedEntities().isEmpty();
    }



}


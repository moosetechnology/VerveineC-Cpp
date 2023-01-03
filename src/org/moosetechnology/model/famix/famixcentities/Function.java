// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixcentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;
import org.moosetechnology.model.famix.famixtraits.TAccess;
import org.moosetechnology.model.famix.famixtraits.TFunction;
import org.moosetechnology.model.famix.famixtraits.THasSignature;
import org.moosetechnology.model.famix.famixtraits.TInvocation;
import org.moosetechnology.model.famix.famixtraits.TNamedEntity;
import org.moosetechnology.model.famix.famixtraits.TParameter;
import org.moosetechnology.model.famix.famixtraits.TReference;
import org.moosetechnology.model.famix.famixtraits.TSourceAnchor;
import org.moosetechnology.model.famix.famixtraits.TSourceEntity;
import org.moosetechnology.model.famix.famixtraits.TType;
import org.moosetechnology.model.famix.famixtraits.TTypedEntity;
import org.moosetechnology.model.famix.famixtraits.TWithAccesses;
import org.moosetechnology.model.famix.famixtraits.TWithFunctions;
import org.moosetechnology.model.famix.famixtraits.TWithInvocations;
import org.moosetechnology.model.famix.famixtraits.TWithParameters;
import org.moosetechnology.model.famix.famixtraits.TWithReferences;
import org.moosetechnology.model.famix.famixtraits.TWithStatements;
import org.moosetechnology.model.famix.moosequery.TEntityMetaLevelDependency;


@FamePackage("Famix-C-Entities")
@FameDescription("Function")
public class Function extends BehaviouralEntity implements TEntityMetaLevelDependency, TFunction, THasSignature, TNamedEntity, TSourceEntity, TTypedEntity, TWithAccesses, TWithInvocations, TWithParameters, TWithReferences, TWithStatements {

    private Collection<TAccess> accesses; 

    private TType declaredType;
    
    private TWithFunctions functionOwner;
    
    private Boolean isStub;
    
    private String name;
    
    private Number numberOfLinesOfCode;
    
    private Collection<TInvocation> outgoingInvocations; 

    private Collection<TReference> outgoingReferences; 

    private Collection<TParameter> parameters; 

    private String signature;
    
    private TSourceAnchor sourceAnchor;
    


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
                    e.setAccessor(Function.this);
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
    
    @FameProperty(name = "functionOwner", opposite = "functions", container = true)
    public TWithFunctions getFunctionOwner() {
        return functionOwner;
    }

    public void setFunctionOwner(TWithFunctions functionOwner) {
        if (this.functionOwner != null) {
            if (this.functionOwner.equals(functionOwner)) return;
            this.functionOwner.getFunctions().remove(this);
        }
        this.functionOwner = functionOwner;
        if (functionOwner == null) return;
        functionOwner.getFunctions().add(this);
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
    
    @FameProperty(name = "numberOfOutgoingInvocations", derived = true)
    public Number getNumberOfOutgoingInvocations() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfParameters", derived = true)
    public Number getNumberOfParameters() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfStatements", derived = true)
    public Number getNumberOfStatements() {
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
                    e.setSender(Function.this);
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
                    e.setReferencer(Function.this);
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

    @FameProperty(name = "parameters", opposite = "parentBehaviouralEntity", derived = true)
    public Collection<TParameter> getParameters() {
        if (parameters == null) {
            parameters = new MultivalueSet<TParameter>() {
                @Override
                protected void clearOpposite(TParameter e) {
                    e.setParentBehaviouralEntity(null);
                }
                @Override
                protected void setOpposite(TParameter e) {
                    e.setParentBehaviouralEntity(Function.this);
                }
            };
        }
        return parameters;
    }
    
    public void setParameters(Collection<? extends TParameter> parameters) {
        this.getParameters().clear();
        this.getParameters().addAll(parameters);
    }                    
    
        
    public void addParameters(TParameter one) {
        this.getParameters().add(one);
    }   
    
    public void addParameters(TParameter one, TParameter... many) {
        this.getParameters().add(one);
        for (TParameter each : many)
            this.getParameters().add(each);
    }   
    
    public void addParameters(Iterable<? extends TParameter> many) {
        for (TParameter each : many)
            this.getParameters().add(each);
    }   
                
    public void addParameters(TParameter[] many) {
        for (TParameter each : many)
            this.getParameters().add(each);
    }
    
    public int numberOfParameters() {
        return getParameters().size();
    }

    public boolean hasParameters() {
        return !getParameters().isEmpty();
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


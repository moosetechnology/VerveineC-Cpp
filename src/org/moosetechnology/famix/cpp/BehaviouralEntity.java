// Automagically generated code, please do not change
package org.moosetechnology.famix.cpp;

import ch.akuhn.fame.internal.MultivalueSet;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.FameDescription;
import java.util.*;

import org.moosetechnology.famix.cpp.Access;
import org.moosetechnology.famix.cpp.BehaviouralEntity;
import org.moosetechnology.famix.cpp.BehaviouralReference;
import org.moosetechnology.famix.cpp.ContainerEntity;
import org.moosetechnology.famix.cpp.ImplicitVariable;
import org.moosetechnology.famix.cpp.Invocation;
import org.moosetechnology.famix.cpp.LocalVariable;
import org.moosetechnology.famix.cpp.Parameter;
import org.moosetechnology.famix.cpp.Reference;
import org.moosetechnology.famix.cpp.Type;

import ch.akuhn.fame.FamePackage;


@FamePackage("FAMIX")
@FameDescription("BehaviouralEntity")
public abstract class BehaviouralEntity extends ContainerEntity {



    @FameProperty(name = "numberOfAccesses", derived = true)
    public Number getNumberOfAccesses() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    private Collection<BehaviouralReference> references; 

    @FameProperty(name = "references", opposite = "pointed", derived = true)
    public Collection<BehaviouralReference> getReferences() {
        if (references == null) {
            references = new MultivalueSet<BehaviouralReference>() {
                @Override
                protected void clearOpposite(BehaviouralReference e) {
                    e.setPointed(null);
                }
                @Override
                protected void setOpposite(BehaviouralReference e) {
                    e.setPointed(BehaviouralEntity.this);
                }
            };
        }
        return references;
    }
    
    public void setReferences(Collection<? extends BehaviouralReference> references) {
        this.getReferences().clear();
        this.getReferences().addAll(references);
    }                    
    
        
    public void addReferences(BehaviouralReference one) {
        this.getReferences().add(one);
    }   
    
    public void addReferences(BehaviouralReference one, BehaviouralReference... many) {
        this.getReferences().add(one);
        for (BehaviouralReference each : many)
            this.getReferences().add(each);
    }   
    
    public void addReferences(Iterable<? extends BehaviouralReference> many) {
        for (BehaviouralReference each : many)
            this.getReferences().add(each);
    }   
                
    public void addReferences(BehaviouralReference[] many) {
        for (BehaviouralReference each : many)
            this.getReferences().add(each);
    }
    
    public int numberOfReferences() {
        return getReferences().size();
    }

    public boolean hasReferences() {
        return !getReferences().isEmpty();
    }
    
                
    private String signature;
    
    @FameProperty(name = "signature")
    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
    
    private Collection<BehaviouralReference> behaviouralPointers; 

    @FameProperty(name = "behaviouralPointers", opposite = "referer", derived = true)
    public Collection<BehaviouralReference> getBehaviouralPointers() {
        if (behaviouralPointers == null) {
            behaviouralPointers = new MultivalueSet<BehaviouralReference>() {
                @Override
                protected void clearOpposite(BehaviouralReference e) {
                    e.setReferer(null);
                }
                @Override
                protected void setOpposite(BehaviouralReference e) {
                    e.setReferer(BehaviouralEntity.this);
                }
            };
        }
        return behaviouralPointers;
    }
    
    public void setBehaviouralPointers(Collection<? extends BehaviouralReference> behaviouralPointers) {
        this.getBehaviouralPointers().clear();
        this.getBehaviouralPointers().addAll(behaviouralPointers);
    }                    
    
        
    public void addBehaviouralPointers(BehaviouralReference one) {
        this.getBehaviouralPointers().add(one);
    }   
    
    public void addBehaviouralPointers(BehaviouralReference one, BehaviouralReference... many) {
        this.getBehaviouralPointers().add(one);
        for (BehaviouralReference each : many)
            this.getBehaviouralPointers().add(each);
    }   
    
    public void addBehaviouralPointers(Iterable<? extends BehaviouralReference> many) {
        for (BehaviouralReference each : many)
            this.getBehaviouralPointers().add(each);
    }   
                
    public void addBehaviouralPointers(BehaviouralReference[] many) {
        for (BehaviouralReference each : many)
            this.getBehaviouralPointers().add(each);
    }
    
    public int numberOfBehaviouralPointers() {
        return getBehaviouralPointers().size();
    }

    public boolean hasBehaviouralPointers() {
        return !getBehaviouralPointers().isEmpty();
    }
    
                
    @FameProperty(name = "numberOfParameters", derived = true)
    public Number getNumberOfParameters() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    private Type declaredType;
    
    @FameProperty(name = "declaredType", opposite = "behavioursWithDeclaredType")
    public Type getDeclaredType() {
        return declaredType;
    }

    public void setDeclaredType(Type declaredType) {
        if (this.declaredType != null) {
            if (this.declaredType.equals(declaredType)) return;
            this.declaredType.getBehavioursWithDeclaredType().remove(this);
        }
        this.declaredType = declaredType;
        if (declaredType == null) return;
        declaredType.getBehavioursWithDeclaredType().add(this);
    }
    
    @FameProperty(name = "providerBehaviours", derived = true)
    public Collection<BehaviouralEntity> getProviderBehaviours() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
        
    @FameProperty(name = "numberOfMessageSends", derived = true)
    public Number getNumberOfMessageSends() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    private Number cyclomaticComplexity;
    
    @FameProperty(name = "cyclomaticComplexity")
    public Number getCyclomaticComplexity() {
        return cyclomaticComplexity;
    }

    public void setCyclomaticComplexity(Number cyclomaticComplexity) {
        this.cyclomaticComplexity = cyclomaticComplexity;
    }
    
    @FameProperty(name = "numberOfComments", derived = true)
    public Number getNumberOfComments() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    private Collection<ImplicitVariable> implicitVariables; 

    @FameProperty(name = "implicitVariables", opposite = "parentBehaviouralEntity", derived = true)
    public Collection<ImplicitVariable> getImplicitVariables() {
        if (implicitVariables == null) {
            implicitVariables = new MultivalueSet<ImplicitVariable>() {
                @Override
                protected void clearOpposite(ImplicitVariable e) {
                    e.setParentBehaviouralEntity(null);
                }
                @Override
                protected void setOpposite(ImplicitVariable e) {
                    e.setParentBehaviouralEntity(BehaviouralEntity.this);
                }
            };
        }
        return implicitVariables;
    }
    
    public void setImplicitVariables(Collection<? extends ImplicitVariable> implicitVariables) {
        this.getImplicitVariables().clear();
        this.getImplicitVariables().addAll(implicitVariables);
    }                    
    
        
    public void addImplicitVariables(ImplicitVariable one) {
        this.getImplicitVariables().add(one);
    }   
    
    public void addImplicitVariables(ImplicitVariable one, ImplicitVariable... many) {
        this.getImplicitVariables().add(one);
        for (ImplicitVariable each : many)
            this.getImplicitVariables().add(each);
    }   
    
    public void addImplicitVariables(Iterable<? extends ImplicitVariable> many) {
        for (ImplicitVariable each : many)
            this.getImplicitVariables().add(each);
    }   
                
    public void addImplicitVariables(ImplicitVariable[] many) {
        for (ImplicitVariable each : many)
            this.getImplicitVariables().add(each);
    }
    
    public int numberOfImplicitVariables() {
        return getImplicitVariables().size();
    }

    public boolean hasImplicitVariables() {
        return !getImplicitVariables().isEmpty();
    }
    
                
    private Collection<LocalVariable> localVariables; 

    @FameProperty(name = "localVariables", opposite = "parentBehaviouralEntity", derived = true)
    public Collection<LocalVariable> getLocalVariables() {
        if (localVariables == null) {
            localVariables = new MultivalueSet<LocalVariable>() {
                @Override
                protected void clearOpposite(LocalVariable e) {
                    e.setParentBehaviouralEntity(null);
                }
                @Override
                protected void setOpposite(LocalVariable e) {
                    e.setParentBehaviouralEntity(BehaviouralEntity.this);
                }
            };
        }
        return localVariables;
    }
    
    public void setLocalVariables(Collection<? extends LocalVariable> localVariables) {
        this.getLocalVariables().clear();
        this.getLocalVariables().addAll(localVariables);
    }                    
    
        
    public void addLocalVariables(LocalVariable one) {
        this.getLocalVariables().add(one);
    }   
    
    public void addLocalVariables(LocalVariable one, LocalVariable... many) {
        this.getLocalVariables().add(one);
        for (LocalVariable each : many)
            this.getLocalVariables().add(each);
    }   
    
    public void addLocalVariables(Iterable<? extends LocalVariable> many) {
        for (LocalVariable each : many)
            this.getLocalVariables().add(each);
    }   
                
    public void addLocalVariables(LocalVariable[] many) {
        for (LocalVariable each : many)
            this.getLocalVariables().add(each);
    }
    
    public int numberOfLocalVariables() {
        return getLocalVariables().size();
    }

    public boolean hasLocalVariables() {
        return !getLocalVariables().isEmpty();
    }
    
                
    private Number numberOfStatements;
    
    @FameProperty(name = "numberOfStatements")
    public Number getNumberOfStatements() {
        return numberOfStatements;
    }

    public void setNumberOfStatements(Number numberOfStatements) {
        this.numberOfStatements = numberOfStatements;
    }
    
    private Number numberOfLinesOfCode;
    
    @FameProperty(name = "numberOfLinesOfCode")
    public Number getNumberOfLinesOfCode() {
        return numberOfLinesOfCode;
    }

    public void setNumberOfLinesOfCode(Number numberOfLinesOfCode) {
        this.numberOfLinesOfCode = numberOfLinesOfCode;
    }
    
    @FameProperty(name = "clientBehaviours", derived = true)
    public Collection<BehaviouralEntity> getClientBehaviours() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
        
    @FameProperty(name = "numberOfOutgoingInvocations", derived = true)
    public Number getNumberOfOutgoingInvocations() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    private Collection<Reference> outgoingReferences; 

    @FameProperty(name = "outgoingReferences", opposite = "source", derived = true)
    public Collection<Reference> getOutgoingReferences() {
        if (outgoingReferences == null) {
            outgoingReferences = new MultivalueSet<Reference>() {
                @Override
                protected void clearOpposite(Reference e) {
                    e.setSource(null);
                }
                @Override
                protected void setOpposite(Reference e) {
                    e.setSource(BehaviouralEntity.this);
                }
            };
        }
        return outgoingReferences;
    }
    
    public void setOutgoingReferences(Collection<? extends Reference> outgoingReferences) {
        this.getOutgoingReferences().clear();
        this.getOutgoingReferences().addAll(outgoingReferences);
    }                    
    
        
    public void addOutgoingReferences(Reference one) {
        this.getOutgoingReferences().add(one);
    }   
    
    public void addOutgoingReferences(Reference one, Reference... many) {
        this.getOutgoingReferences().add(one);
        for (Reference each : many)
            this.getOutgoingReferences().add(each);
    }   
    
    public void addOutgoingReferences(Iterable<? extends Reference> many) {
        for (Reference each : many)
            this.getOutgoingReferences().add(each);
    }   
                
    public void addOutgoingReferences(Reference[] many) {
        for (Reference each : many)
            this.getOutgoingReferences().add(each);
    }
    
    public int numberOfOutgoingReferences() {
        return getOutgoingReferences().size();
    }

    public boolean hasOutgoingReferences() {
        return !getOutgoingReferences().isEmpty();
    }
                
    private Collection<Invocation> outgoingInvocations; 

    @FameProperty(name = "outgoingInvocations", opposite = "sender", derived = true)
    public Collection<Invocation> getOutgoingInvocations() {
        if (outgoingInvocations == null) {
            outgoingInvocations = new MultivalueSet<Invocation>() {
                @Override
                protected void clearOpposite(Invocation e) {
                    e.setSender(null);
                }
                @Override
                protected void setOpposite(Invocation e) {
                    e.setSender(BehaviouralEntity.this);
                }
            };
        }
        return outgoingInvocations;
    }
    
    public void setOutgoingInvocations(Collection<? extends Invocation> outgoingInvocations) {
        this.getOutgoingInvocations().clear();
        this.getOutgoingInvocations().addAll(outgoingInvocations);
    }                    
    
        
    public void addOutgoingInvocations(Invocation one) {
        this.getOutgoingInvocations().add(one);
    }   
    
    public void addOutgoingInvocations(Invocation one, Invocation... many) {
        this.getOutgoingInvocations().add(one);
        for (Invocation each : many)
            this.getOutgoingInvocations().add(each);
    }   
    
    public void addOutgoingInvocations(Iterable<? extends Invocation> many) {
        for (Invocation each : many)
            this.getOutgoingInvocations().add(each);
    }   
                
    public void addOutgoingInvocations(Invocation[] many) {
        for (Invocation each : many)
            this.getOutgoingInvocations().add(each);
    }
    
    public int numberOfOutgoingInvocations() {
        return getOutgoingInvocations().size();
    }

    public boolean hasOutgoingInvocations() {
        return !getOutgoingInvocations().isEmpty();
    }
    
                
    private Collection<Invocation> incomingInvocations; 

    @FameProperty(name = "incomingInvocations", opposite = "candidates", derived = true)
    public Collection<Invocation> getIncomingInvocations() {
        if (incomingInvocations == null) {
            incomingInvocations = new MultivalueSet<Invocation>() {
                @Override
                protected void clearOpposite(Invocation e) {
                    e.getCandidates().remove(BehaviouralEntity.this);
                }
                @Override
                protected void setOpposite(Invocation e) {
                    e.getCandidates().add(BehaviouralEntity.this);
                }
            };
        }
        return incomingInvocations;
    }
    
    public void setIncomingInvocations(Collection<? extends Invocation> incomingInvocations) {
        this.getIncomingInvocations().clear();
        this.getIncomingInvocations().addAll(incomingInvocations);
    }
    
    public void addIncomingInvocations(Invocation one) {
        this.getIncomingInvocations().add(one);
    }   
    
    public void addIncomingInvocations(Invocation one, Invocation... many) {
        this.getIncomingInvocations().add(one);
        for (Invocation each : many)
            this.getIncomingInvocations().add(each);
    }   
    
    public void addIncomingInvocations(Iterable<? extends Invocation> many) {
        for (Invocation each : many)
            this.getIncomingInvocations().add(each);
    }   
                
    public void addIncomingInvocations(Invocation[] many) {
        for (Invocation each : many)
            this.getIncomingInvocations().add(each);
    }
    
    public int numberOfIncomingInvocations() {
        return getIncomingInvocations().size();
    }

    public boolean hasIncomingInvocations() {
        return !getIncomingInvocations().isEmpty();
    }
    
                
    private Number numberOfConditionals;
    
    @FameProperty(name = "numberOfConditionals")
    public Number getNumberOfConditionals() {
        return numberOfConditionals;
    }

    public void setNumberOfConditionals(Number numberOfConditionals) {
        this.numberOfConditionals = numberOfConditionals;
    }
    
    private Collection<Access> accesses; 

    @FameProperty(name = "accesses", opposite = "accessor", derived = true)
    public Collection<Access> getAccesses() {
        if (accesses == null) {
            accesses = new MultivalueSet<Access>() {
                @Override
                protected void clearOpposite(Access e) {
                    e.setAccessor(null);
                }
                @Override
                protected void setOpposite(Access e) {
                    e.setAccessor(BehaviouralEntity.this);
                }
            };
        }
        return accesses;
    }
    
    public void setAccesses(Collection<? extends Access> accesses) {
        this.getAccesses().clear();
        this.getAccesses().addAll(accesses);
    }                    
    
        
    public void addAccesses(Access one) {
        this.getAccesses().add(one);
    }   
    
    public void addAccesses(Access one, Access... many) {
        this.getAccesses().add(one);
        for (Access each : many)
            this.getAccesses().add(each);
    }   
    
    public void addAccesses(Iterable<? extends Access> many) {
        for (Access each : many)
            this.getAccesses().add(each);
    }   
                
    public void addAccesses(Access[] many) {
        for (Access each : many)
            this.getAccesses().add(each);
    }
    
    public int numberOfAccesses() {
        return getAccesses().size();
    }

    public boolean hasAccesses() {
        return !getAccesses().isEmpty();
    }
    
                
    private Collection<Parameter> parameters; 

    @FameProperty(name = "parameters", opposite = "parentBehaviouralEntity", derived = true)
    public Collection<Parameter> getParameters() {
        if (parameters == null) {
            parameters = new MultivalueSet<Parameter>() {
                @Override
                protected void clearOpposite(Parameter e) {
                    e.setParentBehaviouralEntity(null);
                }
                @Override
                protected void setOpposite(Parameter e) {
                    e.setParentBehaviouralEntity(BehaviouralEntity.this);
                }
            };
        }
        return parameters;
    }
    
    public void setParameters(Collection<? extends Parameter> parameters) {
        this.getParameters().clear();
        this.getParameters().addAll(parameters);
    }                    
    
        
    public void addParameters(Parameter one) {
        this.getParameters().add(one);
    }   
    
    public void addParameters(Parameter one, Parameter... many) {
        this.getParameters().add(one);
        for (Parameter each : many)
            this.getParameters().add(each);
    }   
    
    public void addParameters(Iterable<? extends Parameter> many) {
        for (Parameter each : many)
            this.getParameters().add(each);
    }   
                
    public void addParameters(Parameter[] many) {
        for (Parameter each : many)
            this.getParameters().add(each);
    }
    
    public int numberOfParameters() {
        return getParameters().size();
    }

    public boolean hasParameters() {
        return !getParameters().isEmpty();
    }
    
                


}


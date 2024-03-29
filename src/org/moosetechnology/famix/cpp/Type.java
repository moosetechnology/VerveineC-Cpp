// Automagically generated code, please do not change
package org.moosetechnology.famix.cpp;

import ch.akuhn.fame.internal.MultivalueSet;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.FameDescription;
import java.util.*;

import org.moosetechnology.famix.cpp.Attribute;
import org.moosetechnology.famix.cpp.BehaviouralEntity;
import org.moosetechnology.famix.cpp.ContainerEntity;
import org.moosetechnology.famix.cpp.Inheritance;
import org.moosetechnology.famix.cpp.Method;
import org.moosetechnology.famix.cpp.NamedEntity;
import org.moosetechnology.famix.cpp.ParameterizedType;
import org.moosetechnology.famix.cpp.Reference;
import org.moosetechnology.famix.cpp.StructuralEntity;
import org.moosetechnology.famix.cpp.Type;
import org.moosetechnology.famix.cpp.TypeAlias;

import ch.akuhn.fame.FamePackage;


@FamePackage("FAMIX")
@FameDescription("Type")
public class Type extends ContainerEntity {


	@Override
	public NamedEntity getBelongsTo() {
		return getContainer();
	}

	@Override
	public void setBelongsTo(NamedEntity belongsTo) {
		setContainer((ContainerEntity) belongsTo);
		
	}
    

    private ContainerEntity container;
    
    @FameProperty(name = "container", opposite = "types", container = true)
    public ContainerEntity getContainer() {
        return container;
    }

    public void setContainer(ContainerEntity container) {
        if (this.container != null) {
            if (this.container.equals(container)) return;
            this.container.getTypes().remove(this);
        }
        this.container = container;
        if (container == null) return;
        container.getTypes().add(this);
    }
    
    @FameProperty(name = "tightClassCohesion", derived = true)
    public Number getTightClassCohesion() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfMethodProtocols", derived = true)
    public Number getNumberOfMethodProtocols() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfAnnotationInstances", derived = true)
    public Number getNumberOfAnnotationInstances() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfMethodsInherited", derived = true)
    public Number getNumberOfMethodsInherited() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    private Collection<Reference> incomingReferences; 

    @FameProperty(name = "incomingReferences", opposite = "target", derived = true)
    public Collection<Reference> getIncomingReferences() {
        if (incomingReferences == null) {
            incomingReferences = new MultivalueSet<Reference>() {
                @Override
                protected void clearOpposite(Reference e) {
                    e.setTarget(null);
                }
                @Override
                protected void setOpposite(Reference e) {
                    e.setTarget(Type.this);
                }
            };
        }
        return incomingReferences;
    }
    
    public void setIncomingReferences(Collection<? extends Reference> incomingReferences) {
        this.getIncomingReferences().clear();
        this.getIncomingReferences().addAll(incomingReferences);
    }                    
    
        
    public void addIncomingReferences(Reference one) {
        this.getIncomingReferences().add(one);
    }   
    
    public void addIncomingReferences(Reference one, Reference... many) {
        this.getIncomingReferences().add(one);
        for (Reference each : many)
            this.getIncomingReferences().add(each);
    }   
    
    public void addIncomingReferences(Iterable<? extends Reference> many) {
        for (Reference each : many)
            this.getIncomingReferences().add(each);
    }   
                
    public void addIncomingReferences(Reference[] many) {
        for (Reference each : many)
            this.getIncomingReferences().add(each);
    }
    
    public int numberOfIncomingReferences() {
        return getIncomingReferences().size();
    }

    public boolean hasIncomingReferences() {
        return !getIncomingReferences().isEmpty();
    }
    
                
    @FameProperty(name = "totalNumberOfChildren", derived = true)
    public Number getTotalNumberOfChildren() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    private Collection<StructuralEntity> structuresWithDeclaredType; 

    @FameProperty(name = "structuresWithDeclaredType", opposite = "declaredType", derived = true)
    public Collection<StructuralEntity> getStructuresWithDeclaredType() {
        if (structuresWithDeclaredType == null) {
            structuresWithDeclaredType = new MultivalueSet<StructuralEntity>() {
                @Override
                protected void clearOpposite(StructuralEntity e) {
                    e.setDeclaredType(null);
                }
                @Override
                protected void setOpposite(StructuralEntity e) {
                    e.setDeclaredType(Type.this);
                }
            };
        }
        return structuresWithDeclaredType;
    }
    
    public void setStructuresWithDeclaredType(Collection<? extends StructuralEntity> structuresWithDeclaredType) {
        this.getStructuresWithDeclaredType().clear();
        this.getStructuresWithDeclaredType().addAll(structuresWithDeclaredType);
    }                    
    
        
    public void addStructuresWithDeclaredType(StructuralEntity one) {
        this.getStructuresWithDeclaredType().add(one);
    }   
    
    public void addStructuresWithDeclaredType(StructuralEntity one, StructuralEntity... many) {
        this.getStructuresWithDeclaredType().add(one);
        for (StructuralEntity each : many)
            this.getStructuresWithDeclaredType().add(each);
    }   
    
    public void addStructuresWithDeclaredType(Iterable<? extends StructuralEntity> many) {
        for (StructuralEntity each : many)
            this.getStructuresWithDeclaredType().add(each);
    }   
                
    public void addStructuresWithDeclaredType(StructuralEntity[] many) {
        for (StructuralEntity each : many)
            this.getStructuresWithDeclaredType().add(each);
    }
    
    public int numberOfStructuresWithDeclaredType() {
        return getStructuresWithDeclaredType().size();
    }

    public boolean hasStructuresWithDeclaredType() {
        return !getStructuresWithDeclaredType().isEmpty();
    }
    
                
    @FameProperty(name = "numberOfAccessorMethods", derived = true)
    public Number getNumberOfAccessorMethods() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfAbstractMethods", derived = true)
    public Number getNumberOfAbstractMethods() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfPrivateAttributes", derived = true)
    public Number getNumberOfPrivateAttributes() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "fanOut", derived = true)
    public Number getFanOut() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfAttributesInherited", derived = true)
    public Number getNumberOfAttributesInherited() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfPrivateMethods", derived = true)
    public Number getNumberOfPrivateMethods() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "providerTypes", derived = true)
    public Collection<Type> getProviderTypes() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
        
    @FameProperty(name = "numberOfMessageSends", derived = true)
    public Number getNumberOfMessageSends() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    private Collection<TypeAlias> typeAliases; 

    @FameProperty(name = "typeAliases", opposite = "aliasedType", derived = true)
    public Collection<TypeAlias> getTypeAliases() {
        if (typeAliases == null) {
            typeAliases = new MultivalueSet<TypeAlias>() {
                @Override
                protected void clearOpposite(TypeAlias e) {
                    e.setAliasedType(null);
                }
                @Override
                protected void setOpposite(TypeAlias e) {
                    e.setAliasedType(Type.this);
                }
            };
        }
        return typeAliases;
    }
    
    public void setTypeAliases(Collection<? extends TypeAlias> typeAliases) {
        this.getTypeAliases().clear();
        this.getTypeAliases().addAll(typeAliases);
    }                    
    
        
    public void addTypeAliases(TypeAlias one) {
        this.getTypeAliases().add(one);
    }   
    
    public void addTypeAliases(TypeAlias one, TypeAlias... many) {
        this.getTypeAliases().add(one);
        for (TypeAlias each : many)
            this.getTypeAliases().add(each);
    }   
    
    public void addTypeAliases(Iterable<? extends TypeAlias> many) {
        for (TypeAlias each : many)
            this.getTypeAliases().add(each);
    }   
                
    public void addTypeAliases(TypeAlias[] many) {
        for (TypeAlias each : many)
            this.getTypeAliases().add(each);
    }
    
    public int numberOfTypeAliases() {
        return getTypeAliases().size();
    }

    public boolean hasTypeAliases() {
        return !getTypeAliases().isEmpty();
    }
    
                
    @FameProperty(name = "numberOfProtectedAttributes", derived = true)
    public Number getNumberOfProtectedAttributes() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfStatements", derived = true)
    public Number getNumberOfStatements() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfChildren", derived = true)
    public Number getNumberOfChildren() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfMethodsOverriden", derived = true)
    public Number getNumberOfMethodsOverriden() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfConstructorMethods", derived = true)
    public Number getNumberOfConstructorMethods() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfDirectSubclasses", derived = true)
    public Number getNumberOfDirectSubclasses() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    private Collection<Inheritance> superInheritances; 

    @FameProperty(name = "superInheritances", opposite = "subclass", derived = true)
    public Collection<Inheritance> getSuperInheritances() {
        if (superInheritances == null) {
            superInheritances = new MultivalueSet<Inheritance>() {
                @Override
                protected void clearOpposite(Inheritance e) {
                    e.setSubclass(null);
                }
                @Override
                protected void setOpposite(Inheritance e) {
                    e.setSubclass(Type.this);
                }
            };
        }
        return superInheritances;
    }
    
    public void setSuperInheritances(Collection<? extends Inheritance> superInheritances) {
        this.getSuperInheritances().clear();
        this.getSuperInheritances().addAll(superInheritances);
    }                    
    
        
    public void addSuperInheritances(Inheritance one) {
        this.getSuperInheritances().add(one);
    }   
    
    public void addSuperInheritances(Inheritance one, Inheritance... many) {
        this.getSuperInheritances().add(one);
        for (Inheritance each : many)
            this.getSuperInheritances().add(each);
    }   
    
    public void addSuperInheritances(Iterable<? extends Inheritance> many) {
        for (Inheritance each : many)
            this.getSuperInheritances().add(each);
    }   
                
    public void addSuperInheritances(Inheritance[] many) {
        for (Inheritance each : many)
            this.getSuperInheritances().add(each);
    }
    
    public int numberOfSuperInheritances() {
        return getSuperInheritances().size();
    }

    public boolean hasSuperInheritances() {
        return !getSuperInheritances().isEmpty();
    }
    
                
    private Collection<Inheritance> subInheritances; 

    @FameProperty(name = "subInheritances", opposite = "superclass", derived = true)
    public Collection<Inheritance> getSubInheritances() {
        if (subInheritances == null) {
            subInheritances = new MultivalueSet<Inheritance>() {
                @Override
                protected void clearOpposite(Inheritance e) {
                    e.setSuperclass(null);
                }
                @Override
                protected void setOpposite(Inheritance e) {
                    e.setSuperclass(Type.this);
                }
            };
        }
        return subInheritances;
    }
    
    public void setSubInheritances(Collection<? extends Inheritance> subInheritances) {
        this.getSubInheritances().clear();
        this.getSubInheritances().addAll(subInheritances);
    }                    
    
        
    public void addSubInheritances(Inheritance one) {
        this.getSubInheritances().add(one);
    }   
    
    public void addSubInheritances(Inheritance one, Inheritance... many) {
        this.getSubInheritances().add(one);
        for (Inheritance each : many)
            this.getSubInheritances().add(each);
    }   
    
    public void addSubInheritances(Iterable<? extends Inheritance> many) {
        for (Inheritance each : many)
            this.getSubInheritances().add(each);
    }   
                
    public void addSubInheritances(Inheritance[] many) {
        for (Inheritance each : many)
            this.getSubInheritances().add(each);
    }
    
    public int numberOfSubInheritances() {
        return getSubInheritances().size();
    }

    public boolean hasSubInheritances() {
        return !getSubInheritances().isEmpty();
    }
    
                
    @FameProperty(name = "hierarchyNestingLevel", derived = true)
    public Number getHierarchyNestingLevel() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfProtectedMethods", derived = true)
    public Number getNumberOfProtectedMethods() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfAttributes", derived = true)
    public Number getNumberOfAttributes() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    private Collection<BehaviouralEntity> behavioursWithDeclaredType; 

    @FameProperty(name = "behavioursWithDeclaredType", opposite = "declaredType", derived = true)
    public Collection<BehaviouralEntity> getBehavioursWithDeclaredType() {
        if (behavioursWithDeclaredType == null) {
            behavioursWithDeclaredType = new MultivalueSet<BehaviouralEntity>() {
                @Override
                protected void clearOpposite(BehaviouralEntity e) {
                    e.setDeclaredType(null);
                }
                @Override
                protected void setOpposite(BehaviouralEntity e) {
                    e.setDeclaredType(Type.this);
                }
            };
        }
        return behavioursWithDeclaredType;
    }
    
    public void setBehavioursWithDeclaredType(Collection<? extends BehaviouralEntity> behavioursWithDeclaredType) {
        this.getBehavioursWithDeclaredType().clear();
        this.getBehavioursWithDeclaredType().addAll(behavioursWithDeclaredType);
    }                    
    
        
    public void addBehavioursWithDeclaredType(BehaviouralEntity one) {
        this.getBehavioursWithDeclaredType().add(one);
    }   
    
    public void addBehavioursWithDeclaredType(BehaviouralEntity one, BehaviouralEntity... many) {
        this.getBehavioursWithDeclaredType().add(one);
        for (BehaviouralEntity each : many)
            this.getBehavioursWithDeclaredType().add(each);
    }   
    
    public void addBehavioursWithDeclaredType(Iterable<? extends BehaviouralEntity> many) {
        for (BehaviouralEntity each : many)
            this.getBehavioursWithDeclaredType().add(each);
    }   
                
    public void addBehavioursWithDeclaredType(BehaviouralEntity[] many) {
        for (BehaviouralEntity each : many)
            this.getBehavioursWithDeclaredType().add(each);
    }
    
    public int numberOfBehavioursWithDeclaredType() {
        return getBehavioursWithDeclaredType().size();
    }

    public boolean hasBehavioursWithDeclaredType() {
        return !getBehavioursWithDeclaredType().isEmpty();
    }
    
                
    private Collection<Method> methods; 

    @FameProperty(name = "methods", opposite = "parentType", derived = true)
    public Collection<Method> getMethods() {
        if (methods == null) {
            methods = new MultivalueSet<Method>() {
                @Override
                protected void clearOpposite(Method e) {
                    e.setParentType(null);
                }
                @Override
                protected void setOpposite(Method e) {
                    e.setParentType(Type.this);
                }
            };
        }
        return methods;
    }
    
    public void setMethods(Collection<? extends Method> methods) {
        this.getMethods().clear();
        this.getMethods().addAll(methods);
    }                    
    
        
    public void addMethods(Method one) {
        this.getMethods().add(one);
    }   
    
    public void addMethods(Method one, Method... many) {
        this.getMethods().add(one);
        for (Method each : many)
            this.getMethods().add(each);
    }   
    
    public void addMethods(Iterable<? extends Method> many) {
        for (Method each : many)
            this.getMethods().add(each);
    }   
                
    public void addMethods(Method[] many) {
        for (Method each : many)
            this.getMethods().add(each);
    }
    
    public int numberOfMethods() {
        return getMethods().size();
    }

    public boolean hasMethods() {
        return !getMethods().isEmpty();
    }
    
                
    @FameProperty(name = "numberOfMethodsAdded", derived = true)
    public Number getNumberOfMethodsAdded() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfParents", derived = true)
    public Number getNumberOfParents() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfComments", derived = true)
    public Number getNumberOfComments() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "fanIn", derived = true)
    public Number getFanIn() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfRevealedAttributes", derived = true)
    public Number getNumberOfRevealedAttributes() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "subclassHierarchyDepth", derived = true)
    public Number getSubclassHierarchyDepth() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfPublicAttributes", derived = true)
    public Number getNumberOfPublicAttributes() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfDuplicatedLinesOfCodeInternally", derived = true)
    public Number getNumberOfDuplicatedLinesOfCodeInternally() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "clientTypes", derived = true)
    public Collection<Type> getClientTypes() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
        
    @FameProperty(name = "weightedMethodCount", derived = true)
    public Number getWeightedMethodCount() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "isAbstract", derived = true)
    public Boolean getIsAbstract() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "weightOfAClass", derived = true)
    public Number getWeightOfAClass() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfMethods", derived = true)
    public Number getNumberOfMethods() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfAccessesToForeignData", derived = true)
    public Number getNumberOfAccessesToForeignData() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfPublicMethods", derived = true)
    public Number getNumberOfPublicMethods() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfLinesOfCode", derived = true)
    public Number getNumberOfLinesOfCode() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "isJUnit4TestCase", derived = true)
    public Boolean getIsJUnit4TestCase() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "isTestCase", derived = true)
    public Boolean getIsTestCase() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    private Collection<ParameterizedType> argumentsInParameterizedTypes; 

    @FameProperty(name = "argumentsInParameterizedTypes", opposite = "arguments", derived = true)
    public Collection<ParameterizedType> getArgumentsInParameterizedTypes() {
        if (argumentsInParameterizedTypes == null) {
            argumentsInParameterizedTypes = new MultivalueSet<ParameterizedType>() {
                @Override
                protected void clearOpposite(ParameterizedType e) {
                    e.getArguments().remove(Type.this);
                }
                @Override
                protected void setOpposite(ParameterizedType e) {
                    e.getArguments().add(Type.this);
                }
            };
        }
        return argumentsInParameterizedTypes;
    }
    
    public void setArgumentsInParameterizedTypes(Collection<? extends ParameterizedType> argumentsInParameterizedTypes) {
        this.getArgumentsInParameterizedTypes().clear();
        this.getArgumentsInParameterizedTypes().addAll(argumentsInParameterizedTypes);
    }
    
    public void addArgumentsInParameterizedTypes(ParameterizedType one) {
        this.getArgumentsInParameterizedTypes().add(one);
    }   
    
    public void addArgumentsInParameterizedTypes(ParameterizedType one, ParameterizedType... many) {
        this.getArgumentsInParameterizedTypes().add(one);
        for (ParameterizedType each : many)
            this.getArgumentsInParameterizedTypes().add(each);
    }   
    
    public void addArgumentsInParameterizedTypes(Iterable<? extends ParameterizedType> many) {
        for (ParameterizedType each : many)
            this.getArgumentsInParameterizedTypes().add(each);
    }   
                
    public void addArgumentsInParameterizedTypes(ParameterizedType[] many) {
        for (ParameterizedType each : many)
            this.getArgumentsInParameterizedTypes().add(each);
    }
    
    public int numberOfArgumentsInParameterizedTypes() {
        return getArgumentsInParameterizedTypes().size();
    }

    public boolean hasArgumentsInParameterizedTypes() {
        return !getArgumentsInParameterizedTypes().isEmpty();
    }
    
                
    private Collection<Attribute> attributes; 

    @FameProperty(name = "attributes", opposite = "parentType", derived = true)
    public Collection<Attribute> getAttributes() {
        if (attributes == null) {
            attributes = new MultivalueSet<Attribute>() {
                @Override
                protected void clearOpposite(Attribute e) {
                    e.setParentType(null);
                }
                @Override
                protected void setOpposite(Attribute e) {
                    e.setParentType(Type.this);
                }
            };
        }
        return attributes;
    }
    
    public void setAttributes(Collection<? extends Attribute> attributes) {
        this.getAttributes().clear();
        this.getAttributes().addAll(attributes);
    }                    
    
        
    public void addAttributes(Attribute one) {
        this.getAttributes().add(one);
    }   
    
    public void addAttributes(Attribute one, Attribute... many) {
        this.getAttributes().add(one);
        for (Attribute each : many)
            this.getAttributes().add(each);
    }   
    
    public void addAttributes(Iterable<? extends Attribute> many) {
        for (Attribute each : many)
            this.getAttributes().add(each);
    }   
                
    public void addAttributes(Attribute[] many) {
        for (Attribute each : many)
            this.getAttributes().add(each);
    }
    
    public int numberOfAttributes() {
        return getAttributes().size();
    }

    public boolean hasAttributes() {
        return !getAttributes().isEmpty();
    }
    
                
    @FameProperty(name = "isInnerClass", derived = true)
    public Boolean getIsInnerClass() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfMethodsInHierarchy", derived = true)
    public Number getNumberOfMethodsInHierarchy() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }



}


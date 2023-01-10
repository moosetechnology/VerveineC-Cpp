// Automagically generated code, please do not change
package org.moosetechnology.famix.famixcentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;
import org.moosetechnology.famix.famixtraits.TFunction;
import org.moosetechnology.famix.famixtraits.TLocalVariable;
import org.moosetechnology.famix.famixtraits.TType;
import org.moosetechnology.famix.famixtraits.TWithFunctions;
import org.moosetechnology.famix.famixtraits.TWithLocalVariables;
import org.moosetechnology.famix.famixtraits.TWithTypes;


@FamePackage("Famix-C-Entities")
@FameDescription("ContainerEntity")
public class ContainerEntity extends NamedEntity implements TWithFunctions, TWithLocalVariables, TWithTypes {

    private Collection<TFunction> functions; 

    private Collection<TLocalVariable> localVariables; 

    private Collection<TType> types; 



    @FameProperty(name = "functions", opposite = "functionOwner", derived = true)
    public Collection<TFunction> getFunctions() {
        if (functions == null) {
            functions = new MultivalueSet<TFunction>() {
                @Override
                protected void clearOpposite(TFunction e) {
                    e.setFunctionOwner(null);
                }
                @Override
                protected void setOpposite(TFunction e) {
                    e.setFunctionOwner(ContainerEntity.this);
                }
            };
        }
        return functions;
    }
    
    public void setFunctions(Collection<? extends TFunction> functions) {
        this.getFunctions().clear();
        this.getFunctions().addAll(functions);
    }                    
    
        
    public void addFunctions(TFunction one) {
        this.getFunctions().add(one);
    }   
    
    public void addFunctions(TFunction one, TFunction... many) {
        this.getFunctions().add(one);
        for (TFunction each : many)
            this.getFunctions().add(each);
    }   
    
    public void addFunctions(Iterable<? extends TFunction> many) {
        for (TFunction each : many)
            this.getFunctions().add(each);
    }   
                
    public void addFunctions(TFunction[] many) {
        for (TFunction each : many)
            this.getFunctions().add(each);
    }
    
    public int numberOfFunctions() {
        return getFunctions().size();
    }

    public boolean hasFunctions() {
        return !getFunctions().isEmpty();
    }

    @FameProperty(name = "localVariables", opposite = "parentBehaviouralEntity", derived = true)
    public Collection<TLocalVariable> getLocalVariables() {
        if (localVariables == null) {
            localVariables = new MultivalueSet<TLocalVariable>() {
                @Override
                protected void clearOpposite(TLocalVariable e) {
                    e.setParentBehaviouralEntity(null);
                }
                @Override
                protected void setOpposite(TLocalVariable e) {
                    e.setParentBehaviouralEntity(ContainerEntity.this);
                }
            };
        }
        return localVariables;
    }
    
    public void setLocalVariables(Collection<? extends TLocalVariable> localVariables) {
        this.getLocalVariables().clear();
        this.getLocalVariables().addAll(localVariables);
    }                    
    
        
    public void addLocalVariables(TLocalVariable one) {
        this.getLocalVariables().add(one);
    }   
    
    public void addLocalVariables(TLocalVariable one, TLocalVariable... many) {
        this.getLocalVariables().add(one);
        for (TLocalVariable each : many)
            this.getLocalVariables().add(each);
    }   
    
    public void addLocalVariables(Iterable<? extends TLocalVariable> many) {
        for (TLocalVariable each : many)
            this.getLocalVariables().add(each);
    }   
                
    public void addLocalVariables(TLocalVariable[] many) {
        for (TLocalVariable each : many)
            this.getLocalVariables().add(each);
    }
    
    public int numberOfLocalVariables() {
        return getLocalVariables().size();
    }

    public boolean hasLocalVariables() {
        return !getLocalVariables().isEmpty();
    }

    @FameProperty(name = "types", opposite = "typeContainer", derived = true)
    public Collection<TType> getTypes() {
        if (types == null) {
            types = new MultivalueSet<TType>() {
                @Override
                protected void clearOpposite(TType e) {
                    e.setTypeContainer(null);
                }
                @Override
                protected void setOpposite(TType e) {
                    e.setTypeContainer(ContainerEntity.this);
                }
            };
        }
        return types;
    }
    
    public void setTypes(Collection<? extends TType> types) {
        this.getTypes().clear();
        this.getTypes().addAll(types);
    }                    
    
        
    public void addTypes(TType one) {
        this.getTypes().add(one);
    }   
    
    public void addTypes(TType one, TType... many) {
        this.getTypes().add(one);
        for (TType each : many)
            this.getTypes().add(each);
    }   
    
    public void addTypes(Iterable<? extends TType> many) {
        for (TType each : many)
            this.getTypes().add(each);
    }   
                
    public void addTypes(TType[] many) {
        for (TType each : many)
            this.getTypes().add(each);
    }
    
    public int numberOfTypes() {
        return getTypes().size();
    }

    public boolean hasTypes() {
        return !getTypes().isEmpty();
    }



}


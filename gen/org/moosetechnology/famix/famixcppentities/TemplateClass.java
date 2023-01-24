// Automagically generated code, please do not change
package org.moosetechnology.famix.famixcppentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.internal.MultivalueSet;
import java.util.*;
import org.moosetechnology.famix.famixtraits.TParameterizedType;
import org.moosetechnology.famix.famixtraits.TWithParameterizedTypes;


@FamePackage("Famix-Cpp-Entities")
@FameDescription("TemplateClass")
public class TemplateClass extends Class implements TWithParameterizedTypes {

    private Collection<TemplateParameterType> parameterTypes; 

    private Collection<TParameterizedType> parameterizedTypes; 



    @FameProperty(name = "parameterTypes", opposite = "parentParameterizableClass", derived = true)
    public Collection<TemplateParameterType> getParameterTypes() {
        if (parameterTypes == null) {
            parameterTypes = new MultivalueSet<TemplateParameterType>() {
                @Override
                protected void clearOpposite(TemplateParameterType e) {
                    e.setParentParameterizableClass(null);
                }
                @Override
                protected void setOpposite(TemplateParameterType e) {
                    e.setParentParameterizableClass(TemplateClass.this);
                }
            };
        }
        return parameterTypes;
    }
    
    public void setParameterTypes(Collection<? extends TemplateParameterType> parameterTypes) {
        this.getParameterTypes().clear();
        this.getParameterTypes().addAll(parameterTypes);
    }                    
    
        
    public void addParameterTypes(TemplateParameterType one) {
        this.getParameterTypes().add(one);
    }   
    
    public void addParameterTypes(TemplateParameterType one, TemplateParameterType... many) {
        this.getParameterTypes().add(one);
        for (TemplateParameterType each : many)
            this.getParameterTypes().add(each);
    }   
    
    public void addParameterTypes(Iterable<? extends TemplateParameterType> many) {
        for (TemplateParameterType each : many)
            this.getParameterTypes().add(each);
    }   
                
    public void addParameterTypes(TemplateParameterType[] many) {
        for (TemplateParameterType each : many)
            this.getParameterTypes().add(each);
    }
    
    public int numberOfParameterTypes() {
        return getParameterTypes().size();
    }

    public boolean hasParameterTypes() {
        return !getParameterTypes().isEmpty();
    }

    @FameProperty(name = "parameterizedTypes", opposite = "parameterizableClass", derived = true)
    public Collection<TParameterizedType> getParameterizedTypes() {
        if (parameterizedTypes == null) {
            parameterizedTypes = new MultivalueSet<TParameterizedType>() {
                @Override
                protected void clearOpposite(TParameterizedType e) {
                    e.setParameterizableClass(null);
                }
                @Override
                protected void setOpposite(TParameterizedType e) {
                    e.setParameterizableClass(TemplateClass.this);
                }
            };
        }
        return parameterizedTypes;
    }
    
    public void setParameterizedTypes(Collection<? extends TParameterizedType> parameterizedTypes) {
        this.getParameterizedTypes().clear();
        this.getParameterizedTypes().addAll(parameterizedTypes);
    }                    
    
        
    public void addParameterizedTypes(TParameterizedType one) {
        this.getParameterizedTypes().add(one);
    }   
    
    public void addParameterizedTypes(TParameterizedType one, TParameterizedType... many) {
        this.getParameterizedTypes().add(one);
        for (TParameterizedType each : many)
            this.getParameterizedTypes().add(each);
    }   
    
    public void addParameterizedTypes(Iterable<? extends TParameterizedType> many) {
        for (TParameterizedType each : many)
            this.getParameterizedTypes().add(each);
    }   
                
    public void addParameterizedTypes(TParameterizedType[] many) {
        for (TParameterizedType each : many)
            this.getParameterizedTypes().add(each);
    }
    
    public int numberOfParameterizedTypes() {
        return getParameterizedTypes().size();
    }

    public boolean hasParameterizedTypes() {
        return !getParameterizedTypes().isEmpty();
    }



}


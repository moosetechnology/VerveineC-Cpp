// Automagically generated code, please do not change
package org.moosetechnology.famix.cpp;

import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.FameDescription;
import java.util.*;

import org.moosetechnology.famix.cpp.Association;
import org.moosetechnology.famix.cpp.Invocation;

import ch.akuhn.fame.FamePackage;


@FamePackage("FAMIX")
@FameDescription("InvocationWithArgs")
public class InvocationWithArgs extends Invocation {



    private Collection<Association> arguments; 

    @FameProperty(name = "arguments")
    public Collection<Association> getArguments() {
        if (arguments == null) arguments = new HashSet<Association>();
        return arguments;
    }
    
    public void setArguments(Collection<? extends Association> arguments) {
        this.getArguments().clear();
        this.getArguments().addAll(arguments);
    }                    

    public void addArguments(Association one) {
        this.getArguments().add(one);
    }   
    
    public void addArguments(Association one, Association... many) {
        this.getArguments().add(one);
        for (Association each : many)
            this.getArguments().add(each);
    }   
    
    public void addArguments(Iterable<? extends Association> many) {
        for (Association each : many)
            this.getArguments().add(each);
    }   
                
    public void addArguments(Association[] many) {
        for (Association each : many)
            this.getArguments().add(each);
    }
    
    public int numberOfArguments() {
        return getArguments().size();
    }

    public boolean hasArguments() {
        return !getArguments().isEmpty();
    }
    
                


}


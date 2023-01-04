// Automagically generated code, please do not change
package org.moosetechnology.famix.cpp;

import ch.akuhn.fame.FameProperty;

import org.moosetechnology.famix.cpp.Class;
import org.moosetechnology.famix.cpp.Entity;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;


@FamePackage("FAMIX")
@FameDescription("Exception")
public class Exception extends Entity {



    private Class exceptionClass;
    
    @FameProperty(name = "exceptionClass", opposite = "exceptions")
    public Class getExceptionClass() {
        return exceptionClass;
    }

    public void setExceptionClass(Class exceptionClass) {
        if (this.exceptionClass != null) {
            if (this.exceptionClass.equals(exceptionClass)) return;
            this.exceptionClass.getExceptions().remove(this);
        }
        this.exceptionClass = exceptionClass;
        if (exceptionClass == null) return;
        exceptionClass.getExceptions().add(this);
    }
    


}


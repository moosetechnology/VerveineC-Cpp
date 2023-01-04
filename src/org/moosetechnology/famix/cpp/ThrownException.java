// Automagically generated code, please do not change
package org.moosetechnology.famix.cpp;

import ch.akuhn.fame.FameProperty;

import org.moosetechnology.famix.cpp.Exception;
import org.moosetechnology.famix.cpp.Method;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;


@FamePackage("FAMIX")
@FameDescription("ThrownException")
public class ThrownException extends Exception {



    private Method definingMethod;
    
    @FameProperty(name = "definingMethod", opposite = "thrownExceptions")
    public Method getDefiningMethod() {
        return definingMethod;
    }

    public void setDefiningMethod(Method definingMethod) {
        if (this.definingMethod != null) {
            if (this.definingMethod.equals(definingMethod)) return;
            this.definingMethod.getThrownExceptions().remove(this);
        }
        this.definingMethod = definingMethod;
        if (definingMethod == null) return;
        definingMethod.getThrownExceptions().add(this);
    }
    


}


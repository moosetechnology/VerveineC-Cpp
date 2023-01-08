// Automagically generated code, please do not change
package org.moosetechnology.famix.famixcppentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.famix.famixcentities.Type;
import org.moosetechnology.famix.famixtraits.TParameterType;


@FamePackage("Famix-Cpp-Entities")
@FameDescription("ParameterType")
public class ParameterType extends Type implements TParameterType {

    private ParameterizableClass parameterizableClass;
    


    @FameProperty(name = "parameterizableClass", opposite = "parameterTypes", container = true)
    public ParameterizableClass getParameterizableClass() {
        return parameterizableClass;
    }

    public void setParameterizableClass(ParameterizableClass parameterizableClass) {
        if (this.parameterizableClass != null) {
            if (this.parameterizableClass.equals(parameterizableClass)) return;
            this.parameterizableClass.getParameterTypes().remove(this);
        }
        this.parameterizableClass = parameterizableClass;
        if (parameterizableClass == null) return;
        parameterizableClass.getParameterTypes().add(this);
    }
    


}


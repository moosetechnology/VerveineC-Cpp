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

    private ParameterizableClass parentParameterizableClass;
    


    @FameProperty(name = "parentParameterizableClass", opposite = "parameterTypes", container = true)
    public ParameterizableClass getParentParameterizableClass() {
        return parentParameterizableClass;
    }

    public void setParentParameterizableClass(ParameterizableClass parentParameterizableClass) {
        if (this.parentParameterizableClass != null) {
            if (this.parentParameterizableClass.equals(parentParameterizableClass)) return;
            this.parentParameterizableClass.getParameterTypes().remove(this);
        }
        this.parentParameterizableClass = parentParameterizableClass;
        if (parentParameterizableClass == null) return;
        parentParameterizableClass.getParameterTypes().add(this);
    }
    


}


// Automagically generated code, please do not change
package org.moosetechnology.famix.famixcppentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.famix.famixcentities.Type;
import org.moosetechnology.famix.famixtraits.TParameterizedType;
import org.moosetechnology.famix.famixtraits.TWithParameterizedTypes;


@FamePackage("Famix-Cpp-Entities")
@FameDescription("ParameterizedType")
public class ParameterizedType extends Type implements TParameterizedType {

    private TWithParameterizedTypes parameterizableClass;
    


    @FameProperty(name = "parameterizableClass", opposite = "parameterizedTypes")
    public TWithParameterizedTypes getParameterizableClass() {
        return parameterizableClass;
    }

    public void setParameterizableClass(TWithParameterizedTypes parameterizableClass) {
        if (this.parameterizableClass != null) {
            if (this.parameterizableClass.equals(parameterizableClass)) return;
            this.parameterizableClass.getParameterizedTypes().remove(this);
        }
        this.parameterizableClass = parameterizableClass;
        if (parameterizableClass == null) return;
        parameterizableClass.getParameterizedTypes().add(this);
    }
    


}


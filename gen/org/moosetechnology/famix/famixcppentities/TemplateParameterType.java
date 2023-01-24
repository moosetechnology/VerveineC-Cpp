// Automagically generated code, please do not change
package org.moosetechnology.famix.famixcppentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.famix.famixcentities.Type;
import org.moosetechnology.famix.famixtraits.TParameterType;


@FamePackage("Famix-Cpp-Entities")
@FameDescription("TemplateParameterType")
public class TemplateParameterType extends Type implements TParameterType {

    private TemplateClass parentParameterizableClass;
    


    @FameProperty(name = "parentParameterizableClass", opposite = "parameterTypes", container = true)
    public TemplateClass getParentParameterizableClass() {
        return parentParameterizableClass;
    }

    public void setParentParameterizableClass(TemplateClass parentParameterizableClass) {
        if (this.parentParameterizableClass != null) {
            if (this.parentParameterizableClass.equals(parentParameterizableClass)) return;
            this.parentParameterizableClass.getParameterTypes().remove(this);
        }
        this.parentParameterizableClass = parentParameterizableClass;
        if (parentParameterizableClass == null) return;
        parentParameterizableClass.getParameterTypes().add(this);
    }
    


}


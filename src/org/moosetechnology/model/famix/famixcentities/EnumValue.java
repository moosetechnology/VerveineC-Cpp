// Automagically generated code, please do not change
package org.moosetechnology.model.famix.famixcentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.model.famix.famixtraits.TEnumValue;
import org.moosetechnology.model.famix.famixtraits.TWithEnumValues;


@FamePackage("Famix-C-Entities")
@FameDescription("EnumValue")
public class EnumValue extends NamedEntity implements TEnumValue {

    private TWithEnumValues parentEnum;
    


    @FameProperty(name = "parentEnum", opposite = "enumValues", container = true)
    public TWithEnumValues getParentEnum() {
        return parentEnum;
    }

    public void setParentEnum(TWithEnumValues parentEnum) {
        if (this.parentEnum != null) {
            if (this.parentEnum.equals(parentEnum)) return;
            this.parentEnum.getEnumValues().remove(this);
        }
        this.parentEnum = parentEnum;
        if (parentEnum == null) return;
        parentEnum.getEnumValues().add(this);
    }
    


}


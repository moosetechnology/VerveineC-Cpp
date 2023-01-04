// Automagically generated code, please do not change
package org.moosetechnology.famix.cpp;

import ch.akuhn.fame.FameProperty;

import org.moosetechnology.famix.cpp.Enum;
import org.moosetechnology.famix.cpp.NamedEntity;
import org.moosetechnology.famix.cpp.StructuralEntity;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;


@FamePackage("FAMIX")
@FameDescription("EnumValue")
public class EnumValue extends StructuralEntity {



	@Override
	public NamedEntity getBelongsTo() {
		return getParentEnum();
	}

	@Override
	public void setBelongsTo(NamedEntity belongsTo) {
		setParentEnum((Enum) belongsTo);
	}
    

    private Enum parentEnum;
    
    @FameProperty(name = "parentEnum", opposite = "values", container = true)
    public Enum getParentEnum() {
        return parentEnum;
    }

    public void setParentEnum(Enum parentEnum) {
        if (this.parentEnum != null) {
            if (this.parentEnum.equals(parentEnum)) return;
            this.parentEnum.getValues().remove(this);
        }
        this.parentEnum = parentEnum;
        if (parentEnum == null) return;
        parentEnum.getValues().add(this);
    }


}


// Automagically generated code, please do not change
package eu.synectique.verveine.core.gen.famix;

import ch.akuhn.fame.FameProperty;
import eu.synectique.verveine.core.gen.famix.Enum;
import eu.synectique.verveine.core.gen.famix.NamedEntity;
import eu.synectique.verveine.core.gen.famix.StructuralEntity;
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


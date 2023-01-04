// Automagically generated code, please do not change
package org.moosetechnology.famix.cpp;

import ch.akuhn.fame.FameProperty;

import org.moosetechnology.famix.cpp.BehaviouralEntity;
import org.moosetechnology.famix.cpp.NamedEntity;
import org.moosetechnology.famix.cpp.StructuralEntity;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;


@FamePackage("FAMIX")
@FameDescription("ImplicitVariable")
public class ImplicitVariable extends StructuralEntity {


	@Override
	public NamedEntity getBelongsTo() {
		return getParentBehaviouralEntity();
	}

	@Override
	public void setBelongsTo(NamedEntity belongsTo) {
		setParentBehaviouralEntity((BehaviouralEntity) belongsTo);
	}
    

    private BehaviouralEntity parentBehaviouralEntity;
    
    @FameProperty(name = "parentBehaviouralEntity", opposite = "implicitVariables", container = true)
    public BehaviouralEntity getParentBehaviouralEntity() {
        return parentBehaviouralEntity;
    }

    public void setParentBehaviouralEntity(BehaviouralEntity parentBehaviouralEntity) {
        if (this.parentBehaviouralEntity != null) {
            if (this.parentBehaviouralEntity.equals(parentBehaviouralEntity)) return;
            this.parentBehaviouralEntity.getImplicitVariables().remove(this);
        }
        this.parentBehaviouralEntity = parentBehaviouralEntity;
        if (parentBehaviouralEntity == null) return;
        parentBehaviouralEntity.getImplicitVariables().add(this);
    }



}


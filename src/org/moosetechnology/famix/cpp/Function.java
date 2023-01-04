// Automagically generated code, please do not change
package org.moosetechnology.famix.cpp;

import ch.akuhn.fame.FameProperty;

import org.moosetechnology.famix.cpp.BehaviouralEntity;
import org.moosetechnology.famix.cpp.ContainerEntity;
import org.moosetechnology.famix.cpp.Module;
import org.moosetechnology.famix.cpp.NamedEntity;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;


@FamePackage("FAMIX")
@FameDescription("Function")
public class Function extends BehaviouralEntity {


	@Override
	public NamedEntity getBelongsTo() {
		return (NamedEntity)getContainer();
	}

	@Override
	public void setBelongsTo(NamedEntity belongsTo) {
		setContainer((ContainerEntity) belongsTo);
	}
    

    private ContainerEntity container;
    
    @FameProperty(name = "container", opposite = "functions", container = true)
    public ContainerEntity getContainer() {
        return container;
    }

    public void setContainer(ContainerEntity container) {
        if (this.container != null) {
            if (this.container.equals(container)) return;
            this.container.getFunctions().remove(this);
        }
        this.container = container;
        if (container == null) return;
        container.getFunctions().add(this);
    }

    private Module parentModule;
    
    @FameProperty(name = "parentModule")
    public Module getParentModule() {
        return parentModule;
    }

    public void setParentModule(Module parentModule) {
        this.parentModule = parentModule;
    }



}


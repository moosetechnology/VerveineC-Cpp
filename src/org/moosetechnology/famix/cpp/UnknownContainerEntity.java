// Automagically generated code, please do not change
package org.moosetechnology.famix.cpp;

import ch.akuhn.fame.FameProperty;

import org.moosetechnology.famix.cpp.ContainerEntity;
import org.moosetechnology.famix.cpp.NamedEntity;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;


@FamePackage("FAMIX")
@FameDescription("UnknownContainerEntity")
public class UnknownContainerEntity extends ContainerEntity {



    private ContainerEntity belongsTo;
    
   	@Override
    @FameProperty(name = "belongsTo", container = true)
    public NamedEntity getBelongsTo() {
        return belongsTo;
    }

   	@Override
    public void setBelongsTo(NamedEntity belongsTo) {
        this.belongsTo = (ContainerEntity) belongsTo;
    }
    


}


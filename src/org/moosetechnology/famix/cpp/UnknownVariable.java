// Automagically generated code, please do not change
package org.moosetechnology.famix.cpp;

import org.moosetechnology.famix.cpp.ContainerEntity;
import org.moosetechnology.famix.cpp.NamedEntity;
import org.moosetechnology.famix.cpp.StructuralEntity;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;


@FamePackage("FAMIX")
@FameDescription("UnknownVariable")
public class UnknownVariable extends StructuralEntity {

    private ContainerEntity belongsTo;

	@Override
	public NamedEntity getBelongsTo() {
		return belongsTo;
	}

	@Override
	public void setBelongsTo(NamedEntity belongsTo) {
		this.belongsTo = (ContainerEntity) belongsTo;
	}





}


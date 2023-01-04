// Automagically generated code, please do not change
package eu.synectique.verveine.core.gen.famix;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import eu.synectique.verveine.core.gen.famix.ContainerEntity;
import eu.synectique.verveine.core.gen.famix.NamedEntity;
import eu.synectique.verveine.core.gen.famix.StructuralEntity;


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


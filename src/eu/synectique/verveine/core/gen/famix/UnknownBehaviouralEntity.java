// Automagically generated code, please do not change
package eu.synectique.verveine.core.gen.famix;

import ch.akuhn.fame.FameProperty;
import eu.synectique.verveine.core.gen.famix.BehaviouralEntity;
import eu.synectique.verveine.core.gen.famix.ContainerEntity;
import eu.synectique.verveine.core.gen.famix.NamedEntity;
import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;


@FamePackage("FAMIX")
@FameDescription("UnknownBehaviouralEntity")
public class UnknownBehaviouralEntity extends BehaviouralEntity {



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


// Automagically generated code, please do not change
package eu.synectique.verveine.core.gen.famix;

import ch.akuhn.fame.FameProperty;
import eu.synectique.verveine.core.gen.famix.Module;
import eu.synectique.verveine.core.gen.famix.NamedEntity;
import eu.synectique.verveine.core.gen.famix.ScopingEntity;
import eu.synectique.verveine.core.gen.famix.StructuralEntity;
import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;


@FamePackage("FAMIX")
@FameDescription("GlobalVariable")
public class GlobalVariable extends StructuralEntity {


	@Override
	public NamedEntity getBelongsTo() {
		return getParentModule();
	}

	@Override
	public void setBelongsTo(NamedEntity belongsTo) {
		setParentModule((Module) belongsTo);
	}
    


    private ScopingEntity parentScope;
    
    @FameProperty(name = "parentScope", opposite = "globalVariables", container = true)
    public ScopingEntity getParentScope() {
        return parentScope;
    }

    public void setParentScope(ScopingEntity parentScope) {
        if (this.parentScope != null) {
            if (this.parentScope.equals(parentScope)) return;
            this.parentScope.getGlobalVariables().remove(this);
        }
        this.parentScope = parentScope;
        if (parentScope == null) return;
        parentScope.getGlobalVariables().add(this);
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


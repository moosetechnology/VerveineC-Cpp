// Automagically generated code, please do not change
package eu.synectique.verveine.core.gen.famix;

import ch.akuhn.fame.FameProperty;
import eu.synectique.verveine.core.gen.famix.Association;
import eu.synectique.verveine.core.gen.famix.BehaviouralEntity;
import eu.synectique.verveine.core.gen.famix.Type;
import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;


@FamePackage("FAMIX")
@FameDescription("Reference")
public class Reference extends Association {



    private BehaviouralEntity source;
    
    @FameProperty(name = "source", opposite = "outgoingReferences")
    public BehaviouralEntity getSource() {
        return source;
    }

    public void setSource(BehaviouralEntity source) {
        if (this.source != null) {
            if (this.source.equals(source)) return;
            this.source.getOutgoingReferences().remove(this);
        }
        this.source = source;
        if (source == null) return;
        source.getOutgoingReferences().add(this);
    }
    
    private Type target;
    
    @FameProperty(name = "target", opposite = "incomingReferences")
    public Type getTarget() {
        return target;
    }

    public void setTarget(Type target) {
        if (this.target != null) {
            if (this.target.equals(target)) return;
            this.target.getIncomingReferences().remove(this);
        }
        this.target = target;
        if (target == null) return;
        target.getIncomingReferences().add(this);
    }
    


}


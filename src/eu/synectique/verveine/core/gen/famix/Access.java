// Automagically generated code, please do not change
package eu.synectique.verveine.core.gen.famix;

import ch.akuhn.fame.FameProperty;
import eu.synectique.verveine.core.gen.famix.Association;
import eu.synectique.verveine.core.gen.famix.BehaviouralEntity;
import eu.synectique.verveine.core.gen.famix.StructuralEntity;
import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;


@FamePackage("FAMIX")
@FameDescription("Access")
public class Access extends Association {



    private StructuralEntity variable;
    
    @FameProperty(name = "variable", opposite = "incomingAccesses")
    public StructuralEntity getVariable() {
        return variable;
    }

    public void setVariable(StructuralEntity variable) {
        if (this.variable != null) {
            if (this.variable.equals(variable)) return;
            this.variable.getIncomingAccesses().remove(this);
        }
        this.variable = variable;
        if (variable == null) return;
        variable.getIncomingAccesses().add(this);
    }
    
    @FameProperty(name = "isRead", derived = true)
    public Boolean getIsRead() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    private BehaviouralEntity accessor;
    
    @FameProperty(name = "accessor", opposite = "accesses")
    public BehaviouralEntity getAccessor() {
        return accessor;
    }

    public void setAccessor(BehaviouralEntity accessor) {
        if (this.accessor != null) {
            if (this.accessor.equals(accessor)) return;
            this.accessor.getAccesses().remove(this);
        }
        this.accessor = accessor;
        if (accessor == null) return;
        accessor.getAccesses().add(this);
    }
    
    @FameProperty(name = "isReadWriteUnknown", derived = true)
    public Boolean getIsReadWriteUnknown() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    private Boolean isWrite;
    
    @FameProperty(name = "isWrite")
    public Boolean getIsWrite() {
        return isWrite;
    }

    public void setIsWrite(Boolean isWrite) {
        this.isWrite = isWrite;
    }
    


}


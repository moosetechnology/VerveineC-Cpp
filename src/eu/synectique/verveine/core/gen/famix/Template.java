// Automagically generated code, please do not change
package eu.synectique.verveine.core.gen.famix;

import ch.akuhn.fame.internal.MultivalueSet;
import eu.synectique.verveine.core.gen.famix.ContainerEntity;
import eu.synectique.verveine.core.gen.famix.NamedEntity;
import eu.synectique.verveine.core.gen.famix.ParameterizableClass;
import eu.synectique.verveine.core.gen.famix.Template;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.FameDescription;
import java.util.*;
import ch.akuhn.fame.FamePackage;


@FamePackage("FAMIX")
@FameDescription("Template")
public class Template extends ParameterizableClass {



    private Collection<NamedEntity> templateParameters; 

    @FameProperty(name = "templateParameters", opposite = "parentTemplate", derived = true)
    public Collection<NamedEntity> getTemplateParameters() {
        if (templateParameters == null) {
            templateParameters = new MultivalueSet<NamedEntity>() {
                @Override
                protected void clearOpposite(NamedEntity e) {
                    e.setParentTemplate(null);
                }
                @Override
                protected void setOpposite(NamedEntity e) {
                    e.setParentTemplate(Template.this);
                }
            };
        }
        return templateParameters;
    }
    
    public void setTemplateParameters(Collection<? extends NamedEntity> templateParameters) {
        this.getTemplateParameters().clear();
        this.getTemplateParameters().addAll(templateParameters);
    }                    
    
        
    public void addTemplateParameters(NamedEntity one) {
        this.getTemplateParameters().add(one);
    }   
    
    public void addTemplateParameters(NamedEntity one, NamedEntity... many) {
        this.getTemplateParameters().add(one);
        for (NamedEntity each : many)
            this.getTemplateParameters().add(each);
    }   
    
    public void addTemplateParameters(Iterable<? extends NamedEntity> many) {
        for (NamedEntity each : many)
            this.getTemplateParameters().add(each);
    }   
                
    public void addTemplateParameters(NamedEntity[] many) {
        for (NamedEntity each : many)
            this.getTemplateParameters().add(each);
    }
    
    public int numberOfTemplateParameters() {
        return getTemplateParameters().size();
    }

    public boolean hasTemplateParameters() {
        return !getTemplateParameters().isEmpty();
    }
    
                
    private ContainerEntity generic;
    
    @FameProperty(name = "generic", opposite = "templateDescriptor", derived = true)
    public ContainerEntity getGeneric() {
        return generic;
    }

    public void setGeneric(ContainerEntity generic) {
        if (this.generic == null ? generic != null : !this.generic.equals(generic)) {
            ContainerEntity old_generic = this.generic;
            this.generic = generic;
            if (old_generic != null) old_generic.setTemplateDescriptor(null);
            if (generic != null) generic.setTemplateDescriptor(this);
        }
    }
    


}


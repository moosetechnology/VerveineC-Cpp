// Automagically generated code, please do not change
package org.moosetechnology.famix.cpp;

import ch.akuhn.fame.internal.MultivalueSet;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.FameDescription;
import java.util.*;

import org.moosetechnology.famix.cpp.AnnotationInstance;
import org.moosetechnology.famix.cpp.Invocation;
import org.moosetechnology.famix.cpp.NamedEntity;
import org.moosetechnology.famix.cpp.Package;
import org.moosetechnology.famix.cpp.SourcedEntity;
import org.moosetechnology.famix.cpp.Template;

import ch.akuhn.fame.FamePackage;


@FamePackage("FAMIX")
@FameDescription("NamedEntity")
public abstract class NamedEntity extends SourcedEntity {

	public abstract NamedEntity getBelongsTo();
	public abstract void setBelongsTo(NamedEntity belongsTo);

	@Override
	public String toString() {
		String className = this.getClass().getName();
		className = className.substring(this.getClass().getPackageName().length()+1 );
		return className + "(" + this.getName() + ")";
	}

    @FameProperty(name = "nameByPolicy", derived = true)
    public String getNameByPolicy() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    private Collection<Invocation> receivingInvocations; 

    @FameProperty(name = "receivingInvocations", opposite = "receiver", derived = true)
    public Collection<Invocation> getReceivingInvocations() {
        if (receivingInvocations == null) {
            receivingInvocations = new MultivalueSet<Invocation>() {
                @Override
                protected void clearOpposite(Invocation e) {
                    e.setReceiver(null);
                }
                @Override
                protected void setOpposite(Invocation e) {
                    e.setReceiver(NamedEntity.this);
                }
            };
        }
        return receivingInvocations;
    }
    
    public void setReceivingInvocations(Collection<? extends Invocation> receivingInvocations) {
        this.getReceivingInvocations().clear();
        this.getReceivingInvocations().addAll(receivingInvocations);
    }                    
    
        
    public void addReceivingInvocations(Invocation one) {
        this.getReceivingInvocations().add(one);
    }   
    
    public void addReceivingInvocations(Invocation one, Invocation... many) {
        this.getReceivingInvocations().add(one);
        for (Invocation each : many)
            this.getReceivingInvocations().add(each);
    }   
    
    public void addReceivingInvocations(Iterable<? extends Invocation> many) {
        for (Invocation each : many)
            this.getReceivingInvocations().add(each);
    }   
                
    public void addReceivingInvocations(Invocation[] many) {
        for (Invocation each : many)
            this.getReceivingInvocations().add(each);
    }
    
    public int numberOfReceivingInvocations() {
        return getReceivingInvocations().size();
    }

    public boolean hasReceivingInvocations() {
        return !getReceivingInvocations().isEmpty();
    }
    
                
    @FameProperty(name = "isPackage", derived = true)
    public Boolean getIsPackage() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "isPrivate", derived = true)
    public Boolean getIsPrivate() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    private Collection<String> modifiers; 

    @FameProperty(name = "modifiers")
    public Collection<String> getModifiers() {
        if (modifiers == null) modifiers = new HashSet<String>();
        return modifiers;
    }
    
    public void setModifiers(Collection<? extends String> modifiers) {
        this.getModifiers().clear();
        this.getModifiers().addAll(modifiers);
    }                    

    public void addModifiers(String one) {
        this.getModifiers().add(one);
    }   
    
    public void addModifiers(String one, String... many) {
        this.getModifiers().add(one);
        for (String each : many)
            this.getModifiers().add(each);
    }   
    
    public void addModifiers(Iterable<? extends String> many) {
        for (String each : many)
            this.getModifiers().add(each);
    }   
                
    public void addModifiers(String[] many) {
        for (String each : many)
            this.getModifiers().add(each);
    }
    
    public int numberOfModifiers() {
        return getModifiers().size();
    }

    public boolean hasModifiers() {
        return !getModifiers().isEmpty();
    }
    
                
    @FameProperty(name = "isAbstract", derived = true)
    public Boolean getIsAbstract() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    private Template parentTemplate;
    
    @FameProperty(name = "parentTemplate", opposite = "templateParameters")
    public Template getParentTemplate() {
        return parentTemplate;
    }

    public void setParentTemplate(Template parentTemplate) {
        if (this.parentTemplate != null) {
            if (this.parentTemplate.equals(parentTemplate)) return;
            this.parentTemplate.getTemplateParameters().remove(this);
        }
        this.parentTemplate = parentTemplate;
        if (parentTemplate == null) return;
        parentTemplate.getTemplateParameters().add(this);
    }
    
    private Boolean isStub;
    
    @FameProperty(name = "isStub")
    public Boolean getIsStub() {
        return isStub;
    }

    public void setIsStub(Boolean isStub) {
        this.isStub = isStub;
    }
    
    @FameProperty(name = "isProtected", derived = true)
    public Boolean getIsProtected() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "nameLength", derived = true)
    public Number getNameLength() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    private String name;
    
    @FameProperty(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @FameProperty(name = "isPublic", derived = true)
    public Boolean getIsPublic() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "isFinal", derived = true)
    public Boolean getIsFinal() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    private Collection<AnnotationInstance> annotationInstances; 

    @FameProperty(name = "annotationInstances", opposite = "annotatedEntity", derived = true)
    public Collection<AnnotationInstance> getAnnotationInstances() {
        if (annotationInstances == null) {
            annotationInstances = new MultivalueSet<AnnotationInstance>() {
                @Override
                protected void clearOpposite(AnnotationInstance e) {
                    e.setAnnotatedEntity(null);
                }
                @Override
                protected void setOpposite(AnnotationInstance e) {
                    e.setAnnotatedEntity(NamedEntity.this);
                }
            };
        }
        return annotationInstances;
    }
    
    public void setAnnotationInstances(Collection<? extends AnnotationInstance> annotationInstances) {
        this.getAnnotationInstances().clear();
        this.getAnnotationInstances().addAll(annotationInstances);
    }                    
    
        
    public void addAnnotationInstances(AnnotationInstance one) {
        this.getAnnotationInstances().add(one);
    }   
    
    public void addAnnotationInstances(AnnotationInstance one, AnnotationInstance... many) {
        this.getAnnotationInstances().add(one);
        for (AnnotationInstance each : many)
            this.getAnnotationInstances().add(each);
    }   
    
    public void addAnnotationInstances(Iterable<? extends AnnotationInstance> many) {
        for (AnnotationInstance each : many)
            this.getAnnotationInstances().add(each);
    }   
                
    public void addAnnotationInstances(AnnotationInstance[] many) {
        for (AnnotationInstance each : many)
            this.getAnnotationInstances().add(each);
    }
    
    public int numberOfAnnotationInstances() {
        return getAnnotationInstances().size();
    }

    public boolean hasAnnotationInstances() {
        return !getAnnotationInstances().isEmpty();
    }
    
                
    private Collection<NamedEntity> fastNamedEntities; 

                
    private Package parentPackage;
    
    @FameProperty(name = "parentPackage", opposite = "childNamedEntities", container = true)
    public Package getParentPackage() {
        return parentPackage;
    }

    public void setParentPackage(Package parentPackage) {
        if (this.parentPackage != null) {
            if (this.parentPackage.equals(parentPackage)) return;
            this.parentPackage.getChildNamedEntities().remove(this);
        }
        this.parentPackage = parentPackage;
        if (parentPackage == null) return;
        parentPackage.getChildNamedEntities().add(this);
    }
    


}


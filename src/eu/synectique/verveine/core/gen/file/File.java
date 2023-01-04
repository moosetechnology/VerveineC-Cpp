// Automagically generated code, please do not change
package eu.synectique.verveine.core.gen.file;

import ch.akuhn.fame.internal.MultivalueSet;
import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.FameDescription;
import java.util.*;
import ch.akuhn.fame.FamePackage;
import eu.synectique.verveine.core.gen.famix.SourcedEntity;


@FamePackage("FILE")
@FameDescription("File")
public class File extends AbstractFile {



    private Number numberOfInternalClones;
    
    @FameProperty(name = "numberOfInternalClones")
    public Number getNumberOfInternalClones() {
        return numberOfInternalClones;
    }

    public void setNumberOfInternalClones(Number numberOfInternalClones) {
        this.numberOfInternalClones = numberOfInternalClones;
    }
    
    @FameProperty(name = "totalNumberOfLinesOfText", derived = true)
    public Number getTotalNumberOfLinesOfText() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfKiloBytes", derived = true)
    public Number getNumberOfKiloBytes() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    private Number numberOfDuplicatedFiles;
    
    @FameProperty(name = "numberOfDuplicatedFiles")
    public Number getNumberOfDuplicatedFiles() {
        return numberOfDuplicatedFiles;
    }

    public void setNumberOfDuplicatedFiles(Number numberOfDuplicatedFiles) {
        this.numberOfDuplicatedFiles = numberOfDuplicatedFiles;
    }
    
    @FameProperty(name = "numberOfEmptyLinesOfText", derived = true)
    public Number getNumberOfEmptyLinesOfText() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfCharacters", derived = true)
    public Number getNumberOfCharacters() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfInternalDuplications", derived = true)
    public Number getNumberOfInternalDuplications() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "averageNumberOfCharactersPerLine", derived = true)
    public Number getAverageNumberOfCharactersPerLine() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    private Collection<SourcedEntity> entities; 

    @FameProperty(name = "entities", opposite = "containerFiles")
    public Collection<SourcedEntity> getEntities() {
        if (entities == null) {
            entities = new MultivalueSet<SourcedEntity>() {
                @Override
                protected void clearOpposite(SourcedEntity e) {
                    e.getContainerFiles().remove(File.this);
                }
                @Override
                protected void setOpposite(SourcedEntity e) {
                    e.getContainerFiles().add(File.this);
                }
            };
        }
        return entities;
    }
    
    public void setEntities(Collection<? extends SourcedEntity> entities) {
        this.getEntities().clear();
        this.getEntities().addAll(entities);
    }
    
    public void addEntities(SourcedEntity one) {
        this.getEntities().add(one);
    }   
    
    public void addEntities(SourcedEntity one, SourcedEntity... many) {
        this.getEntities().add(one);
        for (SourcedEntity each : many)
            this.getEntities().add(each);
    }   
    
    public void addEntities(Iterable<? extends SourcedEntity> many) {
        for (SourcedEntity each : many)
            this.getEntities().add(each);
    }   
                
    public void addEntities(SourcedEntity[] many) {
        for (SourcedEntity each : many)
            this.getEntities().add(each);
    }
    
    public int numberOfEntities() {
        return getEntities().size();
    }

    public boolean hasEntities() {
        return !getEntities().isEmpty();
    }
    
                
    @FameProperty(name = "numberOfLinesOfText", derived = true)
    public Number getNumberOfLinesOfText() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    private Number numberOfExternalClones;
    
    @FameProperty(name = "numberOfExternalClones")
    public Number getNumberOfExternalClones() {
        return numberOfExternalClones;
    }

    public void setNumberOfExternalClones(Number numberOfExternalClones) {
        this.numberOfExternalClones = numberOfExternalClones;
    }
    
    private Number numberOfInternalMultiplications;
    
    @FameProperty(name = "numberOfInternalMultiplications")
    public Number getNumberOfInternalMultiplications() {
        return numberOfInternalMultiplications;
    }

    public void setNumberOfInternalMultiplications(Number numberOfInternalMultiplications) {
        this.numberOfInternalMultiplications = numberOfInternalMultiplications;
    }
    
    @FameProperty(name = "numberOfExternalDuplications", derived = true)
    public Number getNumberOfExternalDuplications() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    @FameProperty(name = "numberOfBytes", derived = true)
    public Number getNumberOfBytes() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    


}


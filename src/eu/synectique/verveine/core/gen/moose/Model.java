// Automagically generated code, please do not change
package eu.synectique.verveine.core.gen.moose;

import ch.akuhn.fame.FameProperty;
import eu.synectique.verveine.core.gen.famix.SourceLanguage;
import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;


@FamePackage("Moose")
@FameDescription("Model")
public class Model extends AbstractGroup {



    private Number numberOfClassesPerPackage;
    
    @FameProperty(name = "numberOfClassesPerPackage")
    public Number getNumberOfClassesPerPackage() {
        return numberOfClassesPerPackage;
    }

    public void setNumberOfClassesPerPackage(Number numberOfClassesPerPackage) {
        this.numberOfClassesPerPackage = numberOfClassesPerPackage;
    }
    
    private Number numberOfModelMethods;
    
    @FameProperty(name = "numberOfModelMethods")
    public Number getNumberOfModelMethods() {
        return numberOfModelMethods;
    }

    public void setNumberOfModelMethods(Number numberOfModelMethods) {
        this.numberOfModelMethods = numberOfModelMethods;
    }
    
    private Number numberOfMethods;
    
    @FameProperty(name = "numberOfMethods")
    public Number getNumberOfMethods() {
        return numberOfMethods;
    }

    public void setNumberOfMethods(Number numberOfMethods) {
        this.numberOfMethods = numberOfMethods;
    }
    
    @FameProperty(name = "averageCyclomaticComplexity", derived = true)
    public Number getAverageCyclomaticComplexity() {
        // TODO: this is a derived property, implement this method manually.
        throw new UnsupportedOperationException("Not yet implemented!");  
    }
    
    private Number numberOfLinesOfCode;
    
    @FameProperty(name = "numberOfLinesOfCode")
    public Number getNumberOfLinesOfCode() {
        return numberOfLinesOfCode;
    }

    public void setNumberOfLinesOfCode(Number numberOfLinesOfCode) {
        this.numberOfLinesOfCode = numberOfLinesOfCode;
    }
    
    private Number numberOfClasses;
    
    @FameProperty(name = "numberOfClasses")
    public Number getNumberOfClasses() {
        return numberOfClasses;
    }

    public void setNumberOfClasses(Number numberOfClasses) {
        this.numberOfClasses = numberOfClasses;
    }
    
    private Number numberOfModelClasses;
    
    @FameProperty(name = "numberOfModelClasses")
    public Number getNumberOfModelClasses() {
        return numberOfModelClasses;
    }

    public void setNumberOfModelClasses(Number numberOfModelClasses) {
        this.numberOfModelClasses = numberOfModelClasses;
    }
    
    private Number numberOfLinesOfCodePerPackage;
    
    @FameProperty(name = "numberOfLinesOfCodePerPackage")
    public Number getNumberOfLinesOfCodePerPackage() {
        return numberOfLinesOfCodePerPackage;
    }

    public void setNumberOfLinesOfCodePerPackage(Number numberOfLinesOfCodePerPackage) {
        this.numberOfLinesOfCodePerPackage = numberOfLinesOfCodePerPackage;
    }
    
    private Number numberOfLinesOfCodePerMethod;
    
    @FameProperty(name = "numberOfLinesOfCodePerMethod")
    public Number getNumberOfLinesOfCodePerMethod() {
        return numberOfLinesOfCodePerMethod;
    }

    public void setNumberOfLinesOfCodePerMethod(Number numberOfLinesOfCodePerMethod) {
        this.numberOfLinesOfCodePerMethod = numberOfLinesOfCodePerMethod;
    }
    
    private SourceLanguage sourceLanguage;
    
    @FameProperty(name = "sourceLanguage")
    public SourceLanguage getSourceLanguage() {
        return sourceLanguage;
    }

    public void setSourceLanguage(SourceLanguage sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
    }
    
    private Number numberOfLinesOfCodePerClass;
    
    @FameProperty(name = "numberOfLinesOfCodePerClass")
    public Number getNumberOfLinesOfCodePerClass() {
        return numberOfLinesOfCodePerClass;
    }

    public void setNumberOfLinesOfCodePerClass(Number numberOfLinesOfCodePerClass) {
        this.numberOfLinesOfCodePerClass = numberOfLinesOfCodePerClass;
    }
    


}


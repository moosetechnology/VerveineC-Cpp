// Automagically generated code, please do not change
package eu.synectique.verveine.core.gen.moose;

import ch.akuhn.fame.FameProperty;
import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;


@FamePackage("Moose")
@FameDescription("AbstractGroup")
public class AbstractGroup extends Entity {



    private Number numberOfItems;
    
    @FameProperty(name = "numberOfItems")
    public Number getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(Number numberOfItems) {
        this.numberOfItems = numberOfItems;
    }
    
    private Number numberOfPackages;
    
    @FameProperty(name = "numberOfPackages")
    public Number getNumberOfPackages() {
        return numberOfPackages;
    }

    public void setNumberOfPackages(Number numberOfPackages) {
        this.numberOfPackages = numberOfPackages;
    }
    


}


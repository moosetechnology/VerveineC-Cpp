// Automagically generated code, please do not change
package org.moosetechnology.famix.famixcpreprocentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.famix.famixtraits.TNamedEntity;


@FamePackage("Famix-CPreproc-Entities")
@FameDescription("PreprocDefine")
public class PreprocDefine extends PreprocStatement implements TNamedEntity {

    private String name;
    


    @FameProperty(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    


}


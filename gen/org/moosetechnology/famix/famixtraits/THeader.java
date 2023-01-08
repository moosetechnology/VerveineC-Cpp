// Automagically generated code, please do not change
package org.moosetechnology.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("THeader")
public interface THeader extends TFile, TFileSystemEntity {

        @FameProperty(name = "headerOwner", opposite = "header", container = true)
    public TWithHeaders getHeaderOwner();

    public void setHeaderOwner(TWithHeaders headerOwner);



}


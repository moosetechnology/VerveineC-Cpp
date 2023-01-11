// Automagically generated code, please do not change
package org.moosetechnology.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.famix.moosequery.TAssociationMetaLevelDependency;


@FamePackage("Famix-Traits")
@FameDescription("TFileInclude")
public interface TFileInclude extends TAssociation, TSourceEntity, TAssociationMetaLevelDependency {

        @FameProperty(name = "source", opposite = "outgoingIncludeRelations")
    public TWithFileIncludes getSource();

    public void setSource(TWithFileIncludes source);

    @FameProperty(name = "target", opposite = "incomingIncludeRelations")
    public TWithFileIncludes getTarget();

    public void setTarget(TWithFileIncludes target);



}


// Automagically generated code, please do not change
package org.moosetechnology.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.famix.moosequery.TEntityMetaLevelDependency;


@FamePackage("Famix-Traits")
@FameDescription("TGlobalVariable")
public interface TGlobalVariable extends TSourceEntity, TNamedEntity, TEntityMetaLevelDependency, TStructuralEntity, TTypedEntity, TAccessible {

        @FameProperty(name = "parentScope", opposite = "globalVariables", container = true)
    public TWithGlobalVariables getParentScope();

    public void setParentScope(TWithGlobalVariables parentScope);



}


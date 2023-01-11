// Automagically generated code, please do not change
package org.moosetechnology.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.famix.moosequery.TEntityMetaLevelDependency;


@FamePackage("Famix-Traits")
@FameDescription("TLocalVariable")
public interface TLocalVariable extends TSourceEntity, TNamedEntity, TEntityMetaLevelDependency, TStructuralEntity, TTypedEntity, TAccessible {

        @FameProperty(name = "parentBehaviouralEntity", opposite = "localVariables", container = true)
    public TWithLocalVariables getParentBehaviouralEntity();

    public void setParentBehaviouralEntity(TWithLocalVariables parentBehaviouralEntity);



}


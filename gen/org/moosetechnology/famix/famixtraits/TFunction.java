// Automagically generated code, please do not change
package org.moosetechnology.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.famix.moosequery.TEntityMetaLevelDependency;


@FamePackage("Famix-Traits")
@FameDescription("TFunction")
public interface TFunction extends TNamedEntity, TWithReferences, TEntityMetaLevelDependency, TSourceEntity, TWithAccesses, TWithParameters, THasSignature, TWithInvocations, TTypedEntity, TWithStatements {

        @FameProperty(name = "functionOwner", opposite = "functions", container = true)
    public TWithFunctions getFunctionOwner();

    public void setFunctionOwner(TWithFunctions functionOwner);



}


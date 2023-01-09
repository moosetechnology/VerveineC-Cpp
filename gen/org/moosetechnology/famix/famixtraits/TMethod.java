// Automagically generated code, please do not change
package org.moosetechnology.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.famix.moosequery.TEntityMetaLevelDependency;


@FamePackage("Famix-Traits")
@FameDescription("TMethod")
public interface TMethod extends TEntityMetaLevelDependency, TWithReferences, TMethodMetrics, TSourceEntity, TWithImplicitVariables, TWithAccesses, TWithParameters, TWithInvocations, TInvocable, TWithLocalVariables, TNamedEntity, THasSignature, TWithStatements, TTypedEntity {

        @FameProperty(name = "hasClassScope", derived = true)
    public Boolean getHasClassScope();

    @FameProperty(name = "parentType", opposite = "methods", container = true)
    public TWithMethods getParentType();

    public void setParentType(TWithMethods parentType);



}


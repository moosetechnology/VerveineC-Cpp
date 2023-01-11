// Automagically generated code, please do not change
package org.moosetechnology.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.famix.moosequery.TAssociationMetaLevelDependency;


@FamePackage("Famix-Traits")
@FameDescription("TDereferencedInvocation")
public interface TDereferencedInvocation extends TAssociation, TSourceEntity, TInvocation, TAssociationMetaLevelDependency {

        @FameProperty(name = "referencer", opposite = "dereferencedInvocations")
    public TWithDereferencedInvocations getReferencer();

    public void setReferencer(TWithDereferencedInvocations referencer);



}


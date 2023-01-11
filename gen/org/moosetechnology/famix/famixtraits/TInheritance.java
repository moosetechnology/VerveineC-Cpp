// Automagically generated code, please do not change
package org.moosetechnology.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.famix.moosequery.TAssociationMetaLevelDependency;


@FamePackage("Famix-Traits")
@FameDescription("TInheritance")
public interface TInheritance extends TAssociation, TSourceEntity, TAssociationMetaLevelDependency {

        @FameProperty(name = "superclass", opposite = "subInheritances")
    public TWithInheritances getSuperclass();

    public void setSuperclass(TWithInheritances superclass);

    @FameProperty(name = "subclass", opposite = "superInheritances")
    public TWithInheritances getSubclass();

    public void setSubclass(TWithInheritances subclass);



}


// Automagically generated code, please do not change
package org.moosetechnology.famix.famixcentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.famix.famixtraits.TTypeAlias;
import org.moosetechnology.famix.famixtraits.TWithTypeAliases;


@FamePackage("Famix-C-Entities")
@FameDescription("AliasType")
public class AliasType extends Type implements TTypeAlias {

    private TWithTypeAliases aliasedType;
    


    @FameProperty(name = "aliasedType", opposite = "typeAliases")
    public TWithTypeAliases getAliasedType() {
        return aliasedType;
    }

    public void setAliasedType(TWithTypeAliases aliasedType) {
        if (this.aliasedType != null) {
            if (this.aliasedType.equals(aliasedType)) return;
            this.aliasedType.getTypeAliases().remove(this);
        }
        this.aliasedType = aliasedType;
        if (aliasedType == null) return;
        aliasedType.getTypeAliases().add(this);
    }
    


}


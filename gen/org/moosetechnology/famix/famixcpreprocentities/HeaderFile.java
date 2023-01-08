// Automagically generated code, please do not change
package org.moosetechnology.famix.famixcpreprocentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.famix.famixcentities.Module;


@FamePackage("Famix-CPreproc-Entities")
@FameDescription("HeaderFile")
public class HeaderFile extends CFile  {

    private Module module;
    


    @FameProperty(name = "module", opposite = "headerFile", derived = true)
    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        if (this.module == null ? module != null : !this.module.equals(module)) {
            Module old_module = this.module;
            this.module = module;
            if (old_module != null) old_module.setHeaderFile(null);
            if (module != null) module.setHeaderFile(this);
        }
    }
    


}


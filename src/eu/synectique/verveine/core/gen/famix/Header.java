// Automagically generated code, please do not change
package eu.synectique.verveine.core.gen.famix;

import ch.akuhn.fame.FameProperty;
import eu.synectique.verveine.core.gen.famix.CFile;
import eu.synectique.verveine.core.gen.famix.Module;
import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;


@FamePackage("FAMIX")
@FameDescription("Header")
public class Header extends CFile {



    private Module module;
    
    @FameProperty(name = "module", opposite = "header")
    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        if (this.module == null ? module != null : !this.module.equals(module)) {
            Module old_module = this.module;
            this.module = module;
            if (old_module != null) old_module.setHeader(null);
            if (module != null) module.setHeader(this);
        }
    }
    


}


// Automagically generated code, please do not change
package org.moosetechnology.famix.cpp;

import ch.akuhn.fame.FameProperty;

import org.moosetechnology.famix.cpp.CFile;
import org.moosetechnology.famix.cpp.Module;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;


@FamePackage("FAMIX")
@FameDescription("CompilationUnit")
public class CompilationUnit extends CFile {



    private Module module;
    
    @FameProperty(name = "module", opposite = "compilationUnit")
    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        if (this.module == null ? module != null : !this.module.equals(module)) {
            Module old_module = this.module;
            this.module = module;
            if (old_module != null) old_module.setCompilationUnit(null);
            if (module != null) module.setCompilationUnit(this);
        }
    }
    


}


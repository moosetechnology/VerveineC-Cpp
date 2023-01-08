// Automagically generated code, please do not change
package org.moosetechnology.famix.famixcentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.famix.famixcpreprocentities.CompilationUnit;
import org.moosetechnology.famix.famixcpreprocentities.HeaderFile;


@FamePackage("Famix-C-Entities")
@FameDescription("Module")
public class Module extends Entity  {

    private CompilationUnit compilationUnit;
    
    private HeaderFile headerFile;
    


    @FameProperty(name = "compilationUnit", opposite = "module")
    public CompilationUnit getCompilationUnit() {
        return compilationUnit;
    }

    public void setCompilationUnit(CompilationUnit compilationUnit) {
        if (this.compilationUnit == null ? compilationUnit != null : !this.compilationUnit.equals(compilationUnit)) {
            CompilationUnit old_compilationUnit = this.compilationUnit;
            this.compilationUnit = compilationUnit;
            if (old_compilationUnit != null) old_compilationUnit.setModule(null);
            if (compilationUnit != null) compilationUnit.setModule(this);
        }
    }
    
    @FameProperty(name = "headerFile", opposite = "module")
    public HeaderFile getHeaderFile() {
        return headerFile;
    }

    public void setHeaderFile(HeaderFile headerFile) {
        if (this.headerFile == null ? headerFile != null : !this.headerFile.equals(headerFile)) {
            HeaderFile old_headerFile = this.headerFile;
            this.headerFile = headerFile;
            if (old_headerFile != null) old_headerFile.setModule(null);
            if (headerFile != null) headerFile.setModule(this);
        }
    }
    


}


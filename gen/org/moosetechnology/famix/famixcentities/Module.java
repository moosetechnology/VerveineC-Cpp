// Automagically generated code, please do not change
package org.moosetechnology.famix.famixcentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.famix.famixcpreprocentities.CompilationUnit;
import org.moosetechnology.famix.famixcpreprocentities.HeaderFile;
import org.moosetechnology.famix.famixtraits.TPackage;
import org.moosetechnology.famix.famixtraits.TPackageable;


@FamePackage("Famix-C-Entities")
@FameDescription("Module")
public class Module extends NamedEntity implements TPackageable {

    private CompilationUnit compilationUnit;
    
    private HeaderFile headerFile;
    
    private TPackage parentPackage;
    


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
    
    @FameProperty(name = "parentPackage", opposite = "childEntities", container = true)
    public TPackage getParentPackage() {
        return parentPackage;
    }

    public void setParentPackage(TPackage parentPackage) {
        if (this.parentPackage != null) {
            if (this.parentPackage.equals(parentPackage)) return;
            this.parentPackage.getChildEntities().remove(this);
        }
        this.parentPackage = parentPackage;
        if (parentPackage == null) return;
        parentPackage.getChildEntities().add(this);
    }
    


}


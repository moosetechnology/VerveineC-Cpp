// Automagically generated code, please do not change
package org.moosetechnology.famix.cpp;

import ch.akuhn.fame.FameProperty;

import org.moosetechnology.famix.cpp.AbstractFileAnchor;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;


@FamePackage("FAMIX")
@FameDescription("FileAnchor")
public class FileAnchor extends AbstractFileAnchor {



    private Number endLine;
    
    @FameProperty(name = "endLine")
    public Number getEndLine() {
        return endLine;
    }

    public void setEndLine(Number endLine) {
        this.endLine = endLine;
    }
    
    private Number endColumn;
    
    @FameProperty(name = "endColumn")
    public Number getEndColumn() {
        return endColumn;
    }

    public void setEndColumn(Number endColumn) {
        this.endColumn = endColumn;
    }
    
    private Number startColumn;
    
    @FameProperty(name = "startColumn")
    public Number getStartColumn() {
        return startColumn;
    }

    public void setStartColumn(Number startColumn) {
        this.startColumn = startColumn;
    }
    
    private Number startLine;
    
    @FameProperty(name = "startLine")
    public Number getStartLine() {
        return startLine;
    }

    public void setStartLine(Number startLine) {
        this.startLine = startLine;
    }
    


}


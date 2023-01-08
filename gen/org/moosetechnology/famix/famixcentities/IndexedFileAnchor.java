// Automagically generated code, please do not change
package org.moosetechnology.famix.famixcentities;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;
import org.moosetechnology.famix.famixtraits.TFile;
import org.moosetechnology.famix.famixtraits.TFileAnchor;
import org.moosetechnology.famix.famixtraits.TFileNavigation;


@FamePackage("Famix-C-Entities")
@FameDescription("IndexedFileAnchor")
public class IndexedFileAnchor extends SourceAnchor implements TFileAnchor, TFileNavigation {

    private TFile correspondingFile;
    
    private String encoding;
    
    private Number endColumn;
    
    private Number endLine;
    
    private String fileName;
    
    private Number startColumn;
    
    private Number startLine;
    


    @FameProperty(name = "correspondingFile")
    public TFile getCorrespondingFile() {
        return correspondingFile;
    }

    public void setCorrespondingFile(TFile correspondingFile) {
        this.correspondingFile = correspondingFile;
    }
    
    @FameProperty(name = "encoding")
    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
    
    @FameProperty(name = "endColumn")
    public Number getEndColumn() {
        return endColumn;
    }

    public void setEndColumn(Number endColumn) {
        this.endColumn = endColumn;
    }
    
    @FameProperty(name = "endLine")
    public Number getEndLine() {
        return endLine;
    }

    public void setEndLine(Number endLine) {
        this.endLine = endLine;
    }
    
    @FameProperty(name = "fileName")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    @FameProperty(name = "startColumn")
    public Number getStartColumn() {
        return startColumn;
    }

    public void setStartColumn(Number startColumn) {
        this.startColumn = startColumn;
    }
    
    @FameProperty(name = "startLine")
    public Number getStartLine() {
        return startLine;
    }

    public void setStartLine(Number startLine) {
        this.startLine = startLine;
    }
    


}


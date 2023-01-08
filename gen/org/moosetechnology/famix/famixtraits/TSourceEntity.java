// Automagically generated code, please do not change
package org.moosetechnology.famix.famixtraits;

import ch.akuhn.fame.FameDescription;
import ch.akuhn.fame.FamePackage;
import ch.akuhn.fame.FameProperty;


@FamePackage("Famix-Traits")
@FameDescription("TSourceEntity")
public interface TSourceEntity  {

        @FameProperty(name = "isStub")
    public Boolean getIsStub();

    public void setIsStub(Boolean isStub);

    @FameProperty(name = "sourceAnchor", opposite = "element", derived = true)
    public TSourceAnchor getSourceAnchor();

    public void setSourceAnchor(TSourceAnchor sourceAnchor);

    @FameProperty(name = "numberOfLinesOfCodeWithMoreThanOneCharacter", derived = true)
    public Number getNumberOfLinesOfCodeWithMoreThanOneCharacter();

    @FameProperty(name = "numberOfLinesOfCode")
    public Number getNumberOfLinesOfCode();

    public void setNumberOfLinesOfCode(Number numberOfLinesOfCode);

    @FameProperty(name = "sourceText", derived = true)
    public String getSourceText();



}


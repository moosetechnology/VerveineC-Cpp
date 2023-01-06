package org.moosetechnology.famix.famixcentities;

import org.moosetechnology.famix.famixtraits.TIndexedFileNavigation;

public class IndexedFileAnchor extends FileAnchor implements TIndexedFileNavigation {

	protected Number startPos;

	protected Number endPos;

	@Override
	public Number getStartPos() {
		return startPos;
	}

	@Override
	public void setStartPos(Number startPos) {
		this.endPos  = startPos;
	}

	@Override
	public Number getEndPos() {
		return endPos;
	}

	@Override
	public void setEndPos(Number endPos) {
		this.endPos = endPos;
	}

}

package org.moosetechnology.verveineC.utils;

public enum Visibility {
	PUBLIC("public"),
	PROTECTED("protected"),
	PRIVATE("private");
	
	private String modifierString;
	
	private Visibility(String name) {
		modifierString = name;
	}
	
	@Override
	public String toString() {
		return modifierString;
	}
}
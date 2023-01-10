package org.moosetechnology.verveineC.plugin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.cdt.core.model.CModelException;
import org.eclipse.cdt.core.model.ICContainer;
import org.eclipse.cdt.core.model.ICProject;
import org.junit.jupiter.api.Test;
import org.moosetechnology.verveineC.AbstractTest;

class ParserTest extends AbstractTest {

	@Test
	public void testCreateProjectCleansUpTempRepository() throws CModelException {	
		ICProject cproject;
		ICContainer projDir, srcDir;

		newParser();
		cproject = parser.createEclipseProject(VerveineCParser.DEFAULT_PROJECT_NAME, "./test_src/Namespaces");
		assertEquals(1, cproject.getChildren().length );
		projDir = (ICContainer) cproject.getChildren()[0];
		assertEquals(1, projDir.getChildren().length );
		srcDir = (ICContainer) projDir.getChildren()[0];
		assertEquals(1, srcDir.getChildren().length );
		assertEquals("namespaces.cpp", srcDir.getChildren()[0].getElementName() );

		newParser();
		cproject = parser.createEclipseProject(VerveineCParser.DEFAULT_PROJECT_NAME, "./test_src/Templates");
		assertEquals(1, cproject.getChildren().length );
		projDir = (ICContainer) cproject.getChildren()[0];
		assertEquals(1, projDir.getChildren().length );
		srcDir = (ICContainer) projDir.getChildren()[0];
		assertEquals(1, srcDir.getChildren().length );
		assertEquals("myTemplates.cc", srcDir.getChildren()[0].getElementName() );

	}

}

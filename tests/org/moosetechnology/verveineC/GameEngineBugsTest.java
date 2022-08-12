package org.moosetechnology.verveineC;


import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import eu.synectique.verveine.core.gen.famix.NamedEntity;

class GameEngineBugsTest extends AbstractTest {

	/* parsing only once for all tests
	 */
	@BeforeAll
	public static void setup() {
		AbstractTest.newParser();

		parser.setUserProjectDir("./test_src/GameEngineBugs");
		parser.parse();
	}

	@Test
	void testDelectedfunction() {		
		eu.synectique.verveine.core.gen.famix.Class clazz = entityNamed(eu.synectique.verveine.core.gen.famix.Class.class, "Allocator");
		assertNotNull(clazz);
		assertEquals(4, clazz.getMethods().size());
		repo.all(NamedEntity.class);
	}

}

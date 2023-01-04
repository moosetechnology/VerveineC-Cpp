package org.moosetechnology.verveineC;


import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.moosetechnology.famix.cpp.NamedEntity;
import org.moosetechnology.famix.cpp.TypeAlias;

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
	void testDeletedFunction() {		
		org.moosetechnology.famix.cpp.Class clazz = entityNamed(org.moosetechnology.famix.cpp.Class.class, "Allocator");
		assertNotNull(clazz);
		assertEquals(4, clazz.getMethods().size());
		repo.all(NamedEntity.class);
	}

	@Test
	void testStructAlias() {		
		TypeAlias typ = entityNamed(TypeAlias.class, "UBreakIterator");
		assertNotNull(typ);
	}

}

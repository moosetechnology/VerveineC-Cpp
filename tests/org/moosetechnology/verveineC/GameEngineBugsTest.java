package org.moosetechnology.verveineC;


import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.moosetechnology.verveineCore.gen.famix.TypeAlias;

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
		org.moosetechnology.verveineCore.gen.famix.Class clazz = entityNamed(org.moosetechnology.verveineCore.gen.famix.Class.class, "Allocator");
		assertNotNull(clazz);
		assertEquals(4, clazz.getMethods().size());
		repo.all(org.moosetechnology.verveineCore.gen.famix.NamedEntity.class);
	}

	@Test
	void testUBreakIteratorAlias() {		
		TypeAlias typ = entityNamed(TypeAlias.class, "UBreakIterator");
		assertNotNull(typ);
	}

}

package org.moosetechnology.verveineC;


import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.moosetechnology.famix.famixcentities.AliasType;

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
		org.moosetechnology.famix.famixcppentities.Class clazz = entityNamed(org.moosetechnology.famix.famixcppentities.Class.class, "Allocator");
		assertNotNull(clazz);
		assertEquals(4, clazz.getMethods().size());
	}

	@Test
	void testStructAlias() {		
		AliasType typ = entityNamed(AliasType.class, "UBreakIterator");
		assertNotNull(typ);
	}

}

package org.moosetechnology.verveineC;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.moosetechnology.famix.cpp.Attribute;

class StructsTest extends AbstractTest {

	/* parsing only once for all tests
	 */
	@BeforeAll
	public static void setup() {
		newParser();

		parser.setUserProjectDir("./test_src/StructsEnums");
		parser.parse();
	}

	@Test
	public void testNumberOfEntities() {	
		assertEquals( 2, entitiesOfType(org.moosetechnology.famix.cpp.Class.class).size());
		assertEquals( 6, entitiesOfType(Attribute.class).size());
	}

	@Test
	public void testOutterStruct() {
		org.moosetechnology.famix.cpp.Class clazz = entityNamed(org.moosetechnology.famix.cpp.Class.class, "College");
		assertNotNull(clazz);

		assertEquals(3, clazz.numberOfAttributes());
		assertEquals(1, clazz.numberOfTypes());
		assertNull(clazz.getContainer());

		assertEquals("Student", first(clazz.getTypes()).getName());
	}

	@Test
	public void testInnerStruct() {
		org.moosetechnology.famix.cpp.Class clazz = entityNamed(org.moosetechnology.famix.cpp.Class.class, "Student");
		assertNotNull(clazz);

		assertEquals(3, clazz.numberOfAttributes());
		assertEquals(0, clazz.numberOfTypes());
		assertNotNull(clazz.getContainer());

		assertEquals("College", clazz.getContainer().getName());
	}

}

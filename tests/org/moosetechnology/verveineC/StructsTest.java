package org.moosetechnology.verveineC;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.moosetechnology.verveineCore.gen.famix.Attribute;

class StructsTest extends AbstractTest {

	/* parsing only once for all tests
	 */
	@BeforeAll
	public static void setup() {
		newParser();

		parser.setUserProjectDir("./test_src/Structs");
		parser.parse();
	}

	@Test
	public void testNumberOfEntities() {	
		assertEquals( 2, entitiesOfType(org.moosetechnology.verveineCore.gen.famix.Class.class).size());
		assertEquals( 6, entitiesOfType(Attribute.class).size());
	}

	@Test
	public void testOutterStruct() {
		org.moosetechnology.verveineCore.gen.famix.Class clazz = entityNamed(org.moosetechnology.verveineCore.gen.famix.Class.class, "College");
		assertNotNull(clazz);

		assertEquals(3, clazz.numberOfAttributes());
		assertEquals(1, clazz.numberOfTypes());
		assertNull(clazz.getContainer());

		assertEquals("Student", first(clazz.getTypes()).getName());
	}

	@Test
	public void testInnerStruct() {
		org.moosetechnology.verveineCore.gen.famix.Class clazz = entityNamed(org.moosetechnology.verveineCore.gen.famix.Class.class, "Student");
		assertNotNull(clazz);

		assertEquals(3, clazz.numberOfAttributes());
		assertEquals(0, clazz.numberOfTypes());
		assertNotNull(clazz.getContainer());

		assertEquals("College", clazz.getContainer().getName());
	}

}

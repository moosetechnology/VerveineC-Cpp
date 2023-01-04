package org.moosetechnology.verveineC;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.moosetechnology.famix.cpp.Enum;
import org.moosetechnology.famix.cpp.EnumValue;

class EnumsTest extends AbstractTest {

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
		assertEquals( 1, entitiesOfType(Enum.class).size());
		assertEquals( 7, entitiesOfType(EnumValue.class).size());
	}

	@Test
	public void testEnumValuesNames() {	
		EnumValue eval;

		eval = entityNamed(EnumValue.class, "Monday");
		assertNotNull(eval);
		assertEquals("week", eval.getParentEnum().getName());

		eval = entityNamed(EnumValue.class, "Tuesday");
		assertNotNull(eval);
		assertEquals("week", eval.getParentEnum().getName());

		eval = entityNamed(EnumValue.class, "Wednesday");
		assertNotNull(eval);
		assertEquals("week", eval.getParentEnum().getName());

		eval = entityNamed(EnumValue.class, "Thursday");
		assertNotNull(eval);
		assertEquals("week", eval.getParentEnum().getName());

		eval = entityNamed(EnumValue.class, "Friday");
		assertNotNull(eval);
		assertEquals("week", eval.getParentEnum().getName());

		eval = entityNamed(EnumValue.class, "Saturday");
		assertNotNull(eval);
		assertEquals("week", eval.getParentEnum().getName());

		eval = entityNamed(EnumValue.class, "Sunday");
		assertNotNull(eval);
		assertEquals("week", eval.getParentEnum().getName());
	}

	@Test
	@Disabled("Disabled until incoming accesses are implemented")
	public void testEnumValueAccess() {
		EnumValue eval = entityNamed(EnumValue.class, "Wednesday");
		assertNotNull(eval);

		assertEquals(1, eval.getIncomingAccesses().size());
		assertEquals("main", first(eval.getIncomingAccesses()).getAccessor().getName());
	}

}

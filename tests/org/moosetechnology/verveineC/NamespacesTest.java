package org.moosetechnology.verveineC;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.moosetechnology.famix.famixcentities.Function;
import org.moosetechnology.famix.famixcppentities.Namespace;
import org.moosetechnology.famix.famixtraits.TWithFunctions;

class NamespacesTest extends AbstractTest {

	/* parsing only once for all tests
	 */
	@BeforeAll
	public static void setup() {
		newParser();

		parser.setUserProjectDir("./test_src/Namespaces");
		parser.parse();
	}

	@Test
	public void testNumberOfEntities() {	
		assertEquals( 3, entitiesOfType(Namespace.class).size());
		assertEquals( 4, entitiesOfType(Function.class).size());
	}

	@Test
	public void testFirstSpace() {
		Namespace nspace = entityNamed(Namespace.class, "first_space");
		assertNotNull(nspace);

		assertEquals(1, ((TWithFunctions)nspace).numberOfFunctions());
		assertEquals(0, nspace.getNumberOfChildren());
		assertNull(nspace.getParentScope());

		assertEquals("func", first( ((TWithFunctions)nspace).getFunctions()).getName());
	}

	@Test
	public void testSecondSpace() {
		Namespace nspace = entityNamed(Namespace.class, "second_space");
		assertNotNull(nspace);

		assertEquals(1, ((TWithFunctions)nspace).numberOfFunctions());
		assertEquals(1, nspace.getNumberOfChildren());
		assertNull(nspace.getParentScope());

		assertEquals("func", first( ((TWithFunctions)nspace).getFunctions()).getName());
	}

	@Test
	public void testThirdSpace() {
		Namespace nspace = entityNamed(Namespace.class, "third_space");
		assertNotNull(nspace);

		assertEquals(1, ((TWithFunctions)nspace).numberOfFunctions());
		assertEquals(0, nspace.getNumberOfChildren());

		assertNotNull(nspace.getParentScope());
		assertEquals("second_space", nspace.getParentScope().getName());

		assertEquals("func", first( ((TWithFunctions)nspace).getFunctions()).getName());
	}

}

package org.moosetechnology.verveineC;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.moosetechnology.famix.famixcentities.Function;
import org.moosetechnology.famix.famixcentities.Namespace;
import org.moosetechnology.famix.famixtraits.TNamedEntity;
import org.moosetechnology.famix.famixtraits.TPackageable;
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

		assertEquals(1, nspace.numberOfChildEntities());
		assertEquals("func", ((TNamedEntity)first( nspace.getChildEntities())).getName() );

		assertNull(nspace.getParentPackage());
	}

	@Test
	public void testSecondSpace() {
		Namespace nspace = entityNamed(Namespace.class, "second_space");
		assertNotNull(nspace);

		assertEquals(2, nspace.numberOfChildEntities());

		TNamedEntity func = null;
		TNamedEntity subNamespace = null;
		for ( TPackageable child : nspace.getChildEntities()) {
			if (child instanceof Function) {
				func = (TNamedEntity) child;
			}
			else if (child instanceof Namespace) {
				subNamespace = (TNamedEntity) child;
			}
		}

		assertNotNull(func);
		assertEquals("func", func.getName() );
		assertNotNull(subNamespace);
		assertEquals("third_space", subNamespace.getName() );

		assertNull(nspace.getParentPackage());
	}

	@Test
	public void testThirdSpace() {
		Namespace nspace = entityNamed(Namespace.class, "third_space");
		assertNotNull(nspace);

		assertEquals(1, nspace.numberOfChildEntities());
		assertEquals("func", ((TNamedEntity)first( nspace.getChildEntities())).getName() );

		assertNotNull(nspace.getParentPackage());
		assertEquals("second_space", nspace.getParentPackage().getName());
	}

}

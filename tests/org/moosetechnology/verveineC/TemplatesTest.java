package org.moosetechnology.verveineC;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.moosetechnology.famix.famixcentities.Attribute;
import org.moosetechnology.famix.famixcentities.Function;
import org.moosetechnology.famix.famixcentities.NamedEntity;
import org.moosetechnology.famix.famixcentities.Parameter;
import org.moosetechnology.famix.famixcppentities.Method;
import org.moosetechnology.famix.famixcppentities.ParameterType;
import org.moosetechnology.famix.famixcppentities.ParameterizableClass;
import org.moosetechnology.famix.famixcppentities.ParameterizedType;
import org.moosetechnology.famix.famixtraits.TAttribute;
import org.moosetechnology.famix.famixtraits.TParameter;


class TemplatesTest extends AbstractTest {

	/* parsing only once for all tests
	 */
	@BeforeAll
	public static void setup() {
		newParser();

		parser.setUserProjectDir("./test_src/Templates");
		parser.parse();
	}

	@Test
	public void testNumberOfEntities() {	
		assertEquals( 1, entitiesOfType(ParameterizableClass.class).size());
		assertEquals( 1, entitiesOfType(ParameterType.class).size());
		assertEquals( 1, entitiesOfType(Method.class).size());
		assertEquals( 2, entitiesOfType(Function.class).size());
	}

	@Test
	public void testTemplateClass() {
		ParameterizableClass generic = first(entitiesOfType(ParameterizableClass.class));
		assertEquals( "mypair",	generic.getName());

		assertEquals(1, generic.getParameterizedTypes().size());
		ParameterizedType tparam = (ParameterizedType) first( generic.getParameterizedTypes());
		assertEquals("T", tparam.getName());

		assertEquals(1, generic.getAttributes().size());
		TAttribute attribute = first( generic.getAttributes());
		assertEquals("values", attribute.getName());
		assertEquals(tparam, attribute.getDeclaredType());

		assertEquals(1, generic.getMethods().size());
		Collection<TParameter> params = first(generic.getMethods()).getParameters();
		assertEquals(2, params.size());
		for (TParameter p : params) {
			assertEquals(tparam, p.getDeclaredType());
		}
	}

	@Test
	public void testTemplateFunction() {
		Function fct = entityNamed(Function.class, "GetMax");
		assertNotNull(fct);

		assertEquals("T", fct.getDeclaredType().getName());
		assertEquals(ParameterType.class, fct.getDeclaredType().getClass());
		ParameterType tparam = (ParameterType) fct.getDeclaredType();
		
		assertEquals("mypair", ((NamedEntity) tparam.getTypeContainer()).getName());  // not sure this is what should be (the T of GetMax is the same as the one of mypair)
		
		for (TParameter param : fct.getParameters()) {
			assertEquals("T", param.getDeclaredType().getName()); // this should be the same as 'tparam', no ?
		}
	}

}

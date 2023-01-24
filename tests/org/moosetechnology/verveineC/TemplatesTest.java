package org.moosetechnology.verveineC;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.moosetechnology.famix.famixcentities.Function;
import org.moosetechnology.famix.famixcppentities.Method;
import org.moosetechnology.famix.famixcppentities.TemplateParameterType;
import org.moosetechnology.famix.famixcppentities.TemplateClass;
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
		assertEquals( 1, entitiesOfType(TemplateClass.class).size());
		assertEquals( 1, entitiesOfType(TemplateParameterType.class).size());
		assertEquals( 1, entitiesOfType(Method.class).size());
		assertEquals( 2, entitiesOfType(Function.class).size());
	}

	@Test
	public void testTemplateClass() {
		TemplateClass generic = first(entitiesOfType(TemplateClass.class));
		assertEquals( "mypair",	generic.getName());

		assertEquals(1, generic.getParameterTypes().size());
		TemplateParameterType tparam = (TemplateParameterType) first( generic.getParameterTypes());
		assertEquals("T1", tparam.getName());

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

		assertEquals(3, tparam.numberOfTypedEntities());
	}

	@Test
	public void testTemplateFunction() {
		Function fct = entityNamed(Function.class, "GetMax");
		assertNotNull(fct);

		assertEquals("T2", fct.getDeclaredType().getName());
		assertEquals(TemplateParameterType.class, fct.getDeclaredType().getClass());
		TemplateParameterType tparam = (TemplateParameterType) fct.getDeclaredType();
		
		assertEquals("mypair", tparam.getParentParameterizableClass().getName());  // not sure this is what should be (the T of GetMax is the same as the one of mypair)
		
		for (TParameter param : fct.getParameters()) {
			assertEquals("T2", param.getDeclaredType().getName()); // this should be the same as 'tparam', no ?
		}
	}

}

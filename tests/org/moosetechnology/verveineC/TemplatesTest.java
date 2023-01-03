package org.moosetechnology.verveineC;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import eu.synectique.verveine.core.gen.famix.Attribute;
import eu.synectique.verveine.core.gen.famix.Function;
import eu.synectique.verveine.core.gen.famix.Method;
import eu.synectique.verveine.core.gen.famix.Parameter;
import eu.synectique.verveine.core.gen.famix.ParameterType;
import eu.synectique.verveine.core.gen.famix.ParameterizableClass;

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

		assertEquals(1, generic.getParameters().size());
		ParameterType tparam = first( generic.getParameters());
		assertEquals("T", tparam.getName());

		assertEquals(1, generic.getAttributes().size());
		Attribute attribute = first( generic.getAttributes());
		assertEquals("values", attribute.getName());
		assertEquals(tparam, attribute.getDeclaredType());

		assertEquals(1, generic.getMethods().size());
		Collection<Parameter> params = first(generic.getMethods()).getParameters();
		assertEquals(2, params.size());
		for (Parameter p : params) {
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
		
		assertEquals("mypair", tparam.getContainer().getName());  // not sure this is what should be (the T of GetMax is the same as the one of mypair)
		
		for (Parameter param : fct.getParameters()) {
			assertEquals("T", param.getDeclaredType().getName()); // this should be the same as 'tparam', no ?
		}
	}

}

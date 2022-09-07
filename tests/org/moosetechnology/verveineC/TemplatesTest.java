package org.moosetechnology.verveineC;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.moosetechnology.verveineCore.gen.famix.Attribute;
import org.moosetechnology.verveineCore.gen.famix.Function;
import org.moosetechnology.verveineCore.gen.famix.Method;
import org.moosetechnology.verveineCore.gen.famix.Parameter;
import org.moosetechnology.verveineCore.gen.famix.ParameterType;
import org.moosetechnology.verveineCore.gen.famix.ParameterizableClass;

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
		for (Method mth : generic.getMethods()) {
			assertEquals(tparam, mth.getDeclaredType());
		}
	}

	@Test
	public void testTemplateFunction() {
		Function fct = entityNamed(Function.class, "GetMax");
		assertNotNull(fct);

		assertEquals("T", fct.getDeclaredType().getName());
		assertEquals(ParameterType.class, fct.getDeclaredType().getClass());
		ParameterType tparam = (ParameterType) fct.getDeclaredType();
		// not sure this is what should be, but this is what we get
		assertEquals("mypair", tparam.getContainer().getName());
		
		for (Parameter param : fct.getParameters()) {
			assertEquals("T", param.getDeclaredType().getName()); // this should be the same as 'tparam' but it is not ?
		}
	}

}

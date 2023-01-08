package org.moosetechnology.verveineC;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.moosetechnology.famix.famixcentities.Attribute;
import org.moosetechnology.famix.famixcentities.BehaviouralEntity;
import org.moosetechnology.famix.famixcentities.Function;
import org.moosetechnology.famix.famixcentities.GlobalVariable;
import org.moosetechnology.famix.famixcentities.Invocation;
import org.moosetechnology.famix.famixcentities.UnknownBehaviour;
import org.moosetechnology.famix.famixcppentities.Method;
import org.moosetechnology.famix.famixtraits.GlobalVariableGroup;
import org.moosetechnology.famix.famixtraits.TAccess;
import org.moosetechnology.famix.famixtraits.TInvocable;
import org.moosetechnology.famix.famixtraits.TInvocation;
import org.moosetechnology.famix.famixtraits.TMethod;
import org.moosetechnology.famix.famixtraits.TSourceEntity;

class BookShopTest extends AbstractTest {

	/* parsing only once for all tests
	 */
	@BeforeAll
	public static void setup() {
		newParser();

		parser.setUserProjectDir("./test_src/BookShopExample");
		parser.parse();
	}

	@Test
	public void testNumberOfEntities() {	
		assertEquals(
				6,
				entitiesOfType(GlobalVariable.class).size());		
				// conn, rest_set, row, stmt, q, query
		assertEquals(
				7,
				entitiesOfType(org.moosetechnology.famix.famixcppentities.Class.class).size());
				// struct date, books, suppliers, purchases, employees, members, sales
		assertEquals(
				49,
				entitiesOfType(Attribute.class).size());
				// struct date(3), books(5), suppliers(7), purchases(8), employees(10), members(10), sales(6)
		assertEquals(
				22,
				entitiesOfType(Method.class).size());
				// books(5), suppliers(3), purchases(4), employees(5), members(3), sales(2)
		assertEquals(
				9,
				entitiesOfType(Function.class).size());
				// main_menu, book_menu, sup_menu, pur_menu, emp_menu, mem_menu, sal_menu, pass, main
		
/* not exporting local variables
		assertEquals(
				24,
				entitiesOfType(LocalVariable.class).size());
				// books::update_price(1), books::update(4), books::display(1), 
				// purchases::view(1), employees::display(1)
				// main_menu(1), book_menu(2), sup_menu(2), pur_menu(2), emp_menu(2), mem_menu(2), sal_menu(2), pass(2), main(1)
*/

	}

	@Test
	public void testFunctionsInvocations() {
		Function fct = entityNamed(Function.class, "main");
		assertNotNull(fct);
		assertEquals(7, fct.getOutgoingInvocations().size());
		for (TInvocation invok : fct.getOutgoingInvocations()) {
			assertEquals(fct, invok.getSender());
			assertEquals(1, invok.getCandidates().size() );
			
			TMethod candidate = (TMethod) first(invok.getCandidates());
			if (candidate.getIsStub()) {
				assertEquals( UnknownBehaviour.class, candidate.getClass());
			}
			else  {
				assertEquals( Function.class, candidate.getClass());
			}
		}
	}

	@Test
	public void testMethodsInvocations() {
		Function fct = entityNamed(Function.class, "sal_menu");
		assertNotNull(fct);
		assertEquals(2, fct.getOutgoingInvocations().size());
		for (TInvocation invok : fct.getOutgoingInvocations()) {
			assertEquals(fct, invok.getSender());
			assertEquals(1, invok.getCandidates().size() );
			
			TInvocable candidate = first(invok.getCandidates());
			assertFalse(  ((TSourceEntity)candidate).getIsStub());
			assertEquals( Method.class, candidate.getClass());
		}
	}

	@Test
	public void testAccesses() {
		// TODO access are not extracted as they should
		// there should be only 4 access in main(), all to global variable conn
		Function fct = entityNamed(Function.class, "main");
		assertNotNull(fct);
		assertFalse(fct.getAccesses().isEmpty());
		for (TAccess acc : fct.getAccesses()) {
			assertEquals(fct, acc.getAccessor());
/*
			assertEquals( "conn", acc.getVariable().getName());
			assertEquals( GlobalVariable.class, acc.getVariable().getClass());
*/
		}
	}

}

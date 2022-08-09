package org.moosetechnology.verveineC;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.moosetechnology.verveineCore.gen.famix.Attribute;
import org.moosetechnology.verveineCore.gen.famix.Entity;
import org.moosetechnology.verveineCore.gen.famix.Function;
import org.moosetechnology.verveineCore.gen.famix.GlobalVariable;
import org.moosetechnology.verveineCore.gen.famix.LocalVariable;
import org.moosetechnology.verveineCore.gen.famix.Method;
import org.moosetechnology.verveinec.plugin.VerveineCParser;

import ch.akuhn.fame.Repository;

class BookShopTest {
	
	protected VerveineCParser parser;
	protected Repository repo;
	
	@BeforeEach
	public void setup() {
		parser = new VerveineCParser();
		parser.setUserProjectDir("./test_src/BookShopExample");
		repo = parser.getFamixRepo();
		parser.parse();
	}


    public <T extends Entity> Collection<T> entitiesOfType(Class<T> clazz) {
        return repo.all(clazz);
    }

	@Test
	void testNumberOfEntities() {		
		assertEquals(
				6,
				entitiesOfType(GlobalVariable.class).size());		
				// conn, rest_set, row, stmt, q, query
		assertEquals(
				7,
				entitiesOfType(org.moosetechnology.verveineCore.gen.famix.Class.class).size());
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
/* not creating local variables with primitive types
		assertEquals(
				24,
				entitiesOfType(LocalVariable.class).size());
				// books::update_price(1), books::update(4), books::display(1), 
				// purchases::view(1), employees::display(1)
				// main_menu(1), book_menu(2), sup_menu(2), pur_menu(2), emp_menu(2), mem_menu(2), sal_menu(2), pass(2), main(1)
*/

	}

}

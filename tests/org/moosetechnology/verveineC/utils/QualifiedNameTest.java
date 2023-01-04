package org.moosetechnology.verveineC.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.moosetechnology.verveineC.utils.resolution.QualifiedName;

/*
 * NOT A JUNIT TEST -- run as a normal application
 */
public class QualifiedNameTest {

	@Test
	public void testEmpty() {
		QualifiedName qual = new QualifiedName("");
		
		assertFalse(qual.isFullyQualified());
		assertEquals(0, qual.nbParts());
		assertTrue(qual.isEmpty());
		assertEquals("", qual.unqualifiedName());
		assertEquals("", qual.toString());
	}

	@Test
	public void testUnqualified() {
		QualifiedName qual = new QualifiedName("toto");
		
		assertFalse(qual.isFullyQualified());
		assertEquals(1, qual.nbParts());
		assertFalse(qual.isEmpty());
		assertEquals("toto", qual.unqualifiedName());
		assertEquals("toto", qual.toString());
	}

	@Test
	public void testQualified() {
		QualifiedName qual = new QualifiedName("toto::titi::tutu()");
		
		assertTrue(qual.isFullyQualified());
		assertFalse(qual.isAbsoluteQualified());
		assertEquals(3, qual.nbParts());
		assertEquals("tutu()", qual.unqualifiedName());
		assertEquals("toto::titi::tutu()", qual.toString());
	}

	@Test
	public void testQualifiedAbsolute() {
		QualifiedName qual = new QualifiedName("::toto::titi");
		
		assertTrue(qual.isFullyQualified());
		assertTrue(qual.isAbsoluteQualified());
		assertEquals(2, qual.nbParts());
		assertEquals("titi", qual.unqualifiedName());
		assertEquals("::toto::titi", qual.toString());
	}

	@Test
	public void testNameQualifiers() {
		QualifiedName qual = new QualifiedName("toto::titi::tutu()").nameQualifiers();
		
		assertTrue(qual.isFullyQualified());
		assertFalse(qual.isAbsoluteQualified());
		assertEquals(2, qual.nbParts());
		assertEquals("toto::titi", qual.toString());

		qual = new QualifiedName("::toto::titi").nameQualifiers();
		assertTrue(qual.isAbsoluteQualified());

		qual = new QualifiedName("::toto").nameQualifiers();
		assertTrue(qual.isAbsoluteQualified());
	}

	@Test
	public void testForeach() {
		QualifiedName qual = new QualifiedName("toto::titi::tutu()");

		int i = 1;
		for (String part : qual) {
			switch (i) {
			case 1: assertEquals("toto", part);   break;
			case 2: assertEquals("titi", part);   break;
			case 3: assertEquals("tutu()", part); break;
			}
			i++;
		}
	}

	@Test
	public void testQualifiedTemplate() {
		QualifiedName qual = new QualifiedName("toto::titi<bla::blih>");
		
		assertTrue(qual.isFullyQualified());
		assertEquals(2, qual.nbParts());
		assertEquals("titi<bla::blih>", qual.unqualifiedName());
		assertEquals("toto::titi<bla::blih>", qual.toString());
	}

	@Test
	public void testUnqualifiedFct() {
		QualifiedName qual = new QualifiedName("toto()->int");
		
		assertFalse(qual.isFullyQualified());
		assertEquals(1, qual.nbParts());
		assertEquals("toto()", qual.unqualifiedName());
		assertEquals("toto()", qual.toString());
	}

	@Test
	public void testQualifiedFct() {
		QualifiedName qual = new QualifiedName("toto::titi::tutu()->int");
		
		assertTrue(qual.isFullyQualified());
		assertFalse(qual.isAbsoluteQualified());
		assertEquals(3, qual.nbParts());
		assertEquals("tutu()", qual.unqualifiedName());
		assertEquals("toto::titi::tutu()", qual.toString());
	}

}

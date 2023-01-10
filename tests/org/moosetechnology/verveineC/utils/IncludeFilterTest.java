package org.moosetechnology.verveineC.utils;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.moosetechnology.verveineC.utils.fileAndStream.IncludeToLowerFilterStream;
import org.moosetechnology.verveineC.utils.fileAndStream.IncludeWithHExtensionFilterStream;

public class IncludeFilterTest {
	
	public static final String SRC =
					"// comment of include\n" +
					"#define TOTO 0\n" +
					"#include <stdIO>\n" +
					"#include \"OtherOne.h\"\n" +
					"void AndMore() {}";
	public static final String TGT_LOWER =
					"// comment of include\n" +
					"#define TOTO 0\n" +
					"#include <stdio>\n" +
					"#include \"otherone.h\"\n" +
					"void AndMore() {}";
	public static final String TGT_ADD_H =
					"// comment of include\n" +
					"#define TOTO 0\n" +
					"#include <stdIO.h>\n" +
					"#include \"OtherOne.h\"\n" +
					"void AndMore() {}";
	public static final String TGT_BOTH = 
					"// comment of include\n" +
					"#define TOTO 0\n" +
					"#include <stdio.h>\n" +
					"#include \"otherone.h\"\n" +
					"void AndMore() {}";

	// utility
	private InputStream stringToStream(String src2) {
		InputStream stream = null;
		try {
			stream = new ByteArrayInputStream(SRC.getBytes("UTF-8"));
		}
		catch (IOException e) {
			// should not happen
		}
		return stream;
	}

	@Test
	public void testIncludeToLowerFilterStream() {
		byte[] srcBuf = new byte[SRC.length()+2];  // +2 to give room for added ext
		InputStream input;
		int byteRead = 0;

		input = new IncludeToLowerFilterStream( stringToStream(SRC) );
		try {
			byteRead = input.read(srcBuf);
		} catch (IOException e) {
			fail("IO error: " + e.getMessage());
		}
		assertEquals( TGT_LOWER.length(), byteRead);
		assertEquals( TGT_LOWER, new String(srcBuf, 0, byteRead) );
	}
	
	@Test
	public void testIncludeWithHExtensionFilterStream() {
		byte[] srcBuf = new byte[SRC.length()+2];  // +2 to give room for added ext
		InputStream input;
		int byteRead = 0;

		input = new IncludeWithHExtensionFilterStream( stringToStream(SRC) );
		try {
			byteRead = input.read(srcBuf);
		} catch (IOException e) {
			fail("IO error: " + e.getMessage());
		}
		assertEquals( TGT_ADD_H.length(), byteRead);
		assertEquals( TGT_ADD_H, new String(srcBuf, 0, byteRead) );
	}

	@Test
	public void testIncludeToLowerWithHExtensionFilterStream() {
		byte[] srcBuf = new byte[SRC.length()+2];  // +2 to give room for added ext
		InputStream input;
		int byteRead = 0;

		input = new IncludeToLowerFilterStream( new IncludeWithHExtensionFilterStream( stringToStream(SRC)));
		try {
			byteRead = input.read(srcBuf);
		} catch (IOException e) {
			fail("IO error: " + e.getMessage());
		}
		assertEquals( TGT_BOTH.length(), byteRead);
		assertEquals( TGT_BOTH, new String(srcBuf, 0, byteRead) );
	}

	@Test
	public void testIncludeWithHExtensionToLowerFilterStream() {
		byte[] srcBuf = new byte[SRC.length()+2];  // +2 to give room for added ext
		InputStream input;
		int byteRead = 0;

		input = new IncludeWithHExtensionFilterStream( new IncludeToLowerFilterStream( stringToStream(SRC)));
		try {
			byteRead = input.read(srcBuf);
		} catch (IOException e) {
			fail("IO error: " + e.getMessage());
		}
		assertEquals( TGT_BOTH.length(), byteRead);
		assertEquals( TGT_BOTH, new String(srcBuf, 0, byteRead) );
	}

}

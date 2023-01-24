//  Copyright (c) 2023 Inria, France
//  Copyright (c) 2007-2008 University of Bern, Switzerland
//  
//  Written by Adrian Kuhn <akuhn(a)iam.unibe.ch>
//  Modified by Nicolas Anquetil <nicolas.anquetil(a)inria.fr>
//  
//  This file is part of 'Fame Code Generation (for Java)'.
//  
//  'Fame Code Generation (for Java)' is free software: you can redistribute it
//  and/or modify it under the terms of the GNU General Public License as
//  published by the Free Software Foundation, either version 3 of the License,
//  or (at your option) any later version.
//  
//  'Fame Code Generation (for Java)' is distributed in the hope that it will
//  be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
//  Public License for more details.
//  
//  You should have received a copy of the GNU General Public License along
//  with 'Fame Code Generation (for Java)'. If not, see
//  <http://www.gnu.org/licenses/>.
//  

package org.moosetechnology.verveineC.plugin;

import ch.akuhn.fame.MetaRepository;
import ch.akuhn.fame.Repository;
import ch.akuhn.fame.codegen.CodeGeneration;
import ch.akuhn.fame.parser.Importer;
import ch.akuhn.fame.parser.InputSource;

import static ch.akuhn.util.Out.puts;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Generates a meta-model in Java from a description in Famix
 * To get this description, do the following in Moose:
 * <code>'some-file-name.mse' asFileReference writeStreamDo: [:stream | MooseModel metamodel exportOn: stream]</code>
 */
public class FamixCppCodegen {
	
	public static final String[] TO_STRING_METHOD = {
			"    @Override"
			, "    public String toString() {"
			, "    	// to ease debugging"
			, "    	return \"a-\" + this.getClass().getCanonicalName() + \"(\" + this.getName() + \")\";"
			, "    }"
			, ""};

    public static void main(String... args) {
        generateMetamodelClasses("famixCpp.mse", "org.moosetechnology.famix", "gen", "");
        
        try {
			addToStringMethod();
		} catch (IOException e) {
			System.err.println("Unable to add toString() method to NamedEntity.java");
			System.err.println("  This method is not mandatory, but it helps debugging");
			e.printStackTrace();
		}
    }

	private static void generateMetamodelClasses(String mseFile, String destinationPackage, String outputDirectory, String classNamePrefix) {
		InputSource input = InputSource.fromResource(mseFile);
        MetaRepository fm3 = MetaRepository.createFM3();
        Importer builder = new Importer(fm3);
        builder.readFrom(input);
        Repository famix = builder.getResult();
        CodeGeneration gen = new CodeGeneration(destinationPackage, outputDirectory, classNamePrefix);
        gen.accept(famix);
        puts("generation done");
	}

	private static void addToStringMethod() throws IOException {
		File oldFile = new File("gen/org/moosetechnology/famix/famixcentities/NamedEntity.java");
		File tmpFile = new File("gen/org/moosetechnology/famix/famixcentities/NamedEntity-tmp.java");
		
		if (! oldFile.renameTo(tmpFile)) {
			throw new IOException("could not rename generated NamedEntity.java file");
		}

		BufferedReader br = new BufferedReader(new FileReader(tmpFile));
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("gen/org/moosetechnology/famix/famixcentities/NamedEntity.java")));

		copyUpToNameAttribute(br, bw);
		insertTostringMethod(br, bw);
		copyEndOfFile(br, bw);

		br.close();
		bw.close();
		
		if (! tmpFile.delete()) {
			throw new IOException("could not delete temporary NamedEntity.java file");
		}

        puts("added toString() to NamedEntity");

	}

	private static void copyUpToNameAttribute(BufferedReader br, BufferedWriter bw) throws IOException {
		String line;
		int i;
		boolean stop = false;

		while ( (! stop) && (line=br.readLine()) != null ) {
			// looking for "private String name" attribute declaration
			if ( (i=line.indexOf("private", 0)) > 0 ) {
				if ( (i=line.indexOf("String", i)) > 0 ) {
					if ( (i=line.indexOf("name", i)) > 0 ) {
						stop=true;
					}
				}
			}
			bw.write(line);
			bw.newLine();
		}
	}

	private static void insertTostringMethod(BufferedReader br, BufferedWriter bw) throws IOException {
		for (String line : TO_STRING_METHOD) {
			bw.write(line);
			bw.newLine();
		}
		
	}

	private static void copyEndOfFile(BufferedReader br, BufferedWriter bw) throws IOException {
		String line;

		while ( (line=br.readLine()) != null ) {
			bw.write(line);
			bw.newLine();
		}
	}

}

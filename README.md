# VerveineC-Cpp
A C/C++ to Famix extractor (see  )

This is a MSE (Famix) model extractor for C/C++. Look at "https://en.wikipedia.org/wiki/Moose_(analysis)" to understand what are Famix and Moose.

This exporter works on top of the CDT plugin from Eclipse and is itself an Eclipse plugin. It is intended to run in an "headless Eclipse" (Eclipse running in "batch" mode).

It is known to run on Version: 2022-06 (4.24.0) Build id: 20220609-1112

As a starter, it is best to have an Eclipse distribution locally. Run Eclipse from this directory; load this Eclipse project into Eclipse ; export it (still within Eclipse) as a "Deployable plug-ins and fragments". When asked in what directory to export, indicate the local distribution, this will put a new file "verveine.extractor.Cpp_1.0.0.<some-date>.jar" in the "plugins" sub-directory of the Eclipse distribution.

To run the extractor, one must go in said Eclipse distribution directory and execute the "verveineC.sh" shell script.

## Documentation

There is an attempt to document the exporter in the `doc` directory

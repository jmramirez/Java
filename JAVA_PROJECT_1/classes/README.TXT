All *.class files which are NOT members of a named package should appear
in this directory.

Once named packages have been introduced, all  *.class file belonging to
any named package should appear in a sub-directory of this directory ...
name of the subdirectory should be the same as the package name concerned.

*.class files are placed in the correct subdirectories by the compiler, 
depending on the packages specified in the source files, the CLASSPATH
entries and the specific compile command you used when you compile the
code.

If you make ANY mistake in any of those factors you should delete ALL the
*.class files in your project, correct the mistake(s) and recompile ALL
the source files concerned.

It is unlikely that you will be able to successfully run your programs if
you move *.class files from one classes package subdirectories to another
in an attempt to avoid recompilation.


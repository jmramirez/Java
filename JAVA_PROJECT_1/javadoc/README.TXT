The javadoc utility (part of the JDK download) generates *.html and other
files in the appropriate directories.

Providing you are careful about the package membership of your *.java files
and the command you use to generate your javadoc files they will be
correctly positioned in the appropriate subdirectories by the javadoc
utility when it runs.

In general, you would only use javadoc with *.java files that have
already compiled without errors. 

You should NOT move files from the directories and subdirectories in
which javadoc places them.  You MUST submit EVERYTHING that javadoc
generates.

If you make a mistake in either the package names in your source files or 
the location of a source file you should remove ALL the files in the
/javadoc subdirectories for the project and regenerate the ALL the javadoc
files after you have make the necessary corrections to the package names
and/or the location of the source files.

If you make any changes to ANY javadoc comments in ANY source file in
your project you must regenerate the javadoc files for ALL the source
files in your project you cannot just regenerate the *.html file for ONLY
the source files you have changed.

You are strongly advised to view the javadoc web pages for your each lab
and assignment BEFORE you submit your work.  If they are not correct you
must make changes and regenerate them  Not doing so is a common cause
of lost marks.

It causes a lot of confusion if your javadoc files do not accurately
represent your source code (so do not forget to update your javadoc
when you make changes to the source files).

 



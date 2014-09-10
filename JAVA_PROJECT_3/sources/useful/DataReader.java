//DataReader.java

package useful;


import java.io.*;
/**
* The interface DataReader declares(lists) the following public methods,
* readLine(), resetMarker() and close.
  @author Jose Ramirez
  @version 1 -2014-02-19
 */

public interface DataReader {
    
    //--------------------------readline()
    /**
     * <p>This method calls the readLine() method of the BufferedReader.
     * Each call to readLine() either returns the next data item or signals
     * end-of-file. To do this it bypasses comments in test data stream.
     * 
     * In order to separate the data from the comments, this method takes into
     * consideration the marker. If the marker is set to null it returns the 
     * the line just read. If the line just read contains a comment marker that 
     * is not at the beginning of the line, it returns only the data at the 
     * star of the line. If the line does not contain a comment marker, it 
     * returns the hole line. If the end of file is detected it returns null.  
     * </p>
    */
    public String readLine() throws IOException;
    
    //--------------------------resetMarker()
    /**
     * <p>This method resets the command marker text using
     * the parameter data. It returns the old(previous) value
     * of the marker text.
     * </p>
    */
    public String resetMarker(String mkr);
    
    //--------------------------close()
    /**
     * <p>This method calls the close() of the BufferdReader,
     * theReader.
     * </p>
    */
    public void close() throws IOException;
    
}

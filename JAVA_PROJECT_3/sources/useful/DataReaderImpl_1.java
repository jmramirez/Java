//DataReaderImpl_1.java

package useful;


import java.io.*;


/**
* Implements the DataReader interface
  @author Jose Ramirez
  @version 2 -2014-04-10
 */
public class DataReaderImpl_1 implements DataReader {
    
    //----instance data
    private String marker; // holds the comment marker text
    private BufferedReader theReader; //is the stream based on standard input
    
    //----constructor
    
    /**
    * No param constructor. should create a BufferedReader based on 
    * standard-input and set the reference , theReader to point to it.
    * Set the comment marker to "#. the default value.
    */
    public DataReaderImpl_1() {
        theReader = new BufferedReader(new InputStreamReader(System.in));
        // the BufferedReader object is created
        marker = "#";   //market is set to "#"
    } 
    
    //----------------------method close()
    public void close() { //throws IOException
        try
        {
          theReader.close();  // the method close() of BufferedReader is called
        }
        catch (IOException e)
        {
          System.err.println("IOException caught: " + e.getMessage());
          e.printStackTrace(System.err);
        }
        catch (Exception e)
        {
          System.err.println("Exception caught: " + e.getMessage());
          e.printStackTrace(System.err);
        }
    } //end of close()
    
    
    //---------------------method ReadLine()
    public String readLine() { //throws IOException
        String ln = null; //used to return the line read 
        
        try
        {
          ln = theReader.readLine(); //the method readLine is called to read the line from the file  
          
          if(marker == null){
              return ln; //if the marker is set to null it returns the whole line
          }
          
          else{
              if(ln != null){ //if the line read is not the end-of-file
                while (ln != null && ln.startsWith(marker)){ //this is to pass the lines that 
                  ln = theReader.readLine();   // are just coments
                }
                if(ln == null)
                {
                    return ln;
                }
                if(ln.indexOf(marker) > 0){ //this is to verify that the line read contains the marker                
                  ln = ln.substring(0, ln.indexOf(marker));
                  //This is to separate data from comments and return just the 
                  //data
                }
              }
          }
        }
        catch (IOException e)
        {
            System.err.println("IOException caught: " + e.getMessage());
            e.printStackTrace(System.err);
        }
        catch (Exception e)
        {
          System.err.println("Excepion caught: " + e.getMessage());
          e.printStackTrace(System.err);
        }
        return ln; //if the line read is end-of-file it returns null
    } //end of readLine() 
    
    //---------------------method resetMarker()
    public String resetMarker(String mkr){
        String old = marker; // used to return the old marker
        marker = mkr; //the marker is changed to the new value
        return old; //the old marker is returned. 
    } //end of resertMarker()
    
}

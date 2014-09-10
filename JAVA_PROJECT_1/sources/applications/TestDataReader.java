//TestDataReader.java

package applications;

import useful.*;
import java.io.*;

/**
* TestDataReader creates and uses an object of the class DataReaderImpl_1 that 
* implements interface DataReader and works calling its methods. 
  @author Jose Ramirez
  @version 1 -2014-02-19
 */
public class TestDataReader {
    //The DataReaderImpl_1 object is declared 
    private DataReaderImpl_1 dr;
    
    //============================================methods
    //=========================(alphabetic by method name)

    //--------------------------------------------init()
    /**
    * Performs one-time intialization at start of application
    */
    public void init(String[] args) throws IOException{
        
        if(args.length != 0){ //if non argument is recived by command-line
            
            usage();
            System.err.println("program terminating abnormally");
            System.exit(9999);
        }
        //The DataReaderImpl_1 object is created
        dr = new DataReaderImpl_1();
        
        System.out.println(dr.toString());
    }//end of init()
    
    //--------------------------------------------run()
    /**
    * Controls the major part of the application.
    * In this case it shows the output of all the methods of 
    * DataReaderImp_1, such as readLine() and resetMarker(), ,
    */
    public void run() throws IOException{
        String buf; //a String to contain one line from input stream
        String newMarker; //a String to contain a newMarker to be changed
        String oldMarker; // a String to contain the oldMarker changed
        
        System.out.println("Testing the readLine() method:"); //the method to be called
        buf = dr.readLine(); //read the first line
        while (buf != null){ 
            System.out.println(buf); //it prints the line read
            buf = dr.readLine(); //reads the next line
        } //end while()
        
        System.out.println("Testing the resetMarker() method:"); //calls the next method
        newMarker = "//"; //the newMarker
        oldMarker = dr.resetMarker(newMarker);
        System.out.println("The old marker is:" + oldMarker); //it shows the oldMarker
        System.out.println("The new marker is:" + newMarker); // it shows the newMarker
        
        
    }  //end of run()
    
    
     //--------------------------------------------usage()
    /**
    * Displays a help message for how to use this application
    */
    private void usage(){
        //it prints an error in case that the command-line argument is missing
        System.err.println("USAGE IS:" + 
                            "It is neccesary to pass command-line arguments "  );
    } //end of usage()
    
    //--------------------------------------------wrap()
    /**
    * it calls the close() method just before the application ends.
    */
    public int wrap() throws IOException{
        int retval = 0;
        dr.close(); //the method close() is called
        return retval;
    } //end of wrap()
    
    //--------------------------------------------main()
    /**
    * This is the first method called.  Controls the application.
    */
    public static void main(String[] args) throws IOException{
        TestDataReader test = new TestDataReader();
        
        test.init(args);
        test.run();
        test.wrap();
    }//end of main()
}//end of class

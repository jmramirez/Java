//TestBook.java

package applications;

import java.io.*;
import java.util.*;
import business.*; //nedeed for business classes
import useful.*;   //nedeed for business classes

/**
* <p>Application exists to test (business) class Book.  It does this
* by making object(s) of class Book and calling its methods using test
* data read from a file that is specially created to provide such
* data, specially chosen to provide a through test.</p>
 * @author Jose Ramirez
 * @version 1 -2014-04-10
 */
public class TestBook {
  //============================================static data
  // none

  //============================================instance data
  /** In which to store args from command line  */
  private DataReaderImpl_1 br; // (reference to) the file object which will read the data
  private String buf; // into DataReaderImpl_1 reads
  private Hashtable myHash;     // stores the Book objects
  private Book inItem; // use in deserialization
  
  //============================================constructors
  // none defined ( :. default constructor provided by compiler )

  //============================================methods
  //=========================(alphabetic by method name)

  //--------------------------------------------init()
  /**
  * Performs one-time intialization at start of application
  * typically the following...
  * <p>processes the command-line arguments.</p>
  * <p>opens input and/output files or database(s)</p> 
  * <p>opens network connections</p>
  *
  * @param args arguments from command-line
  */
  private void init(String[] args)  //throws IOException, ClassNotFoundException
  { 
    try
    {
      System.out.println("++++++TestBook running in: " + System.getProperty("os.name")+"\n"); //to show the operating system where is running
      br = new DataReaderImpl_1(); // create the file object (it is open when created)
    
      // create the Hashtable
      // NOTE: this will produce two warning metssages from the
      // compiler (you can safely ignore them - as discussed
      // in the classroom.

      // To provide suitable backup you should make this by copying
      // (usually) the most recently created bookHash.in .
      // You might also want to make and keep numbered backup copies
      // of bookHash.in to provide several possible "restart files" until
      // (at least) your applications is fully tested"
      // 
      File serialized = new File("../data/bookHash.in");
      if (serialized.exists())
      {
        FileInputStream fis = new FileInputStream(serialized);
        ObjectInputStream s = new ObjectInputStream(fis);
        //  read objects in the same sequence as they were written
        myHash = (Hashtable) s.readObject();
        s.close();
      }
      else
      {
        // even if there is no serial.in you still need a Hashtable object
        myHash = new Hashtable(25); // create an empty Hashtable
      }
    }
    catch (IOException  e)
    {
       System.err.println("IOException caught: " + e.getMessage());
       e.printStackTrace(System.err);
    }
    catch (Exception e)
    {
       System.err.println("Exception caught  : " + e.getMessage());
       e.printStackTrace(System.err);
    }
  } //end of init
  
  
  
  /**
   * Controls the major part of the application (typically in a loop which
   * reads input file(s). This method controls the getting of the data,
   * making a Book object and causes the contents of the object to be
   * displayed and also shows all the methods applied on the object. Test will Run with NO command 
   * line arguments and NO keyboard input, only from datascripts by redirection.
   *  
   */
  public void run() //throws IOException
  {
    try
    {
      int counter = 0;
      String Action= ""; //to indicate the Action to run
      StringBuffer cvs = new StringBuffer(128); //used by getData() and update()
      Book nemo; // (reference to) an object of class Book
      int returned = 0; // to hold value returned by MyDatReader
      StringBuffer sb = new StringBuffer(128);  // to contain output
      nemo = new Book();
      
       /*The first data line of the datascript must now be an Action? line.
        And then at the start of every iteration the next line read
        should be either an Action? statement or end of file.*/
      do
      { 
        buf= br.readLine();
        if( buf != null) //to verify that is not EOF
        {
            
          Action = buf.substring(0,7); //to define the Action to run
        }
      
        if(( buf == null) || (Action.equals("Action0"))) //Action0 or null, the program ends.
        {
          if(buf == null)
          {
            System.out.println(" End Of File.....");
          }
          System.out.println("\n----------------------End of Test-------------------------");
        }
      
      
        if(Action.equals("Action1")) //Action1 make an object of class Book
        {
          cvs.setLength(0);
          sb.setLength(0);
          System.out.println("\n" + "-----------Test Number " + ++counter + "------------" + "\n");
          sb.append( "\n" + buf + "\n");
          returned = Book.getData(cvs,br,"~");
          sb.append("\n" + "Testing method getData(cvs,br,\"~\"):");
          sb.append("\n"+"Returned err  after getData()= " + returned);
          sb.append("\nString Buffer csv after getData() is :" + cvs + "\n");
          nemo = new Book();
          sb.append("\n" + "Testing method updade(cvs):");
          returned = nemo.update(cvs);
          sb.append("\nReturned err after uptade(sb) = " + returned + "\n");
          sb.append("\n" + "Testing method formatDisplay(sb):\n");
          nemo.formatDisplay(sb);
          sb.append("\n" + "Testing method formatAsHtml(sb):\n");
          nemo.formatAsHtml(sb);    
          myHash.put(nemo.getPrimaryKey(), nemo);
          System.out.println("\n" + sb + "\n");
	
        } //end of Action1
      
        if(Action.equals("Action2")) //Action2 calls the method getPrimaryKey
        {
          sb.setLength(0);
          sb.append( "\n" + buf + "\n");
          sb.append("\nOutput of getPrimaryKey():");
	  sb.append("\nPrimarykey = " + nemo.getPrimaryKey() + "\n");
          System.out.println(sb);
        } //end of Action2
        
      }while((buf != null)&&(! Action.equals("Action0"))); //Action0 ends the program
    }
    catch (Exception e)
    {
       System.err.println("Exception caught  : " + e.getMessage());
       e.printStackTrace(System.err);
    }
  } //end of run
  
   //--------------------------------------------usage()
  /**
  * Displays a help message for how to use this application
  */
  private void usage()
  {
  System.err.println("USAGE IS: " +
                     "Test will Run with NO comand line arguments and NO keyboard input, only from" +
                     " datascript by redirection");
  } // end of usage()
  
  
  //--------------------------------------------wrap()
  /**
  * Performs one-time cleanup just before the application ends.
  * <p>closes input and/output files or database(s)</p> 
  * <p>closes network connections</p>
  */
  public int wrap() //throws IOException, ClassNotFoundException
  {
      int retval = 0;
      try
      {
        
        br.close(); //the method close() is called
        StringBuffer items = new StringBuffer();
        StringBuffer sb =new StringBuffer();
        
        Object key;
        
        FileOutputStream fos = new FileOutputStream("../data/bookHash.out");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        // do the serialization
        oos.writeObject(myHash);
        // flush and close the output file
        oos.flush();
        oos.close();
        
        //This lines are to show the object that are in the HashTable
        System.out.println("\n-----------Show Data from HashTable------------------------");
        
        FileInputStream fis = new FileInputStream("../data/bookHash.out");
        ObjectInputStream s = new ObjectInputStream(fis);
        // read objects in the same sequence as they were written
        myHash = (Hashtable) s.readObject();
        s.close();

        System.out.println("Size of myHash is : " + myHash.size() );

        for (Enumeration e = myHash.keys() ; e.hasMoreElements() ;) 
        {
          key = e.nextElement();     // is an Object and is used as an Object therefore no need to cast it (see stmt 38)
          inItem = (Book) myHash.get(key);
          inItem.formatDisplay(items);
        }
        
        sb.setLength(0);
        sb.append(items);
        System.out.println(sb);
      }
      catch (IOException  e)
    {
       System.err.println("IOException caught: " + e.getMessage());
       e.printStackTrace(System.err);
    }
    catch (Exception e)
    {
       System.err.println("Exception caught  : " + e.getMessage());
       e.printStackTrace(System.err);
    }
        return retval;
    
    
  } //end of wrap
  
  
  //--------------------------------------------main()
  /**
  * This is the first method called. It makes an object or its
  * own class then calls methods init(), run() and wrap() in that
  * sequence. Thus it clearly and completely controls the application
  * (but the major work is delegated to be done in the init(), run() and
  * wrap() methods).
  */
  public static void main(String[] args) 
  {
    TestBook theApp = new TestBook(); // make object of own class

    theApp.init(args); //Then call its methods
    theApp.run();
    theApp.wrap();
  } //end of main
    
} //end of class


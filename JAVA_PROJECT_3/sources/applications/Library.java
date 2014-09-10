//Library.java

package applications;

import java.io.*;
import java.util.*;
import business.*; //nedeed for business classes
import useful.*;   //nedeed for business classes

/**
* <p>Application exists to demonstrate how similar the applications
* how similar the applications are to real-world applications.  It does this
* by Defining the statements INSERT and Select used in SQL.</p>
 * @author Jose Ramirez
 * @version 1 -2014-04-10
 */
public class Library {
  //============================================static data
  // none

  //============================================instance data
  /** In which to store args from command line  */
  private DataReaderImpl_1 br; // (reference to) the file object which will read the data
  private StringBuffer buf = new StringBuffer(); // into DataReaderImpl_1 reads
  private Hashtable myHash;     // stores the InventoryItem objects
  private Book inItem; // use in deserialization
  private String ln;
  
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
      System.out.println("++++++Library running in: " + System.getProperty("os.name")+"\n"); //to show the operating system where is running
      br = new DataReaderImpl_1(); // create the file object (it is open when created)
    
      // create the Hashtable
      // NOTE: this will produce two warning metssages from the
      // compiler (you can safely ignore them - as discussed
      // in the classroom.

      // To provide suitable backup you should make this by copying
      // (usually) the most recently created serial.out.
      // You might also want to make and keep numbered backup copies
      // of serial.out to provide several possible "restart files" until
      // (at least) your applications is fully tested"
      // 
      File serialized = new File("../data/bookDataBase.in");
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
  
  
  //--------------------------------------------insert(StringBuffer sb)
  /**
   * The contents of sb will be the line just read from the datascript.
   * Picks up the data, removes all single quotes, creates an empty book
   * and send the CSV created to the update(..) method of class Book, 
   * finally the new object is added to the database (Hashtable);
   */
  
  public int insert(StringBuffer sb)
  {
    int returned =0; //to hold value returned for update;
    StringBuffer cvs= new StringBuffer(128); //used to hold the data and update()
    Book nemo;
    String data = sb.substring(sb.indexOf("(")+1,sb.indexOf(")"));
    data = ","+ data.replace("\"","")+",";
    StringBuffer output = new StringBuffer(128);
    cvs.append(data);
    nemo = new Book();
    returned = nemo.update(cvs);
    myHash.put(nemo.getPrimaryKey(),nemo);
    return returned;
  }
  
  //-----------------------------------------errorHtml(StringBuffer temp)
  /**
   * This method is used to show an Error message when a book is not found on the
   * Hashtable(database).
   */
  
  public int errorHtml(StringBuffer temp)
    {
      int retval = 0;
      temp.append("\n<html>\n<head><link rel=\"stylesheet\" type=\"text/css\" href=\"mystyle.css\">"+
                "\n<title> Testing Class Book</title>\n<h1>404 - not found</h1>\n</body>\n</html>");
           return retval;
    }
  
  //--------------------------------------------select(StringBuffer sb)
  /**
   * The contents of sb will be the line just read from the datascript.
   * Picks up the value and use it to extract the appropriate object
   * from the Hashtable(database).
   */
  
  public int select(StringBuffer sb)
  {
    int result = 0;
    StringBuffer cvs= new StringBuffer(128);
    Book nemo;
    String value = sb.substring(sb.indexOf("= ")+2,sb.indexOf(";"));
    if(myHash.containsKey(value))
    {
      
      nemo = (Book) myHash.get(value);
      nemo.formatAsHtml(cvs);
      result =1;
    }
    else
    { 
       result = errorHtml(cvs);
    }
    System.out.println(cvs);
    return result;
  }
  
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
      do
      {
          ln= br.readLine();
          if(ln !=null)
          {
            if(ln.startsWith("INSERT")) //when the INSERT statement is read 
            {   
                System.out.println("\nTesting INSERT : \n"+ln+"\n");
                buf.setLength(0);
                insert(buf.append(ln));
            }
            if(ln.startsWith("SELECT")) //when the Select statement is read 
            {
              System.out.println("\nTesting SELECT : \n"+ln+"\n");   
              buf.setLength(0);
              select(buf.append(ln));
            }
          }
          if(ln == null)
          {
            System.out.println("End of File");
          }
      }while (ln != null); //the process finish at the END of File
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
        
        FileOutputStream fos = new FileOutputStream("../data/bookDataBase.out");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        // do the serialization
        oos.writeObject(myHash);
        // flush and close the output file
        oos.flush();
        oos.close();
        
        //this lines are to show what is in the HashTable
        System.out.println("\n-----------Show Data from HashTable------------------------"); 
        
        FileInputStream fis = new FileInputStream("../data/bookDataBase.out");
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
    Library theApp = new Library(); // make object of own class

    theApp.init(args); //Then call its methods
    theApp.run();
    theApp.wrap();
  } //end of main
    
} //end of class



//TestInventoryItemImpl.java

package applications;

import java.io.*;
import java.util.*;
import business.*; //nedeed for business classes
import useful.*;   //nedeed for business classes

/**
* <p>Application exists to test (business) class InventoryImpl.  It does this
* by making object(s) of class InventoryImpl and calling its methods using test
* data read from a file that is specially created to provide such
* data, specially chosen to provide a through test.</p>
 * @author Jose Ramirez
 * @version 1 -2014-03-23
 */
public class TestInventoryItemImpl {
  //============================================static data
  // none

  //============================================instance data
  /** In which to store args from command line  */
  private DataReaderImpl_1 br; // (reference to) the file object which will read the data
  private String buf; // into DataReaderImpl_1 reads
  private Hashtable myHash;     // stores the InventoryItem objects
  private InventoryItemImpl inItem; // use in deserialization
  
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
      System.out.println("++++++TestInventoryItemImpl running in: " + System.getProperty("os.name")+"\n"); //to show the operating system where is running
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
      File serialized = new File("../data/serial.in");
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
  
  private String firstNumRow()
  {
    String retval = "";
    for(int i=1; i<=6; i++)
    {
      for(int j=0;j<=8;j++)
      {
        retval +=" ";
      }
      retval += i;
    }
    retval +="\n";
    return retval;
  }
  
  private String secondNumRow()
  { 
    String retval = "";  
    for(int i=0; i<=5; i++)
    {
      for(int j=1;j<=9;j++)
      {
        retval +=j;
      }
      retval += 0;
    }
    retval +="12345\n";
    return retval;
  }
  

  
  /**
   * Controls the major part of the application (typically in a loop which
   * reads input file(s). This method controls the getting of the data,
   * making a InventoryItemImpl object and causes the contents of the object to be
   * displayed and also shows all the methods applied on the object.
   *  
   */
  public void run() //throws IOException
  {
    try
    {
      int counter = 0;
      String Action= ""; //to indicate the Action to run
      StringBuffer cvs = new StringBuffer(128); //used by getData() and update()
      InventoryItemImpl nemo; // (reference to) an object of class InventoryItemImpl
      int returned = 0; // to hold value returned by MyDatReader
      StringBuffer sb = new StringBuffer(128);  // to contain output
      nemo = new InventoryItemImpl();
      StringBuffer heading = new StringBuffer(128);
    
   
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
      
      
        if(Action.equals("Action1")) //Action1 make an object of class InventoryItemImpl
        {
          cvs.setLength(0);
          sb.setLength(0);
          System.out.println("\n" + "-----------Test Number " + ++counter + "------------" + "\n");
          sb.append( "\n" + buf + "\n");
          returned = InventoryItemImpl.getData(cvs,br,"~");
          sb.append("\n" + "Testing method getData(cvs,br,\"~\"):");
          sb.append("\n"+"Returned err  after getData()= " + returned);
          sb.append("\nString Buffer csv after getData() is :" + cvs + "\n");
          nemo = new InventoryItemImpl();
          sb.append("\n" + "Testing method updade(cvs):");
          returned = nemo.update(cvs);
          sb.append("\nReturned err after uptade(sb) = " + returned + "\n");
          sb.append("\n" + "Testing method formatDisplay(sb):\n");
          nemo.formatDisplay(sb);
          sb.append("\n"+secondNumRow());
          sb.append(firstNumRow());
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
      
       /* if(Action.equals("Action3")) //Action3 calls method placeReplenishmentOrder()
        {
          sb.setLength(0);
          sb.append( "\n" + buf + "\n");
          sb.append("\nMessage of placeReplenishmentOrder():");
	  sb.append("\n" + nemo.placeReplenishmentOrder() + "\n");	
          System.out.println(sb);
        } // end of Action3 */ //After all test palceReplenishmentOrder is declared private 
      
        if(Action.equals("Action4")) //Action4 calls method decreaseStock(amount)
        {
          sb.setLength(0);
          sb.append( "\n" + buf + "\n");
          int amount = Integer.parseInt(br.readLine().replaceAll("\\s","")); //to take the param from the DataScript
          sb.append("\nMessage of decreaseStock("+amount+"):");
	  sb.append("\n" + nemo.decreaseStock(amount) + "\n");
          sb.append("\n" + "Item after decreaseStock("+amount+"):\n");
          nemo.formatDisplay(sb);
          System.out.println(sb);
        } // end of Action4
      
        if(Action.equals("Action5")) //Action5 calls the method increaseStock(amount)
        {
          sb.setLength(0);
          sb.append( "\n" + buf + "\n"); 
          int amount = Integer.parseInt(br.readLine().replaceAll("\\s","")); //to take the param from the DataScript
          sb.append("\nMessage of increaseStock("+amount+"):");
	  sb.append("\n" + nemo.increaseStock(amount) + "\n");
          sb.append("\n" + "Item after increaseStock("+amount+"):\n");
          nemo.formatDisplay(sb);
          System.out.println(sb);
        } //end of Action5
      
        if(Action.equals("Action6")) //Action6 calls the method placeSalesOrder(qty)
        {
          sb.setLength(0);
          sb.append( "\n" + buf + "\n");
          int qty = Integer.parseInt(br.readLine().replaceAll("\\s","")); //to take the param from the DataScript
          sb.append("\nMessage of placeSalesOrder("+qty+"):");
	  sb.append("\n" + nemo.placeSalesOrder(qty) + "\n");
          sb.append("\n" + "Item after placeSalesOrder("+qty+"):\n");
          nemo.formatDisplay(sb);
          System.out.println(sb);
        }  //end od Action6
      
        if(Action.equals("Action7")) //Action7 calls the method shipSalesOrder(qty)
        {
          sb.setLength(0);
          sb.append( "\n" + buf + "\n");
          int qty = Integer.parseInt(br.readLine().replaceAll("\\s","")); //to take the param from the DataScript
          sb.append("\nMessage of shipSalesOrder("+qty+"):");
	  sb.append("\n" + nemo.shipSalesOrder(qty) + "\n");
          sb.append("\n" + "Item after shipSalesOrder("+qty+"):\n");
          nemo.formatDisplay(sb);
          System.out.println(sb);
        } //end of Action7
      
        if(Action.equals("Action8")) //Action8 calls method receiveReplenishment(qty)
        {
          sb.setLength(0);
          sb.append( "\n" + buf + "\n");
          int qty = Integer.parseInt(br.readLine().replaceAll("\\s","")); //to take the param from the DataScript
          sb.append("\nMessage of receiveReplenishment("+qty+"):");
	  sb.append("\n" + nemo.receiveReplenishment(qty) + "\n");
          sb.append("\n" + "Item after receiveReplenishment("+qty+"):\n");
          nemo.formatDisplay(sb);
          System.out.println(sb);
          System.out.println("\n" + "-------End Of Test Number " + counter + "------------" + "\n");
        } //end of Action8
      
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
                     "java TestAddress # any args are ignored");
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
        
        FileOutputStream fos = new FileOutputStream("../data/Assign2.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        // do the serialization
        oos.writeObject(myHash);
        // flush and close the output file
        oos.flush();
        oos.close();
        
        System.out.println("\n-----------Show Data from HashTable------------------------");
        
        FileInputStream fis = new FileInputStream("../data/Assign2.ser");
        ObjectInputStream s = new ObjectInputStream(fis);
        // read objects in the same sequence as they were written
        myHash = (Hashtable) s.readObject();
        s.close();

        System.out.println("Size of myHash is : " + myHash.size() );

        for (Enumeration e = myHash.keys() ; e.hasMoreElements() ;) 
        {
          key = e.nextElement();     // is an Object and is used as an Object therefore no need to cast it (see stmt 38)
          inItem = (InventoryItemImpl) myHash.get(key);
          inItem.formatReportData_1(items);
        }
        
        sb.setLength(0);
        sb.append("\n"+firstNumRow());
        sb.append(secondNumRow());
        inItem.formatReportHeadings_1(sb);
        sb.append(items);
        sb.append("\n" + secondNumRow());
        sb.append(firstNumRow());
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
    TestInventoryItemImpl theApp = new TestInventoryItemImpl(); // make object of own class

    theApp.init(args); //Then call its methods
    theApp.run();
    theApp.wrap();
  } //end of main
    
} //end of class

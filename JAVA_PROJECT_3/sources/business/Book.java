//Book.java

package business;

import java.io.*;
import java.util.*;
import useful.*;

/**
 * An Object of class Book represents one title
 * that a bookstore or library might have in its 
 * collection.
 * 
 * @author Jose Ramirez
 * @version 1 -2014-04-10
 */

public class Book implements Testable, Serializable
{
    //----static data
    private static String[] prompts =
    {
      "isbn   : ",
      "title  : ",
      "author : ",
      "price  : ",
    };
    
    /** unique identifier (International Standard Book Number) */
    private String isbn;
    /** title of the book */
    private String title;
    /** name of the author */
    private String author;
    /** normal price of the book */
    private double price;
    
    //----constructor
    
    /**
    * No param constructor. Should initialize all references to avoid
    * NullPointerException later. Allows primitive variables to
    * get default values. Expects most instance variables
    * to be populated by an update() method
    */
    
    public Book()
    {
      isbn ="";
      title ="";
      author ="";
      price =0.00;
    }
    
    //----------------------------------------formatDisplay()
    /**
     * Appends the data from one object to the StringBuffer passed
     * as parameter 1. This method will be used primarily for
     * testing/checking purposes so that it is desirable that it be
     * in a format that is easy to read.
     */
    
    public int formatDisplay(StringBuffer temp)
    {
     int retval = 0;
     temp.append("\n" + "+++++++++Book+++++++++");     
     temp.append("\n" + "ISNB   : " + isbn);
     temp.append("\n" + "Title  : " + title);
     temp.append("\n" + "Author : " + author);
     temp.append("\n" + "Price  : " + price);
     temp.append("\n" + "+++++end Book+++++++++");     
	   return retval;
    }
    
    //----------------------------------------formatAsHtml()
    /**
     * Appends the data from one object to the StringBuffer passed
     * as parameter 1. The data is in valid web..
     */
    
    public int formatAsHtml(StringBuffer temp)
    {
      int retval = 0;
      temp.append("\n<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"mystyle.css\">"+
                  "\n<title> Testing Class Book</title></head>\n<body><h1>Book</h1>");
      temp.append("\n<h2>Title:</h2><p>"+ title +"</p>\n<ol>\n<li>");
      temp.append("IBSN: </li><ul>\n<li>"+isbn+"</li></ul>\n<li>");
      temp.append("Author :</li><ul>\n<li>" +author +"</li></ul>\n<li>");
      temp.append("Price :</li><ul>\n<li>"+price+"</li></ul></ol>"+
                  "\n<h4>End of Book</h4></body></html>");
           return retval;
    }
    
    //----------------------------------------getData()
    /**
     * Calls the library getData(..) method in class Useful(in the normal way)
     * to produce the StringBuffer in CVS format that will be used later by update(..)
     * method to populate the instance data items of an object.
     */
    
    public static int getData(StringBuffer sb, DataReaderImpl_1 br, String separator) throws IOException
    {
      int retval = 0;
      
      try
      {
        retval = Useful.getData(prompts, sb, br, separator);
        sb.append(separator);
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
      return retval;
    }
    
    /**
    * Returns the value of isbn (to be used as the key for a Hashtable or 
    * database primary key or similar purpose). 
    * @return retval 
    */
    public String getPrimaryKey()
    {
      String retval =""; 
      retval=isbn;   
      return retval;
    }
   
    /**
    * The StringBuffer passed as parameter 1 is in CSV format and was produced by getData().
    * Creates a StringTokenizer using the first character of the StringBuffer as the separator.  
    * Extracts each token and uses the data to update the instance data items, converting the data as necessary.  
    * Stores the current date and time in lastUpdated.
    * @param sb
    * @return retval
    */
    public int update(StringBuffer sb)
    {
      int             retval = 0; // used to indicate status (0 = normal) 
      String          sep;        // separator value used by CSV formal data    
      StringTokenizer tk;         // to extract data
      String          temp;       // for extracted data
    
      try
      {
        //---get separator (value is first character of String Buffer)
        sep = String.valueOf(sb.charAt(0));
        //---create tokenizer (to extract data items)
        tk = new StringTokenizer(sb.toString(),sep);
    
        //---now get tokens and update instance variables
        //   only make change if data is not equal to a single tab
     
        temp = tk.nextToken();
        if (! temp.equals("\t"))
        {
          //String, use as is
          isbn = new String(temp);
        }
    
        temp = tk.nextToken();
        if (! temp.equals("\t"))
        {
         //String, use as is
         title = new String(temp);
        }
    
        temp = tk.nextToken();
        if (! temp.equals("\t"))
        {
          //String, use as is
          author = new String(temp);
        } 
    
        temp = tk.nextToken();
        if (! temp.equals("\t"))
        {
          //Double, conversion is needed
          price = Double.parseDouble(temp);
        } 
      }
      catch (Exception e)
      {
          System.err.println("Exception caught: " + e.getMessage());
          e.printStackTrace(System.err);
      } 
     return retval; //indicate OK (i.e. no errors) 
    } //end of update

    
    
    
    
    
    
    
}

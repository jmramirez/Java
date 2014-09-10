//InventoryItemImpl.java

package business;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import useful.*;

/**
 * Implements the InventoryItem interface
 * @author Jose Ramirez
 * @version 1 -2014-03-23
 */

public class InventoryItemImpl implements InventoryItem, Testable, Serializable  
{ 
  //----static data   
  private static String[] prompts =
  {
    "inventoryId      : ",
    "description      : ",
    "pack             : ",
    "quantityInStock  : ",
    "unitPrice        : ",
    "reorderPoint     : ",
    "reorderQuantity  : ",
    "totalOrdered     : ",
    "totalSalesOrders : ",
  }; 
  
  /**unique identifier for product combination */
  private String inventoryId;
  /** description of the product */
  private String description;
  /** description of packaging */
  private String pack;
  /** the amount currently in stock */
  private int quantityInStock;
  /** selling price of the unit */
  private double unitPrice;
  /** quantity at  which replenishment should be made */
  private int reorderPoint;
  /** quantity normally ordered for replenishment */   
  private int reorderQuantity;
  /** total of outstanding orders for replenishment */    
  private int totalOrdered;
  /** total of outstanding sales orders for this inventory */
  private int totalSalesOrders;
  /** date and time of previous update to this object */
  private String lastUpdated;
  
  //---constructors
  
  /**
  * No param constructor. Should initialize all references to avoid
  * NullPointerException later. Allows primitive variables to
  * get default values. Expects most instance variables
  * to be populated by an update() method
  */
  public InventoryItemImpl()
  {
      inventoryId="";
      description="";
      pack="";
      quantityInStock = 0;
      unitPrice = 0.00;
      reorderPoint = 0;
      reorderQuantity = 0;
      totalOrdered = 0;
  }
  
  
  /**
   * Appends the data from one object to the StringBuffer passed as parameter 1.  
   * <p>This method will be used primarily for testing/checking purposes.</p>
   * @param sb
   * @return retval
   */
  
  public int formatDisplay(StringBuffer sb)
  { 
    int retval = 0;
    
    String dashes= "";
    for(int i = 0; i <=62 ; i++)
    {
      dashes += "-";
    }
    sb.append("\n" + "+++++++++++++++++++++++ Format Display +++++++++++++++++++++++");
    sb.append("\n" + dashes);
    sb.append("\n" + String.format("%-9s%-15s%-17s%7s%7s%8s", "Inv Id", "Description","Package", "Ro-Pt", "Ro-Qty","U-Price"));
    sb.append("\n" + String.format("%24s%-17s%7s%7s%8s"," ","Last-Upd","RepOrd","In Stk","SalOrd"));
    sb.append("\n" + dashes);
    sb.append("\n" + String.format("%-9s%-15s%-17s%7d%7d%8.2f",inventoryId,description,pack,reorderPoint,reorderQuantity,unitPrice));
    sb.append("\n" + String.format("%24s%-17s%7d%7d%5d"," ", lastUpdated.substring(0,10),totalOrdered,quantityInStock,totalSalesOrders) + "\n");
      return retval;
  }
  
  /**
   * Appends  the headings, formatted for Report 1, to the StringBuffer passed as parameter 1. 
   * @param sb
   * @return reval
   */
  
  public int formatReportHeadings_1(StringBuffer sb)
  {
    int retval = 0;
      
    String dashes= "";
    for(int i = 0; i <=62 ; i++)
    {
      dashes += "-";
    }
    
    sb.append("\n" + dashes);
    sb.append("\n" + String.format("%-9s%-15s%-17s%7s%7s%8s", "Inv Id", "Description","Package", "Ro-Pt", "Ro-Qty","U-Price"));
    sb.append("\n" + String.format("%24s%-17s%7s%7s%8s"," ","Last-Upd","RepOrd","In Stk","SalOrd"));
    sb.append("\n" + dashes);
    
    return retval;
  }
  
  /**
   * Appends  the data from one object, formatted for Report 1, to the StringBuffer passed as parameter 1.
   * @param sb
   * @return retval
   */
  public int formatReportData_1(StringBuffer sb)
  {
    int retval = 0;
    
    sb.append("\n" + String.format("%-9s%-15s%-17s%7d%7d%8.2f",inventoryId,description,pack,reorderPoint,reorderQuantity,unitPrice));
    sb.append("\n" + String.format("%24s%-17s%7d%7d%5d"," ", lastUpdated.substring(0,10),totalOrdered,quantityInStock,totalSalesOrders) + "\n");
    
    return retval;
  }
  
  /**
   * Displays prompts and gets data for each instance data item using the DataReaderImpl_1 object passed as parameter 2.  
   * It stores (in CSV format)  the data items input in the StringBuffer passed as parameter 1.
   * @param sb
   * @param br
   * @param separator
   * @return retval
   */
  public static int getData(StringBuffer sb, DataReaderImpl_1 br, String separator) //throws IOException
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
   * Returns the value of inventoryId (to be used as the key for a Hashtable or 
   * database primary key or similar purpose). 
   * @return retval 
   */
  public String getPrimaryKey()
  {
      String retval =""; 
   
      if(! inventoryId.contains(" "))
      {
        retval=inventoryId;   
      }
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
        inventoryId = new String(temp);
      }
    
      temp = tk.nextToken();
      if (! temp.equals("\t"))
      {
        //String, use as is
        description = new String(temp);
      }
    
      temp = tk.nextToken();
      if (! temp.equals("\t"))
      {
        //String, use as is
        pack = new String(temp);
      }
    
      temp = tk.nextToken();
      if (! temp.equals("\t"))
      {
        //Integer, conversion is needed
        quantityInStock = Integer.parseInt(temp);
      }
    
      temp = tk.nextToken();
      if (! temp.equals("\t"))
      {
        //Double, conversion is needed
        unitPrice = Double.parseDouble(temp);
      }
    
      temp = tk.nextToken();
      if (! temp.equals("\t"))
      {
        //Integer, conversion is needed
        reorderPoint = Integer.parseInt(temp);
      }
    
      temp = tk.nextToken();
      if (! temp.equals("\t"))
      {
        //Integer, conversion is needed
        reorderQuantity = Integer.parseInt(temp);
      }
    
      temp = tk.nextToken();
      if (! temp.equals("\t"))
      {
        //Integer, conversion is needed
        totalOrdered = Integer.parseInt(temp);
      }
    
      temp = tk.nextToken();
      if (! temp.equals("\t"))
      {
        //Integer, conversion is needed
        totalSalesOrders = Integer.parseInt(temp);
      }
    
      String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
      lastUpdated = currentTime; 
    }
    catch (Exception e)
    {
        System.err.println("Exception caught: " + e.getMessage());
        e.printStackTrace(System.err);
    }
    return retval; //indicate OK (i.e. no errors) 
  } //end of update
  
  /**
   * Increases the quantityInStock by the amount passed as parameter 1.
   * Returns the message “Increased stock of [inventoryId] by [incAmount].”
   * Stores the current date and time in lastUpdated.
   * @param incAmount
   * @return retmess
   */
  public String increaseStock(int incAmount)
  {
    String retmess = "";
    
    try
    {
      quantityInStock += incAmount;
      retmess ="Increased stock of " + inventoryId + " by " + incAmount + ".";
      String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
      lastUpdated = currentTime;
    }
    catch (Exception e)
    {
      System.err.println("Exception caught: " + e.getMessage());
      e.printStackTrace(System.err);
    }
    return retmess;
  }
  
  /**
   * Adds the reorderQuantity to the totalOrdered and returns the message 
   * “Replenishment [reorderQuantity] ordered for [inventoryId].”
   * Stores the current date and time in lastUpdated.
   * @return retmess 
   */
  private String placeReplenishmentOrder()
  {
    String retmess="";
    try
    {
      totalOrdered += reorderQuantity;  
      retmess = "Replenishment " + reorderQuantity + " ordered for " + inventoryId + ".";
      String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
      lastUpdated = currentTime;
    }
    catch (Exception e)
    {
      System.err.println("Exception caught: " + e.getMessage());
      e.printStackTrace(System.err);
    }
    return retmess;
  }
  
  /**
   * Decreases the quantityInStock by the amount passed as parameter 1.  
   * Returns the message “Decreased stock of [inventoryId] by [decAmount].”
   * Stores the current date and time in lastUpdated.
   * @param decAmount
   * @return retmess
   */
  public String decreaseStock(int decAmount)
  {
    String retmess = "";
    try
    {
      quantityInStock -= decAmount;
      retmess = "Decresed stock of " + inventoryId + " by " + decAmount;
      while(((quantityInStock+totalOrdered)-totalSalesOrders)< reorderPoint)
      {     
	  retmess += "\n" + placeReplenishmentOrder(); 
      }
      String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
      lastUpdated = currentTime;
    }
    catch (Exception e)
    {
      System.err.println("Exception caught: " + e.getMessage());
      e.printStackTrace(System.err);
    }
    return retmess;
  }
  
  /**
   * Adds the qtyOrdered to totalSalesOrders and returns the message 
   * “Sales Order of [qtyOrdered] for [inventoryId].” 
   * Stores the current date and time in lastUpdated.
   * @param qtyOrdered
   * @return remess
   */
  public String placeSalesOrder(int qtyOrdered)
  {
    String retmess = "";
    try
    {
      totalSalesOrders += qtyOrdered;
      retmess = "Sales Order of " + qtyOrdered + " for " + inventoryId + ".";
      String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
      lastUpdated = currentTime;
    }
    catch (Exception e)
    {
      System.err.println("Exception caught: " + e.getMessage());
      e.printStackTrace(System.err);
    }
    return retmess;
  }
  
  /**
   * Reduces totalSalesOrders by the qtyShipped then calls decreaseStock(qtyShipped).  
   * Returns the message “Goods [qtyShipped] items shipped for [inventoryId].”followed by the message(s) 
   * returned by  the call to the decreaseStock() method. (substitutes data values for [qtyShipped] and [inventoryId]).
   * Stores the current date and time in lastUpdated. 
   * @param qtyShipped
   * @return retmess
   */
  public String shipSalesOrder(int qtyShipped)
  {
    String retmess="";
    
    try
    {
      totalSalesOrders -= qtyShipped;
      retmess = "Goods " + qtyShipped + " items shipped for " + inventoryId + ".\n" + decreaseStock(qtyShipped) ;
      String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
      lastUpdated = currentTime;
    }
    catch (Exception e)
    {
      System.err.println("Exception caught: " + e.getMessage());
      e.printStackTrace(System.err);
    }
    return retmess;
  }
  
  /**
   * Reduces the totalOrdered  by the qtyReceived  then call increaseStock(qtyReceived). 
   * Return the message “Replenishment received of [qtyReceived] for [inventoryId].” 
   * followed by the messages returned by the call to the increaseStock() method.  
   * Stores the current date and time in lastUpdated.
   * @param qtyReceived
   * @return remess
   */
  public String receiveReplenishment(int qtyReceived)
  {
    String retmess="";
    
    try
    {
      totalOrdered -= qtyReceived;
      retmess= "Replenishment received of " + qtyReceived + "for " + inventoryId + ".\n" + increaseStock(qtyReceived);
      String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
      lastUpdated = currentTime;
    }
    catch (Exception e)
    {
      System.err.println("Exception caught: " + e.getMessage());
      e.printStackTrace(System.err);
    }
    return retmess;
  }

} //end class InventoryItemImpl

package business;

import java.io.*;
/**
 * The interface InventoryItem declares(list) the following methods,
 * decreaseStock(int decAmount), formatReportData_1(StringBuffer sb),
 * getPrimaryKey(), increaseStock(int incAmount),placeSalesOrder(int qtyOrdered),
 * receiveReplenishment(int qtyReceived),shipSalesOrder(int qtyShipped)
 * @author Jose Ramirez
 * @version 1-2014-03-23
 */
public interface InventoryItem 
{
    //-------------------------------------------String  decreaseStock(int decAmount)
    /** Decreases the quantityInStock by the amount passed as parameter 1.
    */
    public String  decreaseStock(int decAmount);

    //--------------------------------------------int formatReportData_1(StringBuffer sb)
    /** Appends  the data from one object, formatted for Report 1, to the StringBuffer passed as parameter 1. Returns a possible error indicator (zero means “no errors”.
    */
    public int formatReportData_1(StringBuffer sb);

    //--------------------------------------------String getPrimaryKey()
    /** Returns the value of inventoryId (to be used as the key for a Hashtable or database primary key or similar purpose).
    */
    public String getPrimaryKey(); 
    //--------------------------------------------String  increaseStock(int incAmount)
    /** Increases the quantityInStock by the amount passed as parameter 1.
    */ 
    public String  increaseStock(int incAmount);
    //--------------------------------------------String  placeSalesOrder(int qtyOrdered)
    /**
    Adds the qtyOrdered to totalSalesOrders .
    */
    public String  placeSalesOrder(int qtyOrdered);
    //--------------------------------------------String receiveReplenishment(int qtyReceived)
    /**
    Reduces the totalOrdered  by the qtyReceived  then calls increaseStock(qtyReceived). 
    */
    //--------------------------------------------String receiveReplenishment(int qtyReceived)
    /* private String placeReplenishmentOrder()  NOTE: this method is private. It is mentioned here (in the interface) ONLY to help your overall understanding of class InventoryItem and the existence of the receiveReplenishment(...) (see below).
    */
    public String receiveReplenishment(int qtyReceived);
    //--------------------------------------------String shipSalesOrder(int qtyShipped)
    /**
    Reduces totalSalesOrders by the qtyShipped then calls decreaseStock(qtyShipped). 
    */
    public String shipSalesOrder(int qtyShipped);    
}

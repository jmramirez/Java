/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package useful;

import java.io.*;
import useful.DataReaderImpl_1;

public class Useful 
{
  public static int getData(String[] prompts, StringBuffer sb, DataReaderImpl_1 br, String sep)
      throws IOException
  {
      String   buf = ""; // to read into
      int      err = 0;  // used for return value
      int      k = 0;    // loop control

      sb.append(sep);  // start StringBuffer with separator char

      for (k = 0; k < prompts.length; k++)
      {
        //System.err.print(prompts[k]);  // output prompt - leave cursor on same line
        //System.err.flush();            // ensure that the data appears
        buf = br.readLine();           // read the input/response
        if (buf == null)
        {
           // at end of file
           err = -1;
           break;
        }
        //--- data entered may not contain a separator character
        if (  buf.indexOf(sep) >= 0  )
        {
          err = -2;                     // set return code
          break;                       // finish - break out of loop
        }
        //--- deal with empty input field 
        if ( buf.equals("") )
        {
          buf = "\t";                   // tab to indicate empty field
        }
        //--- data item is OK so add it to StringBuffer
        sb.append(buf + sep);          // append separator
      } // end for()

      return err;                      // return result/status code
    } // endof getData()    
}

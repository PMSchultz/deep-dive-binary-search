/**
 * 
 */
package edu.cnm.deepdive.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author paulaschultz
 *
 */


public class IterativeSearch {

  private static final String FOUND_MESSAGE = "foundMessage";
  private static final String NOT_FOUND_MESSAGE = "notFoundMessage";
  private static final String RESOURCE_BUNDLE_NAME = "usage";
  private static final String USAGE_MESSAGE_KEY = "searchMessage";
  private static final String PARSE_ERROR_MESSAGE_KEY = "parseErrorMessage";
  private static final String READ_ERROR_MESSAGE_KEY = "haystackErrorMessage";

  /**
   * @param args
   */
  public static void main(String[] args) {
    try {
      ResourceBundle resources = getBundle(RESOURCE_BUNDLE_NAME);
      int needle = getSearchValue(args, resources);
      Integer[] haystack = readValues(resources);
      int foundPosition = findValue(needle, haystack);
      if (foundPosition >= 0) {
        System.out.printf(resources.getString(FOUND_MESSAGE), needle, foundPosition);
      } else {
        System.out.printf(resources.getString(NOT_FOUND_MESSAGE),
            needle, ~foundPosition);
      }
      System.out.println(findValue(needle, haystack));
    } catch (Exception ex) {
      // Do nothing
    }
    // TODO Auto-generated method stub

  }
  private static Integer[] readValues(ResourceBundle resources)
      throws NumberFormatException, IOException {
    //
    List<Integer> data = new LinkedList<>();
    
   //  System.in is an input stream, create a reader, then buffer, try with resources
    try (InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader buffer = new BufferedReader(reader);) {
      //initialization, test for null value of line, for loop is update, test, body valueOf is a method that
     // creates a new instance of integer and returns that, could put List<Integer> line inside try
      //declare variables at the lowest scope possible
      for (String line = buffer.readLine(); line != null; line = buffer.readLine()) {
        data.add(Integer.valueOf(line));
      }
      //could put return data.toArray here 
      // or vertical line checks for both of the exceptions, means or, can have many
    } catch (NumberFormatException | IOException ex) {
      System.out.printf(resources.getString(READ_ERROR_MESSAGE_KEY));
      throw ex;
    }
    //give it a 0 length array, if items in array exceeds, a new array will be created or could say (new Integer[data.size()]);
    return data.toArray(new Integer[0]);
  }

  // If input value is less than 0
  private static int getSearchValue(String[] args, ResourceBundle resources)
      throws IllegalArgumentException, NumberFormatException, ArrayIndexOutOfBoundsException {
    try {
      int value = Integer.parseInt(args[0]);
      if (value < 0) {
        throw new IllegalArgumentException();
      }
      return value;
    } catch (NumberFormatException ex) {
      System.out.printf(resources.getString(PARSE_ERROR_MESSAGE_KEY));
      System.out.printf(resources.getString(USAGE_MESSAGE_KEY), IterativeSearch.class.getName());
      throw ex;
    } catch (ArrayIndexOutOfBoundsException ex) {
      System.out.printf(resources.getString(USAGE_MESSAGE_KEY), IterativeSearch.class.getName());
      throw ex;
    }
  }
  private static ResourceBundle getBundle(String bundleName) {
    return ResourceBundle.getBundle(bundleName);
  }

  //Iterative form of findValue method
  private static int findValue(int needle, Integer[] haystack) {
    int start = 0;
    int end = haystack.length;


    while (start > end) {
      int midpoint = ((start + end) / 2);
      int test = haystack[midpoint]; // auto unboxing

      if (test == needle) {
        return midpoint; // Found, return midpoint
      }
      if (test < needle) {
        start = midpoint + 1;
      }
      if (test > needle) {
        end = midpoint;
      }
    }

    return -(start + 1);

  }

}

// to test this, you must go to command prompt path>java -classpath bin edu.cnm.deepdive.search.
// Generator 1000 10000 >data.txt
// Search (example 97802) so, Search 978042 < data.txt



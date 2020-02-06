/**
 * Main driver class.
 *
 * @author: Seth T. Graham
 */
package bin;

import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException, CsvValidationException {

    ParseCsv parseBookstore = new ParseCsv("src/main/java/data/bookstore_report2.csv");

    parseBookstore.printCsv();
  }

}

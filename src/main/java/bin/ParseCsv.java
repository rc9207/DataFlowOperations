/**
 * Used to manipulate CSV files. References: Professor J.Greenwell - CsvToDatabase.
 *
 * @author: Seth T. Graham
 */

package bin;


import com.opencsv.exceptions.CsvValidationException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ParseCsv {

  private static List<String[]> rows = new ArrayList();
  private String csvFile;

  public static List<String[]> getRows() {

    return rows;
  }

  /**
   * Check to see if CSV file exists. If file exists read the CSV file.
   *
   * @param csvFile Desired CSV file.
   */
  public ParseCsv(String csvFile) throws IOException, CsvValidationException {

    this.csvFile = csvFile;

    if (fileExists()) {
      readCsv();
    }
  }

  /**
   * Method to check if desired CSV file exists.
   *
   * @return boolean.
   */
  private boolean fileExists() {

    if (!Files.exists(Paths.get(csvFile))) {
      System.out.println(csvFile + " does not exist.");
      return false;
    }
    return true;
  }

  /**
   * Method used to read desired CSV file.
   */
  private void readCsv() throws IOException, CsvValidationException {

    String line;
    int counter = 0;

    BufferedReader buffReader = new BufferedReader(new FileReader(csvFile));

    while ((line = buffReader.readLine()) != null) {

      try {

        if (line != null) {
          rows.add(counter, line.split(",+"));
          counter++;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    rows.remove(0);
  }

  /**
   * Method used to print the CSV to the console.
   */
  protected void printCsv() {

    for (String[] row : rows) {
      for (String result : row) {
        System.out.print(result);
      }
      System.out.println("\n---------------------------------------------------------------------");
    }
  }
}

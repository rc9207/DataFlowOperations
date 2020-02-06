/**
 * Used to manipulate CSV files. References: Professor J.Greenwell - CsvToDatabase.
 *
 * @author: Seth T. Graham
 */

package bin;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ParseCsv {

  private List rows = new ArrayList();
  private String csvFile;

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

    FileInputStream csvStream = new FileInputStream(csvFile);
    InputStreamReader inputStream = new InputStreamReader(csvStream, StandardCharsets.UTF_8);
    CSVReader csvReader = new CSVReader(inputStream);

    String[] nextLine;

    while ((nextLine = csvReader.readNext()) != null) {
      rows.add(nextLine);
    }

    csvReader.close();
  }

  /**
   * Method to print desired CSV file to the console.
   */
  protected void printCsv() {

    for (Object row : rows) {
      for (String stringVal : (String[]) row) {
        System.out.print(stringVal + ", ");
      }
      System.out.println("\b\b\n----------------------------------------------------------------");
    }
  }
}

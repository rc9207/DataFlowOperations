/**
 * Main driver class.
 *
 * @author: Seth T. Graham
 */
package bin;

import com.google.gson.Gson;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Main {

  public static void main(String[] args) throws IOException, CsvValidationException {

    // Read and print the CSV file.
    ParseCsv parseBookstore = new ParseCsv("src/main/java/data/bookstore_report2.csv");

    System.out.println("Csv to console readout: \n");
    parseBookstore.printCsv();
    System.out.println("\nJson to console readout: \n");

    // Load the json.
    Gson gson = new Gson();

    try (Reader reader = new FileReader("src/main/java/data/authors.json")) {

      Author[] authors = gson.fromJson(reader, Author[].class);

      for (Author author : authors) {
        System.out.println(author.getAuthor_name());
      }

    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Could not access JSON!");
    }

    // Insert CSV into book table.
    BookStoreDB bookStoreDB = new BookStoreDB();
    bookStoreDB.insertBookCsv(parseBookstore);

    // Insert Json into author table.
    bookStoreDB.insertAuthorJson("src/main/java/data/authors.json");
  }


}

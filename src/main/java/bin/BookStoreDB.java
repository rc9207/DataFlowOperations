package bin;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Methods used to edit bookstore.db
 *
 * @author: Seth T. Graham
 */
public class BookStoreDB {

  private String dbFileLocation = "jdbc:sqlite:src/main/java/data/bookstore.db";
  private String username = "";
  private String password = "";
  private Connection connection = null;

  /**
   * Default constructor.
   */
  public BookStoreDB() {

  }

  /**
   * Insert values from CSV into book table.
   *
   * @param parseCsv List<String[]> from ParseCsv
   */
  public void insertBookCsv(ParseCsv parseCsv) {

    try {

      connectToDatabase();

      PreparedStatement prStmt = connection.prepareStatement(
          "INSERT or IGNORE INTO BOOK (isbn, publisher_name, author_name, book_title) VALUES (?, ?, ?, ?)",
          PreparedStatement.RETURN_GENERATED_KEYS);

      for (String[] row : parseCsv.getRows()) {
        for (String result : row) {
          prStmt.setString(1, row[0]);
          prStmt.setString(2, row[3]);
          prStmt.setString(3, row[2]);
          prStmt.setString(4, row[1]);
          prStmt.executeUpdate();
        }
      }
      prStmt.close();

    } catch (SQLException se) {
      se.printStackTrace();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   *
   */
  public void insertAuthorJson(String JsonFileLocation) {

    Gson gson = new Gson();

    try (Reader reader = new FileReader(JsonFileLocation)) {

      Author[] authors = gson.fromJson(reader, Author[].class);

      connectToDatabase();

      PreparedStatement prStmt = connection
          .prepareStatement(
              "INSERT or IGNORE INTO AUTHOR (author_name, author_email, author_url) VALUES (?, ?, ?)",
              PreparedStatement.RETURN_GENERATED_KEYS);

      for (Author author : authors) {
        prStmt.setString(1, author.getAuthor_name());
        prStmt.setString(2, author.getAuthor_email());
        prStmt.setString(3, author.getAuthor_url());
        prStmt.executeUpdate();
      }
      prStmt.close();

    } catch (SQLException se) {
      se.printStackTrace();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Connect to bookstore.db method.
   */
  private void connectToDatabase() {

    try {
      Class.forName("org.sqlite.JDBC");
      connection = DriverManager.getConnection(dbFileLocation, username, password);

    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      System.out.println("Could not find the Driver.");

    } catch (SQLException se) {
      se.printStackTrace();
    }
  }
}

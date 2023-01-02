package gui.bank;

import bank.core.Bank;
import bank.core.User;
import bank.json.BankModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;

/**
 * DataAccess.
 */
public class DataAccess {

  private ObjectMapper mapper;
  String endpoint;

  /**
   * Constructur for the class.
   *
   * @param endpoint path to the JSON-file used for storing and accessing data.
   */
  public DataAccess(String endpoint) {
    this.mapper = new ObjectMapper()
        .registerModule(new BankModule())
        .enable(SerializationFeature.INDENT_OUTPUT);
    this.endpoint = endpoint;

  }

  /**
   * This method contacts the server and gets data about the bank.
   *
   * @return bank from the server.
   * @throws IOException          thrown when something goes wrong.
   * @throws InterruptedException thrown when the there is an
   *                              interuption between the contact from server to
   *                              client.
   */
  public Bank getBank() throws IOException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(endpoint + "/bank"))
        .header("Accept", "application/json")
        .GET()
        .build();
    HttpResponse<String> response = HttpClient
        .newBuilder()
        .build()
        .send(request, HttpResponse.BodyHandlers.ofString());

    System.out.println(response.statusCode());
    Bank bank = mapper.readValue(response.body(), Bank.class);
    return bank;

  }

  /**
   * This method contacts the server and gets data about the user.
   *
   * @param socialSecurityNumber is sent to the server.
   * @return user from the server.
   * @throws Exception when something goes wrong.
   */
  public User getUser(long socialSecurityNumber) throws Exception {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(endpoint + "/bank/" + Long.toString(socialSecurityNumber)))
        .header("Accept", "application/json")
        .GET()
        .build();
    HttpResponse<String> response = HttpClient.newBuilder()
        .build()
        .send(request, HttpResponse.BodyHandlers.ofString());
    System.out.println(response.statusCode());
    User user = mapper.readValue(response.body(), User.class);
    return user;

  }

  /**
   * This method deletes a user from the server.
   *
   * @param socialSecurityNumber used to identify the user.
   * @throws Exception when something goes wrong.
   */
  public void deleteUser(long socialSecurityNumber) throws Exception {

    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(endpoint + "/bank/user/" + Long.toString(socialSecurityNumber)))
        .header("Accept", "application/json")
        .DELETE()
        .build();
    HttpResponse<String> response = HttpClient.newBuilder()
        .build()
        .send(request, HttpResponse.BodyHandlers.ofString());
    System.out.println(response.statusCode());

  }

  /**
   * This method registers a new user.
   *
   * @param user used to register a new user.
   * @throws Exception when something goes wrong.
   */
  public void registerUser(User user) throws Exception {
    String jsonString = mapper.writeValueAsString(user);
    final HttpRequest req = HttpRequest
        .newBuilder().uri(URI.create(endpoint + "/bank"))
        .header("Accept", "application/json")
        .header("Content-Type", "application/json")
        .POST(BodyPublishers.ofString(jsonString))
        .build();
    final HttpResponse<String> response = HttpClient.newBuilder()
        .build().send(req, HttpResponse.BodyHandlers.ofString());
    int responseString = response.statusCode();
    System.out.println(responseString);

  }

  /**
   * This method is for depositing and updates the server for a deposit.
   *
   * @param accountNumber used for depositing money onto.
   * @param amount        the amount to be deposited.
   * @throws IOException          when for example depositing a negative number.
   * @throws InterruptedException thrown when the there is an interuption between
   *                              the contact from server to client.
   */
  public void deposit(long accountNumber, double amount) throws IOException, InterruptedException {

    final HttpRequest req = HttpRequest.newBuilder()
        .uri(URI.create(endpoint + "/bank/account/" + accountNumber + "/deposit/" + amount))
        .header("Accept", "application/json")
        .header("Content-Type", "application/json")
        .PUT(BodyPublishers.ofString(""))
        .build();
    final HttpResponse<String> response = HttpClient.newBuilder()
        .build().send(req, HttpResponse.BodyHandlers.ofString());
    int responseString = response.statusCode();
    System.out.println(responseString);

  }

  /**
   * This method is for depositing and updates the server for a deposit.
   *
   * @param accountNumber used for depositing money onto.
   * @param amount        the amount to be deposited.
   * @throws IOException          when for example withdrawing a negative number.
   * @throws InterruptedException thrown when the there is an interuption between
   *                              the contact from server to client.
   */
  public void withdraw(long accountNumber, double amount) throws IOException,
      InterruptedException {
    final HttpRequest req = HttpRequest.newBuilder()
        .uri(URI.create(endpoint + "/bank/account/" + accountNumber + "/withdraw/" + amount))
        .header("Accept", "application/json")
        .header("Content-Type", "application/json")
        .PUT(BodyPublishers.ofString(""))
        .build();
    final HttpResponse<String> response = HttpClient.newBuilder()
        .build().send(req, HttpResponse.BodyHandlers.ofString());
    int responseString = response.statusCode();
    System.out.println(responseString);
  }

  /**
   * This method is from transfering money between accounts, and it contacts
   * and gets data from the server to do so.
   *
   * @param from   account which the transfer is to be done from.
   * @param to     account which the transfer is to be done to.
   * @param amount the amount to be transfer.
   * @throws IOException          when for example a negative amount is entered
   *                              for transfer.
   * @throws InterruptedException thrown when the there is an interuption between
   *                              the contact from server to client.
   */
  public void tranfer(long from, long to, double amount) throws IOException, InterruptedException {
    final HttpRequest req = HttpRequest.newBuilder()
        .uri(URI.create(endpoint + "/bank/from/" + from + "/to/" + to + "/transfer/" + amount))
        .header("Accept", "application/json")
        .header("Content-Type", "application/json")
        .PUT(BodyPublishers.ofString(""))
        .build();
    final HttpResponse<String> response = HttpClient.newBuilder().build().send(req,
        HttpResponse.BodyHandlers.ofString());
    int responseString = response.statusCode();
    System.out.println(responseString);

  }

  /**
   * this method sends and adds inteerest to savingaccount.
   *
   * @param accountNumber the account number to which interest is added on
   * @param timeNow the time when the interrest is added
   * 
   */
  public void addInterest(long accountNumber, int timeNow) throws IOException,
      InterruptedException {
    final HttpRequest req = HttpRequest.newBuilder()
        .uri(URI.create(endpoint + "/bank/addintrest/" + accountNumber))
        .header("Accept", "application/json")
        .header("Content-Type", "application/json")
        .PUT(BodyPublishers.ofString(""))
        .build();
    final HttpResponse<String> response = HttpClient.newBuilder().build().send(req,
        HttpResponse.BodyHandlers.ofString());
    int responseString = response.statusCode();
    System.out.println(responseString);

  }

}

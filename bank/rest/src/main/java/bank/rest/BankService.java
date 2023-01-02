package bank.rest;

import bank.core.Bank;
import bank.core.User;
import bank.json.BankModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Bank service.
 */

@Service
public class BankService {
  private Bank bank;
  private ObjectMapper mapper = new ObjectMapper()
      .registerModule(new BankModule())
      .enable(SerializationFeature.INDENT_OUTPUT);
  private String path;

  @Autowired
  public BankService(String path) throws IOException {
    this.path = path;
    bank = mapper.readValue(new File(path), Bank.class);
  }

  public Bank getBank() {
    return bank;
  }

  /**
   * This method is called when the user registers an account.
   *
   * @param user user is what is used to register the account.
   * @throws Exception throw exception if user already exists.
   *
   */
  public void registerUser(User user) throws Exception {
    bank.registerUser(user);
    autoSave();

  }

  /**
   * Methods is called when getting the user by socialSecurityNumber.
   *
   * @param socialSecurityNumber used to get the specified user.
   * @return
   *
   */
  public Optional<User> getUserBySocialSecurityNumber(long socialSecurityNumber) {
    return Optional.ofNullable(bank.getUser(socialSecurityNumber));
  }

  /**
   * Deletes user by using its social security number.
   *
   * @param socialSecurityNumber using SSN to find the right specified user.
   * @throws Exception if user does not exist.
   */
  public void deleteUser(long socialSecurityNumber) throws Exception {
    bank.deleteUser(socialSecurityNumber);
    autoSave();
  }

  /**
   * Method for depositing money.
   *
   * @param accountNumber using account number to find the right account to
   *                      deposit money.
   * @param amount        specify the amount to deposit.
   * @throws Exception throws Exception if account does not exist, or amount is
   *                   invalid.
   */
  public void deposit(long accountNumber, double amount) throws Exception {
    if (bank.getAccount(accountNumber) != null) {
      bank.getAccount(accountNumber).deposit(amount);
      autoSave();
    }

  }

  /**
   * Method for money withdarwal.
   *
   * @param accountNumber the account number to withdarw from
   * @param amount        the amount to withdarw.
   * @throws Exception if the File save to is not found.
   */
  public void withdraw(long accountNumber, double amount) throws Exception {
    if (bank.getAccount(accountNumber) != null) {
      bank.getAccount(accountNumber).withdraw(amount);
      autoSave();

    }

  }

  public void transfer(long from, long to, double amount) throws Exception {
    bank.transfer(from, to, amount);
    autoSave();
  }

  public void addInterest(long accountNumber, int timeNow) throws Exception {
    bank.addInterest(accountNumber, timeNow);
    autoSave();
  }

  private void autoSave() throws Exception {
    mapper.writeValue(new File(path), bank);
  }

}

package bank.core;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * User.
 */
public class User {
  private long socialSecurityNumber;
  private String password;
  private ArrayList<Account> accounts = new ArrayList<Account>();
  private ArrayList<Transaction> transactionHistory = new ArrayList<Transaction>();
  private int lastLoginnTime;

  public User() {

  }

  /**
   * Constructor.
   *
   * @param socialSecurityNumber users socialsecuritysumber.
   * @param password             users password.
   */
  public User(long socialSecurityNumber, String password) {
    isValidSocialSecurityNumber(socialSecurityNumber);
    isValidPassword(password);
    this.password = password;
    this.socialSecurityNumber = socialSecurityNumber;
    this.lastLoginnTime = LocalDateTime.now().getYear();
  
  }

  /**
   * adds the account.
   *
   * @param account Account
   * @throws IllegalArgumentException if an account with
   *                                  the same account number already exists.
   */

  public void addAccount(Account account) {
    for (Account acc : accounts) {
      if (account.getAccountNumber() == acc.getAccountNumber()) {
        throw new IllegalArgumentException("This account already exists");
      }
    }
    accounts.add(account);

  }

  public void addTransaction(Transaction transaction) {
    transactionHistory.add(transaction);
  }

  /**
   * Checks if the string contains a digit.
   *
   * @param str string
   * @return true if the string contains at least one digit
   *         or else return false.
   * 
   */
  private boolean containsDigit(String str) {
    for (int i = 0; i < str.length(); i++) {
      char x = str.charAt(i);
      if (Character.isDigit(x)) {
        return true;
      }

    }
    return false;

  }

  /**
   * Checks if the string contains a letter.
   *
   * @param str string
   * @return true if the string contains at least one letter
   *         or else return false
   * 
   */

  private boolean containsLetter(String str) {
    for (int i = 0; i < str.length(); i++) {
      char x = str.charAt(i);
      if (Character.isLetter(x)) {
        return true;
      }

    }
    return false;

  }

  /**
   * Checks if the password is valid.
   *
   * @param password password
   * @throws IllegalArgumentException if password is not valid.
   */
  private void isValidPassword(String password) {
    if (password == null || password.length() < 5
        || !containsDigit(password) || !containsLetter(password)) {
      throw new IllegalArgumentException("Invalid password");

    }

    this.password = password;

  }

  /**
   * Checks if socialSecurityNumber is valid.
   *
   * @param socialSecurityNumber the socialSecurityNumber to check.
   * @throws IllegalArgumentException if the input is not valid.
   */

  private void isValidSocialSecurityNumber(long socialSecurityNumber) {
    if (Long.toString(socialSecurityNumber).length() != 11) {
      throw new IllegalArgumentException("Invalid socialSecurityNumber");
    }
  }

  public void setSocialSecurityNumber(long socialSecurityNumber) {
    this.socialSecurityNumber = socialSecurityNumber;
  }

  public long getSocialSecurityNumber() {
    return socialSecurityNumber;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPassword() {
    return password;
  }

  public void setLastLoginnTime(int time) {
    this.lastLoginnTime = time;
  }

  public int getLastLoginnTime() {
    return this.lastLoginnTime;
  }

  /**
   * list of accounts.
   *
   * @return an array of accounts.
   */
  public ArrayList<Account> getAccounts() {
    return new ArrayList<Account>(accounts);

  }

  public ArrayList<Transaction> getTransactionHistory() {
    return new ArrayList<Transaction>(transactionHistory);
  }

 
}

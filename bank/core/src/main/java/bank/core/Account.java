package bank.core;

/**
 * account.
 */
public class Account {
  private double balance;
  private long accountNumber;
  private String accountName;

  /**
   * This is the constructor method of Account that initializes the an account
   * created.
   *
   * @param balance       The value of money that an account starts with in double
   *                      format
   * @param accountNumber the account number
   * 
   */

  public Account(long accountNumber, double balance) {
    this.balance = roundedAmount(balance);
    this.accountName = "User account";
    this.accountNumber = accountNumber;

  }

  public Account() {

  }

  /**
   * This method is used to withdraw money value.
   *
   * @param amount The value of money in double format
   * @throws IllegalArgumentException If the ammount is not reasonable.
   */

  public void withdraw(double amount) {
    if (amount > balance || amount < 0) {
      throw new IllegalArgumentException(
          "Withdraw a reasonable amount, amount entered: "
              + String.valueOf(amount));
    }
    balance -= roundedAmount(amount);
  }

  /**
   * This method is used to deposit money value.
   *
   * @param amount The value of money in double format
   * @throws IllegalArgumentException If the ammount is not reasonable.
   */
  public void deposit(double amount) {
    if (amount < 0) {
      throw new IllegalArgumentException(
          "Deposit a reasonable ammount, amount entered: " + String.valueOf(amount));
    }
    balance += roundedAmount(amount);
  }

  /**
   * This method is used to initialize the balance in bank. This might not be
   * needed in the future.
   *
   * @param amount The value of money in double format
   * @throws IllegalArgumentException If the ammount is not reasonable.
   */
  public void setBalance(double amount) {
    if (amount < 0) {
      throw new IllegalArgumentException("The amount has to be 0 or greater");
    }
    balance = roundedAmount(amount);
  }

  /**
   * This method is used to Get the balance balance in the bank.
   */
  public double getBalance() {
    return this.balance;
  }

  public void setAccountName(String accountName) {
    this.accountName = accountName;
  }

  public String getAccountName() {
    return this.accountName;
  }

  public void setAccountNumber(long accountNumber) {
    this.accountNumber = accountNumber;
  }

  /**
   * This method is used to get the account number.
   */
  public long getAccountNumber() {
    return this.accountNumber;
  }

  private double roundedAmount(double amount) {
    return Math.round(amount * 100.0) / 100.0;
  }

}

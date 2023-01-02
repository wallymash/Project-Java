package bank.core;


/**
 * This is a subclass of Account.
 */
public class SavingAccount extends Account {

  private int withdrawCount;
  

  /**
   * This is the constructor method of SavingAccount that initializes when an
   * account is created.
   *
   * @param balance       The value of money that an account starts with in double
   *                      format
   * @param accountNumber the account number
   * 
   */

  public SavingAccount(long accountNumber, double balance) {

    super(accountNumber, balance);
    super.setAccountName("saving account");
    this.withdrawCount = 0;

  }

  /**
   * Withdraw a certian amount.
   *
   * @param amount ammount money that needs to be withdrawen
   * 
   */
  public void withdraw(double amount) {
    withdrawCount += 1;
    if (withdrawCount > 2) {
      throw new IllegalArgumentException("cannot withdraw anymore,"
          + " you have withdrawen max times this periode");
    } else {
      super.withdraw(amount);
    }
  }

  public int getWithdrawCount() {
    return this.withdrawCount;
  }

}

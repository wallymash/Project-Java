package bank.core;



import java.util.ArrayList;

/**
 * Bank.
 */
public class Bank {
  private String name;
  private long lastUsedAccountNumber;
  private ArrayList<User> users = new ArrayList<>();

  /**
   * This method is used to withdraw money value.
   *
   * @param name The name of the user
   * 
   */
  public Bank(String name) {
    lastUsedAccountNumber = 12040000000L;
    this.name = name;

  }

  public Bank() {

  }



  /**
   * This method is used add interrest to savingaccount.
   *
   * @param accountNumber the account number to which the interest is added
   * @param timeNow when the interest is added
   * 
   */
  public void addInterest(Long accountNumber, int timeNow) {
    Account account = getAccount(accountNumber);
    User user = getUser(account);

    int periode = timeNow - user.getLastLoginnTime();

    double prevBalance = account.getBalance();
    
    account.setBalance(prevBalance * Math.pow(1.05, periode));
    user.setLastLoginnTime(timeNow);
  }

  /**
   * This method is used to withdraw money value.
   *
   * @param user A user object
   */
  public void addUser(User user) {
    User user1 = getUser(user.getSocialSecurityNumber());
    if (user1 != null) {
      throw new IllegalArgumentException(" This user already exists");
    }
    users.add(user);

  }

  /**
   * This method is used to get a user
   * based on the socialsecurity number of the user.
   *
   * @param socialSecurityNumber this is socialsecurity number of the user.
   * @return User user Object if user found, returns null otherwise.
   * 
   */

  public User getUser(long socialSecurityNumber) {
    for (User user : users) {
      if (user.getSocialSecurityNumber() == socialSecurityNumber) {
        return user;
      }

    }
    return null;

  }

  /**
   * This method is used to check if the account already exists.
   *
   * @param account account is the account you register or log in to.
   *
   * @return user returns if it does not already exist, otherwise it returns null.
   */
  public User getUser(Account account) {
    for (User user : users) {
      for (Account acc : user.getAccounts()) {
        if (acc.getAccountNumber() == account.getAccountNumber()) {
          return user;
        }

      }
    }
    return null;

  }

  /**
   * This method is used to verify if an account exist
   * based on the account number.
   *
   * @param accountNumber this is the account number of a particular account.
   * @return Account if account exist, null otherwise.
   * 
   */

  public Account getAccount(long accountNumber) {
    for (User user : users) {
      for (Account account : user.getAccounts()) {
        if (accountNumber == account.getAccountNumber()) {
          return account;
        }

      }
    }
    return null;
  }

  /**
   * Transfers money from an account to another account.
   *
   * @param from   The account number of the account that sends money.
   * @param to     The account number of the account that receives money.
   * @param amount the amount of money that is being transfered.
   * @throws IllegalArgumentException if one of the accounts does not exist
   */
  public void transfer(long from, long to, double amount) {
    if (getAccount(from) == null) {
      throw new IllegalArgumentException("Account:" + Long.toString(from) + " does not exit ");
    }

    if (getAccount(to) == null) {
      throw new IllegalArgumentException("Account:" + Long.toString(to) + " does not exit ");
    } else {
      getAccount(from).withdraw(amount);
      getAccount(to).deposit(amount);
      Transaction transaction = new Transaction(from, to, amount);
      getUser(getAccount(from)).addTransaction(transaction);
      getUser(getAccount(to)).addTransaction(transaction);

    }
  }

  /**
   * Registers user if user does not exist.
   *
   * @param user user
   * @throws IllegalArgumentException if the user already exists.
   */

  public void registerUser(User user) {
    user.addAccount(new Account(generateAccountNumber(lastUsedAccountNumber), 0));
    user.addAccount(new SavingAccount(generateAccountNumber(lastUsedAccountNumber), 0));
    addUser(user);
  }

  /**
   * Logs the user inn if the user exists.
   *
   * @param user User.
   * @throws IllegalArgumentException if the user does not exit.
   */
  public void logIn(User user) {
    
    
    User user1 = getUser(user.getSocialSecurityNumber());
    if (user1 == null) {
      throw new IllegalArgumentException("Wrong SSN or password");
    } else if (!user.getPassword().equals(user1.getPassword())) {
      throw new IllegalArgumentException("Wrong SSN or password");
    }
    
    
    

  }

  /**
   * This method generates an account number of 11 digits based on
   * number of accounts it is in the bank.
   *
   * @param lastUsedAccountNumber the last account number that has been assigned
   *                              to an account.
   */
  public long generateAccountNumber(long lastUsedAccountNumber) {
    this.lastUsedAccountNumber += 1;
    return this.lastUsedAccountNumber;

  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setLastUsedAccountNumber(long lastUsedAccountNumber) {
    this.lastUsedAccountNumber = lastUsedAccountNumber;
  }

  public long getLastUsedAccountNumber() {
    return lastUsedAccountNumber;
  }

  public ArrayList<User> getUsers() {
    return new ArrayList<>(users);
  }

  /**
   * This method if for deleting the user during the tests.
   *
   * @param ssn The Social Security Number is used to delete the account
   * @return after the user has been deleted, it will be returned
   */

  public User deleteUser(long ssn) {
    User user = getUser(ssn);
    users.remove(user);
    return user;

  }

  

}
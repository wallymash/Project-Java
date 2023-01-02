package bank.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {
  Bank bank;
  User user1;
  User user2;
  Account account1;
  Account account2;
  Account account3;
  Account account4;

  @BeforeEach
  public void setup() {
    bank = new Bank("BND");
    user1 = new User( 15020257342L," wally123");
    account1 = new Account(bank.generateAccountNumber(bank.getLastUsedAccountNumber()), 12000);
    account2 = new Account(bank.generateAccountNumber(bank.getLastUsedAccountNumber()), 0);
    user1.addAccount(account1);
    user1.addAccount(account2);
    user2 = new User( 15020257111L,"naqib123");
    account3 = new Account(bank.generateAccountNumber(bank.getLastUsedAccountNumber()), 500);
    account4 = new Account(bank.generateAccountNumber(bank.getLastUsedAccountNumber()), 5000);
    user1.addAccount(account3);
    user1.addAccount(account4);
    bank.addUser(user1);
    bank.addUser(user2);
  }

  @Test
  public void testTransferMoney() {
    assertThrows(IllegalArgumentException.class, () -> {
      bank.transfer(12040000001L,22040000001L , 1000);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      bank.transfer(32040000001L,12040000001L , 1000);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      bank.transfer(32040000001L,33040000001L, 1000);
    });
    bank.transfer(account1.getAccountNumber(), account2.getAccountNumber(), 2000);
    assertEquals(2000, account2.getBalance());
    assertEquals(10000, account1.getBalance());
    }

    @Test
  public void testRegisterUser() {
      assertThrows(IllegalArgumentException.class, () -> {
        bank.registerUser(user1);
      });
      assertEquals(2, bank.getUsers().size());

      
      assertThrows(IllegalArgumentException.class, () -> {
        bank.registerUser(new User( 45121257341L,"Ole"));
      });
      assertEquals(2, bank.getUsers().size());
      bank.registerUser(new User(15020227341L,"Ole121222!"));
      assertEquals(3, bank.getUsers().size());
    }

  @Test
  public void testlogIn() {
    User user = (new User(85020257346L,"Naqib123"));
    assertThrows(IllegalArgumentException.class, () -> {
      bank.logIn(user);
    });
    bank.registerUser(user);

    assertThrows(IllegalArgumentException.class, () -> {
      bank.logIn(new User(85020257346L,"Naqib12"));
    });
    assertThrows(IllegalArgumentException.class, () -> {
      bank.logIn(new User(85020257345L,"Naqib123"));
    });
    bank.logIn(user);
    
  
  }
  
  @Test
  public void testGenerateAccountNumber() {
    assertEquals(12040000001L, account1.getAccountNumber());
    assertEquals(12040000004L, account4.getAccountNumber());
    assertEquals(12040000004L,bank.getLastUsedAccountNumber());
  }

  @Test
  public void testGetUser() {
    Account account = new Account(16000000000L, 0);
    assertNull(bank.getUser(account));
  }

  @Test
  public void testAddInterest() {
    //Account account = user1.getAccounts().get(1);
    bank.transfer(account1.getAccountNumber(), account2.getAccountNumber(), 2000);
    bank.addInterest(account2.
    getAccountNumber(),LocalDateTime.now().getYear()+2);
    assertEquals(2205,account2.getBalance());
  }


 

  @Test
  public void testDeleteUser() {
    bank.deleteUser(user1.getSocialSecurityNumber());
    assertNull(bank.getUser(user1.getSocialSecurityNumber()));
  }



} 

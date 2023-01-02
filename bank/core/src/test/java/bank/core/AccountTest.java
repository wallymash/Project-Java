package bank.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

  
  
public class AccountTest {
  Account account;
  @BeforeEach
  public void Setup() {
    account = new Account(4, 4000);
  
  }
  @Test
  public void testWithdraw() {
    account.withdraw(1000);
    assertEquals(3000, account.getBalance());
    assertThrows(IllegalArgumentException.class, ()-> {
      account.withdraw(3001);
    },"IllegalArgumentException was Expected");
    assertThrows(IllegalArgumentException.class, ()-> {
      account.withdraw(-1);
    },"IllegalArgumentException was Expected");
      
  }
  
  @Test
  public void testDeposit() {
    account.deposit(1000);
    assertEquals(5000, account.getBalance());
    assertThrows(IllegalArgumentException.class, ()-> {
    account.deposit(-1);
    }, "IllegalArgumentException was expected");
}
  
  @Test
  public void testGetBalance() {
    assertEquals(4000, account.getBalance());
    account.deposit(400);
    assertEquals(4400, account.getBalance());
    account.withdraw(1400);
    assertEquals(3000, account.getBalance());
  }
  
  @Test
  public void testSetBalance() {
    account.setBalance(2000);
    assertEquals(2000, account.getBalance());
    assertThrows(IllegalArgumentException.class, ()-> {
      account.setBalance(-1);;
    }, "IllegalArgumentException was expected");
}
}

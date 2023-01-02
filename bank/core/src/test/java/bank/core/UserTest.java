package bank.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class UserTest {
  User user;
  
  @BeforeEach
  public void setup() {
    user = new User(65020257342L,"wally123");
    user.addAccount(new Account(12345678, 0));
  
  }
  

  @Test
  public void testAddAccount() {
  
    assertEquals(1, user.getAccounts().size());
    assertThrows(IllegalArgumentException.class, () -> {
      user.addAccount(new Account(12345678, 0));
    }, "IllegalArgumentException was expected");
    assertEquals(1, user.getAccounts().size());
    user.addAccount(new Account(434524554, 0));
    assertEquals(2, user.getAccounts().size());
  
  }

  @Test
  public void testValidation(){
    
    assertThrows(IllegalArgumentException.class,()->{
      user = new User(1, "wally123");

    });
    assertThrows(IllegalArgumentException.class,()->{
      user = new User(65020257342L, "wallyyy");

    });
    assertThrows(IllegalArgumentException.class,()->{
      user = new User(65020257342L, "123456");

    });
    assertThrows(IllegalArgumentException.class,()->{
      user = new User(65020257342L, "12wl");

    });
    assertThrows(IllegalArgumentException.class,()->{
      user = new User(65020257342L, null);

    });
    user = new User(65020257342L, "123wally");

  }
  
  
  
  
  
}  
  
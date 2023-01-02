package bank.core;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;




public class SavingAccountTest {
  SavingAccount acc; 
  
  @BeforeEach
  public void Setup(){
    acc = new SavingAccount(4, 1000);
  }
  
  @Test
  public void testWithdraw(){
  
    acc.withdraw(200);
    assertEquals(800, acc.getBalance());
    acc.withdraw(50);
    assertEquals(2, acc.getWithdrawCount());
    
    assertThrows(IllegalArgumentException.class, ()->{
      acc.withdraw(50);

    });
  
    assertEquals(750, acc.getBalance());        
  }   
}  
  
  




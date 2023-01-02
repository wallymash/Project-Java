package gui.bank;

import bank.core.Account;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.io.IOException;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * ControllerTest.
 */
@TestMethodOrder(OrderAnnotation.class)
public class ControllerTest extends ApplicationTest {

  private BankController controller;
  private int size;

  @Override
  public void start(final Stage stage) throws Exception {
    final FXMLLoader loader = new FXMLLoader(getClass().getResource("BankApp.fxml"));
    final Parent parent = loader.load();
    this.controller = loader.getController();
    size = controller.bank.getUsers().size();
    stage.setScene(new Scene(parent));
    stage.show();

  }

  @Test
  @Order(1)
  public void testController() {
    assertNotNull(this.controller);
  }

  @Test
  @Order(2)
  public void testRegister() {
    clickOn("#socialSecurityNumber").write("12345678922");
    clickOn("#password").write("123abc");
    clickOn("#registerButton");
    assertEquals(size+1, controller.bank.getUsers().size());
    clickOn(LabeledMatchers.hasText("OK"));
    clickOn("#socialSecurityNumber").write("12345678922");
    clickOn("#password").write("123abc");
    clickOn("#registerButton");
    FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("OK"));
    
    
    
  }

  @Test
  @Order(3)
  public void testLogin(){
    clickOn("#socialSecurityNumber").write("21234567891");
    sleep(500);
    clickOn("#password").write("123abc");
    sleep(500);
    clickOn("#loginButton");
    sleep(500);
    FxAssert.verifyThat("OK", NodeMatchers.isVisible());
    clickOn(LabeledMatchers.hasText("OK"));
    

  }
  
  @Test
  @Order(4)
  public void TestDeposit() {
    login();
    clickOn("#input").write("4000");
    clickOn("#depositButton");
    sleep(500);
    assertEquals(4000, controller.user.getAccounts().get(0).getBalance());

  }

  @Test
  @Order(5)
  public void TestWithdraw() {
    login();
    clickOn("#input").write("400");
    clickOn("#withdrawButton");
    sleep(500);
    assertEquals(3600, controller.user.getAccounts().get(0).getBalance());

  }

  @Test
  @Order(6)
  public void testTransfer() throws IOException{
    login();
    Account from = controller.bank.getUser(12345678922l).getAccounts().get(0); 
    Account to = controller.bank.getUser(12345678922l).getAccounts().get(1);
    clickOn("#am").write("300");
    clickOn("#from").write(Long.toString(from.getAccountNumber()));
    clickOn("#to").write(Long.toString(to.getAccountNumber()));
    clickOn("#transferButton");
    sleep(500);
    assertEquals(3300,controller.bank.getUser(12345678922l).getAccounts().get(0).getBalance());
    assertEquals(300, controller.bank.getUser(12345678922l).getAccounts().get(1).getBalance());

    clickOn("#am").write("300");
    clickOn("#from").write(Long.toString(from.getAccountNumber()-2));
    clickOn("#to").write(Long.toString(to.getAccountNumber()));
    clickOn("#transferButton");
    FxAssert.verifyThat("OK", NodeMatchers.isVisible());

    
    
    
    
  }

  
  @Test
  @Order(7)
  public void testRemovalOfInputs() throws Exception {
    login();

    clickOn("#input").write("1000");
    clickOn("#am").write("1000");
    clickOn("#from").write("1000");
    clickOn("#to").write("1000");
    clickOn("#socialSecurityNumber").write("1000");
    clickOn("#password").write("1000");

    sleep(500);

    clickOn("#depositButton");

    sleep(1000);

    assertNull(controller.input.getText());
    assertNull(controller.am.getText());
    assertNull(controller.from.getText());
    assertNull(controller.to.getText());
    assertNull(controller.socialSecurityNumber.getText());
    assertNull(controller.password.getText());

    tearDown();
  }

  




  
  private void login() {
    clickOn("#socialSecurityNumber").write("12345678922");
    sleep(500);
    clickOn("#password").write("123abc");
    sleep(500);
    clickOn("#loginButton");
    sleep(500);
  }
  
  private void tearDown() throws Exception{
    DataAccess dataAccess = new DataAccess("http://localhost:8080");
    dataAccess.deleteUser(12345678922l);
  }

}

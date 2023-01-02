package bank.json;


import bank.core.Account;
import bank.core.Bank;
import bank.core.Transaction;
import bank.core.User;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Iterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * BankModuleTest.
 */
public class BankModuleTest {
  

  
  ObjectMapper mapper;
  Bank bank;
  User user;
  Account account1;
  Account account2;
  SimpleDateFormat formatter;
  @BeforeEach
  public void setUp() {
    mapper = new ObjectMapper();
    mapper.registerModule(new BankModule());
    bank = new Bank("Norges Bank");
    user = new User(12345678901L, "abc123");
    formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    bank.registerUser(user);
    account1 = user.getAccounts().get(0);
    account2 = user.getAccounts().get(1); 
    account1.deposit(3000);
  }

  final static String bankWithOneUser = "{\"name\":\"Norges Bank\",\"lastUsedAccountNumber\":12040000002,\"users\":[{\"socialSecurityNumber\":12345678901,\"password\":\"abc123\",\"lastLoginnTime\":2022,\"accounts\":[{\"accountName\":\"User account\",\"accountNumber\":12040000001,\"balance\":3000.0},{\"accountName\":\"saving account\",\"accountNumber\":12040000002,\"balance\":0.0}],\"transactionHistory\":[{\"from\":12040000002,\"to\":12040000002,\"amount\":0.0,\"date\":\"22/10/2022 15:30\"}]}]}";
  final static String bankWithTwoUser = "{\"name\":\"Norges Bank\",\"lastUsedAccountNumber\":12040000004,\"users\":[{\"socialSecurityNumber\":12345678901,\"password\":\"abc123\",\"lastLoginnTime\":2022,\"accounts\":[{\"accountName\":\"User account\",\"accountNumber\":12040000001,\"balance\":3000.0},{\"accountName\":\"saving account\",\"accountNumber\":12040000002,\"balance\":0.0}],\"transactionHistory\":[{\"from\":12040000002,\"to\":12040000002,\"amount\":0.0,\"date\":\"22/10/2022 15:30\"}]},{\"socialSecurityNumber\":12345678904,\"password\":\"abb111\",\"lastLoginnTime\":"+LocalDateTime.now().getYear()+",\"accounts\":[{\"accountName\":\"User account\",\"accountNumber\":12040000003,\"balance\":0.0},{\"accountName\":\"saving account\",\"accountNumber\":12040000004,\"balance\":0.0}],\"transactionHistory\":[]}]}";
  
  @Test
  public void testSerializers() throws ParseException {
    
    try {
      Transaction transaction = new Transaction(12040000002L, 12040000002L, 0);
      
      //Date date = formatter.parse();
      transaction.setDate("22/10/2022 15:30");
      user.addTransaction(transaction);
      assertEquals(bankWithOneUser, mapper.writeValueAsString(bank));
      bank.registerUser(new User(12345678904L,"abb111"));
      assertEquals(bankWithTwoUser, mapper.writeValueAsString(bank));
      bank.transfer(account1.getAccountNumber(), account2.getAccountNumber(), 4);
      
    } catch (JsonProcessingException e) {
      
      fail(e.getMessage());
    }
  }

  @Test
  public void testDeserializers(){
    try {
      Bank bank1 = mapper.readValue(bankWithTwoUser, Bank.class);
      assertEquals("Norges Bank", bank1.getName());
      assertEquals(12040000004L, bank1.getLastUsedAccountNumber());
      assertEquals(2, bank1.getUsers().size());
      Iterator<User> iterator = bank1.getUsers().iterator();
      assertTrue(iterator.hasNext());
      iterator.next();
      assertTrue(iterator.hasNext());
      iterator.next();
      assertFalse(iterator.hasNext());
      

    } catch (JsonProcessingException e) {
      fail();
    }
  }

  @Test
  public void testDeserializersIdividualy() throws ParseException, JsonProcessingException{

    String json1 = "{\"from account\":12040000001,\"to account\":12040000001,\"ammount\":400.0,\"date\":\"22/10/2022 15:30\"}";
    Transaction transaction = new Transaction(12040000001L,12040000001L, 400);
    String date = "22/10/2022 15:30";
    transaction.setDate("22/10/2022 15:30");
    
    Transaction transaction2 = mapper.readValue(json1, Transaction.class);
    assertEquals(date, transaction2.getDate());

    String json2 ="{\"accountName\":\"User account\",\"accountNumber\":1204000000,\"balance\":400.0}";
    Account account = mapper.readValue(json2, Account.class);
    assertEquals(400, account.getBalance());



  }
}


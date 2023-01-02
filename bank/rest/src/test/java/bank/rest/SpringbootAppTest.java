package bank.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;
import bank.core.Bank;
import bank.core.User;
import bank.json.BankModule;


//@SpringBootTest

@ContextConfiguration(classes = {SpringController.class , BankService.class, SpringApp.class })
@WebMvcTest()
@AutoConfigureMockMvc

public class SpringbootAppTest {
  @Autowired
  private MockMvc mockMvc;

  private ObjectMapper mapper;

  @BeforeEach
  public void setUp(){
    this.mapper = new ObjectMapper().registerModule(new BankModule());
  }

  @Test
  public void getBankTest() throws Exception{
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/bank")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();
    Bank bank = mapper.readValue(result.getResponse().getContentAsString(), Bank.class);
    assertEquals("Norges Bank", bank.getName());

  }

  

  @Test
  public void registerUsertTest() throws Exception{

    User user = new User(12345678902l, "123abcc");
    String jsonString = mapper.writeValueAsString(user);
    mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/bank")
        .content(jsonString)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON));

    /* MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/bank/12345678902")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn(); */
    User user1 = getUser(); /* mapper.readValue(result.getResponse().getContentAsString(), User.class); */
    assertEquals(12345678902l, user1.getSocialSecurityNumber());

    mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/bank/account/"+user1.getAccounts().get(0).getAccountNumber()+"/deposit/" +2000)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();
    
    User user2 = getUser();
    assertEquals(2000, user2.getAccounts().get(0).getBalance());

    mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/bank/account/"+user1.getAccounts().get(0).getAccountNumber()+"/withdraw/" +500)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();
    User user3 = getUser();
    assertEquals(1500, user3.getAccounts().get(0).getBalance());

    mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/bank/from/"+user1.getAccounts()
          .get(0).getAccountNumber()+"/to/"+user1.getAccounts().get(1).getAccountNumber()+"/transfer/" +500)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();
    User user4 = getUser();
    assertEquals(1000, user4.getAccounts().get(0).getBalance());
    assertEquals(500, user4.getAccounts().get(1).getBalance());
    assertEquals(2, user4.getTransactionHistory().size());

    mockMvc.perform(MockMvcRequestBuilders
    .put("/bank/addintrest/" + getUser().getAccounts().get(1).getAccountNumber()))
    .andExpect(status().isOk())
    .andReturn();




    mockMvc.perform(MockMvcRequestBuilders
    .delete("/bank/user/" + 12345678902l))
    .andExpect(status().isOk())
    .andReturn();
    User user5 = getUser();
    assertNull(user5);


    
    
    
    
    

  }
  
  public User getUser() throws Exception{
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/bank/12345678902")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();
    User user = mapper.readValue(result.getResponse().getContentAsString(), User.class);
    return user;
  }
}


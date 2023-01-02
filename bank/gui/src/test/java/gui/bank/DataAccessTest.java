package gui.bank;

import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;






public class DataAccessTest {

  private WireMockConfiguration config;
  private WireMockServer wireMockServer;
  

  private DataAccess dataAccess;
  private static final String SAMPLE_BANK = "{\"name\" : \"Norges Bank\",\"lastUsedAccountNumber\" : 12040000002,\"users\" : []}";
 private static final String SAMPLE_USER = "{ \"socialSecurityNumber\" : 12345678901,\"password\" : \"123abc\","
  + "\"accounts\" : [ {"
    + "\"accountName\" : \"User account\","
    + "\"accountNumber\" : 12040000001,"
    + "\"balance\" : 1400.0},"
    + "{"
    + "\"accountName\" : \"saving account\","
    + "\"accountNumber\" : 12040000002,"
    + "\"balance\" : 4050.0} ],"
  + "\"transaction history\" : []"
  +"}"; 

  
  @BeforeEach
  public void setUp(){
    config = WireMockConfiguration.wireMockConfig().port(9090);
    wireMockServer = new WireMockServer(config.portNumber());
    wireMockServer.start();
    WireMock.configureFor("localhost", config.portNumber());
    dataAccess= new DataAccess("http://localhost:" + wireMockServer.port());

  }

  @Test
  public void getBankTest() throws IOException, InterruptedException{
    stubFor(get(urlEqualTo("/bank"))
        .withHeader("Accept", equalTo("application/json"))
        .willReturn(aResponse()
          .withStatus(200)
          .withHeader("Content-Type", "application/json")
          .withBody(SAMPLE_BANK)));
            

    assertEquals("Norges Bank", dataAccess.getBank().getName());
    assertEquals(12040000002l, dataAccess.getBank().getLastUsedAccountNumber());
    assertTrue(dataAccess.getBank().getUsers().isEmpty());
    
  }

  @Test
  public void getUsetTest() throws Exception{
    stubFor(get(urlEqualTo("/bank/12345678901"))
        .withHeader("Accept", equalTo("application/json"))
        .willReturn(aResponse()
          .withStatus(200)
          .withHeader("Content-Type", "application/json")
          .withBody(SAMPLE_USER)));
    assertEquals(12345678901l, dataAccess.getUser(12345678901l).getSocialSecurityNumber());
    assertEquals(2, dataAccess.getUser(12345678901l).getAccounts().size());
    assertTrue(dataAccess.getUser(12345678901l).getTransactionHistory().isEmpty());

  }



  @AfterEach
  public void tearDown (){
    wireMockServer.stop();
  }
    
}

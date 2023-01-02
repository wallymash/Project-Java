package bank.json;

import bank.core.Account;
import bank.core.Transaction;
import bank.core.User;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

/**
 * Serializer for the user class.
 */
public class UserSerializer extends JsonSerializer<User> {

  /**
   * format: { "socialSecurityNumber": "...","password:" "...","lastLoginnTime:" "...", "accounts": [ ...
   * ] }
   */
  @Override
  public void serialize(User user, JsonGenerator jsonGenerator, SerializerProvider serializers)
      throws IOException {
    jsonGenerator.writeStartObject();
    jsonGenerator.writeNumberField("socialSecurityNumber", user.getSocialSecurityNumber());
    jsonGenerator.writeStringField("password", user.getPassword());
    jsonGenerator.writeNumberField("lastLoginnTime", user.getLastLoginnTime());
    jsonGenerator.writeArrayFieldStart("accounts");
    for (Account account : user.getAccounts()) {
      jsonGenerator.writeObject(account);
    }
    jsonGenerator.writeEndArray();
    jsonGenerator.writeArrayFieldStart("transactionHistory");
    for (Transaction transaction : user.getTransactionHistory()) {
      jsonGenerator.writeObject(transaction);
    }
    jsonGenerator.writeEndArray();

    jsonGenerator.writeEndObject();

  }

}


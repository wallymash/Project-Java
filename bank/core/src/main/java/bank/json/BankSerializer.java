package bank.json;

import bank.core.Bank;
import bank.core.User;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

/**
 * BankSerializer.
 */
public class BankSerializer extends JsonSerializer<Bank> {

  /**
   * format: { "name": "...","lastUsedAccountNumber": "...", "users": [ ... ] }
   */
  @Override
  public void serialize(Bank bank, JsonGenerator jsonGenerator, SerializerProvider serializers)
      throws IOException {
    jsonGenerator.writeStartObject();
    jsonGenerator.writeStringField("name", bank.getName());
    jsonGenerator.writeNumberField("lastUsedAccountNumber", bank.getLastUsedAccountNumber());
    jsonGenerator.writeArrayFieldStart("users");
    for (User user : bank.getUsers()) {
      jsonGenerator.writeObject(user);
    }
    jsonGenerator.writeEndArray();
    jsonGenerator.writeEndObject();
  }

}

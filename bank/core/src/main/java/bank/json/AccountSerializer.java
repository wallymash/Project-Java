package bank.json;

import bank.core.Account;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

/**
 * AccountSerializer. On format: { "accountName": "...","accountNumber": "...",
 * "balance": "..." }
 */
public class AccountSerializer extends JsonSerializer<Account> {

  @Override
  public void serialize(Account account, JsonGenerator jsonGenerator,
      SerializerProvider serializers) throws IOException {
    jsonGenerator.writeStartObject();
    jsonGenerator.writeStringField("accountName", account.getAccountName());
    jsonGenerator.writeNumberField("accountNumber", account.getAccountNumber());
    jsonGenerator.writeNumberField("balance", account.getBalance());
    jsonGenerator.writeEndObject();
  }

}

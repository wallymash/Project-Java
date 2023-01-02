package bank.json;

import bank.core.Transaction;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

/**
 * Serializer for the user class.
 */
public class TransactionSerializer extends JsonSerializer<Transaction> {

  /**
   * format: { "from account": ""...","to account": "...", "ammount": "...",
   * "date": "..." }
   */

  @Override
  public void serialize(Transaction transaction, JsonGenerator jsonGenerator,
      SerializerProvider serializers) throws IOException {
    jsonGenerator.writeStartObject();
    jsonGenerator.writeNumberField("from", transaction.getFrom());
    jsonGenerator.writeNumberField("to", transaction.getTo());
    jsonGenerator.writeNumberField("amount", transaction.getAmount());
    jsonGenerator.writeStringField("date", transaction.getDate());
    jsonGenerator.writeEndObject();
  }

}

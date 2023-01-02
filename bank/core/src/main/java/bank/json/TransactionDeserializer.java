package bank.json;

import bank.core.Transaction;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import java.io.IOException;

/**
 * private SimpleDateFormat formatter = new
 * SimpleDateFormat("dd/MM/yyyyHH:mm");.
 */
public class TransactionDeserializer extends JsonDeserializer<Transaction> {

  @Override
  public Transaction deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException, JacksonException {
    TreeNode treeNode = parser.getCodec().readTree(parser);
    return deserialize((JsonNode) treeNode);

  }

  /**
   * Deserialize transaction method.
   *
   * @param node node.
   * @return the transaction information, which includes "to", "from", "ammount"
   *         and "date".
   *
   */
  public Transaction deserialize(JsonNode node) {
    if (node instanceof ObjectNode) {
      ObjectNode objectNode = (ObjectNode) node;
      Transaction transaction = new Transaction();
      JsonNode from = objectNode.get("from");
      if (from instanceof LongNode) {

        transaction.setFrom(((LongNode) from).asLong());
      }
      JsonNode to = objectNode.get("to");

      if (to instanceof LongNode) {

        transaction.setTo(((LongNode) to).asLong());

      }
      JsonNode ammount = objectNode.get("amount");

      if (ammount instanceof DoubleNode) {

        transaction.setAmount(((DoubleNode) ammount).asDouble());

      }

      JsonNode stringNode = objectNode.get("date");

      if (stringNode instanceof TextNode) {

        transaction.setDate(((TextNode) stringNode).asText());

      }

      return transaction;
    }
    return null;

  }
}

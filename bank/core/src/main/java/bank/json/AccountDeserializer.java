package bank.json;

import bank.core.Account;
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
 * AccountDeserializer.
 */
public class AccountDeserializer extends JsonDeserializer<Account> {

  @Override
  public Account deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException, JacksonException {
    TreeNode treeNode = parser.getCodec().readTree(parser);
    return deserialize((JsonNode) treeNode);

  }

  /**
   * Deserializes an object of type Account.
   *
   * @param node JsonNode
   * @return Account
   */

  public Account deserialize(JsonNode node) {
    if (node instanceof ObjectNode) {
      ObjectNode objectNode = (ObjectNode) node;
      Account account = new Account();
      JsonNode textNode = objectNode.get("accountName");
      if (textNode instanceof TextNode) {
        account.setAccountName(((TextNode) textNode).asText());

      }
      JsonNode longNode = objectNode.get("accountNumber");
      if (longNode instanceof LongNode) {
        account.setAccountNumber(((LongNode) longNode).asLong());

      }
      JsonNode doubleNode = objectNode.get("balance");
      if (doubleNode instanceof DoubleNode) {
        account.setBalance(((DoubleNode) doubleNode).asDouble());

      }
      return account;

    }
    return null;
  }

}

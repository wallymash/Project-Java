package bank.json;

import bank.core.Bank;
import bank.core.User;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import java.io.IOException;
import java.text.ParseException;

/**
 * BankDeserializer.
 */
public class BankDeserializer extends JsonDeserializer<Bank> {
  private UserDeserializer userDeserializer = new UserDeserializer();

  @Override
  public Bank deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException, JacksonException {
    TreeNode treeNode = parser.getCodec().readTree(parser);
    try {
      return deserialize((JsonNode) treeNode);
    } catch (ParseException e) {
      // TODO Auto-generated catch block

    }

    return null;

  }

  /**
   * Deserializes a JsonNode to an object of type Bank.
   *
   * @param node JsonNode
   * 
   * @return Bank
   * 
   * @throws ParseException Throws a parse exception.
   * 
   */
  public Bank deserialize(JsonNode node) throws ParseException {
    if (node instanceof ObjectNode) {
      ObjectNode objectNode = (ObjectNode) node;
      Bank bank = new Bank();
      JsonNode textNode = objectNode.get("name");
      if (textNode instanceof TextNode) {
        bank.setName(((TextNode) textNode).asText());
      }
      JsonNode longNode = objectNode.get("lastUsedAccountNumber");
      if (longNode instanceof LongNode) {
        bank.setLastUsedAccountNumber(((LongNode) longNode).asLong());

      }
      JsonNode arrayNode = objectNode.get("users");
      if (arrayNode instanceof ArrayNode) {
        for (JsonNode jsonNode : ((ArrayNode) arrayNode)) {
          User user = userDeserializer.deserialize(jsonNode);
          if (user != null) {
            bank.addUser(user);
          }
        }
      }
      return bank;

    }
    return null;
  }

}

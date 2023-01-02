package bank.json;

import bank.core.Account;
import bank.core.Transaction;
import bank.core.User;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import java.io.IOException;
import java.text.ParseException;

/**
 * UserDeserializer.
 */
public class UserDeserializer extends JsonDeserializer<User> {

  private AccountDeserializer accountDeserializer = new AccountDeserializer();
  private TransactionDeserializer transactionDeserializer = new TransactionDeserializer();

  @Override
  public User deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException, JacksonException {
    TreeNode treeNode = parser.getCodec().readTree(parser);
    return deserialize((JsonNode) treeNode);
  }

  /**
   * Deserializes an object of type User.
   *
   * @param node JsonNode
   * @return User
   * @throws ParseException if parsed
   */

  public User deserialize(JsonNode node) {
    if (node instanceof ObjectNode) {
      ObjectNode objectNode = (ObjectNode) node;
      User user = new User();
      

      JsonNode intNode = objectNode.get("lastLoginnTime");
      if (intNode instanceof IntNode) {
        user.setLastLoginnTime(((IntNode) intNode).asInt());
      
      }
      JsonNode longNode = objectNode.get("socialSecurityNumber");
      if (longNode instanceof LongNode) {
        user.setSocialSecurityNumber(((LongNode) longNode).asLong());
      }
      JsonNode textNode = objectNode.get("password");
      if (textNode instanceof TextNode) {
        user.setPassword(((TextNode) textNode).asText());
      }
      JsonNode arrayNode = objectNode.get("accounts");
      if (arrayNode instanceof ArrayNode) {
        for (JsonNode jsonNode : ((ArrayNode) arrayNode)) {
          Account account = accountDeserializer.deserialize(jsonNode);
          if (account != null) {
            user.addAccount(account);
          }
        }
      }
      JsonNode arrayNode1 = objectNode.get("transactionHistory");
      if (arrayNode1 instanceof ArrayNode) {
        for (JsonNode jsonNode : ((ArrayNode) arrayNode1)) {
          Transaction transaction = transactionDeserializer.deserialize(jsonNode);
          if (transaction != null) {
            user.addTransaction(transaction);
            ;
          }
        }
      }

      return user;

    }
    return null;

  }

}
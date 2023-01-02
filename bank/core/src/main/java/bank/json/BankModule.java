package bank.json;

import bank.core.Account;
import bank.core.Bank;
import bank.core.Transaction;
import bank.core.User;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * A Jackson module for configuring JSON serialization of Bank instances.
 */

public class BankModule extends SimpleModule {
  private static final String NAME = "BankModule";
  private static final VersionUtil VERSION_UTIL = new VersionUtil() {
  };

  /**
   * Initializes this BankModule with appropriate serializers and deserializers.
   */
  public BankModule() {
    super(NAME, VERSION_UTIL.version());
    addSerializer(Account.class, new AccountSerializer());
    addSerializer(User.class, new UserSerializer());
    addSerializer(Bank.class, new BankSerializer());
    addSerializer(Transaction.class, new TransactionSerializer());
    addDeserializer(Account.class, new AccountDeserializer());
    addDeserializer(User.class, new UserDeserializer());
    addDeserializer(Bank.class, new BankDeserializer());
    addDeserializer(Transaction.class, new TransactionDeserializer());

  }

}

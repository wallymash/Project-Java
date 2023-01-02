module core {
  requires transitive com.fasterxml.jackson.databind;

  exports bank.core;
  exports bank.json;
}

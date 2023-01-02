module bank.gui {
  requires com.fasterxml.jackson.databind;

  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.base;
  requires javafx.graphics;
  requires java.net.http;

  requires core;

  opens gui.bank to javafx.graphics, javafx.fxml;

  
}

package gui.bank;

import bank.core.Account;
import bank.core.Bank;
import bank.core.Transaction;
import bank.core.User;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller.
 */

public class BankController implements Initializable {

  @FXML
  protected TableView<Account> tableView;
  @FXML
  protected TableColumn<Account, String> accountName;
  @FXML
  protected TableColumn<Account, Long> accountNumber;
  @FXML
  protected TableColumn<Account, Double> balance;
  @FXML
  protected TextField input;
  @FXML
  protected TextField password;
  @FXML
  protected TextField from;
  @FXML
  protected TextField to;
  @FXML
  protected TextField socialSecurityNumber;
  @FXML
  protected TextField am;
  @FXML
  protected Button registerButton;
  @FXML
  protected Button loginButton;
  @FXML
  protected Button withdrawButton;
  @FXML
  protected Button depositButton;
  @FXML
  protected TableView<Transaction> transactionHistoryTableView;
  @FXML
  protected TableColumn<Transaction, String> transactionHistoryDate;
  @FXML
  protected TableColumn<Transaction, Long> transactionHistoryFrom;
  @FXML
  protected TableColumn<Transaction, Long> transactionHistoryTo;
  @FXML
  protected TableColumn<Transaction, Double> transactionHistoryAmount;

  protected Bank bank;
  protected User user;
  protected DataAccess dataAccess;
  protected Account curreAccount;
  protected ObservableList<Transaction> transactions;
  protected ObservableList<Account> accounts;

  /**
   * Initializes the tableview with correct data.
   *
   * @param location  URL to the location; needed for Initializable.
   * @param resources Resourcesbundle; needed for Initializable.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    balance.setCellValueFactory(new PropertyValueFactory<Account, Double>("balance"));
    accountNumber.setCellValueFactory(new PropertyValueFactory<Account, Long>("accountNumber"));
    accountName.setCellValueFactory(new PropertyValueFactory<Account, String>("accountName"));
    accounts = FXCollections.observableArrayList();

    transactionHistoryDate.setCellValueFactory(
        new PropertyValueFactory<Transaction, String>("date"));
    transactionHistoryFrom.setCellValueFactory(new PropertyValueFactory<Transaction, Long>("from"));
    transactionHistoryTo.setCellValueFactory(new PropertyValueFactory<Transaction, Long>("to"));
    transactionHistoryAmount.setCellValueFactory(
        new PropertyValueFactory<Transaction, Double>("amount"));
    transactions = FXCollections.observableArrayList();

    dataAccess = new DataAccess("http://localhost:8080");
    tableView.setItems(accounts);
    user = new User();
    curreAccount = new Account();

    try {
      bank = dataAccess.getBank();
    } catch (IOException | InterruptedException e) {
      // ignore
    }
    if (bank.getLastUsedAccountNumber() < 12040000000L) {
      bank.setLastUsedAccountNumber(12040000000L);
    }

  }

  /**
   * This method registers a new user.
   * Invokes the alert() method if the user already exits.
   *
   * @param event button click.
   */
  public void register(ActionEvent event) {

    try {
      User user1 = new User(Long.parseLong(socialSecurityNumber.getText()), password.getText());
      if (dataAccess.getUser(user1.getSocialSecurityNumber()) == null) {
        dataAccess.registerUser(user1);
        this.user = dataAccess.getUser(user1.getSocialSecurityNumber());
        clearTableView();
        refresh();
        removeInputs();
        alert("Registration complete");
      } else {
        clearTableView();
        removeInputs();
        alert("This user aleardy exits.");

      }
    } catch (NumberFormatException e) {
      alert("invalid input");
    } catch (IllegalArgumentException e) {
      alert(e.getMessage());
    } catch (Exception e) {
      // ignore

    }

  }

  /**
   * This method logs the user into its account.
   * Invokes the alert() method if the user does not exit.
   *
   * @param event button click.
   */
  public void login(ActionEvent event) {

    try {
      User user1 = new User(Long.parseLong(socialSecurityNumber.getText()), password.getText());
      clearTableView();

      if (dataAccess.getUser(user1.getSocialSecurityNumber()) != null) {
        this.user = dataAccess.getUser(user1.getSocialSecurityNumber());
        dataAccess.addInterest(this.user.getAccounts().get(1).getAccountNumber(),
            LocalDate.now().getYear());

        refresh();
        removeInputs();

      } else {
        alert(" this user does not exist");
        refresh();
        removeInputs();

      }

    } catch (NumberFormatException e) {
      alert("invalid input");
    } catch (IllegalArgumentException e) {
      clearTableView();
      alert(e.getMessage());
    } catch (Exception e) {
      // ignore

    }

  }

  /**
   * Deposits money to the users user account.
   *
   * @param event button click.
   * @throws Exception
   * 
   */
  @FXML
  void deposit(ActionEvent event) {
    try {
      curreAccount = user.getAccounts().get(0);
      double amountToDeposit = Double.parseDouble(input.getText());
      dataAccess.deposit(curreAccount.getAccountNumber(), amountToDeposit);
      refresh();
      removeInputs();
    } catch (NumberFormatException e) {
      alert("invalid input");
    } catch (IllegalArgumentException e) {
      alert(e.getMessage());
    } catch (IndexOutOfBoundsException e) {
      alert("Cannot deposit without logging in");
    } catch (Exception e) {
      // ignore
    }
  }

  /**
   * Withdraws money from the users user account.
   *
   * @param event button click.
   *
   */
  @FXML
  public void withdraw(ActionEvent event) {
    try {
      curreAccount = user.getAccounts().get(0);
      double amountToWithdraw = Double.parseDouble(input.getText());
      dataAccess.withdraw(curreAccount.getAccountNumber(), amountToWithdraw);
      refresh();
      removeInputs();
    } catch (NumberFormatException e) {
      alert("invalid input");
    } catch (IllegalArgumentException e) {
      alert(e.getMessage());
    } catch (IndexOutOfBoundsException e) {
      alert("Cannot withdraw without logging in");
    } catch (Exception e) {
      // ignore
    }

  }

  /**
   * Transfers money from an account to another account.
   *
   * @param event button click.
   */
  @FXML
  public void transfer(ActionEvent event) {

    try {

      long fromAccountNumber = Long.parseLong(from.getText());
      long toAccountNumber = Long.parseLong(to.getText());
      double amountToTransfer = Double.parseDouble(am.getText());
      Boolean isOwner = false;
      for (Account account : this.user.getAccounts()) {
        if (account.getAccountNumber() == fromAccountNumber) {
          isOwner = true;
        }
      }
      if (isOwner) {
        dataAccess.tranfer(fromAccountNumber, toAccountNumber, amountToTransfer);
        bank.transfer(fromAccountNumber, toAccountNumber, amountToTransfer);
        refresh();
        removeInputs();
      } else {
        alert("You dont own this:" + fromAccountNumber + " account.");
        refresh();
        removeInputs();
      }

    } catch (NumberFormatException e) {
      alert("invalid input");
    } catch (IllegalArgumentException e) {
      alert(e.getMessage());
    } catch (IndexOutOfBoundsException e) {
      alert("Cannot transfer without logging in.");
    } catch (Exception e) {
      // ignore
    }

  }

  /**
   * This method is used to give the user feedback in case any exception occurs.
   *
   * @param string The feedback that will be shown on the screen.
   */
  public void alert(String string) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Information");
    alert.setContentText(string);
    alert.setHeaderText("Information");
    alert.showAndWait();
  }

  private void clearTableView() {
    for (int i = 0; i <= 2; i++) {
      tableView.getItems().clear();
      transactionHistoryTableView.getItems().clear();
    }

  }

  private void refresh() throws Exception {
    accounts.clear();
    this.user = dataAccess.getUser(this.user.getSocialSecurityNumber());
    for (Account account : this.user.getAccounts()) {
      accounts.add(account);
    }
    transactions.clear();

    for (Transaction transaction : this.user.getTransactionHistory()) {
      transactions.add(transaction);
    }
    tableView.setItems(accounts);
    transactionHistoryTableView.setItems(transactions);
    this.bank = dataAccess.getBank();

  }

  /*
   * Removes all inputs from textfields whenever an event is accessed.
   *
   */
  private void removeInputs() {
    socialSecurityNumber.setText(null);
    password.setText(null);
    input.setText(null);
    am.setText(null);
    from.setText(null);
    to.setText(null);
  }

}

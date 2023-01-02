package bank.core;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Transaction.
 */
public class Transaction {
  private long from;
  private long to;
  private double amount;
  private String date;

  /**
   * This method is called for each transaction, and sets the information of which
   * account it is from, to, which amount and the date.
   *
   * @param from   The bank account which the transaction is from.
   * @param to     The bank account which the transaction is to.
   * @param amount The amount which is transferred, Rounded to 2 decimals.
   *
   */
  public Transaction(long from, long to, double amount) {
    this.from = from;
    this.to = to;
    this.amount = roundedAmount(amount);
    this.date = dateToString(new Date());

  }

  public Transaction() {
  }

  public String getDate() {
    return date;
  }

  public long getFrom() {
    return from;
  }

  public void setFrom(long from) {
    this.from = from;
  }

  public long getTo() {
    return to;
  }

  public void setTo(long to) {
    this.to = to;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = roundedAmount(amount);
  }

  public void setDate(String date) {
    this.date = date;
  }

  private double roundedAmount(double amount) {
    return Math.round(amount * 100.0) / 100.00;
  }

  private String dateToString(Date date) {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    return formatter.format(date);
  }

}

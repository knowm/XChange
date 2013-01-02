package com.xeiam.xchange.bitstamp.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Matija Mazi <br/>
 * @created 4/20/12 8:34 AM
 */
public class Transaction implements Serializable {

  private long date;
  private int tid;
  private double price;
  private double amount;

  public int getTid() {

    return tid;
  }

  public void setTid(int tid) {

    this.tid = tid;
  }

  public double getPrice() {

    return price;
  }

  public void setPrice(double price) {

    this.price = price;
  }

  public double getAmount() {

    return amount;
  }

  public void setAmount(double amount) {

    this.amount = amount;
  }

  public long getDate() {

    return date;
  }

  public void setDate(long date) {

    this.date = date;
  }

  public Date getTransactionDate() {

    return new Date(date * 1000);
  }

  public Double calculateFeeBtc() {

    return roundUp(amount * .5) / 100.;
  }

  private long roundUp(double x) {

    long n = (long) x;
    return x == n ? n : n + 1;
  }

  public Double calculateFeeUsd() {

    return calculateFeeBtc() * price;
  }

  @Override
  public String toString() {

    return String.format("Transaction{date=%s, tid=%d, price=%s, amount=%s}", getTransactionDate(), tid, price, amount);
  }
}

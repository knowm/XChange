package com.xeiam.xchange.bitstamp.api.model;

/**
 * @author Matija Mazi <br/>
 * @created 4/20/12 7:34 PM
 */
public class Order {

  private int id;
  private String datetime;
  /** 0 - buy (bid); 1 - sell (ask) */
  private int type;
  private double price;
  private double amount;

  public String getDatetime() {

    return datetime;
  }

  public void setDatetime(String datetime) {

    this.datetime = datetime;
  }

  public int getId() {

    return id;
  }

  public void setId(int id) {

    this.id = id;
  }

  public int getType() {

    return type;
  }

  public void setType(int type) {

    this.type = type;
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

  @Override
  public String toString() {

    return String.format("Order{id=%s, datetime=%s, type=%s, price=%s, amount=%s}", id, datetime, type, price, amount);
  }
}

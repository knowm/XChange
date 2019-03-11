package org.knowm.xchange.abucoins.dto;

import org.knowm.xchange.abucoins.dto.trade.AbucoinsOrder;

public class AbucoinsOrderRequest {
  AbucoinsOrder.Status status;
  String productID;

  /** all products any status. */
  public AbucoinsOrderRequest() {
    this(null, null);
  }

  /**
   * All products, just the specified <em>status</em>.
   *
   * @param status
   */
  public AbucoinsOrderRequest(AbucoinsOrder.Status status) {
    this(status, null);
  }

  /**
   * Orders for just the provided <em>productID</em> with any status.
   *
   * @param productID
   */
  public AbucoinsOrderRequest(String productID) {
    this(null, productID);
  }

  /**
   * Orders with the specified <em>status</em> for the specific <em>productID</em>
   *
   * @param status
   * @param productID
   */
  public AbucoinsOrderRequest(AbucoinsOrder.Status status, String productID) {
    this.status = status;
    this.productID = productID;
  }

  public AbucoinsOrder.Status getStatus() {
    return status;
  }

  public String getProductID() {
    return productID;
  }

  @Override
  public String toString() {
    return "AbucoinsOrdersRequest [side=" + status + ", productID=" + productID + "]";
  }
}

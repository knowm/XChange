package org.knowm.xchange.ripple.service.params;

public class RippleMarketDataParams {

  private String address = "";
  private String baseCounterparty = "";
  private String counterCounterparty = "";

  private Integer limit = null;

  public String getAddress() {
    return address;
  }

  public void setAddress(final String value) {
    address = value;
  }

  public String getBaseCounterparty() {
    return baseCounterparty;
  }

  public void setBaseCounterparty(final String value) {
    baseCounterparty = value;
  }

  public String getCounterCounterparty() {
    return counterCounterparty;
  }

  public void setCounterCounterparty(final String value) {
    counterCounterparty = value;
  }

  public String getLimit() {
    if (limit == null) {
      return null; // use the exchange default
    } else {
      return limit.toString();
    }
  }

  /**
   * @param value Max results per response. Cannot be less than 10, cannot be greater than 400,
   *     defaults to 200 at the exchange if not set.
   */
  public void setLimit(final int value) {
    limit = value;
  }
}

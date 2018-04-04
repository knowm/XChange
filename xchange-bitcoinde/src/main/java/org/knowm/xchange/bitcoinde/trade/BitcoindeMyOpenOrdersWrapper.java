package org.knowm.xchange.bitcoinde.trade;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** @author kaiserfr */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"orders", "page", "errors", "credits", "maintenance"})
public class BitcoindeMyOpenOrdersWrapper {

  @JsonProperty("orders")
  private List<BitcoindeMyOrder> orders = null;

  @JsonProperty("page")
  private BitcoindePage page;

  @JsonProperty("errors")
  private List<Object> errors = null;

  @JsonProperty("credits")
  private Integer credits;

  @JsonProperty("maintenance")
  private BitcoindeMaintenance maintenance;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /** No args constructor for use in serialization */
  public BitcoindeMyOpenOrdersWrapper() {}

  /**
   * @param errors
   * @param page
   * @param maintenance
   * @param credits
   * @param orders
   */
  public BitcoindeMyOpenOrdersWrapper(
      List<BitcoindeMyOrder> orders,
      BitcoindePage page,
      List<Object> errors,
      Integer credits,
      BitcoindeMaintenance maintenance) {
    super();
    this.orders = orders;
    this.page = page;
    this.errors = errors;
    this.credits = credits;
    this.maintenance = maintenance;
  }

  @JsonProperty("orders")
  public List<BitcoindeMyOrder> getOrders() {
    return orders;
  }

  @JsonProperty("orders")
  public void setOrders(List<BitcoindeMyOrder> orders) {
    this.orders = orders;
  }

  @JsonProperty("page")
  public BitcoindePage getPage() {
    return page;
  }

  @JsonProperty("page")
  public void setPage(BitcoindePage page) {
    this.page = page;
  }

  @JsonProperty("errors")
  public List<Object> getErrors() {
    return errors;
  }

  @JsonProperty("errors")
  public void setErrors(List<Object> errors) {
    this.errors = errors;
  }

  @JsonProperty("credits")
  public Integer getCredits() {
    return credits;
  }

  @JsonProperty("credits")
  public void setCredits(Integer credits) {
    this.credits = credits;
  }

  @JsonProperty("maintenance")
  public BitcoindeMaintenance getMaintenance() {
    return maintenance;
  }

  @JsonProperty("maintenance")
  public void setMaintenance(BitcoindeMaintenance maintenance) {
    this.maintenance = maintenance;
  }

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }
}

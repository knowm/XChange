package org.knowm.xchange.gdax.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GDAXCoinbaseAccountAddress {

  private final String resource;
  private final String network;
  private final boolean exchangeDepositAddress;
  private final String createDate;
  private final String updateDate;
  private final String resourcePath;
  private final String address;
  private final String callbackUrl;
  private final String id;
  private final String name;

  public GDAXCoinbaseAccountAddress(
      @JsonProperty("resource") String resource,
      @JsonProperty("network") String network,
      @JsonProperty("exchange_deposit_address") boolean exchangeDepositAddress,
      @JsonProperty("created_at") String createDate,
      @JsonProperty("updated_at") String updateDate,
      @JsonProperty("resource_path") String resourcePath,
      @JsonProperty("address") String address,
      @JsonProperty("callback_url") String callbackUrl,
      @JsonProperty("id") String id,
      @JsonProperty("name") String name) {
    super();
    this.resource = resource;
    this.network = network;
    this.exchangeDepositAddress = exchangeDepositAddress;
    this.createDate = createDate;
    this.updateDate = updateDate;
    this.resourcePath = resourcePath;
    this.address = address;
    this.callbackUrl = callbackUrl;
    this.id = id;
    this.name = name;
  }

  public String getResource() {
    return resource;
  }

  public String getNetwork() {
    return network;
  }

  public boolean isExchangeDepositAddress() {
    return exchangeDepositAddress;
  }

  public String getCreateDate() {
    return createDate;
  }

  public String getUpdateDate() {
    return updateDate;
  }

  public String getResourcePath() {
    return resourcePath;
  }

  public String getAddress() {
    return address;
  }

  public String getCallbackUrl() {
    return callbackUrl;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}

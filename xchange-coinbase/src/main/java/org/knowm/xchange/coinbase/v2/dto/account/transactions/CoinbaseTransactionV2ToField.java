package org.knowm.xchange.coinbase.v2.dto.account.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CoinbaseTransactionV2ToField {

  private final String addressUrl;
  private String resource;
  private String address;
  private String currency;
  private AddressInfo addressInfo;

  public CoinbaseTransactionV2ToField(
      @JsonProperty("resource") String resource,
      @JsonProperty("address") String address,
      @JsonProperty("currency") String currency,
      @JsonProperty("address_info") AddressInfo addressInfo,
      @JsonProperty("address_url") String addressUrl) {
    this.resource = resource;
    this.address = address;
    this.currency = currency;
    this.addressInfo = addressInfo;
    this.addressUrl = addressUrl;
  }

  @Override
  public String toString() {
    return "{"
        + "\"resource\":"
        + '\"'
        + resource
        + '\"'
        + ",\"address\":"
        + '\"'
        + address
        + '\"'
        + ",\"currency\":"
        + '\"'
        + currency
        + '\"'
        + ",\"addressInfo\":"
        + addressInfo
        + ",\"addressUrl\":"
        + '\"'
        + addressUrl
        + '\"'
        + '}';
  }

  @Getter
  public static class AddressInfo {
    private final String address;

    public AddressInfo(@JsonProperty("address") String address) {

      this.address = address;
    }

    @Override
    public String toString() {
      return "{" + "\"address\":" + '\"' + address + '\"' + "}";
    }
  }
}

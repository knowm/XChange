package org.knowm.xchange.bithumb.dto.account;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class BithumbTransaction {
  public static final String SEARCH_BUY = "1";
  public static final String SEARCH_SELL = "2";
  private final String search;
  private final long transferDate;
  private final String units;
  private final BigDecimal price;
  private final BigDecimal fee;
  private final BigDecimal krwRemain;
  private final Map<String, Object> additionalProperties = new HashMap<>();

  public BithumbTransaction(
      @JsonProperty("search") String search,
      @JsonProperty("transfer_date") long transferDate,
      @JsonProperty("units") String units,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("fee") BigDecimal fee,
      @JsonProperty("krw_remain") BigDecimal krwRemain) {
    this.search = search;
    this.transferDate = transferDate;
    this.units = units;
    this.price = price;
    this.fee = fee;
    this.krwRemain = krwRemain;
  }

  public String getSearch() {
    return search;
  }

  public long getTransferDate() {
    return transferDate;
  }

  public String getUnits() {
    return units;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getFee() {
    return fee;
  }

  public BigDecimal getKrwRemain() {
    return krwRemain;
  }

  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }

  @Override
  public String toString() {
    return "BithumbTransaction{"
        + "search='"
        + search
        + '\''
        + ", transferDate="
        + transferDate
        + ", units='"
        + units
        + '\''
        + ", price="
        + price
        + ", fee='"
        + fee
        + '\''
        + ", krwRemain="
        + krwRemain
        + ", additionalProperties="
        + additionalProperties
        + '}';
  }

  public boolean isBuyOrSell() {
    return StringUtils.equalsAny(search, SEARCH_BUY, SEARCH_SELL);
  }
}

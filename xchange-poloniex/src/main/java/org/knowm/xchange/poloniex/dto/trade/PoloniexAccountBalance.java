package org.knowm.xchange.poloniex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

public class PoloniexAccountBalance {

  private final Map<String, BigDecimal> exchange;
  private final Map<String, BigDecimal> margin;
  private final Map<String, BigDecimal> lending;

  public PoloniexAccountBalance(
      @JsonProperty("exchange") Map<String, BigDecimal> exchange,
      @JsonProperty("margin") Map<String, BigDecimal> margin,
      @JsonProperty("lending") Map<String, BigDecimal> lending) {
    this.exchange = exchange;
    this.margin = margin;
    this.lending = lending;
  }

  public Map<String, BigDecimal> getExchangeBalance() {
    return exchange != null ? Collections.unmodifiableMap(exchange) : Collections.emptyMap();
  }

  public Map<String, BigDecimal> getMarginBalance() {
    return margin != null ? Collections.unmodifiableMap(margin) : Collections.emptyMap();
  }

  public Map<String, BigDecimal> getLendingBalance() {
    return lending != null ? Collections.unmodifiableMap(lending) : Collections.emptyMap();
  }

  @Override
  public String toString() {
    return "PoloniexAvailableAccountBalance{"
        + "exchange="
        + exchange
        + ", margin="
        + margin
        + ", lending="
        + lending
        + '}';
  }

  public enum ACCOUNT {
    EXCHANGE("exchange"),
    MARGIN("margin"),
    LENDING("lending");

    private String name;

    ACCOUNT(String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return name;
    }
  }
}

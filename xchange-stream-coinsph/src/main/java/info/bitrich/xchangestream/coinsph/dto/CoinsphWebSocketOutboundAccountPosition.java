package info.bitrich.xchangestream.coinsph.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

/**
 * WebSocket account position message from Coins.ph. Example:
 * {"e":"outboundAccountPosition","E":1598464861000,"T":1598464861001,"B":[{"a":"BTC","f":"1.0","l":"0.0"}]}
 */
@Getter
@ToString
public class CoinsphWebSocketOutboundAccountPosition {

  @JsonProperty("e")
  private String eventType;

  @JsonProperty("E")
  private long eventTime;

  @JsonProperty("T")
  private long accountUpdateTime;

  @JsonProperty("B")
  private List<Balance> balances;

  public static class Balance {
    @JsonProperty("a")
    private String asset;

    @JsonProperty("f")
    private BigDecimal free;

    @JsonProperty("l")
    private BigDecimal locked;

    public String getAsset() {
      return asset;
    }

    public BigDecimal getFree() {
      return free;
    }

    public BigDecimal getLocked() {
      return locked;
    }
  }

  public String getEventType() {
    return eventType;
  }

  public long getEventTime() {
    return eventTime;
  }

  public long getAccountUpdateTime() {
    return accountUpdateTime;
  }

  public List<Balance> getBalances() {
    return balances;
  }
}

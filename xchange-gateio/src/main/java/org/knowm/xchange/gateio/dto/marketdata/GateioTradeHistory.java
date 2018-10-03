package org.knowm.xchange.gateio.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.gateio.dto.GateioBaseResponse;
import org.knowm.xchange.gateio.dto.GateioOrderType;

public class GateioTradeHistory extends GateioBaseResponse {

  private final List<GateioPublicTrade> trades;
  private final String elapsed;

  private GateioTradeHistory(
      @JsonProperty("data") List<GateioPublicTrade> trades,
      @JsonProperty("result") boolean result,
      @JsonProperty("elapsed") String elapsed) {

    super(result, null);
    this.trades = trades;
    this.elapsed = elapsed;
  }

  public List<GateioPublicTrade> getTrades() {

    return trades;
  }

  public String getElapsed() {

    return elapsed;
  }

  @Override
  public String toString() {

    return "BTERPublicTrades [trades=" + trades + ", elapsed=" + elapsed + "]";
  }

  public static class GateioPublicTrade {

    private final long date;
    private final BigDecimal price;
    private final BigDecimal amount;
    private final String tradeId;
    private final GateioOrderType type;

    private GateioPublicTrade(
        @JsonProperty("date") String date,
        @JsonProperty("rate") BigDecimal price,
        @JsonProperty("amount") BigDecimal amount,
        @JsonProperty("tradeID") String tradeId,
        @JsonProperty("timestamp") long timestamp,
        @JsonProperty("type") GateioOrderType type) {

      this.date = timestamp;
      this.price = price;
      this.amount = amount;
      this.tradeId = tradeId;
      this.type = type;
    }

    public long getDate() {

      return date;
    }

    public BigDecimal getPrice() {

      return price;
    }

    public BigDecimal getAmount() {

      return amount;
    }

    public String getTradeId() {

      return tradeId;
    }

    public GateioOrderType getType() {

      return type;
    }

    @Override
    public String toString() {

      return "BTERPublicTrade [date="
          + date
          + ", price="
          + price
          + ", amount="
          + amount
          + ", tradeId="
          + tradeId
          + ", type="
          + type
          + "]";
    }
  }
}

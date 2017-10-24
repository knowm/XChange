package org.knowm.xchange.bter.dto.marketdata;

import java.math.BigDecimal;
import java.util.List;

import org.knowm.xchange.bter.dto.BTERBaseResponse;
import org.knowm.xchange.bter.dto.BTEROrderType;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BTERTradeHistory extends BTERBaseResponse {

  private final List<BTERPublicTrade> trades;
  private final String elapsed;

  private BTERTradeHistory(@JsonProperty("data") List<BTERPublicTrade> trades, @JsonProperty("result") boolean result,
      @JsonProperty("elapsed") String elapsed) {

    super(result, null);
    this.trades = trades;
    this.elapsed = elapsed;
  }

  public List<BTERPublicTrade> getTrades() {

    return trades;
  }

  public String getElapsed() {

    return elapsed;
  }

  @Override
  public String toString() {

    return "BTERPublicTrades [trades=" + trades + ", elapsed=" + elapsed + "]";
  }

  public static class BTERPublicTrade {

    private final long date;
    private final BigDecimal price;
    private final BigDecimal amount;
    private final String tradeId;
    private final BTEROrderType type;

    private BTERPublicTrade(@JsonProperty("date") long date, @JsonProperty("price") BigDecimal price, @JsonProperty("amount") BigDecimal amount,
        @JsonProperty("tid") String tradeId, @JsonProperty("type") BTEROrderType type) {

      this.date = date;
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

    public BTEROrderType getType() {

      return type;
    }

    @Override
    public String toString() {

      return "BTERPublicTrade [date=" + date + ", price=" + price + ", amount=" + amount + ", tradeId=" + tradeId + ", type=" + type + "]";
    }
  }
}

package org.knowm.xchange.gateio.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.gateio.GateioAdapters;
import org.knowm.xchange.gateio.dto.GateioBaseResponse;
import org.knowm.xchange.gateio.dto.GateioOrderType;

/** Created by David Henry on 2/19/14. */
public class GateioOrderStatus extends GateioBaseResponse {

  private final BTEROrderStatusInfo orderStatusInfo;

  private GateioOrderStatus(
      @JsonProperty("order") BTEROrderStatusInfo orderStatusInfo,
      @JsonProperty("result") boolean result,
      @JsonProperty("msg") String msg) {

    super(result, msg);
    this.orderStatusInfo = orderStatusInfo;
  }

  public String getId() {

    return orderStatusInfo.getId();
  }

  public String getStatus() {

    return orderStatusInfo.getStatus();
  }

  public CurrencyPair getCurrencyPair() {

    return orderStatusInfo.getCurrencyPair();
  }

  public GateioOrderType getType() {

    return orderStatusInfo.getType();
  }

  public BigDecimal getRate() {

    return orderStatusInfo.getRate();
  }

  public BigDecimal getAmount() {

    return orderStatusInfo.getAmount();
  }

  public BigDecimal getInitialRate() {

    return orderStatusInfo.getInitialRate();
  }

  public BigDecimal getInitialAmount() {

    return orderStatusInfo.getInitialAmount();
  }

  public String toString() {

    return orderStatusInfo.toString();
  }

  public static class BTEROrderStatusInfo {

    private final String id;
    private final String status;
    private final CurrencyPair currencyPair;
    private final GateioOrderType type;
    private final BigDecimal rate;
    private final BigDecimal amount;
    private final BigDecimal initialRate;
    private final BigDecimal initialAmount;

    private BTEROrderStatusInfo(
        @JsonProperty("id") String id,
        @JsonProperty("status") String status,
        @JsonProperty("pair") String currencyPair,
        @JsonProperty("type") GateioOrderType type,
        @JsonProperty("rate") BigDecimal rate,
        @JsonProperty("amount") BigDecimal amount,
        @JsonProperty("initial_rate") BigDecimal initialRate,
        @JsonProperty("initial_amount") BigDecimal initialAmount) {

      this.id = id;
      this.status = status;
      this.currencyPair = GateioAdapters.adaptCurrencyPair(currencyPair);
      this.type = type;
      this.rate = rate;
      this.amount = amount;
      this.initialRate = initialRate;
      this.initialAmount = initialAmount;
    }

    public String getId() {

      return id;
    }

    public String getStatus() {

      return status;
    }

    public CurrencyPair getCurrencyPair() {

      return currencyPair;
    }

    public GateioOrderType getType() {

      return type;
    }

    public BigDecimal getRate() {

      return rate;
    }

    public BigDecimal getAmount() {

      return amount;
    }

    public BigDecimal getInitialRate() {

      return initialRate;
    }

    public BigDecimal getInitialAmount() {

      return initialAmount;
    }

    @Override
    public String toString() {

      return "BTEROrderStatusInfo [id="
          + id
          + ", status="
          + status
          + ", currencyPair="
          + currencyPair
          + ", type="
          + type
          + ", rate="
          + rate
          + ", amount="
          + amount
          + ", initialRate="
          + initialRate
          + ", initialAmount="
          + initialAmount
          + "]";
    }
  }
}

package org.knowm.xchange.bybit.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Getter
@ToString
@Builder
@Jacksonized
public class BybitTransactionLogResponse {

  @JsonProperty("list")
  private List<BybitTransactionLog> list;

  @JsonProperty("nextPageCursor")
  private String nextPageCursor;

  @Getter
  @ToString
  @Builder
  @Jacksonized
  public static class BybitTransactionLog {

    @JsonProperty("id")
    private String id;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("category")
    private String category;

    @JsonProperty("side")
    private String side;

    @JsonProperty("transactionTime")
    private Date transactionTime;

    @JsonProperty("type")
    private BybitTransactionLogType type;

    @JsonProperty("qty")
    private BigDecimal qty;

    @JsonProperty("size")
    private BigDecimal size;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("tradePrice")
    private BigDecimal tradePrice;

    @JsonProperty("funding")
    private BigDecimal funding;

    @JsonProperty("fee")
    private BigDecimal fee;

    @JsonProperty("cashFlow")
    private BigDecimal cashFlow;

    @JsonProperty("change")
    private BigDecimal change;

    @JsonProperty("cashBalance")
    private BigDecimal cashBalance;

    @JsonProperty("feeRate")
    private BigDecimal feeRate;

    @JsonProperty("bonusChange")
    private BigDecimal bonusChange;

    @JsonProperty("tradeId")
    private String tradeId;

    @JsonProperty("orderId")
    private String orderId;

    @JsonProperty("orderLinkId")
    private String orderLinkId; //userReference

    public enum BybitTransactionLogType {
      TRANSFER_IN,
      TRANSFER_OUT,
      TRADE,
      SETTLEMENT,
      DELIVERY,
      LIQUIDATION,
      BONUS,
      FEE_REFUND,
      INTEREST,
      CURRENCY_BUY,
      CURRENCY_SELL,
      BORROWED_AMOUNT_INS_LOAN,
      PRINCIPLE_REPAYMENT_INS_LOAN,
      INTEREST_REPAYMENT_INS_LOAN,
      AUTO_SOLD_COLLATERAL_INS_LOAN,
      AUTO_BUY_LIABILITY_INS_LOAN,
      AUTO_PRINCIPLE_REPAYMENT_INS_LOAN,
      AUTO_INTEREST_REPAYMENT_INS_LOAN,
      TRANSFER_IN_INS_LOAN, //Transfer In when in the liquidation of OTC loan
      TRANSFER_OUT_INS_LOAN, //Transfer Out when in the liquidation of OTC loan
      SPOT_REPAYMENT_SELL, //One-click repayment currency sell
      SPOT_REPAYMENT_BUY //One-click repayment currency buy
    }
  }
}

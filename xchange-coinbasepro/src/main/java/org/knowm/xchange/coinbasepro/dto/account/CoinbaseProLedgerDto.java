package org.knowm.xchange.coinbasepro.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.coinbasepro.CoinbaseProAdapters;
import org.knowm.xchange.instrument.Instrument;

@ToString
@Getter
public class CoinbaseProLedgerDto {

  private final String id;

  private final BigDecimal amount;

  private final Date createdAt;

  private final BigDecimal balance;

  private final CoinbaseProLedgerTxType type;

  private final CoinbaseProLedgerDetails details;

  public CoinbaseProLedgerDto(
      @JsonProperty("id") String id,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("created_at") Date createdAt,
      @JsonProperty("balance") BigDecimal balance,
      @JsonProperty("type") CoinbaseProLedgerTxType type,
      @JsonProperty("details") CoinbaseProLedgerDetails details) {
    this.id = id;
    this.amount = amount;
    this.createdAt = createdAt;
    this.balance = balance;
    this.type = type;
    this.details = details;
  }

  @Getter
  @ToString
  public static class CoinbaseProLedgerDetails{

    private final String orderId;

    private final Instrument productId;

    private final String tradeId;

    public CoinbaseProLedgerDetails(
        @JsonProperty("order_id") String orderId,
        @JsonProperty("product_id") String productId,
        @JsonProperty("trade_id") String tradeId) {
      this.orderId = orderId;
      this.productId = (productId == null) ? null : CoinbaseProAdapters.toCurrencyPair(productId);
      this.tradeId = tradeId;
    }
  }
  public enum CoinbaseProLedgerTxType{
    transfer,
    match,
    fee,
    conversion,
    margin_interest,
    rebate,
    otc_fee,
    otc_match,
    tax_credit,
    rfq_match,
    rfq_fee,
    match_conversion,
    stake_wrap

  }
}

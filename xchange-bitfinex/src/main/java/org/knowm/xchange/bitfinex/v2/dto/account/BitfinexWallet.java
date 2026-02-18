package org.knowm.xchange.bitfinex.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bitfinex.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.currency.Currency;

@Data
@Builder
@Jacksonized
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class BitfinexWallet {

  /** Wallet name (exchange, margin, funding) */
  private Type walletType;

  /** Currency (e.g. USD, ...) */
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency currency;

  /** Wallet balance */
  private BigDecimal balance;

  /** Unsettled interest */
  private BigDecimal unsettledInterest;

  /** Wallet balance available for orders/withdrawal/transfer */
  private BigDecimal availableBalance;

  /** Description of the last ledger entry */
  private String lastChange;

  /** If the last change was a trade, this object will show the trade details */
  private Object tradeDetails;

  public enum Type {
    @JsonEnumDefaultValue
    @JsonProperty("exchange")
    EXCHANGE,

    @JsonProperty("margin")
    MARGIN,

    @JsonProperty("funding")
    FUNDING,

    @JsonProperty("contribution")
    CONTRIBUTION
  }
}

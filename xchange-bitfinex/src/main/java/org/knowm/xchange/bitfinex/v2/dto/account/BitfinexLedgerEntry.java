package org.knowm.xchange.bitfinex.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Data;
import org.knowm.xchange.bitfinex.config.converter.StringToCurrencyConverter;
import org.knowm.xchange.currency.Currency;

/** https://docs.bitfinex.com/reference#rest-auth-ledgers */
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@Data
public class BitfinexLedgerEntry {

  /** Ledger identifier */
  private long id;

  /** The symbol of the currency (ex. "BTC") */
  @JsonDeserialize(converter = StringToCurrencyConverter.class)
  private Currency currency;

  /** Wallet name (exchange, margin, funding) */
  private BitfinexWallet.Type walletType;

  /** Timestamp in milliseconds */
  private Instant timestamp;

  private Object placeHolder1;

  /** Amount of funds moved */
  private BigDecimal amount;

  /** New balance */
  private BigDecimal balance;

  private Object placeHolder2;

  /** Description of ledger transaction */
  private String description;
}

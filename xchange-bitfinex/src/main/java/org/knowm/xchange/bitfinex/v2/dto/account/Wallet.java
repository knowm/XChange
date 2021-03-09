package org.knowm.xchange.bitfinex.v2.dto.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

/** https://docs.bitfinex.com/reference#rest-auth-wallets */
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class Wallet {

  /** Wallet name (exchange, margin, funding) */
  private String walletType;
  /** Currency (e.g. USD, ...) */
  private String currency;
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
}

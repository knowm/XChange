package org.knowm.xchange.coinbase.dto.marketdata;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import org.knowm.xchange.coinbase.dto.serialization.CoinbaseMoneyDeserializer;

/** @author jamespedwards42 */
@JsonDeserialize(using = CoinbaseMoneyDeserializer.class)
public class CoinbaseMoney {

  private final String currency;
  private final BigDecimal amount;

  public CoinbaseMoney(String currency, final BigDecimal amount) {

    this.currency = currency;
    this.amount = amount;
  }

  public String getCurrency() {

    return currency;
  }

  public BigDecimal getAmount() {

    return amount;
  }

  @Override
  public String toString() {

    return "CoinbaseMoney [currency=" + currency + ", amount=" + amount + "]";
  }
}

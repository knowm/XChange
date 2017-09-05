package org.knowm.xchange.coinfloor.dto.trade;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CoinfloorUserTransaction {
  public String datetime = "";

  public long id = 0;

  @JsonProperty("order_id")
  public long orderId = 0;

  public TransactionType type = TransactionType.UNKNOWN;

  public BigDecimal fee = BigDecimal.ZERO;

  // amounts: for TRADE transactions, negative -> BID, positive -> ASK
  // for other transactions, negative -> WITHDRAWAL, positive -> DEPOSIT
  @JsonProperty("xbt")
  public BigDecimal btc = BigDecimal.ZERO;

  public BigDecimal gbp = BigDecimal.ZERO;

  public BigDecimal usd = BigDecimal.ZERO;

  public BigDecimal eur = BigDecimal.ZERO;

  public BigDecimal eth = BigDecimal.ZERO;

  public BigDecimal ltc = BigDecimal.ZERO;

  public BigDecimal bch = BigDecimal.ZERO;

  public BigDecimal xrp = BigDecimal.ZERO;

  // prices
  @JsonProperty("xbt_gbp")
  public BigDecimal btc_gbp = BigDecimal.ZERO;

  @JsonProperty("xbt_usd")
  public BigDecimal btc_usd = BigDecimal.ZERO;

  @JsonProperty("xbt_eur")
  public BigDecimal btc_eur = BigDecimal.ZERO;

  @JsonProperty("bch_gbp")
  public BigDecimal bch_gbp = BigDecimal.ZERO;

  @JsonProperty("eth_gbp")
  public BigDecimal eth_gbp = BigDecimal.ZERO;

  @JsonProperty("xrp_gbp")
  public BigDecimal xrp_gbp = BigDecimal.ZERO;

  @JsonProperty("ltc_gbp")
  public BigDecimal ltc_gbp = BigDecimal.ZERO;

  public CurrencyPair getCurrencyPair() {
    if (isTrade()) {
      if (!Objects.equals(btc_gbp, BigDecimal.ZERO)) {
        return CurrencyPair.BTC_GBP;
      } else if (!Objects.equals(btc_usd, BigDecimal.ZERO)) {
        return CurrencyPair.BTC_USD;
      } else if (!Objects.equals(btc_eur, BigDecimal.ZERO)) {
        return CurrencyPair.BTC_EUR;
      } else if (!Objects.equals(bch_gbp, BigDecimal.ZERO)) {
        return CurrencyPair.BCH_GBP;
      } else if (!Objects.equals(eth_gbp, BigDecimal.ZERO)) {
        return CurrencyPair.ETH_GBP;
      } else if (!Objects.equals(xrp_gbp, BigDecimal.ZERO)) {
        return CurrencyPair.XRP_GBP;
      } else if (!Objects.equals(ltc_gbp, BigDecimal.ZERO)) {
        return CurrencyPair.LTC_GBP;
      }
    }
    return null; // not a trade or an unsupported currency pair
  }

  public BigDecimal getPrice() {
    if (isTrade()) {
      if (!Objects.equals(btc_gbp, BigDecimal.ZERO)) {
        return btc_gbp;
      } else if (!Objects.equals(btc_usd, BigDecimal.ZERO)) {
        return btc_usd;
      } else if (!Objects.equals(btc_eur, BigDecimal.ZERO)) {
        return btc_eur;
      } else if (!Objects.equals(bch_gbp, BigDecimal.ZERO)) {
        return bch_gbp;
      } else if (!Objects.equals(eth_gbp, BigDecimal.ZERO)) {
        return eth_gbp;
      } else if (!Objects.equals(xrp_gbp, BigDecimal.ZERO)) {
        return xrp_gbp;
      } else if (!Objects.equals(ltc_gbp, BigDecimal.ZERO)) {
        return ltc_gbp;
      }
    }
    return BigDecimal.ZERO; // not a trade or an unsupported currency pair
  }

  public Currency getCurrency() {
    if (isTrade()) {
      return null;
    } else if (btc.signum() != 0) {
      return Currency.BTC;
    } else if (gbp.signum() != 0) {
      return Currency.GBP;
    } else if (usd.signum() != 0) {
      return Currency.USD;
    } else if (eur.signum() != 0) {
      return Currency.EUR;
    } else if (ltc.signum() != 0) {
      return Currency.LTC;
    } else if (eth.signum() != 0) {
      return Currency.ETH;
    } else if (bch.signum() != 0) {
      return Currency.BCH;
    } else if (xrp.signum() != 0) {
      return Currency.XRP;
    } else {
      return null;
    }
  }

  public BigDecimal getAmount() {
    if (isTrade()) {
      if (getCurrencyPair().base == Currency.BTC) {
        return btc;
      } else if (getCurrencyPair().base == Currency.XRP) {
        return xrp;
      } else if (getCurrencyPair().base == Currency.LTC) {
        return ltc;
      } else if (getCurrencyPair().base == Currency.ETH) {
        return eth;
      } else if (getCurrencyPair().base == Currency.BCH) {
        return bch;
      } else {
        return BigDecimal.ZERO;
      }
    } else if (btc.signum() != 0) {
      return btc;
    } else if (bch.signum() != 0) {
      return bch;
    } else if (xrp.signum() != 0) {
      return xrp;
    } else if (ltc.signum() != 0) {
      return ltc;
    } else if (eth.signum() != 0) {
      return eth;
    } else if (gbp.signum() != 0) {
      return gbp;
    } else if (usd.signum() != 0) {
      return usd;
    } else if (eur.signum() != 0) {
      return eur;
    } else {
      return BigDecimal.ZERO; // an unsupported currency
    }
  }

  public OrderType getSide() {
    if (isTrade()) {
      switch (getAmount().signum()) {
        case 0:
        default:
          return null; // deposit or withdrawal
        case 1:
          return OrderType.BID;
        case -1:
          return OrderType.ASK;
      }
    } else {
      return null;
    }
  }

  public boolean isDeposit() {
    return type == TransactionType.DEPOSIT;
  }

  public boolean isWithdrawal() {
    return type == TransactionType.WITHDRAWAL;
  }

  public boolean isTrade() {
    return type == TransactionType.TRADE;
  }

  public TransactionType getType() {
    return type;
  }

  public String getDateTime() {
    return datetime;
  }

  public long getId() {
    return id;
  }

  public long getOrderId() {
    return orderId;
  }

  public BigDecimal getFee() {
    return fee;
  }

  @Override
  public String toString() {
    if (isTrade()) {
      return String.format("CoinfloorUserTransaction{datetime=%s, id=%d, orderId=%d, type=%s, currencyPair=%s side=%s amount=%s price=%s fee=%s}",
          datetime, id, orderId, type, getCurrencyPair(), getSide(), getAmount(), getPrice(), fee);
    } else {
      return String.format("CoinfloorUserTransaction{datetime=%s, id=%d, type=%s, currency=%s amount=%s }", datetime, id, type, getCurrency(),
          getAmount());
    }
  }

  public enum TransactionType {
    DEPOSIT, WITHDRAWAL, TRADE, UNKNOWN
  }

  public static class CoinfloorTransactionTypeDeserializer extends JsonDeserializer<TransactionType> {

    @Override
    public TransactionType deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
      switch (jp.getValueAsInt()) {
        case 0:
          return TransactionType.DEPOSIT;
        case 1:
          return TransactionType.WITHDRAWAL;
        case 2:
          return TransactionType.TRADE;
        default:
          return TransactionType.UNKNOWN;
      }
    }
  }
}

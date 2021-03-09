package org.knowm.xchange.bitcoinde.v4.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Value;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeAccountLedgerType;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeAccountLedger {

  Date date;
  BitcoindeAccountLedgerType type;
  String reference;
  BigDecimal cashflow;
  BigDecimal balance;
  BitcoindeAccountLedgerTrade trade;

  @JsonCreator
  public BitcoindeAccountLedger(
      @JsonProperty("date") Date date,
      @JsonProperty("type") BitcoindeAccountLedgerType type,
      @JsonProperty("reference") String reference,
      @JsonProperty("cashflow") BigDecimal cashflow,
      @JsonProperty("balance") BigDecimal balance,
      @JsonProperty("trade") BitcoindeAccountLedgerTrade trade) {
    this.date = date;
    this.type = type;
    this.reference = reference;
    this.cashflow = cashflow;
    this.balance = balance;
    this.trade = trade;
  }

  @Value
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class BitcoindeAccountLedgerTrade {

    String tradeId;
    CurrencyPair tradingPair;
    BigDecimal price;
    Boolean isExternalWalletTrade;
    BitcoindeAccountLedgerTradingAmount currencyToTrade;
    BitcoindeAccountLedgerTradingAmount currencyToPay;

    @JsonCreator
    public BitcoindeAccountLedgerTrade(
        @JsonProperty("trade_id") String tradeId,
        @JsonProperty("trading_pair") @JsonDeserialize(using = CurrencyPairDeserializer.class)
            CurrencyPair tradingPair,
        @JsonProperty("price") BigDecimal price,
        @JsonProperty("is_external_wallet_trade") Boolean isExternalWalletTrade,
        @JsonProperty("currency_to_trade") BitcoindeAccountLedgerTradingAmount currencyToTrade,
        @JsonProperty("currency_to_pay") BitcoindeAccountLedgerTradingAmount currencyToPay) {
      this.tradeId = tradeId;
      this.tradingPair = tradingPair;
      this.price = price;
      this.isExternalWalletTrade = isExternalWalletTrade;
      this.currencyToTrade = currencyToTrade;
      this.currencyToPay = currencyToPay;
    }
  }

  @Value
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class BitcoindeAccountLedgerTradingAmount {

    Currency currency;
    BigDecimal beforeFee;
    BigDecimal afterFee;

    @JsonCreator
    public BitcoindeAccountLedgerTradingAmount(
        @JsonProperty("currency") Currency currency,
        @JsonProperty("before_fee") BigDecimal beforeFee,
        @JsonProperty("after_fee") BigDecimal afterFee) {
      this.currency = currency;
      this.beforeFee = beforeFee;
      this.afterFee = afterFee;
    }
  }
}

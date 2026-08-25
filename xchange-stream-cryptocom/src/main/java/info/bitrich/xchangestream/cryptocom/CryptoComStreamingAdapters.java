package info.bitrich.xchangestream.cryptocom;

import info.bitrich.xchangestream.cryptocom.dto.CryptoComUserTradeUpdate;
import java.util.Date;
import org.knowm.xchange.cryptocom.CryptoComAdapters;
import org.knowm.xchange.cryptocom.dto.account.CryptoComBalance;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.trade.UserTrade;

public final class CryptoComStreamingAdapters {

  private CryptoComStreamingAdapters() {}

  public static UserTrade adaptUserTrade(CryptoComUserTradeUpdate update) {
    CurrencyPair pair = CryptoComAdapters.toCurrencyPair(update.getInstrumentName());
    return UserTrade.builder()
        .instrument(pair)
        .id(update.getTradeId())
        .orderId(update.getOrderId())
        .type(CryptoComAdapters.adaptOrderType(update.getSide()))
        .price(CryptoComAdapters.toBigDecimal(update.getPrice()))
        .originalAmount(CryptoComAdapters.toBigDecimal(update.getQuantity()))
        .feeAmount(CryptoComAdapters.toBigDecimal(update.getFee()))
        .feeCurrency(update.getFeeCurrency() == null ? null : new Currency(update.getFeeCurrency()))
        .timestamp(update.getCreateTime() == null ? null : new Date(update.getCreateTime()))
        .build();
  }

  public static Balance adaptBalance(CryptoComBalance.PositionBalance position) {
    return new Balance(
        new Currency(position.getInstrumentName()),
        CryptoComAdapters.toBigDecimal(position.getQuantity()));
  }
}

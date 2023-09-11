package org.knowm.xchange.bybit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.account.BybitBalance;
import org.knowm.xchange.bybit.dto.marketdata.BybitInstrumentInfo;
import org.knowm.xchange.bybit.dto.marketdata.BybitOrderBook;
import org.knowm.xchange.bybit.dto.marketdata.BybitTicker;
import org.knowm.xchange.bybit.dto.trade.BybitOrderDetails;
import org.knowm.xchange.bybit.service.BybitException;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.instrument.Instrument;

@UtilityClass
public class BybitAdapters {

  public static final List<String> QUOTE_CURRENCIES = Arrays.asList("USDT", "USDC", "BTC", "DAI");

  public static Wallet adaptBybitBalances(List<BybitBalance> bybitBalances) {
    List<Balance> balances = new ArrayList<>(bybitBalances.size());
    for (BybitBalance bybitBalance : bybitBalances) {
      balances.add(new Balance(new Currency(bybitBalance.getCoin()),
              new BigDecimal(bybitBalance.getTotal()),
              new BigDecimal(bybitBalance.getFree())
      ));
    }
    return Wallet.Builder.from(balances).build();
  }

  public static String getSideString(Order.OrderType type) {
    if (type == Order.OrderType.ASK)
      return "Sell";
    if (type == Order.OrderType.BID)
      return "Buy";
    throw new IllegalArgumentException("invalid order type");
  }

  public static Order.OrderType getOrderType(String side) {
    if ("sell".equalsIgnoreCase(side)) {
      return Order.OrderType.ASK;
    }
    if ("buy".equalsIgnoreCase(side)) {
      return Order.OrderType.BID;
    }
    throw new IllegalArgumentException("invalid order type");
  }

  public static String convertToBybitSymbol(String instrumentName) {
    return instrumentName.replace("/", "").toUpperCase();
  }

  public static CurrencyPair guessSymbol(String symbol) {
    for (String quoteCurrency : QUOTE_CURRENCIES) {
      if (symbol.endsWith(quoteCurrency)) {
        int splitIndex = symbol.lastIndexOf(quoteCurrency);
        return new CurrencyPair(symbol.substring(0, splitIndex), symbol.substring(splitIndex));
      }
    }
    int splitIndex = symbol.length() - 3;
    return new CurrencyPair(symbol.substring(0, splitIndex), symbol.substring(splitIndex));

  }

  public static LimitOrder adaptBybitOrderDetails(BybitOrderDetails bybitOrderResult) {
    LimitOrder limitOrder = new LimitOrder(
            getOrderType(bybitOrderResult.getSide()),
            new BigDecimal(bybitOrderResult.getOrigQty()),
            new BigDecimal(bybitOrderResult.getExecutedQty()),
            guessSymbol(bybitOrderResult.getSymbol()),
            bybitOrderResult.getOrderId(),
            new Date(Long.parseLong(bybitOrderResult.getTime())),
            new BigDecimal(bybitOrderResult.getPrice())) {
    };
    BigDecimal averagePrice = new BigDecimal(bybitOrderResult.getAvgPrice());
    limitOrder.setAveragePrice(averagePrice);
    limitOrder.setOrderStatus(Order.OrderStatus.valueOf(bybitOrderResult.getStatus()));
    return limitOrder;
  }

  public static <T> BybitException createBybitExceptionFromResult(BybitResult<T> walletBalances) {
    return BybitException.builder()
        .retCode(walletBalances.getRetCode())
        .retMsg(walletBalances.getRetMsg())
        .extInfo(walletBalances.getExtInfo())
//        .result(walletBalances.getResult())
        .timestamp(walletBalances.getTimestamp())
        .build();
  }


  public static String toSymbol(Instrument instrument) {
    if (instrument == null) {
      return null;
    }
    else {
      return instrument.getBase().getCurrencyCode() + instrument.getCounter().getCurrencyCode();
    }
  }

  public Ticker toTicker(BybitTicker gateioTicker, Instrument instrument) {
    return new Ticker.Builder()
        .instrument(instrument)
        .last(gateioTicker.getLastPrice())
        .bid(gateioTicker.getBestBidPrice())
        .ask(gateioTicker.getBestAskPrice())
        .high(gateioTicker.getHighPrice())
        .low(gateioTicker.getLowPrice())
        .volume(gateioTicker.getVolume24h())
        .quoteVolume(gateioTicker.getTurnover24h())
        .percentageChange(gateioTicker.getPrice24hPercentageChange())
        .build();
  }


  public static InstrumentMetaData toInstrumentMetaData(BybitInstrumentInfo bybitInstrumentInfo) {
    return new InstrumentMetaData.Builder()
        .minimumAmount(bybitInstrumentInfo.getLotSizeFilter().getMinOrderQty())
        .maximumAmount(bybitInstrumentInfo.getLotSizeFilter().getMaxOrderQty())
        .counterMinimumAmount(bybitInstrumentInfo.getLotSizeFilter().getMinOrderAmt())
        .counterMaximumAmount(bybitInstrumentInfo.getLotSizeFilter().getMaxOrderAmt())
        .priceScale(bybitInstrumentInfo.getPriceFilter().getTickSize().scale())
        .volumeScale(bybitInstrumentInfo.getLotSizeFilter().getBasePrecision().scale())
        .amountStepSize(bybitInstrumentInfo.getLotSizeFilter().getBasePrecision())
        .priceStepSize(bybitInstrumentInfo.getPriceFilter().getTickSize())
        .build();
  }


  public OrderBook toOrderBook(BybitOrderBook bybitOrderBook, Instrument instrument) {
    List<LimitOrder> asks = bybitOrderBook.getAsks().stream()
        .map(priceSizeEntry -> new LimitOrder(OrderType.ASK, priceSizeEntry.getSize(), instrument, null, null, priceSizeEntry.getPrice()))
        .collect(Collectors.toList());

    List<LimitOrder> bids = bybitOrderBook.getBids().stream()
        .map(priceSizeEntry -> new LimitOrder(OrderType.BID, priceSizeEntry.getSize(), instrument, null, null, priceSizeEntry.getPrice()))
        .collect(Collectors.toList());

    return new OrderBook(Date.from(bybitOrderBook.getTimestamp()), asks, bids);
  }

}

package org.knowm.xchange.coinex;

import java.math.MathContext;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import org.knowm.xchange.coinex.dto.account.CoinexBalanceInfo;
import org.knowm.xchange.coinex.dto.account.CoinexMarketType;
import org.knowm.xchange.coinex.dto.account.CoinexOrder;
import org.knowm.xchange.coinex.dto.marketdata.CoinexCurrencyPairInfo;
import org.knowm.xchange.coinex.dto.marketdata.CoinexMarketDepth;
import org.knowm.xchange.coinex.dto.marketdata.CoinexTickerV1;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Ticker.Builder;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.instrument.Instrument;

@UtilityClass
public class CoinexAdapters {

  private final Map<String, CurrencyPair> SYMBOL_TO_CURRENCY_PAIR = new HashMap<>();

  public String instrumentsToString(Collection<Instrument> instruments) {
    if (instruments == null || instruments.isEmpty()) {
      return null;
    } else {
      return instruments.stream().map(CoinexAdapters::toString).collect(Collectors.joining(","));
    }
  }

  public void putSymbolMapping(String symbol, CurrencyPair currencyPair) {
    SYMBOL_TO_CURRENCY_PAIR.put(symbol, currencyPair);
  }

  public Balance toBalance(CoinexBalanceInfo balance) {
    return new Balance.Builder()
        .currency(balance.getCurrency())
        .available(balance.getAvailable())
        .frozen(balance.getFrozen())
        .build();
  }

  public CoinexOrder toCoinexOrder(MarketOrder marketOrder) {
    return CoinexOrder.builder()
        .currencyPair((CurrencyPair) marketOrder.getInstrument())
        .marketType(CoinexMarketType.SPOT)
        .side(marketOrder.getType())
        .type("market")
        .clientId(marketOrder.getUserReference())
        .amount(marketOrder.getOriginalAmount())
        .build();
  }

  public CurrencyPair toCurrencyPair(String symbol) {
    return SYMBOL_TO_CURRENCY_PAIR.get(symbol);
  }

  public InstrumentMetaData toInstrumentMetaData(CoinexCurrencyPairInfo coinexCurrencyPairInfo) {
    return new InstrumentMetaData.Builder()
        .tradingFee(coinexCurrencyPairInfo.getTakerFeeRate())
        .minimumAmount(coinexCurrencyPairInfo.getMinAssetAmount())
        .volumeScale(coinexCurrencyPairInfo.getBaseCurrencyPrecision())
        .priceScale(coinexCurrencyPairInfo.getQuoteCurrencyPrecision())
        .build();
  }

  public Order toOrder(CoinexOrder coinexOrder) {
    Order.Builder builder;
    Instrument instrument = coinexOrder.getCurrencyPair();
    OrderType orderType = coinexOrder.getSide();

    switch (coinexOrder.getType()) {
      case "market":
        builder = new MarketOrder.Builder(orderType, instrument);
        break;
      case "limit":
        builder = new LimitOrder.Builder(orderType, instrument).limitPrice(coinexOrder.getPrice());
        break;
      default:
        throw new IllegalArgumentException("Can't map " + coinexOrder.getType());
    }

    if (orderType == OrderType.BID) {
      // buy orders fill quote
      builder.cumulativeAmount(coinexOrder.getFilledQuoteAmount());
    } else if (orderType == OrderType.ASK) {
      // sell orders fill asset
      builder.cumulativeAmount(coinexOrder.getFilledAssetAmount());
    } else {
      throw new IllegalArgumentException("Can't map " + orderType);
    }

    // average price
    if (coinexOrder.getFilledAssetAmount() != null
        && coinexOrder.getFilledQuoteAmount() != null
        && coinexOrder.getFilledAssetAmount().signum() > 0) {
      builder.averagePrice(
          coinexOrder
              .getFilledQuoteAmount()
              .divide(coinexOrder.getFilledAssetAmount(), MathContext.DECIMAL32));
    }

    return builder
        .id(String.valueOf(coinexOrder.getOrderId()))
        .originalAmount(coinexOrder.getAmount())
        .userReference(coinexOrder.getClientId())
        .timestamp(Date.from(coinexOrder.getCreatedAt()))
        .orderStatus(coinexOrder.getStatus())
        .build();
  }

  public OrderBook toOrderBook(CoinexMarketDepth coinexMarketDepth) {
    List<LimitOrder> asks =
        coinexMarketDepth.getDepth().getAsks().stream()
            .map(
                priceSizeEntry ->
                    new LimitOrder(
                        OrderType.ASK,
                        priceSizeEntry.getSize(),
                        coinexMarketDepth.getCurrencyPair(),
                        null,
                        null,
                        priceSizeEntry.getPrice()))
            .collect(Collectors.toList());

    List<LimitOrder> bids =
        coinexMarketDepth.getDepth().getBids().stream()
            .map(
                priceSizeEntry ->
                    new LimitOrder(
                        OrderType.BID,
                        priceSizeEntry.getSize(),
                        coinexMarketDepth.getCurrencyPair(),
                        null,
                        null,
                        priceSizeEntry.getPrice()))
            .collect(Collectors.toList());

    return new OrderBook(Date.from(coinexMarketDepth.getDepth().getUpdatedAt()), asks, bids);
  }

  public String toString(Instrument instrument) {
    if (instrument == null) {
      return null;
    } else {
      return instrument.getBase().getCurrencyCode() + instrument.getCounter().getCurrencyCode();
    }
  }

  public Ticker toTicker(Instrument instrument, CoinexTickerV1 coinexTickerV1, Instant timestamp) {
    Builder builder = new Ticker.Builder();

    if (instrument != null) {
      builder.instrument(instrument);
    }

    builder
        .open(coinexTickerV1.getOpen24h())
        .last(coinexTickerV1.getLast())
        .bid(coinexTickerV1.getBestBidPrice())
        .ask(coinexTickerV1.getBestAskPrice())
        .high(coinexTickerV1.getHigh24h())
        .low(coinexTickerV1.getLow24h())
        .volume(coinexTickerV1.getVolume24h())
        .timestamp(Date.from(timestamp))
        .bidSize(coinexTickerV1.getBestBidSize())
        .askSize(coinexTickerV1.getBestAskSize())
        .percentageChange(coinexTickerV1.get24hPercentageChange());

    return builder.build();
  }

  public Ticker toTicker(String symbol, CoinexTickerV1 coinexTickerV1, Instant timestamp) {
    return toTicker(toCurrencyPair(symbol), coinexTickerV1, timestamp);
  }

  public Wallet toWallet(List<CoinexBalanceInfo> coinexBalanceInfos) {
    List<Balance> balances =
        coinexBalanceInfos.stream().map(CoinexAdapters::toBalance).collect(Collectors.toList());

    return Wallet.Builder.from(balances).id("spot").build();
  }
}

package org.knowm.xchange.bybit;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import lombok.SneakyThrows;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.account.allcoins.BybitAllCoinBalance;
import org.knowm.xchange.bybit.dto.account.allcoins.BybitAllCoinsBalance;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitCoinWalletBalance;
import org.knowm.xchange.bybit.dto.marketdata.instruments.BybitInstrumentInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.BybitInstrumentInfo.InstrumentStatus;
import org.knowm.xchange.bybit.dto.marketdata.instruments.linear.BybitLinearInverseInstrumentInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.option.BybitOptionInstrumentInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.spot.BybitSpotInstrumentInfo;
import org.knowm.xchange.bybit.dto.marketdata.tickers.BybitTicker;
import org.knowm.xchange.bybit.dto.marketdata.tickers.linear.BybitLinearInverseTicker;
import org.knowm.xchange.bybit.dto.marketdata.tickers.option.BybitOptionTicker;
import org.knowm.xchange.bybit.dto.marketdata.tickers.spot.BybitSpotTicker;
import org.knowm.xchange.bybit.dto.trade.BybitOrderStatus;
import org.knowm.xchange.bybit.dto.trade.BybitSide;
import org.knowm.xchange.bybit.dto.trade.details.BybitOrderDetail;
import org.knowm.xchange.bybit.service.BybitException;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.derivative.OptionsContract;
import org.knowm.xchange.derivative.OptionsContract.OptionType;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Ticker.Builder;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.instrument.Instrument;

public class BybitAdapters {

  public static final List<String> QUOTE_CURRENCIES =
      Arrays.asList("USDT", "USDC", "BTC", "DAI", "EUR", "ETH");

  public static final String FUTURES_CONTRACT_QUOTE_CURRENCY = "USDC";
  public static final String FUTURES_CONTRACT = "CONTRACT";

  public static final SimpleDateFormat OPTIONS_EXPIRED_DATE_PARSER = new SimpleDateFormat("ddMMMyy", Locale.ENGLISH);

  public static Wallet adaptBybitBalances(List<BybitCoinWalletBalance> coinWalletBalances) {
    List<Balance> balances = new ArrayList<>(coinWalletBalances.size());
    for (BybitCoinWalletBalance bybitCoinBalance : coinWalletBalances) {
      balances.add(
          new Balance(
              new Currency(bybitCoinBalance.getCoin()),
              new BigDecimal(bybitCoinBalance.getEquity()),
              new BigDecimal(bybitCoinBalance.getAvailableToWithdraw())));
    }
    return Wallet.Builder.from(balances).build();
  }

  public static Wallet adaptBybitBalances(BybitAllCoinsBalance allCoinsBalance) {
    List<Balance> balances = new ArrayList<>(allCoinsBalance.getBalance().size());
    for (BybitAllCoinBalance coinBalance : allCoinsBalance.getBalance()) {
      balances.add(
          new Balance(
              new Currency(coinBalance.getCoin()),
              coinBalance.getWalletBalance(),
              coinBalance.getTransferBalance()));
    }
    return Wallet.Builder.from(balances).build();
  }

  public static BybitSide getSideString(Order.OrderType type) {
    if (type == Order.OrderType.ASK) {
      return BybitSide.SELL;
    }
    if (type == Order.OrderType.BID) {
      return BybitSide.BUY;
    }
    throw new IllegalArgumentException("invalid order type");
  }

  public static Order.OrderType getOrderType(BybitSide side) {
    if ("sell".equalsIgnoreCase(side.name())) {
      return Order.OrderType.ASK;
    }
    if ("buy".equalsIgnoreCase(side.name())) {
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

  public static LimitOrder adaptBybitOrderDetails(BybitOrderDetail bybitOrderResult) {
    LimitOrder limitOrder =
        new LimitOrder(
            getOrderType(bybitOrderResult.getSide()),
            bybitOrderResult.getQty(),
            bybitOrderResult.getCumExecQty(),
            guessSymbol(bybitOrderResult.getSymbol()),
            bybitOrderResult.getOrderId(),
            bybitOrderResult.getCreatedTime(),
            bybitOrderResult.getPrice()) {};
    limitOrder.setAveragePrice(bybitOrderResult.getAvgPrice());
    limitOrder.setOrderStatus(adaptBybitOrderStatus(bybitOrderResult.getOrderStatus()));
    return limitOrder;
  }

  private static OrderStatus adaptBybitOrderStatus(BybitOrderStatus orderStatus) {
    switch (orderStatus) {
      case CREATED:
        return OrderStatus.OPEN;
      case NEW:
        return OrderStatus.NEW;
      case REJECTED:
        return OrderStatus.REJECTED;
      case PARTIALLY_FILLED:
      case ACTIVE:
        return OrderStatus.PARTIALLY_FILLED;
      case PARTIALLY_FILLED_CANCELED:
        return OrderStatus.PARTIALLY_CANCELED;
      case FILLED:
        return OrderStatus.FILLED;
      case CANCELLED:
        return OrderStatus.CANCELED;
      case UNTRIGGERED:
      case TRIGGERED:
        return OrderStatus.UNKNOWN;
      case DEACTIVATED:
        return OrderStatus.STOPPED;
      default:
        throw new IllegalStateException("Unexpected value: " + orderStatus);
    }
  }

  public static <T> BybitException createBybitExceptionFromResult(BybitResult<T> walletBalances) {
    return new BybitException(
        walletBalances.getRetCode(), walletBalances.getRetMsg(), walletBalances.getRetExtInfo());
  }

  public static Ticker adaptBybitLinearInverseTicker(
      Instrument instrument, Date time, BybitLinearInverseTicker bybitTicker) {
    return adaptBybitTickerBuilder(instrument, time, bybitTicker)
        .open(bybitTicker.getPrevPrice24h())
        .percentageChange(bybitTicker.getPrice24hPcnt())
        .build();
  }

  public static Ticker adaptBybitSpotTicker(
      Instrument instrument, Date time, BybitSpotTicker bybitTicker) {
    return adaptBybitTickerBuilder(instrument, time, bybitTicker)
        .open(bybitTicker.getPrevPrice24h())
        .percentageChange(bybitTicker.getPrice24hPcnt())
        .build();
  }

  public static Ticker adaptBybitOptionTicker(
      Instrument instrument, Date time, BybitOptionTicker bybitTicker) {
    return adaptBybitTickerBuilder(instrument, time, bybitTicker).build();
  }

  private static Builder adaptBybitTickerBuilder(
      Instrument instrument, Date time, BybitTicker bybitTicker) {
    return new Ticker.Builder()
        .timestamp(time)
        .instrument(instrument)
        .last(bybitTicker.getLastPrice())
        .bid(bybitTicker.getBid1Price())
        .bidSize(bybitTicker.getBid1Size())
        .ask(bybitTicker.getAsk1Price())
        .askSize(bybitTicker.getAsk1Size())
        .high(bybitTicker.getHighPrice24h())
        .low(bybitTicker.getLowPrice24h())
        .quoteVolume(bybitTicker.getTurnover24h())
        .volume(bybitTicker.getVolume24h());
  }

  public static Map<Instrument, InstrumentMetaData> adaptBybitInstruments(
      List<BybitInstrumentInfo> instrumentList) {
    Map<Instrument, InstrumentMetaData> map = new HashMap<>();

    instrumentList.forEach(
        info -> {
          if (info instanceof BybitSpotInstrumentInfo) {
            BybitSpotInstrumentInfo spotInstrumentInfo = (BybitSpotInstrumentInfo) info;
            map.put(
                adaptInstrument(spotInstrumentInfo.getSymbol(), BybitCategory.SPOT),
                new InstrumentMetaData.Builder()
                    .minimumAmount(spotInstrumentInfo.getLotSizeFilter().getMinOrderQty())
                    .maximumAmount(spotInstrumentInfo.getLotSizeFilter().getMaxOrderQty())
                    .counterMinimumAmount(spotInstrumentInfo.getLotSizeFilter().getMinOrderAmt())
                    .counterMaximumAmount(spotInstrumentInfo.getLotSizeFilter().getMaxOrderAmt())
                    .priceScale(spotInstrumentInfo.getPriceFilter().getTickSize().scale())
                    .volumeScale(spotInstrumentInfo.getLotSizeFilter().getBasePrecision().scale())
                    .amountStepSize(spotInstrumentInfo.getLotSizeFilter().getBasePrecision())
                    .priceStepSize(spotInstrumentInfo.getPriceFilter().getTickSize())
                    .marketOrderEnabled(
                        spotInstrumentInfo.getStatus().equals(InstrumentStatus.TRADING))
                    .build());
          } else if (info instanceof BybitLinearInverseInstrumentInfo) {
            BybitLinearInverseInstrumentInfo perpetualInstrumentInfo =
                (BybitLinearInverseInstrumentInfo) info;
            map.put(
                adaptInstrument(perpetualInstrumentInfo.getSymbol(), BybitCategory.LINEAR),
                new InstrumentMetaData.Builder()
                    .minimumAmount(perpetualInstrumentInfo.getLotSizeFilter().getMinOrderQty())
                    .maximumAmount(perpetualInstrumentInfo.getLotSizeFilter().getMaxOrderQty())
                    .counterMinimumAmount(
                        perpetualInstrumentInfo.getLotSizeFilter().getMinOrderQty())
                    .counterMaximumAmount(
                        perpetualInstrumentInfo.getLotSizeFilter().getMaxOrderQty())
                    .priceScale(perpetualInstrumentInfo.getPriceScale())
                    .volumeScale(perpetualInstrumentInfo.getLotSizeFilter().getQtyStep().scale())
                    .amountStepSize(perpetualInstrumentInfo.getLotSizeFilter().getQtyStep())
                    .priceStepSize(perpetualInstrumentInfo.getPriceFilter().getTickSize())
                    .marketOrderEnabled(
                        perpetualInstrumentInfo.getStatus().equals(InstrumentStatus.TRADING))
                    .build());
          } else if (info instanceof BybitOptionInstrumentInfo) {
            BybitOptionInstrumentInfo optionsInstrumentInfo = (BybitOptionInstrumentInfo) info;
            map.put(
                adaptInstrument(optionsInstrumentInfo.getSymbol(), BybitCategory.OPTION),
                new InstrumentMetaData.Builder()
                    .minimumAmount(optionsInstrumentInfo.getLotSizeFilter().getMinOrderQty())
                    .maximumAmount(optionsInstrumentInfo.getLotSizeFilter().getMaxOrderQty())
                    .counterMinimumAmount(optionsInstrumentInfo.getLotSizeFilter().getMinOrderQty())
                    .counterMaximumAmount(optionsInstrumentInfo.getLotSizeFilter().getMaxOrderQty())
                    .priceScale(optionsInstrumentInfo.getPriceFilter().getTickSize().scale())
                    .volumeScale(optionsInstrumentInfo.getLotSizeFilter().getQtyStep().scale())
                    .amountStepSize(optionsInstrumentInfo.getLotSizeFilter().getQtyStep())
                    .priceStepSize(optionsInstrumentInfo.getPriceFilter().getTickSize())
                    .marketOrderEnabled(optionsInstrumentInfo.getStatus().equals(InstrumentStatus.TRADING))
                    .build());
          }
        });
    return map;
  }

  public static OrderType adaptSide(BybitSide side) {
    return (side.equals(BybitSide.BUY)) ? OrderType.BID : OrderType.ASK;
  }

  public static String getBybitQuoteCurrency(String symbol) {
    String quoteCurrency = FUTURES_CONTRACT_QUOTE_CURRENCY;

    for (String quote : QUOTE_CURRENCIES) {
      if (symbol.endsWith(quote)) {
        quoteCurrency = quote;
        break;
      }
    }

    return quoteCurrency;
  }

  public static Currency getFeeCurrency(
      boolean isMaker, BigDecimal feeRate, Instrument instrument, BybitSide side) {
    if (instrument instanceof CurrencyPair) {
      if (isMaker && feeRate.compareTo(BigDecimal.ZERO) > 0) {
        return (side.equals(BybitSide.BUY)
            ? ((CurrencyPair) instrument).base
            : ((CurrencyPair) instrument).counter);
      } else {
        if (isMaker) {
          return (side.equals(BybitSide.BUY)
              ? ((CurrencyPair) instrument).counter
              : ((CurrencyPair) instrument).base);
        } else {
          return (side.equals(BybitSide.BUY)
              ? ((CurrencyPair) instrument).base
              : ((CurrencyPair) instrument).counter);
        }
      }
    } else {
      return instrument.getCounter();
    }
  }

  @SneakyThrows
  public static Instrument adaptInstrument(String symbol, BybitCategory category) {
    Instrument instrument = null;

    String quoteCurrency = getBybitQuoteCurrency(symbol);

    if (category.equals(BybitCategory.SPOT)) {
      String baseCurrency = symbol.substring(0, symbol.length() - quoteCurrency.length());

      instrument = new CurrencyPair(baseCurrency, quoteCurrency);
    } else if (category.equals(BybitCategory.LINEAR) || category.equals(BybitCategory.INVERSE)) {
      instrument =
          (symbol.contains("-"))
              ? new FuturesContract(new CurrencyPair(symbol.substring(0, symbol.indexOf("-")), quoteCurrency), symbol.substring(symbol.indexOf("-") + 1))
              : new FuturesContract(new CurrencyPair(symbol.substring(0, symbol.length() - quoteCurrency.length()), quoteCurrency), "PERP");
    } else if (category.equals(BybitCategory.OPTION)) {
      int secondIndex = symbol.indexOf("-", symbol.indexOf("-") + 1); // second index of "-" after the first one
      instrument =
          new OptionsContract.Builder()
              .currencyPair(new CurrencyPair(symbol.substring(0, symbol.indexOf("-")), quoteCurrency))
              .expireDate(OPTIONS_EXPIRED_DATE_PARSER.parse(symbol.substring(symbol.indexOf("-") + 1, secondIndex)))
              .strike(new BigDecimal(symbol.substring(secondIndex + 1, symbol.lastIndexOf("-"))))
              .type(symbol.contains("C") ? OptionType.CALL : OptionType.PUT)
              .build();
    }

    return instrument;
  }
}

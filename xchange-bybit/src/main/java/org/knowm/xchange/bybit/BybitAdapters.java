package org.knowm.xchange.bybit;

import static org.knowm.xchange.bybit.dto.BybitCategory.INVERSE;
import static org.knowm.xchange.bybit.dto.BybitCategory.OPTION;
import static org.knowm.xchange.bybit.dto.marketdata.instruments.option.BybitOptionInstrumentInfo.OptionType.CALL;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.account.allcoins.BybitAllCoinBalance;
import org.knowm.xchange.bybit.dto.account.allcoins.BybitAllCoinsBalance;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitCoinWalletBalance;
import org.knowm.xchange.bybit.dto.marketdata.instruments.BybitInstrumentInfo;
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
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Ticker.Builder;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.instrument.Instrument;

public class BybitAdapters {

  private static final ThreadLocal<SimpleDateFormat> OPTION_DATE_FORMAT =
      ThreadLocal.withInitial(() -> new SimpleDateFormat("ddMMMyy", Locale.US));
  public static final List<String> QUOTE_CURRENCIES =
      Arrays.asList("USDT", "USDC", "USDE", "EUR", "BRL", "PLN", "TRY", "SOL", "BTC", "ETH", "DAI",
          "BRZ");


  public static Wallet adaptBybitBalances(List<BybitCoinWalletBalance> coinWalletBalances) {
    List<Balance> balances = new ArrayList<>(coinWalletBalances.size());
    for (BybitCoinWalletBalance bybitCoinBalance : coinWalletBalances) {
      BigDecimal availableToWithdraw = bybitCoinBalance.getAvailableToWithdraw().isEmpty() ? BigDecimal.ZERO : new BigDecimal(bybitCoinBalance.getAvailableToWithdraw());
      balances.add(
          new Balance(
              new Currency(bybitCoinBalance.getCoin()),
              new BigDecimal(bybitCoinBalance.getEquity()),
              availableToWithdraw));
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
    if (type == Order.OrderType.ASK || type == OrderType.EXIT_BID) {
      return BybitSide.SELL;
    }
    if (type == Order.OrderType.BID || type == OrderType.EXIT_ASK) {
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

  /**
   * Converts instruments to Bybit symbols. For futures contracts, the prompt must represent the
   * date.
   */
  public static String convertToBybitSymbol(Instrument instrument) {
    BybitCategory category = getCategory(instrument);
    FuturesContract futuresContract;

    switch (category) {
      case SPOT:
        return String.format("%s%s", instrument.getBase(), instrument.getCounter()).toUpperCase();
      case LINEAR:
        futuresContract = (FuturesContract) instrument;
        if (futuresContract.isPerpetual() && !instrument.getCounter().getSymbol().equals("USDC")) {
          // eg. contractType: LINEAR_PERPETUAL, symbol: ETHUSDT, base: ETH, quote: USDT
          return String.format("%s%s", instrument.getBase(), instrument.getCounter());
        } else if (futuresContract.isPerpetual()
            && instrument.getCounter().getSymbol().equals("USDC")) {
          // eg. contractType: LINEAR_PERPETUAL, symbol: ETHPERP, base: ETH, quote: USDC
          return String.format("%sPERP", instrument.getBase());
        } else {
          // eg. contractType: LINEAR_FUTURES, symbol: ETH-02FEB24, base: ETH, quote: USDC
          return String.format("%s-%s", instrument.getBase(), futuresContract.getPrompt());
        }
      case INVERSE:
        futuresContract = (FuturesContract) instrument;
        // eg. contractType: INVERSE_FUTURES, symbol: ETHUSDH24, base: ETH, quote: USD
        return String.format(
                "%s%s%s",
                futuresContract.getBase(),
                futuresContract.getCounter(),
                futuresContract.isPerpetual() ? "" : futuresContract.getPrompt())
            .toUpperCase();
      case OPTION:
        OptionsContract optionsContract = ((OptionsContract) instrument);
        return String.format(
                "%s-%s-%s-%s",
                optionsContract.getBase(),
                OPTION_DATE_FORMAT.get().format(optionsContract.getExpireDate()),
                optionsContract.getStrike(),
                optionsContract.getType().equals(OptionsContract.OptionType.PUT) ? "P" : "C")
            .toUpperCase();
      default:
        throw new IllegalStateException("Unexpected value: " + category);
    }
  }

  public static CurrencyPair guessSymbol(String symbol) {
//SPOT Only
    for (String quoteCurrency : QUOTE_CURRENCIES) {
      if (symbol.endsWith(quoteCurrency)) {
        int splitIndex = symbol.lastIndexOf(quoteCurrency);
        return new CurrencyPair(symbol.substring(0, splitIndex), symbol.substring(splitIndex));
      }
    }
    int splitIndex = symbol.length() - 3;
    return new CurrencyPair(symbol.substring(0, splitIndex), symbol.substring(splitIndex));
  }

  public static Instrument adaptInstrumentInfo(BybitInstrumentInfo instrumentInfo) {
    if (instrumentInfo instanceof BybitSpotInstrumentInfo) {
      return new CurrencyPair(instrumentInfo.getBaseCoin(), instrumentInfo.getQuoteCoin());

    } else if (instrumentInfo instanceof BybitLinearInverseInstrumentInfo) {
      return new FuturesContract(
          new CurrencyPair(instrumentInfo.getBaseCoin(), instrumentInfo.getQuoteCoin()),
          BybitAdapters.getPrompt((BybitLinearInverseInstrumentInfo) instrumentInfo));

    } else if (instrumentInfo instanceof BybitOptionInstrumentInfo) {
      try {
        BybitOptionInstrumentInfo optionInstrumentInfo = (BybitOptionInstrumentInfo) instrumentInfo;

        String[] parts = optionInstrumentInfo.getSymbol().split("-");
        String expireDateString = parts[1];
        BigDecimal strike = new BigDecimal(parts[2]);

        return new OptionsContract.Builder()
            .currencyPair(
                new CurrencyPair(instrumentInfo.getBaseCoin(), instrumentInfo.getQuoteCoin()))
            .expireDate(OPTION_DATE_FORMAT.get().parse(expireDateString))
            .strike(strike)
            .type(
                optionInstrumentInfo.getOptionsType().equals(CALL)
                    ? OptionsContract.OptionType.CALL
                    : OptionsContract.OptionType.PUT)
            .build();
      } catch (ParseException e) {
        throw new ExchangeException("Unable to convert instrument info.", e);
      }
    }

    throw new IllegalStateException(
        "Unexpected instrument info instance: " + instrumentInfo.getClass().getSimpleName());
  }

  public static InstrumentMetaData symbolToCurrencyPairMetaData(
      BybitSpotInstrumentInfo instrumentInfo) {
    return new InstrumentMetaData.Builder()
        .marketOrderEnabled(true)
        .minimumAmount(instrumentInfo.getLotSizeFilter().getMinOrderQty())
        .maximumAmount(instrumentInfo.getLotSizeFilter().getMaxOrderQty())
        .counterMinimumAmount(instrumentInfo.getLotSizeFilter().getMinOrderAmt())
        .counterMaximumAmount(instrumentInfo.getLotSizeFilter().getMaxOrderAmt())
        .priceScale(instrumentInfo.getPriceFilter().getTickSize().scale())
        .volumeScale(instrumentInfo.getLotSizeFilter().getBasePrecision().scale())
        .amountStepSize(instrumentInfo.getLotSizeFilter().getBasePrecision())
        .priceStepSize(instrumentInfo.getPriceFilter().getTickSize())
        .build();
  }

  public static InstrumentMetaData symbolToCurrencyPairMetaData(
      BybitLinearInverseInstrumentInfo instrumentInfo) {
    return new InstrumentMetaData.Builder()
        .marketOrderEnabled(true)
        .minimumAmount(instrumentInfo.getLotSizeFilter().getMinOrderQty())
        .maximumAmount(instrumentInfo.getLotSizeFilter().getMaxOrderQty())
        .priceScale(instrumentInfo.getPriceFilter().getTickSize().scale())
        .priceStepSize(instrumentInfo.getPriceFilter().getTickSize())
        .tradingFee(instrumentInfo.getDeliveryFeeRate())
        .volumeScale(instrumentInfo.getLotSizeFilter().getQtyStep().scale())
        .amountStepSize(instrumentInfo.getLotSizeFilter().getQtyStep())
        .build();
  }

  public static InstrumentMetaData symbolToCurrencyPairMetaData(
      BybitOptionInstrumentInfo instrumentInfo) {
    return new InstrumentMetaData.Builder()
        .marketOrderEnabled(true)
        .minimumAmount(instrumentInfo.getLotSizeFilter().getMinOrderQty())
        .maximumAmount(instrumentInfo.getLotSizeFilter().getMaxOrderQty())
        .priceScale(instrumentInfo.getPriceFilter().getTickSize().scale())
        .priceStepSize(instrumentInfo.getPriceFilter().getTickSize())
        .tradingFee(instrumentInfo.getDeliveryFeeRate())
        .build();
  }

  public static Order adaptBybitOrderDetails(BybitOrderDetail bybitOrderResult, BybitCategory category) {
    Order.Builder builder;

    switch (bybitOrderResult.getOrderType()) {
      case MARKET:
        builder =
            new MarketOrder.Builder(
                adaptOrderType(bybitOrderResult), convertBybitSymbolToInstrument(bybitOrderResult.getSymbol(),category));
        break;
      case LIMIT:
        builder =
            new LimitOrder.Builder(
                adaptOrderType(bybitOrderResult), convertBybitSymbolToInstrument(bybitOrderResult.getSymbol(),category))
                .limitPrice(bybitOrderResult.getPrice());
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + bybitOrderResult.getOrderType());
    }

    return builder
        .orderType(getOrderType(bybitOrderResult.getSide()))
        .originalAmount(bybitOrderResult.getQty())
        .cumulativeAmount(bybitOrderResult.getCumExecQty())
        .id(bybitOrderResult.getOrderId())
        .timestamp(bybitOrderResult.getCreatedTime())
        .averagePrice(bybitOrderResult.getAvgPrice())
        .orderStatus(adaptBybitOrderStatus(bybitOrderResult.getOrderStatus()))
        .build();
  }

  private static OrderType adaptOrderType(BybitOrderDetail bybitOrderResult) {
    switch (bybitOrderResult.getSide()) {
      case BUY:
        return OrderType.BID;
      case SELL:
        return OrderType.ASK;
      default:
        throw new IllegalStateException("Unexpected value: " + bybitOrderResult.getSide());
    }
  }

  public static OrderStatus adaptBybitOrderStatus(BybitOrderStatus orderStatus) {
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

  public static <T> BybitException createBybitExceptionFromResult(BybitResult<T> bybitResult) {
    return new BybitException(
        bybitResult.getRetCode(), bybitResult.getRetMsg(), bybitResult.getRetExtInfo());
  }

  public static BybitCategory getCategory(Instrument instrument) {
    if (instrument instanceof CurrencyPair) {
      return BybitCategory.SPOT;
    } else if (instrument instanceof FuturesContract) {
      return BybitAdapters.isInverse(instrument) ? INVERSE : BybitCategory.LINEAR;
    } else if (instrument instanceof OptionsContract) {
      return OPTION;
    }
    throw new IllegalStateException(
        "Unexpected instrument instance type: " + instrument.getClass().getSimpleName());
  }

  public static String getPrompt(BybitLinearInverseInstrumentInfo instrumentInfo) {
    switch (instrumentInfo.getContractType()) {
      case INVERSE_PERPETUAL:
      case LINEAR_PERPETUAL:
        return "PERP";
      case LINEAR_FUTURES:
        return instrumentInfo.getSymbol().split("-")[1];
      case INVERSE_FUTURES:
        return instrumentInfo
            .getSymbol()
            .replace(instrumentInfo.getBaseCoin() + instrumentInfo.getQuoteCoin(), "");
      default:
        throw new IllegalStateException("Unexpected value: " + instrumentInfo.getContractType());
    }
  }

  public static Boolean isInverse(Instrument pair) {
    return pair instanceof FuturesContract && pair.getCounter().equals(Currency.USD);
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

  public static Instrument convertBybitSymbolToInstrument(String symbol, BybitCategory category) {
    switch (category) {
      case SPOT: {
        return guessSymbol(symbol);
      }
      case LINEAR: {
        if (symbol.endsWith("USDT")) {
          int splitIndex = symbol.lastIndexOf("USDT");
          return new FuturesContract(
              new CurrencyPair(symbol.substring(0, splitIndex), "USDT"), "PERP");
        }
        if (symbol.endsWith("PERP")) {
          int splitIndex = symbol.lastIndexOf("PERP");
          return new FuturesContract(
              new CurrencyPair(symbol.substring(0, splitIndex), "USDC"), "PERP");
        }
        // USDC Futures
        int splitIndex = symbol.lastIndexOf("-");
        return new FuturesContract(
            new CurrencyPair(symbol.substring(0, splitIndex), "USDC"),
            symbol.substring(splitIndex + 1));
      }
      case INVERSE: {
        int splitIndex = symbol.lastIndexOf("USD");
        String perp = symbol.length() > splitIndex + 3 ? symbol.substring(splitIndex + 3) : "";
        return new FuturesContract(
            new CurrencyPair(symbol.substring(0, splitIndex), "USD"), perp);
      }
      case OPTION: {
        DateTimeFormatter dateParser = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("ddLLLyy")
            .toFormatter(Locale.US);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyMMdd");
        String[] tokens = symbol.split("-");
        String base = tokens[0];
        String quote = "USDC";
        String date =   dateFormat.format(LocalDate.parse(tokens[1], dateParser));
        BigDecimal strike = new BigDecimal(tokens[2]);
        return new OptionsContract(base + "/" + quote + "/" + date + "/" + strike + "/" + tokens[3]);
      }
    }
    return null;
  }
}

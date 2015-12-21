package com.xeiam.xchange.bleutrade.service.polling;

import com.xeiam.xchange.bleutrade.dto.account.BleutradeBalance;
import com.xeiam.xchange.bleutrade.dto.account.BleutradeDepositAddress;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeCurrency;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeLevel;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeMarket;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeOrderBook;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeTicker;
import com.xeiam.xchange.bleutrade.dto.marketdata.BleutradeTrade;
import com.xeiam.xchange.bleutrade.dto.trade.BleutradeOpenOrder;
import com.xeiam.xchange.bleutrade.dto.trade.BleutradeOrderId;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class BleutradeServiceTestSupport {

  protected static final String SPECIFICATION_USERNAME = "admin";
  protected static final String SPECIFICATION_API_KEY = "publicKey";
  protected static final String SPECIFICATION_SECRET_KEY = "secretKey";

  protected static final CurrencyPair BLEU_BTC_CP = new CurrencyPair("BLEU", "BTC");

  protected static final List<BleutradeOpenOrder> BLEUTRADE_OPEN_ORDERS_LIST =  Collections.unmodifiableList((Arrays.asList(
      createBleutradeOpenOrder("65489", "LTC_BTC", "BUY", new BigDecimal("20.00000000"), new BigDecimal("5.00000000"), "0.16549400",
          new BigDecimal("0.01268311"), "OPEN", "2014-08-03 13:55:20", "My optional comment, eg function id #123"),
      createBleutradeOpenOrder("65724", "DOGE_BTC", "SELL", new BigDecimal("150491.98700000"), new BigDecimal("795.00000000"), "0.04349400",
          new BigDecimal("0.00000055"), "OPEN", "2014-07-29 18:45:17", "Function #123 Connect #456"))));

  protected static final String[] OPEN_ORDERS_STR = new String[]{
      "LimitOrder [limitPrice=0.01268311, Order [type=BID, tradableAmount=5.00000000, currencyPair=LTC/BTC, id=65489, timestamp=null]]",
      "LimitOrder [limitPrice=5.5E-7, Order [type=ASK, tradableAmount=795.00000000, currencyPair=DOGE/BTC, id=65724, timestamp=null]]"
  };

  protected static final LimitOrder[] PLACED_ORDERS = new LimitOrder[] {
    new LimitOrder(Order.OrderType.BID, new BigDecimal("10.00000000"), CurrencyPair.BTC_AUD, "", null, new BigDecimal("1.1")),
      new LimitOrder(Order.OrderType.ASK, new BigDecimal("20.00000000"), CurrencyPair.BTC_AUD, "", null, new BigDecimal("2.2"))};

  protected static final List<BleutradeTicker> BLEUTRADE_TICKER =  Collections.unmodifiableList(Arrays.asList(
      createBleutradeTicker("BLEU_BTC", new BigDecimal("0.00095000"), new BigDecimal("0.00105000"),
          new BigDecimal("0.00086000"), new BigDecimal("0.00101977"), new BigDecimal("0.00103455"),
          new BigDecimal("2450.97496015"), new BigDecimal("2.40781647"), "2014-07-29 11:19:30",
          new BigDecimal("0.00100000"), new BigDecimal("0.00101977"), true)
  ));

  protected static final String BLEUTRADE_TICKER_STR = "Ticker [currencyPair=BLEU/BTC, last=0.00101977, bid=0.00100000, ask=0.00101977, "
      + "high=0.00105000, low=0.00086000,avg=0.00103455, volume=2450.97496015, timestamp=1406632770000]";

  protected static final List<BleutradeTicker> BLEUTRADE_TICKERS =  Collections.unmodifiableList(Arrays.asList(
      createBleutradeTicker("BLEU_BTC", new BigDecimal("0.00095000"), new BigDecimal("0.00105000"),
          new BigDecimal("0.00086000"), new BigDecimal("0.00101977"), new BigDecimal("0.00103455"),
          new BigDecimal("2450.97496015"), new BigDecimal("2.40781647"), "2014-07-29 11:19:30",
          new BigDecimal("0.00100000"), new BigDecimal("0.00101977"), true),
      createBleutradeTicker("LTC_BTC", new BigDecimal("0.00095000"), new BigDecimal("0.01333000"),
          new BigDecimal("0.01167001"), new BigDecimal("0.01333000"), new BigDecimal("0.01235000"),
          new BigDecimal("14.46077245"), new BigDecimal("0.18765956"), "2014-07-29 11:48:02",
          new BigDecimal("0.01268311"), new BigDecimal("0.01333000"), true)
      ));

  protected static final String[] BLEUTRADE_TICKERS_STR = new String[] {
      "BleutradeTicker [MarketName=BLEU_BTC, PrevDay=0.00095000, High=0.00105000, Low=0.00086000, Last=0.00101977, Average=0.00103455, "
          + "Volume=2450.97496015, BaseVolume=2.40781647, TimeStamp=2014-07-29 11:19:30, Bid=0.00100000, Ask=0.00101977, IsActive=true, additionalProperties={}]",
      "BleutradeTicker [MarketName=LTC_BTC, PrevDay=0.00095000, High=0.01333000, Low=0.01167001, Last=0.01333000, Average=0.01235000, "
          + "Volume=14.46077245, BaseVolume=0.18765956, TimeStamp=2014-07-29 11:48:02, Bid=0.01268311, Ask=0.01333000, IsActive=true, additionalProperties={}]"
  };

  protected static final List<BleutradeLevel> BLEUTRADE_LEVEL_BUYS =  Collections.unmodifiableList(Arrays.asList(
      createBleutradeLevel(new BigDecimal("4.99400000"), new BigDecimal("3.00650900")),
      createBleutradeLevel(new BigDecimal("50.00000000"), new BigDecimal("3.50000000"))
  ));

  protected static final String[] BUYS_STR = new String[] {
      "LimitOrder [limitPrice=3.00650900, Order [type=BID, tradableAmount=4.99400000, currencyPair=BLEU/BTC, id=null, timestamp=null]]",
      "LimitOrder [limitPrice=3.50000000, Order [type=BID, tradableAmount=50.00000000, currencyPair=BLEU/BTC, id=null, timestamp=null]]"
  };

  protected static final List<BleutradeLevel> BLEUTRADE_LEVEL_SELLS = Collections.unmodifiableList(Arrays.asList(
      createBleutradeLevel(new BigDecimal("12.44147454"), new BigDecimal("5.13540000")),
      createBleutradeLevel(new BigDecimal("100.00000000"), new BigDecimal("6.25500000")),
      createBleutradeLevel(new BigDecimal("30.00000000"), new BigDecimal("6.75500001")),
      createBleutradeLevel(new BigDecimal("13.49989999"), new BigDecimal("6.76260099"))
  ));

  protected static final String[] SELLS_STR = new String[] {
      "LimitOrder [limitPrice=5.13540000, Order [type=ASK, tradableAmount=12.44147454, currencyPair=BLEU/BTC, id=null, timestamp=null]]",
      "LimitOrder [limitPrice=6.25500000, Order [type=ASK, tradableAmount=100.00000000, currencyPair=BLEU/BTC, id=null, timestamp=null]]",
      "LimitOrder [limitPrice=6.75500001, Order [type=ASK, tradableAmount=30.00000000, currencyPair=BLEU/BTC, id=null, timestamp=null]]",
      "LimitOrder [limitPrice=6.76260099, Order [type=ASK, tradableAmount=13.49989999, currencyPair=BLEU/BTC, id=null, timestamp=null]]"
  };

  protected static final List<BleutradeTrade> BLEUTRADE_TRADES = Collections.unmodifiableList(Arrays.asList(
      createBleutradeTrade("2014-07-29 18:08:00", new BigDecimal("654971.69417461"), new BigDecimal("0.00000055"), new BigDecimal("0.360234432"), "BUY"),
      createBleutradeTrade("2014-07-29 18:12:35", new BigDecimal("120.00000000"), new BigDecimal("0.00006600"), new BigDecimal("0.360234432"), "SELL")
  ));

  protected static final String[] TRADES_STR = new String[] {
      "[trade=Trade [type=BID, tradableAmount=654971.69417461, currencyPair=BLEU/BTC, price=5.5E-7, timestamp=%s, id=null]]",
      "[trade=Trade [type=ASK, tradableAmount=120.00000000, currencyPair=BLEU/BTC, price=0.00006600, timestamp=%s, id=null]]"
  };

  protected static final List<BleutradeCurrency> BLEUTRADE_CURRENCIES = Collections.unmodifiableList(Arrays.asList(
      createBleutradeCurrency("BTC", "Bitcoin", 2, new BigDecimal("0.00080000"), true, "BITCOIN"),
      createBleutradeCurrency("LTC", "Litecoin", 4, new BigDecimal("0.02000000"), true, "BITCOIN")
  ));

  protected static final String[] CURRENCIES_STR = new String[] {
      "BleutradeCurrency [Currency=BTC, CurrencyLong=Bitcoin, MinConfirmation=2, TxFee=0.00080000, IsActive=true, CoinType=BITCOIN, additionalProperties={}]",
      "BleutradeCurrency [Currency=LTC, CurrencyLong=Litecoin, MinConfirmation=4, TxFee=0.02000000, IsActive=true, CoinType=BITCOIN, additionalProperties={}]"
  };

  protected static final List<BleutradeMarket> BLEUTRADE_MARKETS = Collections.unmodifiableList(Arrays.asList(
      createBleutradeMarket("DOGE", "BTC", "Dogecoin", "Bitcoin", new BigDecimal("0.10000000"), "DOGE_BTC", true),
      createBleutradeMarket("BLEU", "BTC", "Bleutrade Share", "Bitcoin", new BigDecimal("0.00000001"), "BLEU_BTC", true)
  ));

  protected static final String[] MARKETS_STR = new String[] {
    "BleutradeMarket [MarketCurrency=DOGE, BaseCurrency=BTC, MarketCurrencyLong=Dogecoin, BaseCurrencyLong=Bitcoin, MinTradeSize=0.10000000, "
        + "MarketName=DOGE_BTC, IsActive=true, additionalProperties={}]",
    "BleutradeMarket [MarketCurrency=BLEU, BaseCurrency=BTC, MarketCurrencyLong=Bleutrade Share, BaseCurrencyLong=Bitcoin, MinTradeSize=1E-8, "
        + "MarketName=BLEU_BTC, IsActive=true, additionalProperties={}]"
  };

  protected static final List<BleutradeBalance> BLEUTRADE_BALANCES = Collections.unmodifiableList(Arrays.asList(
      createBalance(new BigDecimal("10.00000000"), "AUD", new BigDecimal("40.00000000"), new BigDecimal("30.00000000"), true),
      createBalance(new BigDecimal("40.00000000"), "BTC", new BigDecimal("100.00000000"), new BigDecimal("60.00000000"), false),
      createBalance(new BigDecimal("70.00000000"), "BLEU", new BigDecimal("160.00000000"), new BigDecimal("90.00000000"), true)
  ));

  protected static final String BLEUTRADE_BALANCE_STR = "BleutradeBalance [Currency=AUD, Balance=40.00000000, Available=10.00000000, "
      + "Pending=30.00000000, CryptoAddress=null, IsActive=true, additionalProperties={}]";

  protected static final String[] BALANCES_STR = new String[] {
      "Balance [currency=AUD, total=40.00000000, available=10.00000000, frozen=30.00000000, borrowed=0, loaned=0, withdrawing=0, depositing=0]",
      "Balance [currency=BTC, total=100.00000000, available=40.00000000, frozen=60.00000000, borrowed=0, loaned=0, withdrawing=0, depositing=0]",
      "Balance [currency=BLEU, total=160.00000000, available=70.00000000, frozen=90.00000000, borrowed=0, loaned=0, withdrawing=0, depositing=0]"
  };

  protected static final BleutradeDepositAddress BLEUTRADE_DEPOSIT_ADDRESS = createDepositAddress("AUD", "Deposit Address Details");

  protected static BleutradeBalance createBalance(BigDecimal available, String currency, BigDecimal balance, BigDecimal pending, Boolean isActive) {
    BleutradeBalance bleutradeBalance = new BleutradeBalance();
    bleutradeBalance.setAvailable(available);
    bleutradeBalance.setCurrency(currency);
    bleutradeBalance.setBalance(balance);
    bleutradeBalance.setPending(pending);
    bleutradeBalance.setIsActive(isActive);

    return bleutradeBalance;
  }

  protected static BleutradeDepositAddress createDepositAddress(String currency, String address) {
    BleutradeDepositAddress depositAddress = new BleutradeDepositAddress();
    depositAddress.setCurrency(currency);
    depositAddress.setAddress(address);

    return depositAddress;
  }

  protected static BleutradeTicker createBleutradeTicker(String marketName, BigDecimal prevDay, BigDecimal high, BigDecimal low, BigDecimal last,
      BigDecimal average, BigDecimal volume, BigDecimal baseVolume, String timestamp, BigDecimal bid, BigDecimal ask, Boolean isActive) {

    BleutradeTicker ticker = new BleutradeTicker();
    ticker.setMarketName(marketName);
    ticker.setPrevDay(prevDay);
    ticker.setHigh(high);
    ticker.setLow(low);
    ticker.setLast(last);
    ticker.setAverage(average);
    ticker.setVolume(volume);
    ticker.setBaseVolume(baseVolume);
    ticker.setTimeStamp(timestamp);
    ticker.setBid(bid);
    ticker.setAsk(ask);
    ticker.setIsActive(isActive);

    return ticker;
  }

  protected static BleutradeOrderBook createBleutradeOrderBook(List<BleutradeLevel> buy, List<BleutradeLevel>  sell) {
    BleutradeOrderBook bleutradeOrderBook = new BleutradeOrderBook();
    bleutradeOrderBook.setBuy(buy);
    bleutradeOrderBook.setSell(sell);

    return bleutradeOrderBook;
  }

  protected static BleutradeLevel createBleutradeLevel(BigDecimal quantity, BigDecimal rate) {
    BleutradeLevel level = new BleutradeLevel();
    level.setQuantity(quantity);
    level.setRate(rate);

    return level;
  }

  protected static BleutradeTrade createBleutradeTrade(String timestamp, BigDecimal quantity, BigDecimal price, BigDecimal total, String orderType) {
    BleutradeTrade bleutradeTrade = new BleutradeTrade();
    bleutradeTrade.setTimeStamp(timestamp);
    bleutradeTrade.setQuantity(quantity);
    bleutradeTrade.setPrice(price);
    bleutradeTrade.setTotal(total);
    bleutradeTrade.setOrderType(orderType);

    return bleutradeTrade;
  }

  protected static BleutradeCurrency createBleutradeCurrency(String currency, String currencyLong, Integer minConfirmation,
      BigDecimal txFee, Boolean isActive, String coinType) {

    BleutradeCurrency bleutradeCurrency = new BleutradeCurrency();
    bleutradeCurrency.setCurrency(currency);
    bleutradeCurrency.setCurrencyLong(currencyLong);
    bleutradeCurrency.setMinConfirmation(minConfirmation);
    bleutradeCurrency.setTxFee(txFee);
    bleutradeCurrency.setIsActive(isActive);
    bleutradeCurrency.setCoinType(coinType);

    return bleutradeCurrency;
  }

  protected static BleutradeMarket createBleutradeMarket(String marketCurrency, String baseCurrency, String marketCurrencyLong,
      String baseCurrencyLong, BigDecimal minTradeSize, String marketName, Boolean isActive) {
    BleutradeMarket bleutradeMarket = new BleutradeMarket();
    bleutradeMarket.setMarketCurrency(marketCurrency);
    bleutradeMarket.setBaseCurrency(baseCurrency);
    bleutradeMarket.setMarketCurrencyLong(marketCurrencyLong);
    bleutradeMarket.setBaseCurrencyLong(baseCurrencyLong);
    bleutradeMarket.setMinTradeSize(minTradeSize);
    bleutradeMarket.setMarketName(marketName);
    bleutradeMarket.setIsActive(isActive);

    return bleutradeMarket;
  }

  protected static BleutradeOpenOrder createBleutradeOpenOrder(String orderId, String exchange, String type,
      BigDecimal quantity, BigDecimal quantityRemaining, String quantityBaseTraded, BigDecimal price,
      String status, String created, String comments) {
    BleutradeOpenOrder bleutradeOpenOrder = new BleutradeOpenOrder();
    bleutradeOpenOrder.setOrderId(orderId);
    bleutradeOpenOrder.setExchange(exchange);
    bleutradeOpenOrder.setType(type);
    bleutradeOpenOrder.setQuantity(quantity);
    bleutradeOpenOrder.setQuantityRemaining(quantityRemaining);
    bleutradeOpenOrder.setQuantityBaseTraded(quantityBaseTraded);
    bleutradeOpenOrder.setPrice(price);
    bleutradeOpenOrder.setStatus(status);
    bleutradeOpenOrder.setCreated(created);
    bleutradeOpenOrder.setComments(comments);

    return bleutradeOpenOrder;
  }

  protected static BleutradeOrderId createBleutradeOrderId(String orderId) {
    BleutradeOrderId bleutradeOrderId = new BleutradeOrderId();
    bleutradeOrderId.setOrderid(orderId);

    return bleutradeOrderId;
  }
}

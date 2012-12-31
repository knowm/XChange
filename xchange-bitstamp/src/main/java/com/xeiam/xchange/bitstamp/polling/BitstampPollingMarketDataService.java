package com.xeiam.xchange.bitstamp.polling;

import com.xeiam.xchange.CurrencyPair;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.marketdata.*;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.service.BasePollingExchangeService;
import com.xeiam.xchange.service.marketdata.polling.PollingMarketDataService;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import org.joda.time.DateTime;
import si.mazi.bitstampapi.BitStamp;
import si.mazi.bitstampapi.BitstampFactory;
import si.mazi.bitstampapi.model.Transaction;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Matija Mazi <br/>
 */
public class BitstampPollingMarketDataService extends BasePollingExchangeService implements PollingMarketDataService {
  public static final List<CurrencyPair> CURRENCY_PAIRS = Arrays.asList(CurrencyPair.BTC_USD);

  private final BitStamp bitStamp;
  private static final CurrencyUnit BTC = CurrencyUnit.of("BTC");
  private static final CurrencyUnit USD = CurrencyUnit.of("USD");

  public BitstampPollingMarketDataService(ExchangeSpecification exchangeSpecification) {
    super(exchangeSpecification);
    bitStamp = BitstampFactory.createResteasyEndpoint();
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {
    return CURRENCY_PAIRS;
  }

  @Override
  public Ticker getTicker(String tradableIdentifier, String currency) {
    checkArgument(tradableIdentifier.equals(BTC.getCode()));
    checkArgument(currency.equals(USD.getCode()));
    si.mazi.bitstampapi.model.Ticker tck = bitStamp.getTicker();
    return new TickerBuilder()
        .withAsk(BigMoney.of(USD, tck.getAsk()))
        .withBid(BigMoney.of(USD, tck.getBid()))
        .withHigh(BigMoney.of(USD, tck.getHigh()))
        .withLow(BigMoney.of(USD, tck.getLow()))
        .withLast(BigMoney.of(USD, tck.getLast()))
        .withVolume(new BigDecimal(tck.getVolume()))
        .build();
  }

  @Override
  public OrderBook getPartialOrderBook(String tradableIdentifier, String currency) {
    return getFullOrderBook(tradableIdentifier, currency);
  }

  @Override
  public OrderBook getFullOrderBook(String tradableIdentifier, String currency) {
    si.mazi.bitstampapi.model.OrderBook orderBook = bitStamp.getOrderBook();
    List<LimitOrder> asks = createOrders(tradableIdentifier, currency, Order.OrderType.ASK, orderBook.getAsks());
    List<LimitOrder> bids = createOrders(tradableIdentifier, currency, Order.OrderType.BID, orderBook.getBids());
    return new OrderBook(asks, bids);
  }

  private List<LimitOrder> createOrders(String tradableIdentifier, String currency, Order.OrderType orderType, List<List<Double>> orders) {
    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (List<Double> ask : orders) {
      checkArgument(ask.size() == 2, "Expected a pair (price, amount) but got {0} elements.", ask.size());
      limitOrders.add(createOrder(tradableIdentifier, currency, ask, orderType));
    }
    return limitOrders;
  }

  private LimitOrder createOrder(String tradableIdentifier, String currency, List<Double> priceAndAmount, Order.OrderType orderType) {
    return new LimitOrder(orderType, new BigDecimal(priceAndAmount.get(1)), tradableIdentifier, currency, BigMoney.of(CurrencyUnit.USD, priceAndAmount.get(0)));
  }

  @Override
  public Trades getTrades(String tradableIdentifier, String currency) {
    List<Transaction> transactions = bitStamp.getTransactions(24 * 3600); // 24 hours
    List<Trade> trades = new ArrayList<Trade>();
    for (Transaction tx : transactions) {
      trades.add(new Trade(null, new BigDecimal(tx.getAmount()), "BTC", "USD", BigMoney.of(CurrencyUnit.of(currency), tx.getPrice()), new DateTime(tx.getTransactionDate())));
    }
    return new Trades(trades);
  }

  private static void checkArgument(boolean argument) {
    checkArgument(argument, "Illegal argument.");
  }

  private static void checkArgument(boolean argument, String msgPattern, Object... msgArgs) {
    if (!argument) {
      throw new IllegalArgumentException(MessageFormat.format(msgPattern, msgArgs));
    }
  }
}

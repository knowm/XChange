package org.knowm.xchange.binance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.knowm.xchange.Exchange.USE_SANDBOX;
import static org.knowm.xchange.binance.BinanceExchange.EXCHANGE_TYPE;
import static org.knowm.xchange.binance.dto.ExchangeType.SPOT;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.dto.trade.BinanceCancelOrderParams;
import org.knowm.xchange.binance.dto.trade.BinanceQueryOrderParams;
import org.knowm.xchange.binance.dto.trade.BinanceTradeHistoryParams;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.IOrderFlags;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamInstrument;
import org.knowm.xchange.utils.AuthUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
public class BinanceTest {
  private static final Instrument instrument = new CurrencyPair("ETH/USDT");

  protected final Logger logger = LoggerFactory.getLogger(getClass());
  private static Exchange binanceExchange;

  @Before
  public void setUp() throws IOException {
    Properties properties = new Properties();
    try {
      properties.load(BinanceTest.class.getResourceAsStream("/secret.keys"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    final String apiKey =
        (properties.getProperty("apikey") == null)
            ? System.getenv("binance-api-key")
            : properties.getProperty("apikey");
    final String apiSecret =
        (properties.getProperty("secret") == null)
            ? System.getenv("binance-api-secret")
            : properties.getProperty("secret");
    ExchangeSpecification spec = new ExchangeSpecification(BinanceExchange.class);
    spec.setApiKey(apiKey);
    spec.setSecretKey(apiSecret);
    spec.setExchangeSpecificParametersItem(EXCHANGE_TYPE, SPOT);
    spec.setExchangeSpecificParametersItem(USE_SANDBOX, true);
    AuthUtils.setApiAndSecretKey(spec, "binance-demo");
    binanceExchange = ExchangeFactory.INSTANCE.createExchange(spec);
  }

  @Test
  public void binanceMarketDataService() throws IOException {
    // Get Ticker
    Ticker ticker = binanceExchange.getMarketDataService().getTicker(instrument);
    logger.info("Ticker: " + ticker);
    assertThat(ticker.getInstrument()).isEqualTo(instrument);
    // Get Tickers
    List<Ticker> tickers = binanceExchange.getMarketDataService().getTickers(null);
    logger.info("Tickers: " + tickers);
    // Get OrderBook
    OrderBook orderBook = binanceExchange.getMarketDataService().getOrderBook(instrument);
    logger.info("OrderBook: " + orderBook);
    assertThat(orderBook.getBids().get(0).getInstrument()).isEqualTo(instrument);
    // Get Trades
    Trades trades = binanceExchange.getMarketDataService().getTrades(instrument);
    logger.info("Trades: " + trades);
    assertThat(trades.getTrades().get(0).getInstrument()).isEqualTo(instrument);
  }

  @Test
  public void binanceAccountService() throws IOException {
    // Works only on main(not demo) account
    Map<Instrument, Fee> fees =
        binanceExchange.getAccountService().getDynamicTradingFeesByInstrument();
    logger.info("fee: {}", fees);
    AccountInfo accountInfo = binanceExchange.getAccountService().getAccountInfo();
    logger.info("AccountInfo: {}", accountInfo.getWallet());
  }

  @Test
  public void binanceTradeService() throws IOException {
    Set<IOrderFlags> orderFlags = new HashSet<>();
    //        orderFlags.add(BinanceOrderFlags.REDUCE_ONLY);

    // Get UserTrades
    List<UserTrade> userTrades =
        binanceExchange
            .getTradeService()
            .getTradeHistory(new BinanceTradeHistoryParams(instrument))
            .getUserTrades();
    logger.info("UserTrades: " + userTrades);
    // Place LimitOrder
    String orderId =
        binanceExchange
            .getTradeService()
            .placeLimitOrder(
                new LimitOrder.Builder(Order.OrderType.BID, instrument)
                    .limitPrice(BigDecimal.valueOf(1000))
                    .flags(orderFlags)
                    .originalAmount(BigDecimal.ONE)
                    .build());
    // Get OpenOrders
    List<LimitOrder> openOrders =
        binanceExchange
            .getTradeService()
            .getOpenOrders(new DefaultOpenOrdersParamInstrument(instrument))
            .getOpenOrders();
    logger.info("OpenOrders: " + openOrders);
    assertThat(
            openOrders.stream().anyMatch(openOrder -> openOrder.getInstrument().equals(instrument)))
        .isTrue();

    // Get order
    Collection<Order> order =
        binanceExchange
            .getTradeService()
            .getOrder(new BinanceQueryOrderParams(instrument, orderId));
    logger.info("GetOrder: " + order);
    assertThat(order.stream().anyMatch(order1 -> order1.getInstrument().equals(instrument)))
        .isTrue();

    // Cancel LimitOrder
    logger.info(
        "CancelOrder: "
            + binanceExchange
                .getTradeService()
                .cancelOrder(new BinanceCancelOrderParams(instrument, orderId, "")));
  }
}

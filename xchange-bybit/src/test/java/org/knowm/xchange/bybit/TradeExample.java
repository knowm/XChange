package org.knowm.xchange.bybit;

import static org.knowm.xchange.Exchange.USE_SANDBOX;
import static org.knowm.xchange.bybit.BybitExchange.SPECIFIC_PARAM_ACCOUNT_TYPE;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;
import org.knowm.xchange.bybit.service.BybitTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.instrument.Instrument;

public class TradeExample {

  public static void main(String[] args) {
    try {
      testTrade();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void testTrade() throws IOException {
    ExchangeSpecification exchangeSpecification =
        new BybitExchange().getDefaultExchangeSpecification();
    exchangeSpecification.setApiKey(System.getProperty("test_api_key"));
    exchangeSpecification.setSecretKey(System.getProperty("test_secret_key"));
    exchangeSpecification.setExchangeSpecificParametersItem(
        SPECIFIC_PARAM_ACCOUNT_TYPE, BybitAccountType.UNIFIED);
    exchangeSpecification.setExchangeSpecificParametersItem(USE_SANDBOX, true);
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(
        exchangeSpecification);
    Instrument ETH_USDT = new CurrencyPair("ETH/USDT");
    Instrument BTC_USDT_PERP = new FuturesContract(new CurrencyPair("BTC/USDT"), "PERP");
    Instrument ETH_USDT_PERP = new FuturesContract(new CurrencyPair("ETH/USDT"), "PERP");

//    System.out.printf("Tickers SPOT %s", exchange.getMarketDataService().getTickers(BybitCategory.SPOT));
//    System.out.printf("Tickers LINEAR %s", exchange.getMarketDataService().getTickers(BybitCategory.LINEAR));
//    System.out.printf("Tickers INVERSE %s", exchange.getMarketDataService().getTickers(BybitCategory.INVERSE));
//    System.out.printf("Tickers OPTION %s", exchange.getMarketDataService().getTickers(BybitCategory.OPTION));
    System.out.printf("Wallets: %n%s%n",
        exchange.getAccountService().getAccountInfo().getWallets());
    Ticker tickerETH_USDT_PERP = exchange
        .getMarketDataService()
        .getTicker(ETH_USDT_PERP);
    Ticker tickerETH_USDT = exchange
        .getMarketDataService()
        .getTicker(ETH_USDT);
    System.out.println(tickerETH_USDT_PERP.toString());

    System.out.printf("Instrument %s:%n %s", ETH_USDT, exchange.getExchangeMetaData()
        .getInstruments().get(ETH_USDT));
    System.out.printf("Instrument %s:%n %s", ETH_USDT_PERP, exchange.getExchangeMetaData()
        .getInstruments().get(ETH_USDT_PERP));
    BigDecimal minAmountSpot = exchange.getExchangeMetaData().getInstruments().get(ETH_USDT)
        .getMinimumAmount();

    //sell
    MarketOrder marketOrderSpot = new MarketOrder(OrderType.ASK, minAmountSpot, ETH_USDT);
    String marketSpotOrderId = exchange.getTradeService().placeMarketOrder(marketOrderSpot);
    System.out.println("Market Spot order id: " + marketSpotOrderId);

    BigDecimal minAmountFuture = exchange.getExchangeMetaData().getInstruments().get(ETH_USDT_PERP)
        .getMinimumAmount();

    //long
    String marketFutureOrderId =
        exchange
            .getTradeService()
            .placeMarketOrder(
                new MarketOrder(OrderType.BID, minAmountFuture, ETH_USDT_PERP));
    System.out.println("Market Future order id: " + marketFutureOrderId);

    //short
    LimitOrder limitOrderFuture = new LimitOrder(OrderType.ASK, minAmountFuture, ETH_USDT_PERP,
        null, null, tickerETH_USDT_PERP.getHigh());
    String limitFutureOrderId =
        exchange.getTradeService().placeLimitOrder(limitOrderFuture);
    System.out.println("Limit Future order id: " + limitFutureOrderId);

    //amend order by order id
    LimitOrder amendOrder1 = new LimitOrder(limitOrderFuture.getType(),
        limitOrderFuture.getOriginalAmount().multiply(new BigDecimal(2)),
        limitOrderFuture.getInstrument(), limitFutureOrderId, limitOrderFuture.getTimestamp(),
        tickerETH_USDT_PERP.getHigh(),
        null, null, null, OrderStatus.PENDING_NEW, null);
    String limitFutureOrderAmend1 =
        ((BybitTradeService) exchange.getTradeService()).amendOrder(amendOrder1);
    System.out.printf("amend limit order %s%n", limitFutureOrderAmend1);

    //amend order by user id
    LimitOrder amendOrder2 = new LimitOrder(limitOrderFuture.getType(),
        limitOrderFuture.getOriginalAmount(), limitOrderFuture.getInstrument(),
        "", limitOrderFuture.getTimestamp(), tickerETH_USDT_PERP.getLast(),
        null, null, null, OrderStatus.PENDING_NEW, limitOrderFuture.getUserReference());
    String limitFutureOrderAmend2 =
        ((BybitTradeService) exchange.getTradeService())
            .amendOrder(amendOrder2);
    System.out.printf("amend limit order %s%n", limitFutureOrderAmend2);

  }
}

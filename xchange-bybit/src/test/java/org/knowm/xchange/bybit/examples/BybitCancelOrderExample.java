package org.knowm.xchange.bybit.examples;

import static org.knowm.xchange.Exchange.USE_SANDBOX;
import static org.knowm.xchange.bybit.BybitExchange.SPECIFIC_PARAM_ACCOUNT_TYPE;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Random;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;
import org.knowm.xchange.bybit.dto.trade.BybitCancelOrderParams;
import org.knowm.xchange.bybit.service.BybitAccountService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.instrument.Instrument;

public class BybitCancelOrderExample {
  public static void main(String[] args) {
    try {
      testCancelOrders();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void testCancelOrders() throws IOException {
    ExchangeSpecification exchangeSpecification =
        new BybitExchange().getDefaultExchangeSpecification();
    exchangeSpecification.setApiKey(System.getProperty("test_api_key"));
    exchangeSpecification.setSecretKey(System.getProperty("test_secret_key"));
    exchangeSpecification.setExchangeSpecificParametersItem(
        SPECIFIC_PARAM_ACCOUNT_TYPE, BybitAccountType.UNIFIED);
    exchangeSpecification.setExchangeSpecificParametersItem(USE_SANDBOX, true);
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
    Instrument ETH_USDT = new CurrencyPair("ETH/USDT");
    Instrument ETH_USDT_PERP = new FuturesContract(new CurrencyPair("ETH/USDT"), "PERP");
    Ticker tickerETH_USDT_PERP = exchange.getMarketDataService().getTicker(ETH_USDT_PERP);
    BybitAccountService bybitAccountService = (BybitAccountService) exchange.getAccountService();

    // switch mode to one-way
    bybitAccountService.switchPositionMode(BybitCategory.LINEAR, ETH_USDT_PERP, "USDT", 0);
    System.out.printf(
        "Instrument %s:%n %s %n",
        ETH_USDT_PERP, exchange.getExchangeMetaData().getInstruments().get(ETH_USDT_PERP));
    BigDecimal price = tickerETH_USDT_PERP.getLow().add(new BigDecimal("50").negate());
    LimitOrder limitSpotOrder =
        new LimitOrder(OrderType.BID, new BigDecimal("0.05"), ETH_USDT, null, null, price);
    String userReference0 = String.valueOf(new Random().nextLong());
    LimitOrder limitFutureOrder0 =
        new LimitOrder(
            OrderType.BID,
            new BigDecimal("0.05"),
            ETH_USDT_PERP,
            null,
            null,
            price,
            null,
            null,
            null,
            OrderStatus.PENDING_NEW,
            userReference0);
    String userReference1 = String.valueOf(new Random().nextLong());
    LimitOrder limitFutureOrder1 =
        new LimitOrder(
            OrderType.BID,
            new BigDecimal("0.05"),
            ETH_USDT_PERP,
            null,
            null,
            price,
            null,
            null,
            null,
            OrderStatus.PENDING_NEW,
            userReference1);

    String limitSpotOrderId = exchange.getTradeService().placeLimitOrder(limitSpotOrder);
    String limitFutureOrderId0 = exchange.getTradeService().placeLimitOrder(limitFutureOrder0);
    String limitFutureOrderId1 = exchange.getTradeService().placeLimitOrder(limitFutureOrder1);

    boolean resultSpot =
        exchange
            .getTradeService()
            .cancelOrder(new BybitCancelOrderParams(ETH_USDT, limitSpotOrderId, ""));
    boolean resultFuture0 =
        exchange
            .getTradeService()
            .cancelOrder(
                new BybitCancelOrderParams(ETH_USDT_PERP, limitFutureOrderId0, userReference0));
    boolean resultFuture1 =
        exchange
            .getTradeService()
            .cancelOrder(new BybitCancelOrderParams(ETH_USDT_PERP, "", userReference1));
    System.out.printf("cancel order SPOT, result %s%n", resultSpot);
    System.out.printf("cancel order LINEAR0, result %s%n", resultFuture0);
    System.out.printf("cancel order LINEAR1, result %s%n", resultFuture1);
  }
}

package org.knowm.xchange.bybit.examples;

import static org.knowm.xchange.Exchange.USE_SANDBOX;
import static org.knowm.xchange.bybit.BybitExchange.SPECIFIC_PARAM_ACCOUNT_TYPE;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;
import org.knowm.xchange.bybit.service.BybitAccountService;
import org.knowm.xchange.bybit.service.BybitTradeService.BybitCancelAllOrdersParams;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.TradeService;

public class BybitCancelAllOrdersExample {

  public static void main(String[] args) {
    try {
      testCancelAllOrders();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void testCancelAllOrders() throws IOException {
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

    //switch mode to one-way
    bybitAccountService.switchPositionMode(BybitCategory.LINEAR, ETH_USDT_PERP, "USDT", 0);
    System.out.printf(
        "Instrument %s:%n %s",
        ETH_USDT_PERP, exchange.getExchangeMetaData().getInstruments().get(ETH_USDT_PERP));
    BigDecimal price = tickerETH_USDT_PERP.getLow().add(new BigDecimal("50").negate());
    LimitOrder limitFutureOrder =
        new LimitOrder(
            OrderType.BID,
            new BigDecimal("0.05"),
            ETH_USDT_PERP,
            null,
            null, price);
    LimitOrder limitSpotOrder =
        new LimitOrder(
            OrderType.BID,
            new BigDecimal("0.05"),
            ETH_USDT,
            null,
            null, price);
    String limitFutureOrderId = exchange.getTradeService().placeLimitOrder(limitFutureOrder);
    String limitSpotOrderId = exchange.getTradeService().placeLimitOrder(limitSpotOrder);
   TradeService tradeService = exchange.getTradeService();
    Collection<String> resultSpot = tradeService.cancelAllOrders(new BybitCancelAllOrdersParams(BybitCategory.SPOT, null));
    Collection<String> resultFuture0 = tradeService.cancelAllOrders(new BybitCancelAllOrdersParams(BybitCategory.LINEAR, ETH_USDT_PERP));
    System.out.printf("cancel all orders SPOT, result %s%n", resultSpot);
    System.out.printf("cancel all orders LINEAR, result %s%n", resultFuture0);

  }
}

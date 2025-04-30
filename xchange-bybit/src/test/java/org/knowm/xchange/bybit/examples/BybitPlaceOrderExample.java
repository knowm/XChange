package org.knowm.xchange.bybit.examples;

import static org.knowm.xchange.bybit.BybitExchange.SPECIFIC_PARAM_ACCOUNT_TYPE;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;
import org.knowm.xchange.bybit.dto.trade.BybitOpenOrdersParam;
import org.knowm.xchange.bybit.dto.trade.details.BybitHedgeMode;
import org.knowm.xchange.bybit.dto.trade.details.BybitTimeInForce;
import org.knowm.xchange.bybit.service.BybitAccountService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.instrument.Instrument;

public class BybitPlaceOrderExample {

  public static void main(String[] args) throws InterruptedException {
    try {
      testOrder();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void testOrder() throws IOException {
    ExchangeSpecification exchangeSpecification =
        new BybitExchange().getDefaultExchangeSpecification();
    exchangeSpecification.setApiKey(System.getProperty("test_api_key"));
    exchangeSpecification.setSecretKey(System.getProperty("test_secret_key"));
    exchangeSpecification.setExchangeSpecificParametersItem(
        SPECIFIC_PARAM_ACCOUNT_TYPE, BybitAccountType.UNIFIED);
    exchangeSpecification.setExchangeSpecificParametersItem(Exchange.USE_SANDBOX, true);
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);

    Instrument ETH_USDT_PERP = new FuturesContract(new CurrencyPair("ETH/USDT"), "PERP");

    System.out.printf(
        "Wallets: %n%s%n", exchange.getAccountService().getAccountInfo().getWallets());
    Ticker ticker = exchange.getMarketDataService().getTicker(ETH_USDT_PERP);

    System.out.printf(
        "Instrument %s:%n %s %n",
        ETH_USDT_PERP, exchange.getExchangeMetaData().getInstruments().get(ETH_USDT_PERP));

    BigDecimal minAmountFuture =
        exchange.getExchangeMetaData().getInstruments().get(ETH_USDT_PERP).getMinimumAmount();
    BybitAccountService bybitAccountService = (BybitAccountService) exchange.getAccountService();
    // switch mode to two-way
    bybitAccountService.switchPositionMode(BybitCategory.LINEAR, ETH_USDT_PERP, "USDT", 3);
    MarketOrder marketOrder = new MarketOrder(OrderType.BID, minAmountFuture, ETH_USDT_PERP);
    marketOrder.addOrderFlag(BybitHedgeMode.TWOWAY);
    String marketOrderId = exchange.getTradeService().placeMarketOrder(marketOrder);
    System.out.println("market order id: " + marketOrderId);

    LimitOrder limitOrder =
        new LimitOrder(
            OrderType.EXIT_BID,
            minAmountFuture,
            BigDecimal.ZERO,
            ETH_USDT_PERP,
            "",
            new Date(),
            ticker.getHigh());
    limitOrder.addOrderFlag(BybitTimeInForce.POSTONLY);
    limitOrder.addOrderFlag(BybitHedgeMode.TWOWAY);
    String limitOrderId = exchange.getTradeService().placeLimitOrder(limitOrder);
    System.out.println("limit order id: " + limitOrderId);

    // Main net only
    //    for (Order order : exchange.getTradeService().getOrder(limitOrderId)) {
    //      System.out.println("get order: " + order);
    //    }

    BybitOpenOrdersParam param = new BybitOpenOrdersParam(ETH_USDT_PERP, BybitCategory.LINEAR);
    for (LimitOrder order : exchange.getTradeService().getOpenOrders(param).getOpenOrders()) {
      System.out.println("get open orders: " + order);
    }
  }
}

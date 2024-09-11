package org.knowm.xchange.bybit;


import static org.knowm.xchange.bybit.BybitExchange.SPECIFIC_PARAM_ACCOUNT_TYPE;
import static org.knowm.xchange.bybit.dto.trade.BybitAdvancedOrder.SlTriggerBy.LASTPRICE;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;
import org.knowm.xchange.bybit.dto.trade.BybitAdvancedOrder;
import org.knowm.xchange.bybit.dto.trade.BybitAdvancedOrder.TimeInForce;
import org.knowm.xchange.bybit.dto.trade.BybitAdvancedOrder.TpslMode;
import org.knowm.xchange.bybit.dto.trade.BybitOrderType;
import org.knowm.xchange.bybit.service.BybitTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.instrument.Instrument;

public class AdvancedOrderExample {

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
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(
        exchangeSpecification);
    Instrument ETH_USDT = new CurrencyPair("ETH/USDT");
    Instrument BTC_USDT_PERP = new FuturesContract(new CurrencyPair("BTC/USDT"), "PERP");
    Instrument ETH_USDT_PERP = new FuturesContract(new CurrencyPair("ETH/USDT"), "PERP");

//    System.out.printf("Wallets: %n%s%n",
//        exchange.getAccountService().getAccountInfo().getWallets());
    Ticker ticker = exchange
        .getMarketDataService()
        .getTicker(ETH_USDT_PERP);
    System.out.println(ticker.toString());

    System.out.printf("Instrument %s:%n %s", ETH_USDT, exchange.getExchangeMetaData()
        .getInstruments().get(ETH_USDT));
    System.out.printf("Instrument %s:%n %s %n", ETH_USDT_PERP, exchange.getExchangeMetaData()
        .getInstruments().get(ETH_USDT_PERP));

    BigDecimal minAmountSpot = exchange.getExchangeMetaData().getInstruments().get(ETH_USDT)
        .getMinimumAmount();
    BigDecimal minAmountFuture = exchange.getExchangeMetaData().getInstruments().get(ETH_USDT_PERP)
        .getMinimumAmount();

    BigDecimal minUSDTSpot = minAmountSpot.multiply(ticker.getLast());
    BybitAdvancedOrder advancedOrder = new BybitAdvancedOrder(OrderType.BID, BybitOrderType.LIMIT,"",
        ETH_USDT_PERP, minAmountFuture, ticker.getLast(), new Date(),
        ticker.getLast().multiply(new BigDecimal("0.995")), LASTPRICE,
        ticker.getLast().multiply(new BigDecimal("0.995")), BybitOrderType.LIMIT,
        new BigDecimal("0"), TimeInForce.POSTONLY, false,1);

    BybitAdvancedOrder advancedOrder1 = new BybitAdvancedOrder(OrderType.BID, BybitOrderType.LIMIT,"",
         ETH_USDT_PERP, minAmountFuture, ticker.getLast(), new Date());

    BybitTradeService tradeService = (BybitTradeService)exchange.getTradeService();
    String advancedOrderId =
        tradeService.placeAdvancedOrder(advancedOrder);
    System.out.println("advanced order id: " + advancedOrderId);
  }

}


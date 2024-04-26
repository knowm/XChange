package org.knowm.xchange.bybit;

import static org.knowm.xchange.bybit.BybitExchange.SPECIFIC_PARAM_ACCOUNT_TYPE;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.instrument.Instrument;

public class TradeExample {

  public static void main(String[] args) throws InterruptedException {
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
        SPECIFIC_PARAM_ACCOUNT_TYPE, BybitAccountType.UNIFIED); // or FUND
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
    Ticker ticker = exchange
        .getMarketDataService()
        .getTicker(ETH_USDT_PERP);
    System.out.println(ticker.toString());

    System.out.printf("Instrument %s:%n %s", ETH_USDT, exchange.getExchangeMetaData()
        .getInstruments().get(ETH_USDT));
    System.out.printf("Instrument %s:%n %s", ETH_USDT_PERP, exchange.getExchangeMetaData()
        .getInstruments().get(ETH_USDT_PERP));

    BigDecimal minAmountSpot = exchange.getExchangeMetaData().getInstruments().get(ETH_USDT)
        .getMinimumAmount();
        BigDecimal minUSDTSpot = minAmountSpot.multiply(ticker.getLast());
    //buy
    String marketSpotOrderId =
        exchange
            .getTradeService()
            .placeMarketOrder(
                new MarketOrder(OrderType.BID, minUSDTSpot, ETH_USDT));
    System.out.println("Market Spot order id: " + marketSpotOrderId);
    //sell
    marketSpotOrderId =
        exchange
            .getTradeService()
            .placeMarketOrder(
                new MarketOrder(OrderType.ASK, minAmountSpot, ETH_USDT));

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
    String limitFutureOrderId =
        exchange
            .getTradeService()
            .placeLimitOrder(
                new LimitOrder(OrderType.ASK, minAmountFuture, ETH_USDT_PERP, "123213", null,
                    ticker.getLast()));
    System.out.println("Limit Future order id: " + limitFutureOrderId);
  }

}

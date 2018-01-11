package org.knowm.xchange.test.binance;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.service.BinanceAccountService;
import org.knowm.xchange.binance.service.BinanceCancelOrderParams;
import org.knowm.xchange.binance.service.BinanceMarketDataService;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.utils.StreamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TradeServiceIntegration {

  static Logger LOG = LoggerFactory.getLogger(TradeServiceIntegration.class);

    static Exchange exchange;
    static BinanceTradeService tradeService;
    static BinanceMarketDataService marketService;
    static BinanceAccountService accountService;

    @BeforeClass
    public static void beforeClass() {
        exchange = ExchangeFactory.INSTANCE.createExchange(BinanceExchange.class.getName());
        marketService = (BinanceMarketDataService) exchange.getMarketDataService();
        accountService = (BinanceAccountService) exchange.getAccountService();
        tradeService = (BinanceTradeService) exchange.getTradeService();
    }

    @Before
    public void before() {
      Assume.assumeNotNull(exchange.getExchangeSpecification().getApiKey());
    }

    @Test
    public void openOrders() throws Exception {

        CurrencyPair pair = CurrencyPair.XRP_BTC;
        
        OpenOrders orders = tradeService.getOpenOrders(pair);
        LimitOrder order = orders.getOpenOrders().stream().collect(StreamUtils.singletonCollector());
        if (order != null) {
          System.out.println(order);
        }
    }

    @Test
    public void stopLossLimit() throws Exception {

        // Get EOS/ETH market price
        CurrencyPair pair = CurrencyPair.EOS_ETH;
        BigDecimal tickerPrice = marketService.tickerPrice(pair).getPrice();
        LOG.info("TickerPrice {}", String.format("%.6f", tickerPrice));

        // Set the StopLoss to 5% below the distro price
        BigDecimal stopPrice = new BigDecimal(String.format("%.6f", tickerPrice.doubleValue() * 0.95)); 
        LOG.info("StopLoss    {}", stopPrice);

        // Get the available EOS amount
        Balance balance = accountService.getAccountInfo().getWallet().getBalance(pair.base);
        LOG.info("Balance    {} {}", String.format("%.6f", balance.getAvailable()), balance.getCurrency());
        
        BigDecimal amount = new BigDecimal("10");
        Assume.assumeTrue(amount.compareTo(balance.getAvailable()) <= 0);
        
        // Place the StopLoss order
        LimitOrder limitOrder = new LimitOrder.Builder(OrderType.ASK, pair)
                .originalAmount(amount)
                .limitPrice(tickerPrice)
                .stopPrice(stopPrice)
                .build();
        
        String orderId = tradeService.placeStopLimitOrder(limitOrder);
        Assert.assertNotNull("New orderId", orderId);
        
        BinanceCancelOrderParams params = new BinanceCancelOrderParams(pair, orderId);
        Assert.assertTrue("Cancel order", tradeService.cancelOrder(params));
    }
}

package org.knowm.xchange.bitrue.service.trade;

import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.bitrue.BitrueExchange;
import org.knowm.xchange.bitrue.BitrueExchangeIntegration;
import org.knowm.xchange.bitrue.dto.trade.TimeInForce;
import org.knowm.xchange.bitrue.service.BitrueTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.StopOrder;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.knowm.xchange.bitrue.dto.trade.OrderType.*;
import static org.knowm.xchange.dto.Order.OrderType.BID;

public class TradeServiceIntegration extends BitrueExchangeIntegration {

  static BitrueTradeService tradeService;

  @BeforeClass
  public static void beforeClass() throws Exception {
    createExchange();
    tradeService = (BitrueTradeService) exchange.getTradeService();
  }

  @Before
  public void before() {
    Assume.assumeNotNull(exchange.getExchangeSpecification().getApiKey());
  }


  private LimitOrder sampleLimitOrder() throws IOException {
    final CurrencyPair currencyPair = CurrencyPair.BTC_USDT;
    final BigDecimal amount = BigDecimal.ONE;
    final BigDecimal limitPrice = limitPriceForCurrencyPair(currencyPair);
    return new LimitOrder.Builder(BID, currencyPair)
        .originalAmount(amount)
        .limitPrice(limitPrice)
        .flag(TimeInForce.GTC)
        .build();
  }

  private BigDecimal limitPriceForCurrencyPair(CurrencyPair currencyPair) throws IOException {
    return exchange
        .getMarketDataService()
        .getOrderBook(currencyPair)
        .getAsks()
        .get(0)
        .getLimitPrice();
  }

  private MarketOrder sampleMarketOrder() {
    final CurrencyPair currencyPair = CurrencyPair.BTC_USDT;
    final BigDecimal amount = BigDecimal.ONE;
    return new MarketOrder.Builder(BID, currencyPair).originalAmount(amount).build();
  }

  private StopOrder sampleStopLimitOrder() throws IOException {
    final CurrencyPair currencyPair = CurrencyPair.BTC_USDT;
    final BigDecimal amount = BigDecimal.ONE;
    final BigDecimal limitPrice = limitPriceForCurrencyPair(currencyPair);
    final BigDecimal stopPrice =
        limitPrice.multiply(new BigDecimal("0.9")).setScale(2, RoundingMode.HALF_UP);
    return new StopOrder.Builder(BID, currencyPair)
        .originalAmount(amount)
        .limitPrice(limitPrice)
        .stopPrice(stopPrice)
        .intention(StopOrder.Intention.STOP_LOSS)
        .flag(TimeInForce.GTC)
        .build();
  }



  private StopOrder sampleTakeProfitLimitOrder() throws IOException {
    final CurrencyPair currencyPair = CurrencyPair.BTC_USDT;
    final BigDecimal amount = BigDecimal.ONE;
    final BigDecimal limitPrice = limitPriceForCurrencyPair(currencyPair);
    final BigDecimal takeProfitPrice =
        limitPrice.multiply(new BigDecimal("1.1")).setScale(2, RoundingMode.HALF_UP);
    return new StopOrder.Builder(BID, currencyPair)
        .originalAmount(amount)
        .stopPrice(takeProfitPrice)
        .limitPrice(limitPrice)
        .intention(StopOrder.Intention.TAKE_PROFIT)
        .flag(TimeInForce.GTC)
        .build();
  }


}

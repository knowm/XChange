package org.knowm.xchange.gateio.examples;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.dto.marketdata.CandleStickData;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.gateio.GateioExchange;
import org.knowm.xchange.gateio.dto.trade.GateioOrderFlags;
import org.knowm.xchange.gateio.dto.trade.GateioTimeInForce;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.CandleStickDataParams;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParam;
import org.knowm.xchange.utils.AuthUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GateioSpotTest {
  private final Instrument currencyPair = CurrencyPair.ETH_USDT;
  public Exchange exchange;
  private final boolean logOutput = false;

  @Before
  public void before() throws InterruptedException {
    init();
  }

  @Test
  @Ignore
  public void placeOrder() throws IOException {
    //amount: Trading quantity When type is limit, it refers to the base currency (the currency being traded), such as BTC in BTC_USDT When type is market, it refers to different currencies based on the side:
    //    side: buy refers to quote currency, BTC_USDT means USDT
    //    side: sell refers to base currency, BTC_USDT means BTC
    MarketOrder marketOrder = new MarketOrder(Order.OrderType.BID, new BigDecimal("5"), currencyPair);
    Ticker ticker = exchange.getMarketDataService().getTicker(currencyPair);
    LimitOrder limitOrder = new LimitOrder.Builder(Order.OrderType.BID, currencyPair).limitPrice(ticker.getLow()).originalAmount(new BigDecimal("0.002"))
        .userReference(String.valueOf(System.currentTimeMillis())).build();
    limitOrder.addOrderFlag(new GateioOrderFlags(GateioTimeInForce.POC));
    String marketOrderId = exchange.getTradeService().placeMarketOrder(marketOrder);
    String limitOrderId = exchange.getTradeService().placeLimitOrder(limitOrder);
  }

  @Test
  @Ignore
  public void getTicker() throws IOException {
    Ticker ticker = exchange.getMarketDataService().getTicker(currencyPair);
    assertThat(ticker).isNotNull();
  }

  @Test
  @Ignore
  public void getTickers() throws IOException {
    List<Ticker> tickers = exchange.getMarketDataService().getTickers(null);
    assertThat(tickers).isNotNull();
    assertThat(tickers.get(0)).isNotNull();
  }

  @Test
  @Ignore
  public void candleStick() throws IOException {
    CandleStickDataParams params = new DefaultCandleStickParam(new Date(System.currentTimeMillis() - 86400000 * 4), new Date(), 86400);
    CandleStickData candleStickData = exchange.getMarketDataService().getCandleStickData(currencyPair, params);
    assertThat(candleStickData).isNotNull();
    assertThat(candleStickData.getCandleSticks()).isNotEmpty();
    if (logOutput)
      candleStickData.getCandleSticks().forEach(System.out::println);
  }

  @Test
  @Ignore
  public void getFees() throws IOException {
    Map<Instrument, Fee> fees = exchange.getAccountService().getDynamicTradingFeesByInstrument("SPOT");
    assertThat(fees).isNotEmpty();
    fees.forEach((instrument, fee) -> {
      assertThat(instrument).isNotNull();
      assertThat(fee.getMakerFee()).isNotNull();
      assertThat(fee.getTakerFee()).isNotNull();
      if (logOutput)
        System.out.println("Instrument: " + instrument + ", Maker Fee: " + fee.getMakerFee() + ", Taker Fee: " + fee.getTakerFee());
    });
  }

  private void init() {
    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(GateioExchange.class);
    AuthUtils.setApiAndSecretKey(exchangeSpecification, "gateio-main");
    exchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
  }
}

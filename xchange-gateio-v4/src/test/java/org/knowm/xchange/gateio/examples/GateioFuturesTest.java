package org.knowm.xchange.gateio.examples;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.dto.marketdata.CandleStickData;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.gateio.GateioExchange;
import org.knowm.xchange.gateio.dto.marketdata.GateioFundingRateHistory;
import org.knowm.xchange.gateio.dto.trade.GateioCancelOrderParams;
import org.knowm.xchange.gateio.dto.trade.GateioOrderFlags;
import org.knowm.xchange.gateio.dto.trade.GateioTimeInForce;
import org.knowm.xchange.gateio.service.GateioMarketDataService;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.CandleStickDataParams;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParamWithLimit;
import org.knowm.xchange.utils.AuthUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.knowm.xchange.gateio.GateioExchange.EXCHANGE_TYPE;
import static org.knowm.xchange.gateio.dto.GateioExchangeType.FUTURES;

@Slf4j
public class GateioFuturesTest {
  private final Instrument instrument = new FuturesContract("T/USDT/PERP");
  public Exchange exchange;
  private final boolean logOutput = true;

  @Before
  public void before() {
    init();
  }


  @Test
  @Ignore
  public void order() throws IOException {
    String userReference = "t-" + System.currentTimeMillis();
    MarketOrder marketOrder = new MarketOrder.Builder(Order.OrderType.ASK, instrument).originalAmount(new BigDecimal("0.001")).build();
    Ticker ticker = exchange.getMarketDataService().getTicker(instrument);
    LimitOrder limitOrder = new LimitOrder.Builder(Order.OrderType.BID, instrument).limitPrice(ticker.getLow()).originalAmount(new BigDecimal("0.001"))
        .userReference(userReference).build();
    limitOrder.addOrderFlag(new GateioOrderFlags(GateioTimeInForce.POC));
//    String marketOrderId = exchange.getTradeService().placeMarketOrder(marketOrder);
    String limitOrderId = exchange.getTradeService().placeLimitOrder(limitOrder);
    LimitOrder limitOrder1 = new LimitOrder.Builder(Order.OrderType.BID, instrument).limitPrice(ticker.getLow().add(BigDecimal.ONE)).originalAmount(new BigDecimal("0.001"))
        .userReference(userReference).build();
//    exchange.getTradeService().changeOrder(limitOrder1);
//    DefaultCancelOrderByInstrumentAndIdParams params = new DefaultCancelOrderByInstrumentAndIdParams(instrument, limitOrderId);
    GateioCancelOrderParams params = new GateioCancelOrderParams(null, instrument, userReference);
    exchange.getTradeService().cancelOrder(params);
  }

  @Test
  @Ignore
  public void candleStick() throws IOException {
    CandleStickDataParams params = new DefaultCandleStickParamWithLimit(new Date(System.currentTimeMillis() - 86400000 * 4), new Date(), 86400, 2);
    CandleStickData candleStickData = exchange.getMarketDataService().getCandleStickData(instrument, params);
    assertThat(candleStickData).isNotNull();
    assertThat(candleStickData.getCandleSticks()).isNotEmpty();
    if (logOutput)
      candleStickData.getCandleSticks().forEach(System.out::println);
  }

  @Test
  @Ignore
  public void getTicker() throws IOException {
    Ticker ticker = exchange.getMarketDataService().getTicker(instrument);
    assertThat(ticker).isNotNull();
    assertThat(ticker.getInstrument()).isEqualTo(instrument);
    assertThat(ticker.getLast()).isGreaterThan(java.math.BigDecimal.ZERO);
  }

  @Test
  @Ignore
  public void getTickers() throws IOException {
    List<Ticker> tickers = exchange.getMarketDataService().getTickers(null);
    assertThat(tickers).isNotEmpty();
    assertThat(tickers).allSatisfy(ticker -> {
      assertThat(ticker.getInstrument()).isNotNull();
      assertThat(ticker.getLast()).isNotNull();
    });
  }

  @Test
  @Ignore
  public void setLeverage() throws IOException {
    exchange.getAccountService().setLeverage(instrument, 1);
    exchange.getAccountService().setLeverage(instrument, 1, 1);
  }

  @Test
  @Ignore
  public void getFees() throws IOException {
    Map<Instrument, Fee> fees = exchange.getAccountService().getDynamicTradingFeesByInstrument("FUTURES");
    assertThat(fees).isNotNull();
    fees.forEach((instrument, fee) -> {
      assertThat(instrument).isNotNull();
      assertThat(fee).isNotNull();
      assertThat(fee.getMakerFee()).isNotNull();
      assertThat(fee.getTakerFee()).isNotNull();
      if (logOutput)
        System.out.println("Instrument: " + instrument + ", Maker Fee: " + fee.getMakerFee() + ", Taker Fee: " + fee.getTakerFee());
    });
  }

  @Test
  @Ignore
  public void getFundingRateHistory() throws IOException {
    Long from =
        (System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 30) / 1000;
    List<GateioFundingRateHistory> fundingRateHistory = ((GateioMarketDataService) exchange.getMarketDataService()).getFundingRateHistory(instrument, from, (System.currentTimeMillis()) / 1000, 100);
    if (logOutput)
      log.info("Instrument: {}, fundingRateHistory size {}, fundingRateHistory  {}", instrument, fundingRateHistory.size(), fundingRateHistory);
  }

  private void init() {
    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(GateioExchange.class);
    exchangeSpecification.setExchangeSpecificParametersItem(EXCHANGE_TYPE, FUTURES);
    AuthUtils.setApiAndSecretKey(exchangeSpecification, "gateio-main");
    exchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
  }
}
